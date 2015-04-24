package com.smart.safety.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import javax.validation.*;

import org.slf4j.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
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
			
			if(contractorVO == null){//업체가 없는경우 -> 삭제된 업체 소속의 사용자로 접속한 경우..
				model.addAttribute("hasNoContractor", true);
			}else{
				workVO.setCont_name(contractorVO.getCont_name());
			}
			
			parse_placecodes(workVO);//workVO의 선택한 placecodes를 parse하여 array형태로 parse_placecodes에 삽입
			
			model.addAttribute("hasNoContractor", false);
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
				
				
				//업체의 경우 현장사용자는 직접 타이핑 입력이기때문에
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
					model.addAttribute("hasNoContractor", true);
				}
				
			}
			
			model.addAttribute("hasNoContractor", false);
			model.addAttribute("updateMode", false);
			model.addAttribute("workVO", workVO);
		}//else
		
	}
	
	
	
	private void parse_placecodes(WorkVO workVO) {
		//place
		StringTokenizer stk = new StringTokenizer(workVO.getPlacecodes() , "|");
		while(stk.hasMoreElements()) {
			workVO.getParse_placecodes().add((String) stk.nextElement());
		}
			
		
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
			//장소유형 선택 리스트 보존
			workVO.setParse_placecodes(workVO.getInput_placecodes());
			
			//업체리스트 호출 , 작업책임자 소속에서 사용		
			List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx()); 		
			model.addAttribute("contList", contList);
			model.addAttribute("isNotValid", true);
			model.addAttribute("hasNoContractor", false);
			return "registerWork";
		}
		
		else {
			try{
				setPlaceCodeAndName(workVO);
				setRiskData(workVO);
				checkPrintExist(workVO); //PTW, PUI가 존재하는지 여부 판단
				work_idx = workService.insertWork(workVO);
				
			}catch(Exception e) {//초기화
				e.printStackTrace();
				return "redirect:registerWork";
			}
			
			//return "redirect:workList";
			
			pushWorkToSiteUser(workVO.getSite_idx(), "작업 등록 완료"); // -스케쥴러에서해결..현재는 확인용으로 풀어놓음

			return "redirect:viewWork?viewIdx=" + workVO.getWork_idx();
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
			//장소유형 선택 리스트 보존
			workVO.setParse_placecodes(workVO.getInput_placecodes());
			
			
			//업체리스트 호출 , 작업책임자 소속에서 사용		
			List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(siteVO.getSite_idx()); 		
			model.addAttribute("contList", contList);
			model.addAttribute("isNotValid", true);
			model.addAttribute("hasNoContractor", false);
			return "registerWork";
		}
		
		else {
			try{
				setPlaceCodeAndName(workVO);
				setRiskData(workVO);
				checkPrintExist(workVO); //PTW, PUI가 존재하는지 여부 판단
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
	
	private void checkPrintExist(WorkVO workVO) {
		//PTW체크
		/**특수한 작업+장소의 경우 별도의 체크사항이 필요함**/		
		
		try {
			Map<String, Object> jsonMap = RequestRiskMatrix.INSTANCE.getPermitList(workVO.getWorkcode(), workVO.getPlacecodes());
			@SuppressWarnings("unchecked")
			ArrayList<Map<String,String>>permitList = (ArrayList<Map<String,String>>) jsonMap.get("permitList");
			if(permitList.size() > 0 )workVO.setPtw_exist("Y");
			else workVO.setPtw_exist("N");
		} catch (JsonParseException e) {e.printStackTrace();} catch (JsonMappingException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();	}
		
		
		//PUI체크
		boolean hasValidTool = false; 
		if(workVO.getToollist() != null) {
			for(ToolVO vo : workVO.getToollist()) {
				int type = vo.getTooltype();
				if( type != 98 && type != 99) {//수기 입력아닌것만 체크하여 하나라도 있으면 PUI 존재. RiskMatrix에 코드등록후 내용 등록안된경우 PUI안나타날수있으니 참고
					hasValidTool = true;
				}
			}
		}
		if(hasValidTool) workVO.setPui_exist("Y");
		else workVO.setPui_exist("N");
	}
	
	private void setPlaceCodeAndName(WorkVO workVO) {
		
		//code : code|code|
		StringBuffer stb_code = new StringBuffer();
		StringBuffer stb_name = new StringBuffer();
		List<String> inputCodeList = workVO.getInput_placecodes();
		List<String> inputNameList = workVO.getInput_placenames();
		
		for (int i = 0; i < inputCodeList.size() ; i++ ) {
			String placecode = inputCodeList.get(i);
			if(placecode != null && !placecode.equals("") && !placecode.equals("null")) {
				stb_code.append(placecode).append("|");
				stb_name.append(inputNameList.get(i)).append(", ");
			}
		}
		
		workVO.setPlacecodes(stb_code.toString());
		
		String placename = stb_name.toString();
		if(placename.lastIndexOf(',') != -1)
			workVO.setPlacenames(placename.substring(0, placename.lastIndexOf(',')));
		
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

		String json_result = RequestRiskMatrix.INSTANCE.getRiskData(buf.toString());
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
		
		workVO.setRisk_warn("해당없음");//RiskWarn
		
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
	public String viewWork(@RequestParam(value="viewIdx",required=false)String viewIdx, @RequestParam(value="fromCEO",required=false)boolean fromCEO,
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
			
			return "viewWork";
		}else {
			return "redirect:workList";
		}
	}
	
	@RequestMapping("workPopup")
	public void workPopup(HttpSession session) {
		
	}

	public void pushWorkToSiteUser(String site_idx, String message) {
		//String message = "신규 작업 등록 ";
		try {
			List<ManagerVO> manList = managerSerivce.getManagerListBySiteIdx(site_idx);
			
			Iterator<ManagerVO> it_man = manList.iterator();			
			while(it_man.hasNext()) {
				ManagerVO manVO = it_man.next();
				int level = manVO.getLevel();
				if(level == USERLEVEL.SITE_MANAGER.idx || level == USERLEVEL.CONT_CHEIF.idx || level == USERLEVEL.CONT_LEADER.idx) 
					if(manVO.getPid() != null && !manVO.getPid().equals("")){
						GCMPusher.INSTANCE.sendMessage(manVO.getPid(), message);
				}
			}
			
			//Contractor 전달
			/*List<ContractorVO> contList = contractorService.getContractorListBySiteIdx(site_idx);
			
			Iterator<ContractorVO> it_cont = contList.iterator();			
			while(it_cont.hasNext()) {
				ContractorVO contVO = it_cont.next();
				GCMPusher.INSTANCE.sendMessage(contVO.getPid(), message);	
				System.out.println("push 알림 전달 [ " + contVO.getId() +"]");
			}*/
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**당일 발송 , 주말 제외 **/
	  @Scheduled(cron = "3 0 9 * * MON-FRI")
	  public void pushWorkToday(){
		  Date d = new Date();
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		  String curdate = sdf.format(d); //현재날짜 string으로 
		  List<String> siteIdxList = workService.getSiteIdxListByDate(curdate);
		  
		  for(String site_idx : siteIdxList) {
			 pushWorkToSiteUser(site_idx, "오늘 점검할 작업이 있습니다.");
		  }
		  
	  }
	  
	  
	  /**전날 발송, 주말 제외 **/	  
	  @Scheduled(cron = "4 0 9 * * SUN-THU")
	  public void pushWorkBefore(){
		Date d = new Date();

		long longtime = d.getTime();
		d = new Date(longtime + 1000 * 60 * 60 * 24);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String tomdate = sdf.format(d); 
		
		List<String> siteIdxList = workService.getSiteIdxListByDate(tomdate);

		for (String site_idx : siteIdxList) {
			pushWorkToSiteUser(site_idx, "내일 점검할 작업이 있습니다");
		  }
		  
		  
	  }
	
	
	
}
