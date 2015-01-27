package com.smart.safety.persistence;

import java.util.*;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import com.smart.safety.domain.*;


@Repository(value="MobileMapper")
public interface MobileMapper {
	public UserVO getMobileLogin(UserVO userVO);
	public UserVO getMobileContractorInfo(String userIdx);
	public UserVO getMobileManagerInfo(String userIdx);
	public MobileVO getWorkInfomation( String workIdx );
	public List<MobileVO> getWorkToolInfomation( String workIdx );
	public void updateRegId( UserVO userVO );
	public List<MobileVO> getMobileWorkList(@Param("siteidx") String siteidx, @Param("searchdate") String searchdate);
	public int updateCheckYn(MobileVO mobileVO);
	public int updateChifCheckYn(MobileVO mobileVO);
	public int updateLeadCheckYn(MobileVO mobileVO);
	
	//notice
	public List<MobileVO> getMobileNoticeList( String siteIdx );
	public MobileVO getMobileNoticeInfo( String noticeIdx );

}
