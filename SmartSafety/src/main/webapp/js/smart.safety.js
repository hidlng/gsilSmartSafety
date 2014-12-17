

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


