package com.smart.safety.services;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.smart.safety.domain.MobileVO;
import com.smart.safety.domain.UserVO;
import com.smart.safety.persistence.MobileMapper;

@Service(value="MobileService")
public class MobileService {
	@Resource( name="MobileMapper" )
	private MobileMapper mobileMapper;
	
	public String getMobileLogin(String id, String password, String regId) {		
		UserVO userVO = new UserVO();
		userVO.setId(id);
		userVO.setPassword(password);
		
		UserVO returnVO = mobileMapper.getMobileLogin( userVO );
		
		JSONObject jo = new JSONObject();
		if( returnVO != null && !returnVO.getId().equals("") ) {
			jo.put("result", "true");
			jo.put("id", returnVO.getId());
			jo.put("password", returnVO.getPassword());
			jo.put("useridx", returnVO.getUser_idx());
			jo.put("authority", returnVO.getAuthority());
			jo.put("level", returnVO.getLevel());
			
			if( returnVO.getLevel() == 7 ) {
				UserVO contInfo = mobileMapper.getMobileContractorInfo(returnVO.getUser_idx());
				jo.put("siteidx", contInfo.getSite_idx());
				jo.put("sitename", contInfo.getSitename());
			} else {
				UserVO managerInfo = mobileMapper.getMobileManagerInfo(returnVO.getUser_idx());
				jo.put("siteidx", managerInfo.getSite_idx());
				jo.put("sitename", managerInfo.getSitename());
			}
			returnVO.setPid(regId);
			mobileMapper.updateRegId(returnVO);
		
		} else {
			jo.put("result", "false");
		}
		
		return jo.toString();
	}
	
	public String getMobileWorkInfomation(String workIdx) {		

		
		MobileVO returnVO = mobileMapper.getWorkInfomation(workIdx);
		
		JSONObject jo = new JSONObject();
		if( returnVO != null ) {
			jo.put("result", "true");
			jo.put("workIdx", returnVO.getWork_idx());
			jo.put("worktype", returnVO.getWorktype());
			jo.put("placenames", returnVO.getPlacenames());
			jo.put("ptwExist", returnVO.getPtw_exist());
			jo.put("puiExist", returnVO.getPui_exist());
			jo.put("picNumWorker", returnVO.getPic_num_worker());
			jo.put("picName", returnVO.getPic_name());

			List<MobileVO> toolList = mobileMapper.getWorkToolInfomation(workIdx);
			
			String toolName = "";
			if( toolList != null && toolList.size() > 0 ) {
				for( int i = 0; i < toolList.size(); i++ ) {
					MobileVO tool = (MobileVO)toolList.get(i);
					if( i == 0 ) {
						toolName = tool.getToolname();
					} else {
						toolName += "," + tool.getToolname();
					}
				}
			}
			jo.put("toolName", toolName);
			
			
		} else {
			jo.put("result", "false");
		}
		
		return jo.toString();
	}
	

	
	public String getMobileWorkList(String siteidx, String searchdate) {
		
		JSONObject jo = new JSONObject();
		
		List<MobileVO> mlist = mobileMapper.getMobileWorkList(siteidx, searchdate);
		
		
		if( mlist != null && mlist.size() > 0 ) {
			jo.put("result", "true");
			
			JSONArray ja = new JSONArray();
			
			for (MobileVO mobileVO : mlist) {
				JSONObject jo2 = new JSONObject();
				jo2.put("riskGrage", mobileVO.getRisk_grade() );
				jo2.put("workidx", mobileVO.getWork_idx() );
				jo2.put("checkyn", mobileVO.getCheckyn() );
				jo2.put("enddate", mobileVO.getEnddate() );
				jo2.put("picName", mobileVO.getPic_name() );
				jo2.put("startdate", mobileVO.getStartdate() );
				jo2.put("worklevel", mobileVO.getWorklevel() );
				jo2.put("worktitle", mobileVO.getWorktitle() );
				
				ja.put(jo2);
			}
			jo.put("item", ja);
			
		} else {
			jo.put("result", "false");
		}
		
		
		return jo.toString();
	}
	
	public String updatCheckYn( String workdate, String useridx, String checkyn, String workidx, String userlevel ) {
		
		JSONObject jo = new JSONObject();
		
		MobileVO mobileVO = new MobileVO();
		mobileVO.setCheckyn(checkyn);
		mobileVO.setUser_idx(useridx);
		mobileVO.setWork_idx(workidx);
		mobileVO.setWorkdate(workdate);
		
		int resultInt = 0;
		
		if( userlevel.equals("2") ) {
			resultInt = mobileMapper.updateCheckYn(mobileVO);
		} else if( userlevel.equals("4") ) {
			resultInt = mobileMapper.updateChifCheckYn(mobileVO);
		} else if( userlevel.equals("5") ) {
			resultInt = mobileMapper.updateLeadCheckYn(mobileVO);
		}
		
		if( resultInt > 0  ) {
			jo.put("result", "true");
		} else {
			jo.put("result", "false");
		}
		
		
		return jo.toString();
	}
	
	
	//notice
	public String getMobileNoticeList(String siteidx) {
		
		JSONObject jo = new JSONObject();
		
		List<MobileVO> mlist = mobileMapper.getMobileNoticeList(siteidx);
		
		
		if( mlist != null && mlist.size() > 0 ) {
			jo.put("result", "true");
			
			JSONArray ja = new JSONArray();
			
			for (MobileVO mobileVO : mlist) {
				JSONObject jo2 = new JSONObject();
				jo2.put("name", mobileVO.getName() );
				jo2.put("noticeIdx", mobileVO.getNotice_idx() );
				jo2.put("siteIdx", mobileVO.getSite_idx() );
				jo2.put("title", mobileVO.getTitle() );
				jo2.put("content", mobileVO.getContent() );
				jo2.put("writetime", mobileVO.getWritetime() );
				
				ja.put(jo2);
			}
			jo.put("item", ja);
			
		} else {
			jo.put("result", "false");
		}
		
		
		return jo.toString();
	}
	
	public String getMobileNoticeInfo(String noticeIdx) {		

		
		MobileVO returnVO = mobileMapper.getMobileNoticeInfo(noticeIdx);
		
		JSONObject jo = new JSONObject();
		if( returnVO != null ) {
			jo.put("result", "true");
			jo.put("name", returnVO.getName() );
			jo.put("noticeIdx", returnVO.getNotice_idx() );
			jo.put("siteIdx", returnVO.getSite_idx() );
			jo.put("title", returnVO.getTitle() );
			jo.put("content", returnVO.getContent() );
			jo.put("writetime", returnVO.getWritetime() );
		} else {
			jo.put("result", "false");
		}
		
		return jo.toString();
	}
	
}
