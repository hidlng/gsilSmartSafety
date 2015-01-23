package com.spring.risk.web.actions;

import java.util.*;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.*;

import com.spring.risk.domain.*;
import com.spring.risk.service.*;

public class TestActionBean extends AbstractActionBean {
	

		private static final long serialVersionUID = 1L;

		@SpringBean
		private transient TestService testService;
	
		public static final String TEST= "/WEB-INF/views/admin/test.jsp";
		
		private List<TestVO> testList ;
		
		@DefaultHandler
		public Resolution viewMain() {		
			clear();
			
			testList = testService.getTest();
			return new ForwardResolution(TEST);
		}



		private void clear() {
			// TODO Auto-generated method stub
			
		}



		public List<TestVO> getTestList() {
			if(testList == null)
				testList = testService.getTest();
			return testList;
		}



		public void setTestList(List<TestVO> testList) {
			this.testList = testList;
		}
}
