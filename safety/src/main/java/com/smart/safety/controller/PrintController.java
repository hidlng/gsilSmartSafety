package com.smart.safety.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.*;

import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.support.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.smart.safety.domain.*;
import com.smart.safety.services.*;


@Controller(value="PrintController")
public class PrintController {
	private static final Logger logger = LoggerFactory.getLogger(PrintController.class);
	
	@Resource(name="WorkService")
	WorkService workService;
		
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;

	@Resource(name="PrintService")
	private PrintService printService;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;

	
	/**
	waitPermit 작업승인 대기상태
	printList -> TBM, PUI, PTW , 사고사례 출력 선택
	
	- getDetailByJSON =>  workcode   => return workVO
	-  getDetailByJSON => toolcodeList => return List<ToolVO>
	- getDetailByJSON =>  placeode => return placeVO
	- getRiskGrade&PertmitWork(작업허가)? => codeList 전달 =>return {riskGrade, permitWork}
	(-  getAccExam => codeList전달 => url 반환 = > 위에서 전달받은걸로 해결 가능)	
	
	printTBM		
	printPUI
	printPTW
	getAccExam(url링크 걸린 리스트 )
	
	**/
	
	
	@RequestMapping("printList")
	public void printList(Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttr) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		String work_idx = (String) flashMap.get("work_idx");
		
		
		//String tbm_idx = printService.insertTBM(tbmVO);
		//String pui_idx = printService.insertTBM(tbmVO);
		//String ptw_idx = printService.insertTBM(tbmVO);
		String ptw_idx="";
		String pui_idx="";
		
		
		model.addAttribute("work_idx", work_idx);
//		model.addAttribute("tbm_idx", tbm_idx);
//		model.addAttribute("ptw_idx", ptw_idx);
//		model.addAttribute("pui_idx", pui_idx);
		
		//return jsonMap != null ? (String) jsonMap.get("url") : null;
	}
	
	@RequestMapping(value = "tbm")
	public void tbm(Model model, HttpSession session, HttpServletRequest request) {
		String work_idx = request.getParameter("work_idx");
//		String tbm_idx = request.getParameter("tbm_idx");
//		TBMVO tbmVO = printService.getTBMByTBMIdx(tbm_idx);

		TBMVO tbmVO = makeTBM(work_idx);
		model.addAttribute("tbmVO", tbmVO);

	}
	
	public String getDetailByJSON(String code, int type) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getDetailByJSON=&code="+code+"&type="+type;
		String result = restTemplate.getForObject(url, String.class);
		String json_result = result.substring(result.indexOf('(') + 1, result.length() - 1);
		return json_result;
				
	}

	private TBMVO makeTBM(String work_idx) {		
		ObjectMapper objectMapper = new ObjectMapper();		
		TBMVO tbmVO = new TBMVO();
		//workVO 가져오기
		WorkVO workVO = workService.getWorkByIdx(work_idx);
		SiteVO siteVO = siteService.getSiteByIdx(workVO.getSite_idx());
		//ContractorVO contVO = contractorService.getContractorByIdx(workVO.getCont_name())
		
		//tbmVO.setTbm_idx(tbm_idx);
		tbmVO.setWork_idx(workVO.getWork_idx());//		 work_idx	
		tbmVO.setSitename(workVO.getSitename());//		 sitename
		tbmVO.setPrinttime("");//		 printtime
		tbmVO.setCont_name(workVO.getCont_name());//		 cont_name
		//cont_rep_name
		//tbmVO.setCont_phone(workVO.getcon);//		 cont_phone
		//tbmVO.setCont_phone(workVO.ge);//		 cont_rep_phone
		//cont_emg_phone
		tbmVO.setWorktitle(workVO.getWorktitle());//		 worktitle
		tbmVO.setPic_name(workVO.getPic_name());//		 pic_name
		tbmVO.setPic_phone(workVO.getPic_phone());//		 pic_phone
		tbmVO.setPic_num_worker(workVO.getPic_num_worker());//		 pic_num_worker
		tbmVO.setStartdate(workVO.getStartdate());//		 startdate
		tbmVO.setStarttime(workVO.getStarttime());//		 starttime
		tbmVO.setEnddate(workVO.getEnddate());//		 enddate
		tbmVO.setEndtime(workVO.getEndtime());//		 endtime
	
		tbmVO.setToollist(makeToolJSON(workVO.getToollist()));	//		 toollist
		//		 mainrisk
		//		 weather
		
		tbmVO.setWorkname(workVO.getWorkname());//		 workname
		tbmVO.setPlacename(workVO.getPlacename());//	 placename
		
		//		 risk_grade
		//		 risk_warn
		//		 workpermit
		
		//		 measure
		//		 equip
		//		 guide
		try {
			setWorkTBM(workVO, tbmVO);		
			setToolTBM(workVO, tbmVO);
//			setPlaceTBM(workVO, tbmVO);
		} catch (JsonParseException e) {e.printStackTrace();
		} catch (JsonMappingException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		
		
		
		tbmVO.setRemark(workVO.getRemark());//		 remark
		tbmVO.setSitename(workVO.getSitename());
		
		
		tbmVO.setSite_rep_name(siteVO.getRep_name());
		tbmVO.setSite_rep_phone(siteVO.getRep_phone());
		//		 writetime
		//		 delyn
		//1:work 2:tool 3:place
		
		
		
		return tbmVO;
		//String json_result = getDetailByJSON(workVO.getPlacecode(), 3);		return null;

	}

	private String makeToolJSON(List<ToolVO> toollist) {	
		JSONObject jsonObject = new JSONObject(); // JSONObject 생성
		JSONArray array = new JSONArray();
		
		for(ToolVO toolVO : toollist) {
			array.put(toolVO.getToolname());
		}
		return jsonObject.put("toollist", array).toString();		
	}

	
	private void setWorkTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json_result = getDetailByJSON(workVO.getWorkcode(), 1);
		if(json_result != null) {
			Map<String, Object> jsonMap = null;
			jsonMap = objectMapper.readValue(json_result, Map.class);
			Map<String,String>work = (Map<String, String>) jsonMap.get("workVO");
			
			tbmVO.setMeasure(tbmVO.getMeasure() + "\r\n" + (String) work.get("measure"));
			tbmVO.setEquip(tbmVO.getEquip() + "\r\n" + (String) work.get("equip"));
			tbmVO.setGuide(tbmVO.getGuide() + "\r\n" + (String) work.get("guide"));
			System.out.println(work.get("equip"));
		}
		
	}
	
	private void setToolTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ToolVO> toollist = workVO.getToollist();
		for(ToolVO vo : toollist) {
			if(vo.getTooltype() == 99) continue;
			
			String json_result = getDetailByJSON(vo.getToolcode(), 2);
			if(json_result != null) {
			Map<String, Object> jsonMap = null;
			
				System.out.println(json_result.toString());
				jsonMap = objectMapper.readValue(json_result, Map.class);
				Map<String,String>work = (Map<String, String>) jsonMap.get("toolVO");
				
				//기존에 추가된 내용에 더함
				tbmVO.setMainrisk(tbmVO.getMainrisk() + "\r\n" + (String) work.get("mainRisk"));
				tbmVO.setEquip(tbmVO.getEquip() + "\r\n" + (String) work.get("equip"));
				tbmVO.setGuide(tbmVO.getGuide() + "\r\n" + (String) work.get("guide"));
				System.out.println(work.get("equip"));
			}
		}
	}
	
//	private void setToolPUI(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
//		List<ToolVO> toollist = workVO.getToollist();
//		for(ToolVO vo : toollist) {
//			if(vo.getTooltype() == 99) continue;
//			
//			String json_result = getDetailByJSON(vo.getToolcode(), 2);
//			if(json_result != null) {
//			Map<String, Object> jsonMap = null;
//			
//				System.out.println(json_result.toString());
//				jsonMap = objectMapper.readValue(json_result, Map.class);
//				List<Map<String, Object>> checkList = (List<Map<String, Object>>) jsonMap.get("checkList");
//					for (int i = 0; i < checkList.size(); i++) {
//						Map<String, Object> checkVO = (Map<String, Object>) checkList
//								.get(i);
//						String test = (String) (checkVO.get("check_idx") + "");// integer
//						
//						System.out.println(test);
//					}
//			}
//		}
//	}
	

	private void setPlace(WorkVO workVO, TBMVO tbmVO) {
		String json_result = getDetailByJSON(workVO.getWork_idx(), 3);
		
	}
}
