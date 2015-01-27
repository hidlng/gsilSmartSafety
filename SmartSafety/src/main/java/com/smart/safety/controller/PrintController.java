package com.smart.safety.controller;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.annotation.*;

import org.json.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.smart.safety.domain.*;
import com.smart.safety.services.*;
import com.smart.safety.util.*;


@Controller(value="PrintController")
public class PrintController {
	
	

	@Resource(name="WorkService")
	WorkService workService;
		
	
	@Resource(name="ManagerService")
	private ManagerService managerSerivce;

	
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="ContractorService")
	private ContractorService contractorService;

	@Resource(name="PrintService")
	private PrintService printService;

	public enum PrintType { TBM(1), PTW(2), PUI(3)	;	
		public int idx;	 PrintType(int val){this.idx = val;}
	};

	public enum CODETYPE { WORK(1), TOOL(2), PLACE(3)	;	
		public int idx;	 CODETYPE(int val){this.idx = val;}
	};
		
	
	
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
	

	
	/**tbm이 호출 된 순간 출력이 되었다고 판단하기로함**/
	@RequestMapping(value = "tbm")
	public void tbm(Model model, String work_idx) {
		WorkVO workVO = workService.getWorkByIdx(work_idx);
		
		/** PUSH 알림을 위한 INSERT 수행
		 * 작업시작 이후 출력을 한 경우에만 printVO insert**/		
		try {
			printService.insertPrintVO(workVO.getWork_idx(), workVO.getStartdate(), PrintType.TBM);
		} catch (ParseException e) {e.printStackTrace();}	
		
		TBMVO tbmVO = makeTBM(workVO);
		model.addAttribute("tbmVO", tbmVO);				
	}
	
	/**ptw이 호출 된 순간 출력이 되었다고 판단하기로함**/
	@RequestMapping(value = "ptw")
	public void ptw(Model model, String work_idx) {
		WorkVO workVO = workService.getWorkByIdx(work_idx);
		
		/**작업시작 이후 출력을 한 경우에만 printVO insert**/		
		try {
			printService.insertPrintVO(workVO.getWork_idx(), workVO.getStartdate(), PrintType.PTW);
		} catch (ParseException e) {e.printStackTrace();}	
		
		PTWVO ptwVO = makePTW(workVO);
		model.addAttribute("ptwVO", ptwVO);
	}

	/**pui이 호출 된 순간 출력이 되었다고 판단하기로함**/
	@RequestMapping(value = "pui")
	public void pui(Model model, String work_idx) {
		WorkVO workVO = workService.getWorkByIdx(work_idx);
		
		/**작업시작 이후 출력을 한 경우에만 printVO insert**/		
		try {
			printService.insertPrintVO(workVO.getWork_idx(), workVO.getStartdate(), PrintType.PUI);
		} catch (ParseException e) {e.printStackTrace();}			
		
		List<PUIVO> puiList = makePUI(workVO);
		
		
		
		model.addAttribute("puiList", puiList);
		model.addAttribute("puiSize", puiList.size());
	}


	/**pui이 호출 된 순간 출력이 되었다고 판단하기로함**/
	@RequestMapping(value = "accident")
	public void accident(Model model, String work_idx) {
		WorkVO workVO = workService.getWorkByIdx(work_idx);		
		
		
		//Work, Toollist, place에서 사고사례 링크주소를 가져온다
		try {
			ArrayList<FileVO> workFileList = new ArrayList<FileVO>();
			Map<String, Object> jsonMap_work = RequestRiskMatrix.INSTANCE.getDetailByJSON(workVO.getWorkcode(), CODETYPE.WORK);		
			if(jsonMap_work != null) {
				@SuppressWarnings("unchecked")
				ArrayList<Map<String,String>>fileList_work = (ArrayList<Map<String,String>>)jsonMap_work.get("fileList");
				if(fileList_work != null) {
					Iterator<Map<String,String>> it = fileList_work.iterator();
					while(it.hasNext()){
						Map<String,String> filemap = (Map<String,String>)it.next();
						FileVO fileVO = new FileVO();
						fileVO.setName(workVO.getWorkname());
						fileVO.setUrl(RequestRiskMatrix.FILE_URL + filemap.get("file_idx"));
						fileVO.setFileName(filemap.get("fileName"));
						workFileList.add(fileVO);
					}
						
				}
			}
			
			model.addAttribute("workFileList", workFileList);
			
			/**tool**/
			ArrayList<FileVO> toolFileList = new ArrayList<FileVO>();
			List<ToolVO> toollist = workVO.getToollist();
			if(toollist != null) {//등록된 장비가 존재하면 각 장비의 PUI 정보를 가져옴
				for(ToolVO toolVO : toollist){
					if(toolVO.getTooltype() == 98 || toolVO.getTooltype() == 99 ) continue; //98, 99의 경우 수기입력이므로 RiskMatrix에는 내용이없음
					Map<String, Object> jsonMap_tool = RequestRiskMatrix.INSTANCE.getDetailByJSON(toolVO.getToolcode(), CODETYPE.TOOL);
					if(jsonMap_tool != null) {
						@SuppressWarnings("unchecked")
						ArrayList<Map<String,String>>fileList_tool = (ArrayList<Map<String,String>>)jsonMap_tool.get("fileList");
						
						if(fileList_tool != null) {
							Iterator<Map<String,String>> it = fileList_tool.iterator();
							while(it.hasNext()){
								Map<String,String> filemap = (Map<String,String>)it.next();
								FileVO fileVO = new FileVO();
								fileVO.setName(toolVO.getToolname());
								fileVO.setUrl(RequestRiskMatrix.FILE_URL + filemap.get("file_idx"));
								fileVO.setFileName(filemap.get("fileName"));
								toolFileList.add(fileVO);
							}
								
						}
					}
				}
			}
			
			model.addAttribute("toolFileList", toolFileList);
			
			
			/**Place**/
			ArrayList<FileVO> placeFileList = new ArrayList<FileVO>();
			
			StringTokenizer stk = new StringTokenizer(workVO.getPlacecodes(), "|");
			while(stk.hasMoreElements()) {
					String code = (String) stk.nextElement();
					Map<String, Object> jsonMap_place = RequestRiskMatrix.INSTANCE.getDetailByJSON(code, CODETYPE.PLACE);
					if(jsonMap_place != null) {			@SuppressWarnings("unchecked")
						ArrayList<Map<String,String>>fileList_place = (ArrayList<Map<String,String>>)jsonMap_place.get("fileList");
						
					
						//place의 경우에는 이름을 placenames에 보관하고 있기는하나 parsing이 여의치않아 직접 가지고옴
						@SuppressWarnings("unchecked")
						Map<String,String>placeVO = (Map<String,String>)jsonMap_place.get("placeVO");
						String placename = placeVO.get("placeName");
						
						if(fileList_place != null) {
							Iterator<Map<String,String>> it = fileList_place.iterator();
							while(it.hasNext()){
								Map<String,String> filemap = (Map<String,String>)it.next();
								FileVO fileVO = new FileVO();
								fileVO.setName(placename);
								fileVO.setUrl(RequestRiskMatrix.FILE_URL + filemap.get("file_idx"));
								fileVO.setFileName(filemap.get("fileName"));
								placeFileList.add(fileVO);
							}
								
						}
					}
			}
			model.addAttribute("placeFileList", placeFileList);
//			Map<String, Object> jsonMap_work = getJsonMap(workVO.getWorkcode(), CODETYPE.WORK);
//			Map<String,String>work = (Map<String, String>) jsonMap_work.get("placeVO");
					
		} catch (JsonParseException e) {e.printStackTrace();
		} catch (JsonMappingException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		
		
		
	
		
		
		
	}
	
	





	private TBMVO makeTBM(WorkVO workVO) {		
		@SuppressWarnings("unused")
		ObjectMapper objectMapper = new ObjectMapper();		
		TBMVO tbmVO = new TBMVO();
		tbmVO.setWork_idx(workVO.getWork_idx());//		 work_idx	
		
		try {
		//기본정보 setting
		makeBaseInfo(tbmVO, workVO);
	
		tbmVO.setToollist(makeToolString(workVO.getToollist()));	//		 toollist
		tbmVO.setWorkname(workVO.getWorkname());//		 workname
		tbmVO.setPlacename(workVO.getPlacenames());//	 placename
		
		tbmVO.setRisk_level(workVO.getRisk_level());//		 risk_grade
		tbmVO.setRisk_grade(workVO.getRisk_grade());//		 risk_grade
		tbmVO.setRisk_warn(workVO.getRisk_warn());//		 risk_warn
		tbmVO.setWorkpermit(workVO.getPtw_exist());//		 workpermit
	
		//mainrisk, measure, equip, guide , checklist
			setWorkTBM(workVO, tbmVO);		
			setToolTBM(workVO, tbmVO);
			setPlaceTBM(workVO, tbmVO);
			
			tbmVO.setPlace_state(workVO.getPlacenames());
		} catch (JsonParseException e) {e.printStackTrace();
		} catch (JsonMappingException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		
		

		tbmVO.setRemark(workVO.getRemark().replace("\r\n", "<br>"));//		 remark
		tbmVO.setRemark_leader(workVO.getRemark_leader().replace("\r\n", "<br>"));//		 remark
		tbmVO.setRemark_chief(workVO.getRemark_chief().replace("\r\n", "<br>"));//		 remark
		tbmVO.setSitename(workVO.getSitename());

		
		
		return tbmVO;
		//String json_result = getDetailByJSON(workVO.getPlacecode(), 3);		return null;

	}
	
	private void makeBaseInfo(BaseInfoVO targetVO, WorkVO workVO) throws JSONException, IOException {
		String cont_idx = workVO.getCont_idx();
		ContractorVO contVO = contractorService.getContractorByIdx(cont_idx);
		ManagerVO chiefVO = managerSerivce.getChiefBySiteIdx(workVO.getSite_idx());
		ManagerVO inspectorVO = managerSerivce.getManagerByIdx(workVO.getInspec_mgr_idx());
		SiteVO siteVO = siteService.getSiteByIdx(workVO.getSite_idx());
		
		targetVO.setSitename(siteVO.getSitename());//		 sitename
		
		if(chiefVO != null){
			targetVO.setChief_phone(chiefVO.getPhone());
			targetVO.setChief_name(chiefVO.getName());
		}
		
		//날씨
		targetVO.setWeather(WeatherUtil.getWeatherText(siteVO.getLat(), siteVO.getLng()));
		
		SimpleDateFormat formatter = new SimpleDateFormat  ("yyyy.MM.dd hh:mm");// ("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
		Date cur_date= new Date();	
		targetVO.setPrinttime(formatter.format(cur_date));//		 printtime
		
		targetVO.setCont_name(contVO.getCont_name());//		 cont_name
		targetVO.setCont_phone(contVO.getCont_phone());
		targetVO.setCont_rep_name(contVO.getRep_name());
		targetVO.setCont_rep_phone(contVO.getRep_phone());
		targetVO.setCont_emg_phone(contVO.getCont_emg_phone());
		
		if(inspectorVO != null){
			targetVO.setInspector(inspectorVO.getName());
			targetVO.setInspector_phone(inspectorVO.getPhone());
		}
		
		targetVO.setWorktitle(workVO.getWorktitle() + "("+ workVO.getAddr_detail() +")");//		 worktitle(addr_detail)
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
		if(toollist == null || toollist.size() == 0 ) return "";
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

	
	@SuppressWarnings("unchecked")
	private void setWorkTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> jsonMap = RequestRiskMatrix.INSTANCE.getDetailByJSON(workVO.getWorkcode(), CODETYPE.WORK);
		Map<String, String>work = (Map<String, String>) jsonMap.get("workVO");
			if(work != null) {
				if(tbmVO.getMeasure().equals(""))
					tbmVO.setMeasure((String) work.get("measure").replace("\r\n", "<br>"));
				else
					tbmVO.setMeasure(tbmVO.getMeasure() + "<br>" + (String) work.get("measure").replace("\r\n", "<br>"));
				
				if(tbmVO.getEquip().equals(""))
					tbmVO.setEquip((String) work.get("equip").replace("\r\n", "<br>"));
				else 
					tbmVO.setEquip(tbmVO.getEquip() + "<br>" + (String) work.get("equip").replace("\r\n", "<br>"));
				
				if(tbmVO.getSafety().equals(""))
					tbmVO.setGuide((String) work.get("guide").replace("\r\n", "<br>"));
				else 
					tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) work.get("guide").replace("\r\n", "<br>"));
				
				if(tbmVO.getSafety().equals(""))
					tbmVO.setSafety((String) work.get("safety").replace("\r\n", "<br>"));
				else 
					tbmVO.setSafety(tbmVO.getSafety() + "<br>" + (String) work.get("safety").replace("\r\n", "<br>"));
			}
	
		
		
	}
	
	private void setToolTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		
		List<ToolVO> toollist = workVO.getToollist();
		if(toollist != null) {
		for(ToolVO vo : toollist) {
			if(vo.getTooltype() == 99) continue;
			
			Map<String, Object> jsonMap = RequestRiskMatrix.INSTANCE.getDetailByJSON(vo.getToolcode(), CODETYPE.TOOL);
			@SuppressWarnings("unchecked")
			Map<String,String>tool = (Map<String, String>) jsonMap.get("toolVO");
			if(tool != null) {
				//기존에 추가된 내용에 더함
				if(tbmVO.getMainrisk().equals(""))
					tbmVO.setMainrisk((String) tool.get("mainRisk").replace("\r\n", "<br>"));
				else
					tbmVO.setMainrisk(tbmVO.getMainrisk() + "<br>" + (String) tool.get("mainRisk").replace("\r\n", "<br>"));
				
				if(tbmVO.getEquip().equals(""))
					tbmVO.setEquip((String) tool.get("equip").replace("\r\n", "<br>"));
				else
					tbmVO.setEquip(tbmVO.getEquip() + "<br>" + (String) tool.get("equip").replace("\r\n", "<br>"));
				
				//tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) tool.get("guide").replace("\r\n", "<br>"));//PUI로 이동시킴 150112
				
			}
			
			}
		}
	}
	
	private void setPlaceTBM(WorkVO workVO, TBMVO tbmVO) throws JsonParseException, JsonMappingException, IOException {
		StringTokenizer stk = new StringTokenizer(workVO.getPlacecodes(), "|");
		while(stk.hasMoreElements()) {
				String code = (String) stk.nextElement();
				Map<String, Object> jsonMap = RequestRiskMatrix.INSTANCE.getDetailByJSON(code, CODETYPE.PLACE);
				@SuppressWarnings("unchecked")
				Map<String,String>place = (Map<String, String>) jsonMap.get("placeVO");
				if(place != null) {
					//기존에 추가된 내용에 더함
					if(tbmVO.getMainrisk().equals(""))
						tbmVO.setMainrisk((String) place.get("mainRisk").replace("\r\n", "<br>"));
					else
						tbmVO.setMainrisk(tbmVO.getMainrisk() + "<br>" + (String) place.get("mainRisk").replace("\r\n", "<br>"));
					
					if(tbmVO.getGuide().equals(""))
						tbmVO.setGuide((String) place.get("guide").replace("\r\n", "<br>"));
					else
						tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) place.get("guide").replace("\r\n", "<br>"));
					
					if(tbmVO.getEquip().equals(""))
						tbmVO.setEquip((String) place.get("equip").replace("\r\n", "<br>"));
					else
						tbmVO.setEquip(tbmVO.getEquip() + "<br>" + (String) place.get("equip").replace("\r\n", "<br>"));
					
					//tbmVO.setGuide(tbmVO.getGuide() + "<br>" + (String) tool.get("guide").replace("\r\n", "<br>"));//PUI로 이동시킴 150112
					
				}
				
				
			
		}
	}
	
	
	private List<PUIVO> makePUI(WorkVO workVO) {
		ArrayList<PUIVO> puilist = new ArrayList<PUIVO>();
		List<ToolVO> toollist = workVO.getToollist();
		if(toollist != null) {//등록된 장비가 존재하면 각 장비의 PUI 정보를 가져옴
			for(ToolVO toolVO : toollist){
				if(toolVO.getTooltype() == 98 || toolVO.getTooltype() == 99 ) continue; //98, 99의 경우 수기입력이므로 RiskMatrix에는 내용이없음
				
				PUIVO puiVO = new PUIVO();//장비 별 PUI 1장
				
				//기본정보 setting
				
				try{
					makeBaseInfo(puiVO, workVO);
					Map<String, Object> jsonMap = RequestRiskMatrix.INSTANCE.getDetailByJSON(toolVO.getToolcode(), CODETYPE.TOOL);
					@SuppressWarnings("unchecked")
					Map<String,String>tool = ((Map<String, String>) jsonMap.get("toolVO"));
					if(tool != null) {							
						puiVO.setToolname(toolVO.getToolname());
						//PUIVO.setToolurl();
						puiVO.setMainrisk(puiVO.getMainrisk() + "<br>" + (String) tool.get("mainRisk").replace("\r\n", "<br>"));
						puiVO.setGuide(puiVO.getGuide() + "<br>" + (String) tool.get("guide").replace("\r\n", "<br>"));
						puiVO.setToolurl(RequestRiskMatrix.TOOL_IMG_URL + tool.get("imgVirtName"));
						
						//set Checklist
						@SuppressWarnings("unchecked")
						ArrayList<Map<String,String>>checklist = (ArrayList<Map<String,String>>)jsonMap.get("checkList");
						Iterator<Map<String,String>> it = checklist.iterator();
						
						ArrayList<CheckVO> input_checklist = new ArrayList<CheckVO>();
						while(it.hasNext()){
							Map<String,String> check = (Map<String,String>)it.next();
							
							CheckVO checkVO = new CheckVO();
							checkVO.setCheck(check.get("checklist"));
							
							if(check.get("virtName") != null && !check.get("virtName").equals(""))
								checkVO.setUrl(RequestRiskMatrix.CHECK_IMG_URL + check.get("virtName"));
							else {//null일 경우 그냥둠 (jsp에서 null check하여 img src를 아예 사용안하도록))
							}
							input_checklist.add(checkVO);
						}

						
						puiVO.setChecklist(input_checklist);
						
						puilist.add(puiVO);
					}				
				
				}catch(Exception e ){
					e.printStackTrace();
				}
			}
		}
		
		return puilist;
	}
	
	@SuppressWarnings({ "unchecked"})
	private PTWVO makePTW(WorkVO workVO) {
		PTWVO ptwVO = new PTWVO();
		//기본정보 setting
		try {
			makeBaseInfo(ptwVO, workVO);
				
			/**특수한 작업+장소의 경우 별도의 체크사항이 필요함**/		
			Map<String, Object> jsonMap = RequestRiskMatrix.INSTANCE.getPermitList(workVO.getWorkcode(), workVO.getPlacecodes()) ;
			ArrayList<Map<String,String>>permitList = (ArrayList<Map<String,String>>) jsonMap.get("permitList");
			Iterator<Map<String,String>> it = permitList.iterator();
			
			while(it.hasNext()){
				Map<String,String>elem = it.next();
				PermitVO permitVO = new PermitVO();				
				permitVO.setName(elem.get("name").replace("+", "<br>"));
				permitVO.setContent(elem.get("content"));
				permitVO.setType(String.valueOf(elem.get("type")));
				
				ptwVO.getPermitList().add(permitVO);
			}
			
			
				
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ptwVO;
	}

}
