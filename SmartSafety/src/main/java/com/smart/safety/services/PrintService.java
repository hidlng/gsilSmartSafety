package com.smart.safety.services;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javax.annotation.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.smart.safety.controller.PrintController.PrintType;
import com.smart.safety.controller.PrintController.*;
import com.smart.safety.domain.*;
import com.smart.safety.persistence.*;

@Service(value="PrintService")
public class PrintService{	
	
	@Resource(name="PrintMapper")
	private PrintMapper printMapper;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat frm= new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
	
	public PrintVO getPrintVO(PrintVO printVO) {
		return printMapper.getPrintVO(printVO);
	}
	
	
	@Transactional
	private void insert(PrintVO printVO) {
		printMapper.insert(printVO);
	}
	
	@Transactional
	private void update(PrintVO printVO){
		printMapper.update(printVO);
	}
	
	@Transactional
	public void deleteByWorkIdx(String work_idx) {
		printMapper.deleteByWorkIdx(work_idx);
	}

	//현재시간과 비교하여 지난시간인지 판별 
	public boolean isPastDate(String curDate, String tarDate) throws ParseException {		
		Date startdate = sdf.parse(tarDate);
		Date curdate = sdf.parse(curDate);
		
		return curdate.compareTo(startdate) >= 0; //curdate >= startdate ( 작업시작일이후에 출력한경우)		
	}
	

	/**작업시작 시간이 현재시간 이후면 insert수행(작업종료는 상관없음 어차피 모바일에서는 현재 날짜에 해당하는 리스트만 보여주기 때문)
	 * @param ptype **/
	public void insertPrintVO(String work_idx, String startDate, PrintType ptype) throws ParseException {
		Date d = new Date();
		String curdate = sdf.format(d); //현재날짜 string으로 
		if(isPastDate(curdate, startDate)) {
			PrintVO printVO = new PrintVO();
			printVO.setWork_idx(work_idx);
			printVO.setWorkdate(curdate);
			
			/**work_idx,type, workdate기준으로 값이 존재할 경우 update, 존재하지 않을 경우 insert) **/
			PrintVO pVO = getPrintVO(printVO);
			if(pVO != null) {//출력이 수행된 종류에 따라 해당 항목 Y로 셋팅
				switch(ptype) {
				case PTW : pVO.setPtw("Y");break;
				case PUI : pVO.setPui("Y");break;
				case TBM : pVO.setTbm("Y");break;
				}				
				update(pVO);
			}else
				insert(printVO);
		}	
	}
	
/*
	public void insertPrintVO(String work_idx, String startDate) throws ParseException {
		Date d = new Date();
		String curdate = sdf.format(d); //현재날짜 string으로 
		if(isPastDate(curdate, startDate)) {
			PrintVO printVO = new PrintVO();
			printVO.setWork_idx(work_idx);
			//printVO.setPrint_type(PrintType.TBM.idx);//TODO : enum필요없으면 제거
			printVO.setWorkdate(curdate);
			//printVO.setPrinttime(frm.format(d));
			//printVO.setChk_user_idx(chk_user_idx); 체크시 업데이트
			
			*//**work_idx,type, workdate기준으로 값이 존재할 경우 update, 존재하지 않을 경우 insert) **//*
			if(isExists(printVO)) 
				update(printVO);
			else
				insert(printVO);
		}	
	}*/



	
	
}
