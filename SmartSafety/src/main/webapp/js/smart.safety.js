/**작업등록 **/
/** set Child Category Of Idx (add option to target Selectbox) **/
function clearSelect() {
	
	for(var i in arguments) {
		$('#' + arguments[i]).empty();
		$('#popupOKBtn').hide();
	}
	
}

function setChildCategoryOf(idx, targetId) {
	setChildCategoryOf(idx,targetId,"")
}
function setChildCategoryOf(idx, targetId, selectName) {
	
	$.ajax({
 		type : "POST",
 		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getCategoryByJSON=",
 		data : {ancIdx : idx},
 		dataType : "jsonp",
 	    jsonp : "callback",
 		cache : false,
 		success : function(json) {
 			$('#' + targetId).empty();
 			var catList = json.catList;
 		
 			var length = 0;
 			for(var prop in catList){
 			    if(catList.hasOwnProperty(prop))
 			        length++;
 			}
 			
 			
 			$('#' + targetId).append('<option id="" value="">-------선택-------</option>');
 			for(var i = 0 ; i < length; i ++) {
 				//alert(selectName +" : "+catList[i].name );
 				if(selectName == catList[i].name )
 					$('#' + targetId).append('<option selected="selected" id="' + catList[i].idx + '" value="' + catList[i].name + '">' + catList[i].name  + '</option>');
 				else 
 					$('#' + targetId).append('<option id="' + catList[i].idx + '" value="' + catList[i].name + '">' + catList[i].name  + '</option>');
 			} 
			
 		},
 		error : onError
	});
}

function setCateogry(objId, targetId) {
	var idx = $("#" + objId + " option:selected" ).attr('id');
	setChildCategoryOf(idx , targetId);
}

function setCodeBySelect(optId, targetId) {
	 var idx = $("#" + optId + " option:selected" ).attr('id');
	
	 setCode(idx, targetId);

}

//var existList = new Array('건설1'); 

/**
 * 
 * @param categoryIdx  해당 category의 code list 출력
 * @param targetId	대상 select box (name속성)
 * @param existList 이미 존재하는 codelist
 */
function setCode(categoryIdx , targetId, existList) {
	 $.ajax({
	  		type : "POST",
	  		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getCodeByJSON=",
	  		data : {lastIdx : categoryIdx},
	  		dataType : "jsonp",
	  	    jsonp : "callback",
	  		cache : false,
	  		success : function(json) {
	  			$('#' + targetId).empty();
	  			var codeList = json.codeList;
	  			$('#' + targetId).append('<option val="test">------선택------</option>');
	  			
	  			var length = 0;
	  			for(var prop in codeList){//size 파악
	 			    if(codeList.hasOwnProperty(prop))
	 			        length++;
	 			}
	 			
	  			
	  			
	  			for(var i = 0 ; i < length; i ++) {
//	  				var isExist = false;
//	  				//현재 존재하는 list와 비교하여 존재시 체크 
//	  				if(existList != undefined && existList != null) {
//		  				for(i = 0 ; existList.length;i++) 
//		  					if(codeList[i].code == existList[i])
//		  						isExist = true;
//	  				}	
//	  			
//		  			if(!isExist)
		  				$('#' + targetId).append('<option id="'+ codeList[i].code + '" value="' + codeList[i].name + '">' + codeList[i].name  + '</option>');
		  				//$('#' + codeId +"_" + i).val(codeList[i].code);
	  				
	  			} 
				
	  		},
	  		error : onError
		});
	 
	 	
}


function onError(data, status) {
	alert(this.url);
	alert("error : " + status +"data:"+ data);
	
}
	

function getCodeDetail(codeNum, typeNum) {
	//1 work 2 tool 3 place
	
	var result;
	
	 $.ajax({
	  		type : "POST",
	  		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getDetailByJSON=",
	  		data : {code : codeNum, type : typeNum },
	  		dataType : "jsonp",
	  	    jsonp : "callback",
	  		cache : false,
	  		success : function(json) {
	  			//alert(json.workVO.guide);
	  			//result = eval(json);
	  		
	  		//	alert(json.toolVO.checkList[0].check_idx);
	  			//alert(s.toolVO.checkList[0].check_idx);
	  			//alert("r" + json);
	  		
	  		},
	  		error : onError
		});
	 
	
}

function setWorkDetail(objId) {
	 var idx = $("#" + objId + " option:selected" ).attr('id');
	 getCodeDetail(idx, 1);
}




/**장비관련**/
var equipIdx = 0;

/**option 선택시 새로 추가될 selectbox형태 설정
 * tarId : select박스를 추가할 TD
 * isSelect : Seletbox/Text 결정
 */
function getTool(tarId, inputType) {
	var str;
	if(inputType == 0){//Select
		str = "<select id='toolSelect_" + equipIdx + "' name='toollist["
		+ equipIdx + "].toolname' class='siteSelectBox' onchange='selectTool("
		+ equipIdx + ", \""	+ tarId + "\")'>"
	//	+ "<option>:::선택:::</option>"
		+ "</select>";
	}else if(inputType == 1) { //readonly text
		str =  "<input type='text' id='toolSelect_" + equipIdx + "' name='toollist["
		+ equipIdx + "].toolname' readonly='true'/>";
	}else if(inputType == 2) {//수기입력 text
		str =  "<input type='text' id='toolSelect_" + equipIdx + "' name='toollist["
		+ equipIdx + "].toolname' value=''/>";
	}
	
	str += "<input type='button' class='deleteButton' id='toolDelete_" + equipIdx + "'  onclick='removeTool(" 
   	+ equipIdx + ")' value='X' ></input>"
   	+ "<input  type='hidden' name='toollist[" + equipIdx + "].toolcode' id='toolcode_" + equipIdx + "' />" 
 	+ "<input  type='hidden' name='toollist[" + equipIdx + "].tooltype' id='tooltype_" + equipIdx + "' />" 
   	;
 	
 	return str;
}


/* selectbox 추가
 * tarId : select박스를 추가할 TD
 */
function addTool(tarId, inputType) {	
	var type;
	
	var str = getTool(tarId , inputType);
	
	/**span 추가**/
	var addedSpan = document.createElement("span");
	addedSpan.id = "toolSpan_" + equipIdx;
	addedSpan.innerHTML = str;
	$("#" + tarId).append(addedSpan);
	
	//test
	
		
	
	if(inputType == 0){//selectbox 항목추가
		if(tarId == 'cons_machine') setCode(57, 'toolSelect_' + equipIdx);
		else if(tarId == 'trans_machine') setCode(58, 'toolSelect_' + equipIdx);
		else if(tarId == 'etc_machine') setCode(59, 'toolSelect_' + equipIdx);
		else if(tarId == 'weld_tool') setCode(61, 'toolSelect_' + equipIdx);
		else if(tarId == 'elec_tool') setCode(62, 'toolSelect_' + equipIdx);
		else if(tarId == 'nelec_tool') setCode(63, 'toolSelect_' + equipIdx);
		else if(tarId == 'etc_tool') setCode(64, 'toolSelect_' + equipIdx);

	}
	equipIdx++;
//	$("#checkCount").val(checkCount);//전달시 checklistArray에서 제거된 checkvo가 계속남아있으므로 이를 지정된 갯수만큼 잘라주기 위함	
}

/**수기입력용 Textbox생성**/
function addToolText(tarId, isTool) {
	 var str = getTool(tarId , 2);//text박스
	 
	 var addedSpan = document.createElement("span");
	 	addedSpan.id = "toolSpan_" + equipIdx;
	 	addedSpan.innerHTML = str;
	 	$("#" + tarId).append(addedSpan);
	 	
		/**hidden값 code, type 값 부여**/
	 	$('#toolcode_' + equipIdx).val('_');
	 	if(isTool) //공도구면 99
	 		$('#tooltype_' + equipIdx).val(99); //99: 수기입력
	 	else//장비면 98
	 		$('#tooltype_' + equipIdx).val(98); //98: 수기입력
	 	
	 equipIdx++;
}

//넘겨받은 selectbox의 id값은 code값과 같음
//code 및 type hiddne값 지정용도
function selectTool(eIdx, tarId) {
	var code = $('#toolSelect_' + eIdx + ' option:selected').attr('id'); //선택된 option의 id값이 code값임.(val은 name)
	var type;
	if(tarId == 'cons_machine') type=0;
	else if(tarId == 'trans_machine') type=1;
	else if(tarId == 'etc_machine') type=2;
	else if(tarId == 'weld_tool') type=3;
	else if(tarId == 'elec_tool')  type=4;
	else if(tarId == 'nelec_tool') type=5;
	else if(tarId == 'etc_tool') type=6;
	
	
	/**hidden값 code, type 값 부여**/
	$('#toolcode_' + eIdx).val(code);
	$('#tooltype_' + eIdx).val(type); //TODO : type에 따라 구분할것
	
	/**추가된 span 내 select에 항목 추가**/	
//	addTool(tarId, true);
}

/**submit시 toollist에 추가되지않게 관련속성 모두 제거**/
function removeTool(idx) {
	$('#toolSelect_' + idx).remove();
	$('#toolcode_' + idx).remove();
	$('#tooltype_' + idx).remove();
	$('#toolDelete_' + idx).remove();
}

//submit전 선택하지 않은 list에 대해 정리를 실시 
//error발생시 ---선택--이 나타나는 문제떄문
function checkBeforeSubmit() {
	
	for(var i = 0 ; i < equipIdx ; i++ ) {
		if($('#toolcode_' + i).val() == ''){
			removeTool(i);
			//alert($('#toolcode_' + i).parent().attr('id'));
		}
	}
		
}


function goPopup(){
	$.ajax({
		type : "POST",
		url : "workPopup",
		cache : false,
		success : function(json){
			$('#viewContent').html(json);
			$('#popupOKBtn').hide();
			setChildCategoryOf(1, 'worktype_pop');//init
			doOverlayOpen();	
		},
		error : onError
	});
}



function confirmCode() {
	//alert(worktype + " 1" + category1 + " " + category2 + " " + workcode + " " + workname);	 
	var worktype = $('#worktype_pop option:selected').val();
	var category1 = $('#category1_pop option:selected').val();
	var category2 = $('#category2_pop option:selected').val();
	var workname = $('#workname_pop option:selected').val();
	var workcode = $('#workname_pop  option:selected').attr('id');
	
	$('#worktype').val(worktype);
	$('#category1').val(category1);
	$('#category2').val(category2);
	$('#workcode').val(workcode);
	$('#workname').val(workname);
	
	$('#viewContent').html('');
	doOverlayClose();	
}


/**작업등록 END **/

 function duplicateIdCheck() {
	  var id = $('#input_id').val();
	  
	  if(id == "") {
		  alert("ID를 입력하십시오");
		  return;
	  }
	  
		$.ajax({
			type : "GET",
			url : "duplicateIdCheck",
			data : {"id" : id },
			cache : false,
			success : onSuccessIDCheck,
			error : onErrorIDCheck
		});
  }
 

 
 function onSuccessIDCheck(json, status) {
	  if(json == "true") {
		  idNotDuplicate = true;
		  
		  $('#input_id').attr("readonly",true);
		  $('.duplCheck').hide();  
		  $('.duplCheckOk').show();
		  alert("사용 가능합니다 (This ID is available)")
		  
	  }else {
		  idNotDuplicate = false;
		  alert("이미 존재하는 ID 입니다 (This ID already exists)")
	  }
 }
 
 function onErrorIDCheck() {
	  idNotDuplicate = false;
	  alert('ERROR');
	  
 }
 

 
function newPassword() {
	  $('#btnNewPasswd').hide();
	  $('#input_password').show();
	  $('#input_password').val('');
	  $('#isPWChanged').val('true'); 
}



$(function() {
	   //작업일자
	   $( "#startDateInput" ).datepick({
	    	yearRange: '-1:+5',
	    	showSecond: true,
	    	onClose: function (selectedDate) {
	    		if(selectedDate != "") {	    			
	    			$("#endDateInput").datepick("option", "minDate",new Date(selectedDate));
	    		}
	    	}
	    }).attr('readonly','readonly');   
	    $( "#endDateInput" ).datepick({
	    	yearRange: '-1:+5',
	    	onClose: function (selectedDate) {
	    		if(selectedDate != "") { 	    			
	    			$("#startDateInput").datepick("option", "maxDate",new Date(selectedDate));
	    		}
	    	}
	    }).attr('readonly','readonly');   
	    
/*
		$('#starttimeInput').timepicker({
			'step':10
			
		})
		*/
	    
	    //작업시간
	    $('#starttimeInput').timepicker({
	    	//interval: 15, // 15 minutos
	    	timeFormat : 'HH:mm',
		 
	    });
		
		$('#endtimeInput').timepicker({
			//interval: 15, // 15 minutos
			timeFormat : 'HH:mm'
	    });

		
		//생년월일
	    $( "#birthInput" ).datepick({
	    	yearRange: '1960:2000',
	    	defaultDate: new Date(1985, 00, 01)	
	    }).attr('readonly','readonly');     
			
		
	});

/**핸드폰번호 체크**/
function checkPhone(obj, strValue) {
	 
//	 var rgEx = /(01[016789])[-](\d{4}|\d{3})[-]\d{4}$/g;  
//	 //  var strValue = f.hphone1.value+"-"+f.hphone2.value+"-"+f.hphone3.value;
//	   var chkFlg = rgEx.test(strValue);   
//	   if(!chkFlg){
//	    alert("올바른 휴대폰번호가 아닙니다.");
//	    obj.focus();
//	    return false; 
//	   }
};


function checkBtn(checkId) {
	 $('#' + checkId).prop("checked", true);
}
 

/**dateString : YYYY-MM-DD **/
function diffDateWithCurDate(dateString) {
	var today = new Date();  
	//var dateString = '${workVO.startdate}';
	//var dateString = '2015-01-06';
	  
	var dateArray = dateString.split("-");  
	  
	var dateObj = new Date(dateArray[0], Number(dateArray[1])-1, dateArray[2]);  
	  
	var betweenDay = (today.getTime() - dateObj.getTime())/1000/60/60/24;   
}

/**print**/

function openTBM(work_idx){
   var url    ="tbm";
   var title  = "tbmView";
   var status = "toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=930, height=700, top=0,left=20"; 
   window.open("tbm?work_idx=" + work_idx , title,status);  //프로그램처럽보일떈 파업 X?   
}

function openPUI(work_idx){
	   var url    ="pui";
	   var title  = "puiView";
	   var status = "toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=930, height=700, top=0,left=20"; 
	   window.open("pui?work_idx=" + work_idx , title,status);  //프로그램처럽보일떈 파업 X?   
}
function openPTW(work_idx){
	   var url    ="ptw";
	   var title  = "ptwView";
	   var status = "toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=930, height=700, top=0,left=20"; 
	   window.open("ptw?work_idx=" + work_idx, title,status);  //프로그램처럽보일떈 파업 X?   
}

function goBack() {
	alert(1);
	
	
}
