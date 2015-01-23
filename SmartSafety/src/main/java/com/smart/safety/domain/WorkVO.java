package com.smart.safety.domain;

import java.io.Serializable;
import java.util.*;

import org.hibernate.validator.constraints.NotEmpty;

public class WorkVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String work_idx;//자동부여
	
	private String site_idx;//id 정보를 통해 가져옴
	private String sitename;//조인 정보
	private String write_user_idx;//user_list에서 가져옴
	private String cont_idx; 
	private String cont_name; //view 용도
	
	@NotEmpty(message="입력 필요")
	private String ischarge = "N"; //돌관작업 여부
	
	@NotEmpty(message="선택 필요")
	private String worktype;
	private int workstatus;
	
	@NotEmpty(message="선택 필요")
	private String category1;
	@NotEmpty(message="선택 필요")
	private String category2;	
	private String category3;
	
	@NotEmpty(message="선택 필요")
	private String workcode;
	@NotEmpty(message="선택 필요")
	private String workname;
	private String worktitle;
	
	@NotEmpty(message="입력 필요")
	private String startdate;
	@NotEmpty(message="입력 필요")
	private String enddate;
	@NotEmpty(message="입력 필요")
	private String starttime;
	@NotEmpty(message="입력 필요")
	private String endtime;
	
	private String placecode;
	private String placename;
	
	private String place_airtight="N";
	private String place_acro="N";
	private String place_indoor="N";
	
	@NotEmpty(message="입력 필요")
	private String addr_detail;

	@NotEmpty(message="입력 필요")
	private String pic_name;
	@NotEmpty(message="입력 필요")
	private String pic_birth;
	@NotEmpty(message="입력 필요")
	private String pic_phone;
	@NotEmpty(message="선택 필요")
	private String pic_position;
	
	private String pic_pos_detail;
	@NotEmpty(message="입력 필요")
	private String pic_num_worker;
	@NotEmpty(message="입력 필요")
	private String worklevel;
	private String remark;
	private String remark_leader;
	private String remark_chief;
	
	@NotEmpty(message="선택 필요")
	private String inspec_mgr_idx;
	private String inspec_mgr_name;//view 용도
	
	private int risk_level;
	private String risk_grade;
	private String risk_warn;
	private String workpermit;
	
	private String print_tbm;
	private String print_pui;
	private String print_ptw;
	
	private String writetime;
	private String updatetime;
	private String delyn;
	
	/**Tool**/
	private List<ToolVO> toollist;
	

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
	public List<ToolVO> getToollist() {
		return toollist;
	}
	public void setToollist(List<ToolVO> toollist) {
		this.toollist = toollist;
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
	public int getWorkstatus() {
		return workstatus;
	}
	public void setWorkstatus(int workstatus) {
		this.workstatus = workstatus;
	}

	public String getRisk_grade() {
		return risk_grade;
	}
	public void setRisk_grade(String risk_grade) {
		this.risk_grade = risk_grade;
	}
	public String getRisk_warn() {
		return risk_warn;
	}
	public void setRisk_warn(String risk_warn) {
		this.risk_warn = risk_warn;
	}
	public String getWorkpermit() {
		return workpermit;
	}
	public void setWorkpermit(String workpermit) {
		this.workpermit = workpermit;
	}
	public String getCont_idx() {
		return cont_idx;
	}
	public void setCont_idx(String cont_idx) {
		this.cont_idx = cont_idx;
	}
	public String getPrint_tbm() {
		return print_tbm;
	}
	public void setPrint_tbm(String print_tbm) {
		this.print_tbm = print_tbm;
	}
	public String getPrint_pui() {
		return print_pui;
	}
	public void setPrint_pui(String print_pui) {
		this.print_pui = print_pui;
	}
	public String getPrint_ptw() {
		return print_ptw;
	}
	public void setPrint_ptw(String print_ptw) {
		this.print_ptw = print_ptw;
	}
	public int getRisk_level() {
		return risk_level;
	}
	public void setRisk_level(int risk_level) {
		this.risk_level = risk_level;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public String getInspec_mgr_idx() {
		return inspec_mgr_idx;
	}
	public void setInspec_mgr_idx(String inspec_mgr_idx) {
		this.inspec_mgr_idx = inspec_mgr_idx;
	}
	public String getInspec_mgr_name() {
		return inspec_mgr_name;
	}
	public void setInspec_mgr_name(String inspec_mgr_name) {
		this.inspec_mgr_name = inspec_mgr_name;
	}
	public String getPic_pos_detail() {
		return pic_pos_detail;
	}
	public void setPic_pos_detail(String pic_pos_detail) {
		this.pic_pos_detail = pic_pos_detail;
	}
	public String getRemark_chief() {
		return remark_chief;
	}
	public void setRemark_chief(String remark_chief) {
		this.remark_chief = remark_chief;
	}
	public String getRemark_leader() {
		return remark_leader;
	}
	public void setRemark_leader(String remark_leader) {
		this.remark_leader = remark_leader;
	}
	public String getPlace_airtight() {
		return place_airtight;
	}
	public void setPlace_airtight(String place_airtight) {
		this.place_airtight = place_airtight;
	}
	public String getPlace_acro() {
		return place_acro;
	}
	public void setPlace_acro(String place_acro) {
		this.place_acro = place_acro;
	}
	public String getPlace_indoor() {
		return place_indoor;
	}
	public void setPlace_indoor(String place_indoor) {
		this.place_indoor = place_indoor;
	}

	
}
