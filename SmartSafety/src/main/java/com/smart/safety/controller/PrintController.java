package com.smart.safety.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.annotation.*;
import javax.servlet.http.*;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.support.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.smart.safety.domain.*;
import com.smart.safety.services.*;


@Controller(value="PrintController")
public class PrintController {
	private static final Logger logger = LoggerFactory.getLogger(PrintController.class);
	
	public static final String RISK_DATA_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getRiskData=&codelist=";
	public static final String CODE_DETAIL_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getDetailByJSON=";
	public static final String CHECK_IMG_URL = "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getChekcListImage=&filename=";
		
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
	public void printList(Model model,String work_idx) {
		//Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		//String work_idx = (String) flashMap.get("work_idx");
		model.addAttribute("work_idx", work_idx);
	}
	
	@RequestMapping(value = "tbm")
	public void tbm(Model model, HttpSession session, HttpServletRequest request, String work_idx) {
		TBMVO tbmVO = makeTBM(work_idx);
		model.addAttribute("tbmVO", tbmVO);
	}
	
	@RequestMapping(value = "ptw")
	public void ptw(Model model, HttpSession session, HttpServletRequest request, String work_idx) {
	
	}

	@RequestMapping(value = "pui")
	public void pui(Model model, HttpSession session, HttpServletRequest request, String work_idx) {
		List<PUIVO> puiList = makePUI(work_idx);
		model.addAttribute("puiVO", puiList.get(0));
	}


	public String getDetailByJSON(String code, int type) {
		RestTemplate restTemplate = new RestTemplate();
		String url = CODE_DETAIL_URL + "&code="+code+"&type="+type;
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
		
		//현장관련
		String site_idx = workVO.getSite_idx();
		siteService.getSiteByIdx(site_idx);
		tbmVO.setSitename(siteVO.getSitename());//		 sitename
		tbmVO.setSite_rep_phone(siteVO.getRep_phone());
		
		SimpleDateFormat formatter = new SimpleDateFormat  ("yyyy.MM.dd hh:mm");// ("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
		Date cur_date= new Date();	
		tbmVO.setPrinttime(formatter.format(cur_date));//		 printtime
		
		//기본정보 setting
		makeBaseInfo(tbmVO, workVO);
	
		tbmVO.setToollist(makeToolString(workVO.getToollist()));	//		 toollist
		
		//		 weather
		
		tbmVO.setWorkname(workVO.getWorkname());//		 workname
		tbmVO.setPlacename(workVO.getPlacename());//	 placename
		
		tbmVO.setRisk_grade(workVO.getRisk_grade());//		 risk_grade
		tbmVO.setRisk_warn(workVO.getRisk_warn());//		 risk_warn
		tbmVO.setWorkpermit(workVO.getWorkpermit());//		 workpermit
		
		
		try {
//			 mainrisk, measure, equip, guide , checklist
			setWorkTBM(workVO, tbmVO);		
			setToolTBM(workVO, tbmVO);
			setPlaceTBM(workVO, tbmVO);
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
	
	private void makeBaseInfo(BaseInfoVO targetVO, WorkVO workVO) {
		String cont_idx = workVO.getCont_idx();
		ContractorVO contVO = contractorService.getContractorByIdx(cont_idx);
		
		targetVO.setCont_name(contVO.getCont_name());//		 cont_name
		targetVO.setCont_phone(contVO.getCont_phone());
		targetVO.setCont_rep_name(contVO.getRep_name());
		targetVO.setCont_rep_phone(contVO.getRep_phone());
		targetVO.setCont_emg_phone(contVO.getCont_emg_phone());
		
		
		targetVO.setWorktitle(workVO.getWorktitle());//		 worktitle
		targetVO.setPic_name(workVO.getPic_name());//		 pic_name
		targetVO.setPic_phone(workVO.getPic_phone());//		 pic_phone
		targetVO.setPic_num_worker(workVO.getPic_num_worker());//		 pic_num_worker
		
		targetVO.setStartdate(workVO.getStartdate());//		 startdate
		targetVO.setStarttime(workVO.getStarttime());//		 starttime
		targetVO.setEnddate(workVO.getEnddate());//		 enddate
		targetVO.setEndtime(workVO.getEndtime());//		 endtime
		
	}

	/**형태 : toolname(Y), toolname(Y), toolname(N), ... 
	 * 
	 * @param toollist
	 * @return
	 */
	private String makeToolString(List<ToolVO> toollist) {	
		//JSONObject jsonObject = new JSONObject(); // JSONObject 생성
		//JSONArray array = new JSONArray();
		
		StringBuffer sb = new StringBuffer();
		
		for(ToolVO toolVO : toollist) {
			//array.put(toolVO.getToolname() + "(" + ")");
			sb.append(toolVO.getToolname()).append("(");
			if(toolVO.getTooltype() != 98 & toolVO.getTooltype() != 99)//수기입력만 N 
				sb.append("Y");
			else
				sb.append("N");
			
			sb.append("),");
		}
		
		//return jsonObject.put("toollist", array).toString();
		return sb.substring(0, sb.length() -1 );
	}

	
	private void setWorkTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String json_result = getDetailByJSON(workVO.getWorkcode(), 1);
		if(json_result != null) {
			Map<String, Object> jsonMap = null;
			System.out.println(json_result.toString());
			jsonMap = objectMapper.readValue(json_result, Map.class);
			Map<String,String>work = (Map<String, String>) jsonMap.get("workVO");
			if(work != null) {
				tbmVO.setMeasure(tbmVO.getMeasure() + "<br>" + (String) work.get("measure").replace("\r\n", "<br>"));
				tbmVO.setEquip(tbmVO.getEquip() + "<br>" + (String) work.get("equip").replace("\r\n", "<br>"));
				tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) work.get("guide").replace("\r\n", "<br>"));
			}
		
		}
		
	}
	
	private void setToolTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ToolVO> toollist = workVO.getToollist();
		if(toollist != null) {
		for(ToolVO vo : toollist) {
			if(vo.getTooltype() == 99) continue;
			
			String json_result = getDetailByJSON(vo.getToolcode(), 2);
			if(json_result != null) {
			Map<String, Object> jsonMap = null;
			
				System.out.println(json_result.toString());
				jsonMap = objectMapper.readValue(json_result, Map.class);
				Map<String,String>tool = (Map<String, String>) jsonMap.get("toolVO");
				if(tool != null) {
					//기존에 추가된 내용에 더함
					tbmVO.setMainrisk(tbmVO.getMainrisk() + "<br>" + (String) tool.get("mainRisk").replace("\r\n", "<br>"));
					tbmVO.setEquip(tbmVO.getEquip() + "<br>" + (String) tool.get("equip").replace("\r\n", "<br>"));
					tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) tool.get("guide").replace("\r\n", "<br>"));
					//System.out.println(tool.get("equip"));
				}
			}
		}
		}
	}
	
	private void setPlaceTBM(WorkVO workVO, TBMVO tbmVO) {
//		objectMapper = new ObjectMapper();
//		String json_result = getDetailByJSON(workVO.getWorkcode(), 1);
//		if(json_result != null) {
//			Map<String, Object> jsonMap = null;
//			System.out.println(json_result.toString());
//			jsonMap = objectMapper.readValue(json_result, Map.class);
//			Map<String,String>work = (Map<String, String>) jsonMap.get("workVO");
//			if(work != null) {
//				tbmVO.setMeasure(tbmVO.getMeasure() + "<br>" + (String) work.get("measure").replace("\r\n", "<br>"));
//				tbmVO.setEquip(tbmVO.getEquip() + "<br>" + (String) work.get("equip").replace("\r\n", "<br>"));
//				tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) work.get("guide").replace("\r\n", "<br>"));
//			}
//		
//		}
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
	
	private List<PUIVO> makePUI(String work_idx) {
		ObjectMapper objectMapper = new ObjectMapper();
		WorkVO workVO = workService.getWorkByIdx(work_idx);
		
		
		ArrayList<PUIVO> puilist = new ArrayList<PUIVO>();
		List<ToolVO> toollist = workVO.getToollist();
		if(toollist != null) {//등록된 장비가 존재하면 각 장비의 PUI 정보를 가져옴
			for(ToolVO toolVO : toollist){
				if(toolVO.getTooltype() == 98 || toolVO.getTooltype() == 99 ) continue; //98, 99의 경우 수기입력이므로 RiskMatrix에는 내용이없음
				
				PUIVO puiVO = new PUIVO();//장비 별 PUI 1장
				
				//기본정보 setting
				makeBaseInfo(puiVO, workVO);
				try{
					String json_result = getDetailByJSON(toolVO.getToolcode(), 2);
					if(json_result != null) {
						Map<String, Object> jsonMap = null;
						//수기입력 체크 제외
						System.out.println(json_result.toString());
						jsonMap = objectMapper.readValue(json_result, Map.class);
						
						//set ToolVO 
						Map<String,String>tool = (Map<String, String>) jsonMap.get("toolVO");					
							if(tool != null) {							
								puiVO.setToolname(toolVO.getToolname());
								//PUIVO.setToolurl();
								puiVO.setMainrisk(puiVO.getMainrisk() + "<br>" + (String) tool.get("mainRisk").replace("\r\n", "<br>"));
								
								
								//set Checklist
								ArrayList<Map<String,String>>checklist = (ArrayList<Map<String,String>>)jsonMap.get("checkList");
							
								ArrayList<CheckVO> input_checklist = new ArrayList<CheckVO>();
								Iterator<Map<String,String>> it = checklist.iterator();
								while(it.hasNext()){
									Map<String,String> check = (Map<String,String>)it.next();
									
									CheckVO checkVO = new CheckVO();
									checkVO.setCheck(check.get("checklist"));
									checkVO.setUrl(CHECK_IMG_URL + check.get("virtName"));
									
									input_checklist.add(checkVO);
								}
		
								
								puiVO.setChecklist(input_checklist);
								
								puilist.add(puiVO);
							}
						
					}
				}catch(Exception e ){
					e.printStackTrace();
				}
			}
		}
		
		return puilist;
	}
	private TBMVO makePTW(String work_idx) {
		// TODO Auto-generated method stub
		return null;
	}

}
