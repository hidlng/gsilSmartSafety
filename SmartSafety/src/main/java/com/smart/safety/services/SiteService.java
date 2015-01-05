package com.smart.safety.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smart.safety.domain.ManagerVO;
import com.smart.safety.domain.SiteVO;
import com.smart.safety.persistence.ManagerMapper;
import com.smart.safety.persistence.SiteMapper;
import com.smart.safety.persistence.UserMapper;

@Service(value="SiteService")
public class SiteService {
	@Resource(name="SiteMapper")
	private SiteMapper siteMapper;
	
	public List<SiteVO> getSiteList() {
		return siteMapper.getSiteList();
	}

	public List<SiteVO> getSiteListByVO(SiteVO siteVO) {
		return siteMapper.getSiteListByVO(siteVO);
	}
	public SiteVO getSiteByIdx(String idx) {
		return siteMapper.getSiteByIdx(idx);
	}
	
	public int getRowCount(SiteVO siteVO) {
		return siteMapper.getRowCount(siteVO);
	}
	
	@Transactional
	public void insertSite(SiteVO siteVO) {
		siteMapper.insert(siteVO);
	}
	@Transactional
	public void updateSite(SiteVO siteVO) {
		siteMapper.update(siteVO);
	}
	@Transactional
	public void deleteSite(String idx) {
		siteMapper.delete(idx);
		//TODO: 관련 내용 제거
		//contractor, manager, work
	}
	
}
