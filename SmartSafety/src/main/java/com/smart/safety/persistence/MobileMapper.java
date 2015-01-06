package com.smart.safety.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.smart.safety.domain.MobileVO;
import com.smart.safety.domain.UserVO;


@Repository(value="MobileMapper")
public interface MobileMapper {
	public UserVO getMobileLogin(UserVO userVO);
	public void updateRegId( UserVO userVO );
	public List<MobileVO> getMobileWorkList(@Param("siteidx") String siteidx, @Param("searchdate") String searchdate);
	public int updateCheckYn(MobileVO mobileVO);

}
