/**작업등록 **/
/** set Child Category Of Idx (add option to target Selectbox) **/
function setChildCategoryOf(idx, targetId) {
	
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
 			
 			
 			$('#' + targetId).append('<option id="" value="">-----------선택----------</option>');
 			for(var i = 0 ; i < length; i ++) {
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

/**
 * 
 * @param categoryIdx  해당 category의 code list 출력
 * @param targetId	대상 select box (name속성)
 */
function setCode(categoryIdx , targetId) {
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
	  			$('#' + targetId).append('<option val="test">-----------선택----------</option>');
	  			
	  			var length = 0;
	  			for(var prop in codeList){
	 			    if(codeList.hasOwnProperty(prop))
	 			        length++;
	 			}
	 			
	  			for(var i = 0 ; i < length; i ++) {
	  				//$('#' + targetId).append('<option id="CODE_' + codeList[i].code + '" value="' + codeList[i].name + '">' + codeList[i].name  + '</option>');
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
	// alert(codeNum +" " + typeNum);
	 $.ajax({
	  		type : "POST",
	  		url : "http://54.64.28.175:8080/RiskMatrix/actions/Data.action?getDetailByJSON=",
	  		data : {code : codeNum, type : typeNum },
	  		dataType : "jsonp",
	  	    jsonp : "callback",
	  		cache : false,
	  		success : function(json) {
	  			alert(json.workVO.guide);
	  		},
	  		error : onError
		});
}

function setWorkDetail(objId) {
	 var idx = $("#" + objId + " option:selected" ).attr('id');
	 getCodeDetail(idx, 1);
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
		 startTime: new Date(0,0,0,0,0,0)
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


