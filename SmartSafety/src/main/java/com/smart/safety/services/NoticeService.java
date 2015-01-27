package com.smart.safety.services;

import java.util.*;

import javax.annotation.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;
import com.smart.safety.util.*;

@Service(value="NoticeService")
public class NoticeService{	
	
	@Resource(name="NoticeMapper")
	private NoticeMapper noticeMapper;
	
	public List<NoticeVO> getNoticeListByVO(NoticeVO noticeVO){
		return noticeMapper.getNoticeListByVO(noticeVO);
	}
	public int getRowCount(NoticeVO noticeVO){
		return noticeMapper.getRowCount(noticeVO);
	}
	
	public NoticeVO getNoticeVO(String notice_idx){
		return noticeMapper.getNoticeVO(notice_idx);
	}
	
	@Transactional
	public void insert(NoticeVO noticeVO) {
		String notice_idx = UIDMaker.makeNewUID("NT");
		noticeVO.setNotice_idx(notice_idx);
		noticeMapper.insert(noticeVO);
	}
	
	@Transactional
	public void update(NoticeVO noticeVO){
		noticeMapper.update(noticeVO);
	}
	
	@Transactional
	public void deleteByIdx(String notice_idx) {
		noticeMapper.deleteByIdx(notice_idx);
	}

	


	
	
}
