package com.smart.safety.persistence;

import java.util.*;

import org.springframework.stereotype.*;

import com.smart.safety.domain.*;

@Repository(value="NoticeMapper")
public interface NoticeMapper {
	public List<NoticeVO> getNoticeListByVO(NoticeVO noticeVO);
	public int getRowCount(NoticeVO noticeVO);
	public NoticeVO getNoticeVO(String notice_idx);
	public void insert(NoticeVO noticeVO);
	public void update(NoticeVO noticeVO);
	public void deleteByIdx(String notice_idx);
	
}
