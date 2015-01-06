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
			jo.put("siteidx", returnVO.getSite_idx());
			
			returnVO.setPid(regId);
			mobileMapper.updateRegId(returnVO);
		
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
	
	public String updatCheckYn( String workdate, String useridx, String checkyn, String workidx ) {
		
		JSONObject jo = new JSONObject();
		
		MobileVO mobileVO = new MobileVO();
		mobileVO.setCheckyn(checkyn);
		mobileVO.setUser_idx(useridx);
		mobileVO.setWork_idx(workidx);
		mobileVO.setWorkdate(workdate);
		
		int resultInt = mobileMapper.updateCheckYn(mobileVO);
		
		if( resultInt > 0  ) {
			jo.put("result", "true");
		} else {
			jo.put("result", "false");
		}
		
		
		return jo.toString();
	}
	
}
