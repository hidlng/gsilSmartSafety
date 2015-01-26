package com.smart.safety.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smart.safety.domain.MobileVO;
import com.smart.safety.domain.UserVO;


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

}
