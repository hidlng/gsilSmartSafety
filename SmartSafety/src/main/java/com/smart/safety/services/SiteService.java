package com.smart.safety.services;

import java.util.*;

import javax.annotation.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;
import com.smart.safety.util.*;

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
		siteVO.setSite_idx(UIDMaker.makeNewUID("S"));
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
