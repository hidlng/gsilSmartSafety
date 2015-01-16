package com.smart.safety.controller;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import javax.validation.*;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.servlet.mvc.support.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.google.android.gcm.server.*;
import com.smart.safety.domain.*;
import com.smart.safety.services.*;
import com.smart.safety.util.*;



@Controller(value="WorkController")
public class WorkController {
	private static final Logger logger = LoggerFactory.getLogger(WorkController.class);
	
	@Resource(name="WorkService")
	WorkService workService;
		
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;

	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	
	
	
	@RequestMapping(value = "workList")
	public void workList(Model model, @RequestParam(value="pageNum", defaultValue="1")int pageNum, 
				@RequestParam(value="searchWord", defaultValue="")String searchWord ,
				HttpSession session) {
			//keyword 생성
		
		
			//검색조건설정
			UserVO userVO = (UserVO)session.getAttribute("userLoginInfo");
			SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");			
			
			String keyword = "%" + searchWord + "%";
			WorkVO workVO = new WorkVO();
			workVO.setWorktype(keyword);
			workVO.setWorktitle(keyword);
			workVO.setUsername(keyword);
			
			if(userVO.getLevel() == USERLEVEL.SS_MANAGER.idx ||
					userVO.getLevel() == USERLEVEL.EHS_MANAGER.idx || 
					userVO.getLevel() == USERLEVEL.CEO.idx) //SS관리자, EHS , CEO 의 경우 모든 현장 볼수 있도록 
				workVO.setSite_idx("%");
			else if(siteVO != null)
				workVO.setSite_idx(siteVO.getSite_idx());
			else {
				//TODO:excpetion
			}
			
			
			
			//Paging처리			
			int rowCnt = workService.getRowCount(workVO);
		    Paging paging = new Paging(pageNum, rowCnt, MAX_ROW_NUM, MAX_PAGE_NUM);
		    paging.makePaging();
		    workVO.setStart((pageNum - 1) * MAX_ROW_NUM);
		    workVO.setSize(MAX_ROW_NUM);		
			
			//model setting
			model.addAttribute("searchWord",searchWord);
		    model.addAttribute("paging",paging);
		     
			List<WorkVO> list = workService.getWorkListByVO(workVO);
			model.addAttribute("workList", list);
			session.setAttribute("contentView", "workList");
			
		
		}
	
	@RequestMapping(value = "registerWork")
	public void registerWork(@RequestParam(value="updateIdx",required=false)String updateIdx, HttpServletRequest request, Model model, HttpSession session) {
		model.addAttribute("isNotValid", false);
		SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");	
		setBaseModel(siteVO, model);
		
		UserVO userVO = (UserVO)session.getAttribute("userLoginInfo");
		
		if(updateIdx != null && !updateIdx.equals("")) {
			WorkVO workVO = workService.getWorkByIdx(updateIdx);
			//contname은 따로저장하지않으므로
			ContractorVO contractorVO = contractorService.getContractorByIdx(workVO.getCont_idx());
			workVO.setCont_name(contractorVO.getCont_name());
			
			model.addAttribute("updateMode", true);
			model.addAttribute("workVO", workVO);
		}
		else {//insertMode
			//입력시점에 최신데이터를 가져옴
			//작업등록자의 현장정보, 업체정보, 유저정보 필요
			
		
			WorkVO workVO = new WorkVO();
			workVO.setToollist(new ArrayList<ToolVO>());
			
			if(userVO == null || siteVO == null) {
				//error 처리
			}else {
				workVO.setWrite_user_idx(userVO.getUser_idx());
				workVO.setSite_idx(siteVO.getSite_idx());
				
				
				//업체에 경우 현장사용자는 직접 타이핑 입력이기때문에
				//통일 성을 위해 둘다 이름값만 입력함(cont_idx 사용 X)
				if(userVO.getLevel() == USERLEVEL.CONT_CHEIF.idx || userVO.getLevel() == USERLEVEL.CONT_LEADER.idx || userVO.getLevel() == USERLEVEL.CONT_INSPECTOR.idx) {//현장사용자
					ManagerVO managerVO = (ManagerVO)session.getAttribute("managerVO");
					ContractorVO contractorVO = contractorService.getContractorByIdx(managerVO.getCont_idx());	
					workVO.setCont_idx(managerVO.getCont_idx());
					workVO.setCont_name(contractorVO.getCont_name());
				}else if(userVO.getLevel() == USERLEVEL.CONTRACTOR.idx) {//업체
					ContractorVO contractorVO = (ContractorVO)session.getAttribute("contractorVO");
					workVO.setCont_idx(contractorVO.getCont_idx());
					workVO.setCont_name(contractorVO.getCont_name());
				}else {
					//error 처리
					workVO.setCont_name("소속없음");
				}
				
			}
			model.addAttribute("updateMode", false);
			model.addAttribute("workVO", workVO);
		}//else
		
	}
	
	
	
	private void setBaseModel(SiteVO siteVO, Model model) {	
		//업체리스트 호출 , 작업책임자 소속에서 사용		
		List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx()); 		
		model.addAttribute("contList", contList);
		
		//감독자리스트 호출 , 작업책임자 소속에서 사용		
		List<ManagerVO> managerList = managerSerivce.getManagerListByLevel(siteVO.getSite_idx(), USERLEVEL.CONT_INSPECTOR); 		
		model.addAttribute("managerList", managerList);
		
	}

	@RequestMapping(value = "insertWork", method = RequestMethod.POST)
	public String insertWork(HttpSession session, @ModelAttribute @Valid WorkVO workVO
			, BindingResult bindingResult, Model model, RedirectAttributes redirectAttr) {
		String work_idx;
		
		SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");	
		setBaseModel(siteVO, model);
		
		/**error발생시 이전 form으로 되돌아갈때 사용하기 위함**/
		arrayFilter(workVO.getToollist()); //빈공간 제거		
		model.addAttribute("workVO", workVO);
		model.addAttribute("updateMode", false);
		
		if(bindingResult.hasErrors()) {
			//업체리스트 호출 , 작업책임자 소속에서 사용		
			List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx()); 		
			model.addAttribute("contList", contList);
			model.addAttribute("isNotValid", true);
			return "registerWork";
		}
		
		else {
			try{
				setRiskData(workVO);
				work_idx = workService.insertWork(workVO);
				
			}catch(Exception e) {//초기화
				e.printStackTrace();
				return "redirect:registerWork";
			}
			
			//return "redirect:workList";
			
		pushWorkToUser(workVO);
			
			
			
			
			//redirectAttr.addFlashAttribute("work_idx", work_idx);
			//return "redirect:printList";
			return "redirect:viewWork?viewIdx=" + workVO.getWork_idx();
		}
		
	}
	
	private void pushWorkToUser(WorkVO workVO) {
		try {
			//Manager 전달 (감독자제외 전부)
			List<ManagerVO> manList = managerSerivce.getManagerListBySiteIdx(workVO.getSite_idx());
			
			Iterator<ManagerVO> it_man = manList.iterator();			
			while(it_man.hasNext()) {
				ManagerVO manVO = it_man.next();
				int level = manVO.getLevel();
				if(level == USERLEVEL.SITE_MANAGER.idx || level == USERLEVEL.CONT_CHEIF.idx || level == USERLEVEL.CONT_LEADER.idx) 
					if(manVO.getPid() != null && !manVO.getPid().equals("")){
						sendMessage(manVO.getPid());	
						System.out.println("push 알림 전달 [ " + manVO.getId() +"]");
				}
			}
			
			//Contractor 전달
			List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(workVO.getSite_idx());
			
			Iterator<ContractorVO> it_cont = contList.iterator();			
			while(it_cont.hasNext()) {
				ContractorVO contVO = it_cont.next();
				sendMessage(contVO.getPid());	
				System.out.println("push 알림 전달 [ " + contVO.getId() +"]");
			}
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**push**/
	public void sendMessage(String regId) throws IOException {
		Sender sender = new Sender("AIzaSyBQNLUyd80UKgvloLjeUg3FUYRHNCRKtjU");
		//String regId = "APA91bHKzAacDO86UqeCntFzUck6bf8RcVyiDDJo4uvcYSJzErpGkLWNBKAZLArm3G0lpLllxp1mHfK4__SKytzqLtXh9sRkH66tmI9Fs5h1JO_eIP8qaVryYsSeCY3TRdleBgbSn9G06_625NAiDdVrDKbkVU_HEaSkyca01lSUt3ts4dz_Dwg";
		Message message = new Message.Builder().addData("msg", URLEncoder.encode("점검해야할 작업이 있습니다","UTF-8")).build();
		List<String> list = new ArrayList<String>();
		list.add(regId);
		
		MulticastResult multiResult = sender.send(message, list, 5);
		if (multiResult != null) {
			List<Result> resultList = multiResult.getResults();
			for (Result result : resultList) {
				System.out.println(result.getMessageId());
			}
		}
		
		
	}
	
	
	@RequestMapping(value = "updateWork", method = RequestMethod.POST)
	public String updateWork(HttpSession session, @ModelAttribute @Valid WorkVO workVO, BindingResult bindingResult,
		Model model, RedirectAttributes redirectAttr) {
		
		SiteVO siteVO = (SiteVO) session.getAttribute("siteVO");	
		setBaseModel(siteVO, model);
		
		model.addAttribute("updateMode", true);
		arrayFilter(workVO.getToollist()); //빈공간 제거
		model.addAttribute("workVO", workVO);
		
		if(bindingResult.hasErrors()) { 
						
			//업체리스트 호출 , 작업책임자 소속에서 사용		
			List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx()); 		
			model.addAttribute("contList", contList);
			model.addAttribute("isNotValid", true);
			return "registerWork";
		}
		
		else {
			try{
				setRiskData(workVO);
				workService.updateWork(workVO);
			}catch(Exception e) {//초기
				e.printStackTrace();
				return "redirect:registerWork";
			}
			
			//redirectAttr.addFlashAttribute("work_idx",workVO.getWork_idx());
			//return "redirect:workList";
			return "redirect:viewWork?viewIdx=" + workVO.getWork_idx();
		}
		
	}
	
	public String getRiskData(String list) {
		RestTemplate restTemplate = new RestTemplate();
		String url = PrintController.RISK_DATA_URL + list;
		String result = restTemplate.getForObject(url, String.class);
		String json_result = result.substring(result.indexOf('(') + 1, result.length() - 1);
		return json_result;
				
	}
	
	
	/**작업코드(심각성,위험도) + 작업자수 + 난이도(소장판단) 전달 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException **/ 
	private void setRiskData(WorkVO workVO) throws JsonParseException, JsonMappingException, IOException {

		StringBuffer buf = new StringBuffer();
		
		buf.append(workVO.getWorkcode());
		buf.append('!');
		buf.append(workVO.getPic_num_worker());
		buf.append('!');
		buf.append(workVO.getWorklevel());
		buf.append('!');

		String json_result = getRiskData(buf.toString());
		System.out.println(json_result);
		
		ObjectMapper objectMapper = new ObjectMapper();
		if(json_result != null) {
			Map<String, Object> jsonMap = null;
			jsonMap = objectMapper.readValue(json_result, Map.class);
			String riskGrade = (String) jsonMap.get("riskgrade");
			int risklevel = (Integer)jsonMap.get("risklevel");
			workVO.setRisk_grade(riskGrade);//RiskGrade
			workVO.setRisk_level(risklevel);//RiskGrade 
		}
		
		workVO.setRisk_warn("1");//RiskWarn
		workVO.setWorkpermit("N");//WorkPermit
		
	}

	private void arrayFilter(List<ToolVO> toollist) {
		if(toollist == null) return;  
		Iterator<ToolVO> it = toollist.iterator();
		while (it.hasNext()) {
			ToolVO vo = it.next();
			if(vo.getToolcode() == null || vo.getToolname() == null)
				it.remove();
		}
	}

	

	/**
	 * 
	 * @param viewIdx -> workIdx
	 * @param request
	 * @param model
	 * @param session
	 */
	@RequestMapping(value = "viewWork")
	public void viewWork(@RequestParam(value="viewIdx",required=false)String viewIdx, @RequestParam(value="fromCEO",required=false)boolean fromCEO,
			HttpServletRequest request, Model model, HttpSession session) {
		if(viewIdx != null && !viewIdx.equals("")) {
			model.addAttribute("updateMode", true);//null값 방지용	
			model.addAttribute("fromCEO", fromCEO);//ceo화면에서 넘어온 경우 체크하여 jsp에반영
			
			WorkVO workVO = workService.getWorkByIdx(viewIdx);
			UserVO userVO = (UserVO)session.getAttribute("userLoginInfo");

			//remark의 경우 줄바꿈이 안되어서 나타나므로 다음작업 수행
			workVO.setRemark(workVO.getRemark().replace("\r\n", "<br>"));//		 remark
			model.addAttribute("workVO", workVO);
			
			
			
			/**수정권한 체크 - 대상 : 작성자 or 작업 관련 업체 사람 or 현장관리자**/
			USERLEVEL userlevel = USERLEVEL.get(userVO.getLevel());
			
			boolean canModify = false;
			
			switch(userlevel) {
			case SS_MANAGER:case EHS_MANAGER:case CEO:break;
			case SITE_MANAGER ://자신의 현장의 모든 작업 수정 가능
				canModify = true;
				break;
			case CONT_CHEIF: case CONT_LEADER : //자신현장에 대해 모두 수정가능
				//ManagerVO managerVO = (ManagerVO)session.getAttribute("managerVO");
				//if(managerVO != null && workVO.getCont_idx().equals(managerVO.getCont_idx()))
				canModify = true;
				break;
			case CONT_INSPECTOR : break;
			case CONTRACTOR://자신업체에 대해 수정가능
				ContractorVO contractorVO = (ContractorVO)session.getAttribute("contractorVO");
				if(contractorVO != null && workVO.getCont_idx().equals(contractorVO.getCont_idx()))
					canModify = true;
				break;
			}
			
			//수정권한 체크 작업의 업체idx와 UserSession의 소속현장 idx를 비교 
			model.addAttribute("canModify", canModify);
		}
	}
	
	@RequestMapping("workPopup")
	public void workPopup(HttpSession session) {
		
	}

	
	
}
