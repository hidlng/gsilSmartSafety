package com.smart.safety.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.safety.domain.SiteVO;

@Repository(value="SiteMapper")
public interface SiteMapper  {
	public List<SiteVO> getSiteList();
	public List<SiteVO> getSiteListByVO(SiteVO siteVO);
	public SiteVO getSiteByIdx(String idx);
	public void insert(SiteVO siteVO);
	public void update(SiteVO siteVO);
	public void delete(String idx);
	public int getRowCount(SiteVO siteVO);
}
