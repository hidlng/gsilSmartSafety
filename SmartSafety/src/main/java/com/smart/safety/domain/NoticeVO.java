package com.smart.safety.domain;

import java.io.*;

import org.hibernate.validator.constraints.*;

public class NoticeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String notice_idx;
	private String site_idx;
	@NotEmpty(message="입력 필요")
	private String title;
	private String content;
	private String writer_idx;
	private String writer_name;
	private String writetime;
	private String lastupt_time;
	private String delyn;
	
	/**paging**/
	private int start=0;
	private int size=10;
	
	
	public String getNotice_idx() {
		return notice_idx;
	}
	public void setNotice_idx(String notice_idx) {
		this.notice_idx = notice_idx;
	}
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	public String getLastupt_time() {
		return lastupt_time;
	}
	public void setLastupt_time(String lastupt_time) {
		this.lastupt_time = lastupt_time;
	}
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public String getWriter_idx() {
		return writer_idx;
	}
	public void setWriter_idx(String writer_idx) {
		this.writer_idx = writer_idx;
	}
	

}
