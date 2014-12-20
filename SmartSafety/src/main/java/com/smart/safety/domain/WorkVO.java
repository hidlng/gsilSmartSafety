package com.smart.safety.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class WorkVO implements Serializable{

	String work_idx;//자동부여
	
	String site_idx;//id 정보를 통해 가져옴
	String sitename;//조인 정보
	String write_user_idx;//user_list에서 가져옴
//	String cont_idx; //보류
	String cont_name; 
	
	@NotEmpty(message="입력 필요")
	String ischarge = "N"; //돌관작업 여부
	
	@NotEmpty(message="선택 필요")
	String worktype;
	@NotEmpty(message="선택 필요")
	String category1;
	@NotEmpty(message="선택 필요")
	String category2;	
	String category3;
	
	@NotEmpty(message="선택 필요")
	String workcode;
	@NotEmpty(message="선택 필요")
	String workname;
	String worktitle;
	
	@NotEmpty(message="입력 필요")
	String startdate;
	@NotEmpty(message="입력 필요")
	String enddate;
	@NotEmpty(message="입력 필요")
	String starttime;
	@NotEmpty(message="입력 필요")
	String endtime;
	
	String placecode;
	String placename;
	
	@NotEmpty(message="입력 필요")
	String addr_detail;
	@NotEmpty(message="입력 필요")
	String indoor ="N";
	@NotEmpty(message="입력 필요")
	String pic_name;
	@NotEmpty(message="입력 필요")
	String pic_birth;
	@NotEmpty(message="입력 필요")
	String pic_phone;
	@NotEmpty(message="입력 필요")
	String pic_position;
	@NotEmpty(message="입력 필요")
	String pic_num_worker;
	@NotEmpty(message="입력 필요")
	String worklevel;
	String remark;
	String writetime;
	String updatetime;
	String delyn;
	
	/**additional**/
	String username;
	
	/**paging**/
	private int start=0;
	private int size=10;
	public String getWork_idx() {
		return work_idx;
	}
	public void setWork_idx(String work_idx) {
		this.work_idx = work_idx;
	}
	public String getSite_idx() {
		return site_idx;
	}
	public void setSite_idx(String site_idx) {
		this.site_idx = site_idx;
	}


	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getCategory3() {
		return category3;
	}
	public void setCategory3(String category3) {
		this.category3 = category3;
	}
	
	public String getWorkname() {
		return workname;
	}
	public void setWorkname(String workname) {
		this.workname = workname;
	}
	public String getWorktitle() {
		return worktitle;
	}
	public void setWorktitle(String worktitle) {
		this.worktitle = worktitle;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getPlacecode() {
		return placecode;
	}
	public void setPlacecode(String placecode) {
		this.placecode = placecode;
	}
	public String getPlacename() {
		return placename;
	}
	public void setPlacename(String placename) {
		this.placename = placename;
	}
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getPic_birth() {
		return pic_birth;
	}
	public void setPic_birth(String pic_birth) {
		this.pic_birth = pic_birth;
	}
	public String getPic_phone() {
		return pic_phone;
	}
	public void setPic_phone(String pic_phone) {
		this.pic_phone = pic_phone;
	}
	public String getPic_position() {
		return pic_position;
	}
	public void setPic_position(String pic_position) {
		this.pic_position = pic_position;
	}
	public String getPic_num_worker() {
		return pic_num_worker;
	}
	public void setPic_num_worker(String pic_num_worker) {
		this.pic_num_worker = pic_num_worker;
	}
	public String getWorklevel() {
		return worklevel;
	}
	public void setWorklevel(String worklevel) {
		this.worklevel = worklevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getWrite_user_idx() {
		return write_user_idx;
	}
	public void setWrite_user_idx(String write_user_idx) {
		this.write_user_idx = write_user_idx;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getIndoor() {
		return indoor;
	}
	public void setIndoor(String indoor) {
		this.indoor = indoor;
	}
	public String getIscharge() {
		return ischarge;
	}
	public void setIscharge(String ischarge) {
		this.ischarge = ischarge;
	}
	public String getWorkcode() {
		return workcode;
	}
	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
