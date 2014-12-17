package com.spring.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.risk.domain.AccDetailVO;
import com.spring.risk.domain.WorkVO;
import com.spring.risk.persistence.AccDetailMapper;
import com.spring.risk.persistence.WorkMapper;

@Service
public class WorkListService {

	@Autowired
	private WorkMapper workMapper;

	@Autowired
	private AccDetailMapper accDetaillMapper;

	

	public WorkVO getWorkByWorkCode(String workCode) {
		return workMapper.getWorkByWorkCode(workCode);
	}

	public List<AccDetailVO> getAccListByWorkcode(String workCode) {
		return accDetaillMapper.getAccListByWorkcode(workCode);
	}
	
	public List<AccDetailVO> getAccDetailListByCode(String code) {
		return accDetaillMapper.getAccListByWorkcode(code);
	}


	@Transactional
	public void insertWorkVO(WorkVO workVO, List<AccDetailVO> inputAccList) {
		workMapper.insertWorkVO(workVO);	
		for(AccDetailVO vo : inputAccList){
			vo.setWorkCode(workVO.getWorkCode());
			/**NONE -> pass **/
			if( (vo.getAccCode() != null) && !(vo.getAccCode().equals("NONE")) )
				accDetaillMapper.insertAccVO(vo);
		}
		
	}
	
	@Transactional
	public void updateWorkVO(WorkVO workVO, List<AccDetailVO> inputAccList) {
		workMapper.updateWorkVO(workVO);	
		
		/**Accdetail의 경우 추가/제거가 발생할수있으므로 전부 제거 후 새로 추가**/
		accDetaillMapper.deleteAccVOByWorkCode(workVO.getWorkCode());
		
		for(AccDetailVO vo : inputAccList){
			vo.setWorkCode(workVO.getWorkCode());
			/**NONE -> pass **/
			if( (vo.getAccCode() != null) && !(vo.getAccCode().equals("NONE")) )
				accDetaillMapper.insertAccVO(vo);
		}
		
	}
	
	@Transactional
	public void deleteWorkByWorkCode(String workCode) {
		accDetaillMapper.deleteAccVOByWorkCode(workCode);
		workMapper.deleteWorkVOByCode(workCode);
	}
	

//	@Transactional
//	public void updateAccVOByCode(AccDetailVO accvo) {
//		accDetaillMapper.updateAccVO(accvo);
//	}



	
	// public List<AccDetailVO> getAccDetailListByCode(String code){
	// return workMapper.getAccDetailListByCode();
	// }

//	@Transactional
//	public void insertWorkVO(WorkVO workvo) {
//		workMapper.insertWorkVO(workvo);
//	}


//	@Transactional
//	public void insertAccVO(AccDetailVO accvo) {
//		accDetaillMapper.insertAccVO(accvo);
//	}


	// @Transactional
	// public void insertWorkCategory(Category category) {
	// categoryMapper.insertWorkCategory(category);
	// }

}
