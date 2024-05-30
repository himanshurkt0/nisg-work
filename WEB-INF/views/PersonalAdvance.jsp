<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@page
	import="org.springframework.http.StreamingHttpOutputMessage.Body"%>
<%@page
	import="com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<spring:message code="" />
<!DOCTYPE html>
<html lang="en">
<head>
<!-- <meta charset="utf-8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<%@ page import="java.util.*"%>
<title><spring:message code="app.lang.HomeTitle" /></title>


<link rel="stylesheet" type="text/css"
	href="<c:url value='/assets/css/jquery.dialogue-ui.css' />">
<script src="<c:url value='/assets/js/dailougueBox.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/assets/js/modal.min.js'/>"
	type="text/javascript"></script>


<script>
  $( function() {
	  
	 var notifyMsg = $('#notifyMsg').val();
	 if(notifyMsg != ""){
    $("#dialogOk").dialog();
    $("#btnShowOK").click(function () {
    	$('#dialogOk').dialog('close');
    	var dependent = '${dependentValue}';
	 	var coursePersuevalue = '${coursePersuevalue}';
	 	var coursePassedValue = '${coursePassedValue}';
	 	var courseYearValue = '${courseYearValue}';
	 	var bill = '${billUpload}';
	 	$('#dependents option[value = '+dependent+']').attr('selected',true);
	 	$('#coursePersued option[value = '+coursePersuevalue+']').attr('selected',true);
	 	$('#coursePassed option[value = '+coursePassedValue+']').attr('selected',true);
	 	$('#courseYear option[value = '+courseYearValue+']').attr('selected',true);
	 	$('#billUpload').val(bill);
    }); 
	 }
    
	 $(document).ready(function() {
	 //	$('#finAssistHistoryDatatable').DataTable();
	 	
	 });
    
  } );
  </script>

<script type="text/javascript">
	
	window.onunload = function() {
		null
	};
	
		 
	
	$(document).ready(function() {
		$('#coursePersued').on('change', function() {
			var dependentCode = $('#dependents').val();
			var coursePersued = $(this).val();
			if(dependentCode == ""){
				alert("Please select Dependents first.");
				$(this).val("");
				return;
			}
			$.ajax({
				url : 'getDependentsCount',
				type : 'POST',
				data : {
					dependentCode : dependentCode,
					coursePersued : coursePersued
				},

				success : function(data) {
					if(parseInt(data) >= 3 && parseInt(coursePersued) == 5){
						alert("Financial Assistance Facility already availed for Dependent");
					} else if(parseInt(data) > 1 && parseInt(coursePersued) == 6){
						alert("Financial Assistance Facility already availed for Dependent");
					}
				}

			});

		})
	});
	
	function validatePercentage(){
		
		var percentage = $('#percentage').val();
		if(parseInt(percentage) < 50){
			alert("Marks entered is not eligible for Financial Assistance Scheme for dependent.");
			$('#percentage').val("");
		}
		
	}
	
	function isNumber1(evt) 
	{
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if ((charCode < 47 || charCode > 57) || (charCode == 47))
	    {
	    	 alert("Please Enter Valid Number Only")
	          return false;
	    }
	    return true;
	}
	
	
	function validateDate(){
		var fromDate = $('#startDate').val();
		var toDate = $('#endDate').val();
		
		if(fromDate == ""){
			alert("Please Select Session Start Date.");
			$('#endDate').val("");
			return;
		}
		
		var arr1 = fromDate.split('-');  
	      var arr2 = arr1[0] + '/' + arr1[1] + '/' + arr1[2];
	     var arrs1 = toDate.split('-');
	      var arrs2 = arrs1[0] + '/' + arrs1[1] + '/' + arrs1[2]; 
	       date1 = new Date(arr2);  
	      date2 = new Date(arrs2);  
	      var time_difference = date2.getTime() - date1.getTime();  
	      var days_difference = time_difference / (1000 * 60 * 60 * 24); 
	    
	      var noOfDays=days_difference;
	      if(noOfDays < 0){
	    	  alert("End Date cannot be less that Begin Date");
	    	  $('#endDate').val("");
	    	  return;
	      }
	      var months = Math.floor(noOfDays/30);
	      if(parseInt(months) > 12){
	    	  alert("Session Duration cannot be more than 12 Months.");
	    	  $('#endDate').val("");
	    	  return;
	      } else {
	    	  $('#duration').val(months);
	      }
	      
	      if(parseInt(months) != ""){
	    	  var coursePersued = $('#coursePersued').val();
	    	  var coursePassed = $('#coursePassed').val();
	    	  if(parseInt(coursePersued) == 5 && parseInt(coursePassed) == 4) {
	    		  $('#dependentGrade').val('Z');
	    	  } else {
	    		  var empGrade = $('#empGrade').val();
	    		  $('#dependentGrade').val(empGrade);
	    	  }
	    	  var sessionStart = "2015-09-01";
	    	  var minStartDate = new Date(sessionStart);
	    	  var startDateDiff =  minStartDate.getTime() - date1.getTime();
	    	  var difference = startDateDiff / (1000 * 60 * 60 * 24); 
	    	  if(startDateDiff > 0) {
	    		  $('#startDate').val(sessionStart);
	    	  }
	      }
	      
	}
	
		function getPaymentvalue(){
			 var coursePersued = $('#coursePersued').val();
	    	  var coursePassed = $('#coursePassed').val();
	    	  var empGrade =  $('#empGrade').val();
			var percentage = $('#percentage').val();
			 var courseYear = $('#courseYear').val();
			 var startDate = $('#startDate').val();
			
			$.ajax({
				url : 'getPaymentValue',
				type : 'POST',
				data : {
					coursePassed : coursePassed,
					coursePersued : coursePersued,
					empGrade : empGrade,
					courseYear : courseYear,
					startDate : startDate,
					percentage : percentage,
					appConstantCode : '555',
				},

				success : function(data) {
					$('#payment').val(data);
				}

			});
			
		}
		 function waitLoader(){
		  	   $('#waitId').show();  
		  }
	     
	     function submitForm(){
	    	 var dependents = $('#dependents').val();
	    	 if(dependents == ""){
	    		 alert("Please select dependent.");
	    		 return;
	    	 }
	    	 var coursePersued = $('#coursePersued').val();
	    	 if(coursePersued == ""){
	    		 alert("Please Select Course Persued.");
	    		 return;
	    	 }
	    	  var coursePassed = $('#coursePassed').val();
	    	  if(coursePassed == ""){
	    		  alert("Please Select Course Passed.");
	    		  return;
	    	  }
	    	  var courseYear = $('#courseYear').val();
	    	  if(courseYear == ""){
	    		  alert("Select Course Year First.");
	    		  return;
	    	  }
	    	  var percentage = $('#percentage').val();
	    	  if(percentage == ""){
	    		  alert("Please Enter Percentage.");
	    		  return;
	    	  }
	    	  var startDate = $('#startDate').val();
	    	  if(startDate == ""){
	    		  alert("Please Select Session Start Date.");
	    		  return;
	    	  }
	    	  var endDate = $('#endDate').val();
	    	  if(endDate == ""){
	    		  alert("Please Select Session End Date.");
	    		  return;
	    	  }
	    	  var duration = $('#duration').val();
	    	  if(duration == ""){
	    		  alert("Duration Error !. Please retry.");
	    		  return;
	    	  }
	    	  var payment = $('#payment').val();
	    	  if(payment <= 0 || payment == ""){
	    		  alert("Payment value Error !. Please retry.");
	    		  return;
	    	  }
	    	  
	    	  var empCode = $('#empCode').val();
	    	  
			  waitLoader();
	    	//  document.myform.action = 'personalAdvanceSave';
			  $('#personalAdvSubmit').submit();
			   
	    	
	     } 
	     
	    
	     function ajaxCallForFinAssist(){
	    	 var trRow = "";
	    	
	    	 $.ajax({
	    		type : "GET",
	    		url : 'getFinAssistData',
	    		data : {valThis : "test"},
	    		success : function(data){
	    			if(data.length != 0){
	    			  trRow = trRow + ("<thead><tr class='firt_table_row'>" +
	    					  "<th><spring:message code='app.lang.empCode'/></th>" +
	    					  "<th><spring:message code='label.sNo'/></th>" +
	                           "<th><spring:message code='label.relation'/></th>" +
	                           "<th><spring:message code='label.dependentName'/></th>" +
	                           "<th><spring:message code='label.coursePassCode'/></th>" +
	                           "<th><spring:message code='label.coursePersueCode'/></th>" +
	                           "<th><spring:message code='label.percentage'/></th>" +
	                           "<th><spring:message code='label.sessionStart'/></th>" +
	                           "<th><spring:message code='label.sessionEnd'/></th>" +
	                           "<th><spring:message code='label.createdDate'/></th>" +
	                           "<th><spring:message code='label.verifyDate'/></th>" +
	                           "<th><spring:message code='label.verifyState'/></th>" +
	                           "<th><spring:message code='label.year'/></th>" +
	                           "<th><spring:message code='label.amount'/></th>" +
	                           "<th><spring:message code='label.code'/></th>" +
	                           "<th><spring:message code='label.bill'/></th>" +
	                           "<th><spring:message code='label.viewBill'/></th>" +
	                           "<th><spring:message code='label.edit'/></th>");
	    			  
	    			  var length = data.length;
	    			  for(var i =0; i < length; i++){
	    				  var editData = dataItem = JSON.stringify(data[i]);
	    				  
	    				  trRow = trRow + "<tr>";
	    				  for(var j = 0; j < data[i].length; j++){
	    					  trRow = trRow + ("<td>"+data[i][j]+"</td>");
	    				  }
	    				  
	    				  var array = editData.split(",");
							var element = array[11].substr(1,array[11].length-2);
							trRow = trRow
							+ "<td><a href = 'downloadFinancialAssistBill/{"+data[i][0]+"}/{"+data[i][1]+"}' target='_blank' class = 'form-control'><spring:message code='label.view'/></a></td>";
							if(element == 'Y'){
								trRow = trRow
								+ "<td><button disabled = 'true' id = 'editButton' class='form-control' onclick = 'fillScholarshipData("
								+ editData
								+ ");'><spring:message code='label.edit'/></button></td></tr>";
								
							} else {
								trRow = trRow
								+ "<td><button id = 'editButton' class='form-control' onclick = 'fillScholarshipData("
								+ editData
								+ ");'><spring:message code='label.edit'/></button></td></tr>";
							}
	    			  }
	    			  trRow = trRow + "</tbody>";
	    			}
	    			 $('#finAssistHistoryDatatable').html(trRow);
					 $('#waitId').hide();
	    		}
	    	 });
	     }
	   /*   $(function() {
	    		$('#editButton').click(function() {
	    		    $('#finAssistHistoryData').modal('hide');
	    		});
	    		}); */
	function fillFinAssisData(value){
		
		var empCode = value[0];
		var srNo = value[1];
		$.ajax({
			type : "GET",
    		url : 'getFinAssistDataToEdit',
    		data : {empCode : empCode,
    				srNo : srNo},
    	    success : function(editData){
    			if(editData != null){
    				var data = JSON.stringify(editData);
    				data = data.slice(2,-2);
    				var dataArray = data.split(',');
    				$('#dependents option[value = '+dataArray[2]+']').attr('selected',true);
    			 	$('#coursePersued option[value = '+dataArray[4]+']').attr('selected',true);
    			 	$('#coursePassed option[value = '+dataArray[3]+']').attr('selected',true);
    			 	$('#courseYear option[value = '+dataArray[8]+']').attr('selected',true);
    			 	$('#percentage').val(dataArray[5]);
    			 	$('#payment').val(dataArray[9]);
    			 	var startDate = dataArray[6].slice(1,-1);
    			 	var endDate = dataArray[7].slice(1,-1);
    			 	$('#startDate').val(startDate);
    			 	$('#endDate').val(endDate);
    			 	var blob = new Blob([editData[0][11]], {type: "application/pdf"}); 
    			 //	var fileName = "blobFile.pdf";
    			 //	 const newFile = new File([blob], fileName, {type: 'application/pdf'});
    			 	 
    			 //  $('#billUpload').val(fileName);
    				 $('#lv_sr_no').val(srNo);
    				 validateDate();
    				debugger;	
    			//	$("#finAssistHistoryData").removeClass('modal fade show').addClass('modal fade hide');
    				var closeModal = document.getElementById("modalClose");
    				closeModal.click();
    				document.getElementById("finAssistHistoryData").style.display = "none";
    				
    			}
    		}
		});
		
	}
	
	
</script>

<style type="text/css">
.notification {
	background-color: white;
	color: black;
	text-decoration: none;
	padding: 4px 7px;
	position: relative;
	display: inline-block;
	border-radius: 2px;
}

.notification .badge {
	position: absolute;
	top: -10px;
	right: -10px;
	padding: 5px 10px;
	border-radius: 50%;
	background-color: red;
	color: white;
}
</style>
<style>
div.scrollBar {
	margin: 0px;
	padding: 0px;
	width: 100%;
	height: 100%;
	overflow: auto;
	text-align: justify;
}

.modal-body div.scrollBar {
	height: 350px;
}

.loader-main {
	position: fixed;
	text-align: center;
	width: 100%;
	left: 0;
	vertical-align: middle;
	right: 0;
	z-index: 99999;
	background: rgba(0, 0, 0, 0.56);
	top: 0;
	bottom: 0;
}

.loader {
	top: 0;
	bottom: 0;
	display: inline-block;
	margin: auto;
	border: 16px solid #b8ffc7;
	border-radius: 50%;
	border-bottom: 16px solid #71baea;
	border-top: 16px solid #71baea;
	width: 120px;
	height: 120px;
	-webkit-animation: spin 2s linear infinite;
	animation: spin 2s linear infinite;
	position: absolute;
}

.loader {
	border: 16px solid #f3f3f3;
	border-radius: 50%;
	border-top: 16px solid blue;
	border-right: 16px solid green;
	border-bottom: 16px solid red;
	border-left: 16px solid pink;
	width: 120px;
	height: 120px;
	-webkit-animation: spin 2s linear infinite;
	animation: spin 2s linear infinite;
}

@
-webkit-keyframes spin { 0% {
	-webkit-transform: rotate(0deg);
}

100%
{
-webkit-transform
:
 
rotate
(360deg);
 
}
}
@
keyframes spin { 0% {
	transform: rotate(0deg);
}

100%
{
transform
:
 
rotate
(360deg);
 
}
}

/* The Modal (background) */
/
* .modal {
	display: none;
	position: fixed;
	z-index: 1;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
	-webkit-animation-name: fadeIn;
	-webkit-animation-duration: 0.4s;
	animation-name: fadeIn;
	animation-duration: 0.4s
}

body {
	padding-right: 0 !important
}

.modal-content {
	top: calc(100% - 90%);
	left: calc(100% - 90%);
	left: calc(100% - 82%);
	background-color: #fefefe;
	width: 100%;
	max-height: 800px;
	overflow: auto;
	-webkit-animation-name: slideIn;
	-webkit-animation-duration: 0.4s;
	animation-name: slideIn;
	animation-duration: 0.4s
}
</style>
</head>

<body id="page-top">

	<!-- Main Content -->
	<div id="content">
		<div id="dialogOk" style="display: none;" align="center">
			<p style="font-size: 12px; font-weight: bold;" id="paraId">${notifyMsg }</p>
			<div class="" style="clear: both;">
				<input type="button" id="btnShowOK" value="OK"
					class="btn btn-primary" />

			</div>
		</div>
		<div id="waitId" class="loader-main" style="display: none">
			<div class="loader"></div>
			<span>Please Wait..</span>
		</div>
		<div id="dialog" style="display: none;" align="center">
			<p style="font-size: 12px; font-weight: bold;" id="paraSubId">
				<spring:message code="app.lang.submitMsg" />
			</p>
			<div class="" style="clear: both;">
				<input type="button" id="btnShowYes" value="Yes"
					class="btn btn-primary" /> <input type="button" id="btnShowNo"
					value="No" class="btn btn-primary" />
			</div>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="app.lang.customer.details" />
			</h3>
		</div>
		<form:form method="POST" modelAttribute="saveFinancialAssist"
			id="personalAdvSubmit" action="personalAdvanceSave"
			enctype="multipart/form-data">
			<form:input type="hidden" path="dependentGrade" id="dependentGrade" />
			<form:input type="hidden" path="lv_sr_no" id="lv_sr_no" />
			<input type="hidden" name="notifyMsg" id="notifyMsg"
				value="${notifyMsg }" />
			<input type="hidden" name="isUpdate" id="isUpdate" value="" />
			<div class="form-group fld_hero">
				<div class="form-group row ">
					<div class="col-md-12">
						<br>
						<div class="col-md-2">
							<label class="required" style="text-align: right"><spring:message
									code="app.lang.customer.name" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="empName" value="${empName }"
								class="form-control" readonly="true" />
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.empCode" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="empCode" value="${empCode }"
								class="form-control" readonly="true" />
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.customer.category" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="category" id="category" value="${catCode }"
								class="form-control" readonly="true" />
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.grade" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="empGrade" id="empGrade" value="${empGrade }"
								class="form-control" readonly="true" />
						</div>
					</div>
				</div>

			</div>

			<div
				class="card-header d-flex flex-row align-items-center justify-content-between">
				<h3 class="m-0 font-weight-bold text-white">
					<spring:message code="app.lang.course.dependents" />
				</h3>
				<button type="button" class="btn btn-success form-control"
					data-toggle="modal" data-target="#finAssistHistoryData"
					onclick="waitLoader();ajaxCallForFinAssist();" style="width: 50px;">
					<spring:message code="app.lang.history" />
				</button>
				<%-- <a data-bs-toggle="modal" data-bs-target="#finAssistHistoryData" onclick="ajaxCallForFinAssist();" href=""><h3 class="m-0 font-weight-bold text-white">
						<spring:message code="app.lang.history" />
					</h3></a> --%>
			</div>
			<div class="form-group fld_hero">
				<div class="form-group row">
					<div class="col-md-12">
						<br>

						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.dependents" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="dependents" id="dependents"
								style="max-width: 100%;" class="form-control"
								selected="${dependentValue }">
								<option value=""><spring:message code="app.lang.select" /></option>
								<c:forEach items="${dependents }" var="entry">
									<option value="${entry.key }">${entry.value }</option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.course.passed" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="coursePassed" id="coursePassed"
								style="max-width: 100%;" class="form-control">
								<option value=""><spring:message code="app.lang.select" /></option>
								<c:forEach items="${schoolCoursePassed }" var="entry">
									<option value="${entry.key }">${entry.value }</option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-md-2">
							<label class="required" style="text-align: right"><spring:message
									code="app.lang.emp.course.persued" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="coursePersued" id="coursePersued"
								style="max-width: 100%;" class="form-control">
								<option value=""><spring:message code="app.lang.select" /></option>
								<c:forEach items="${schoolCoursePersue }" var="entry">
									<option value="${entry.key }">${entry.value }</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<br>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.percentage" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="percentage" id="percentage"
								onkeypress="return isNumber1(event)"
								onchange="validatePercentage();" class="form-control" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.startDate" /></label>
						</div>
						<div class="col-md-2">
							<form:input type="date" path="startDate" id="startDate"
								class="form-control" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.endDate" /></label>
						</div>
						<div class="col-md-2">
							<form:input type="date" path="endDate" id="endDate"
								onchange="validateDate();" class="form-control" />
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<br>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.year" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="courseYear" id="courseYear"
								onchange="getPaymentvalue();" class="form-control">
								<option value=""><spring:message code="app.lang.select" /></option>
								<option value="1">I</option>
								<option value="2">II</option>
								<option value="3">III</option>
							</form:select>
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.duration" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="duration" id="duration" class="form-control"
								readOnly="true" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.payment" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="payment" id="payment" class="form-control"
								readOnly="true" />
						</div>

					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<br>

						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.uploadBill" /></label>
						</div>
						<div class="col-md-2">
							<form:input type="file" onchange="checkFileSize()"
								path="billUpload" id="billUpload" accept=".pdf"
								class="form-control" required="required" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.verifyRemark" /></label>
						</div>
						<div class="col-md-6">
							<form:input path="remark" id="remark" class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group row">
					<div class="col-md-12">
						<br>
						<center>
							<button type="submit" onclick="waitLoader();submitForm();"
								class="btn btn-success">
								<spring:message code="app.lang.submit" />
							</button>
						</center>
					</div>
				</div>
		</form:form>
	</div>


	<!-- End of Main Content -->


	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>




	<!-- <script>
		var coll = document.getElementsByClassName("collapsible");
		var i;
		for (i = 0; i < coll.length; i++) {
			coll[i].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.maxHeight) {
					content.style.maxHeight = null;
				} else {
					content.style.maxHeight = content.scrollHeight + "px";
				}
			});
		}
	</script> -->

	<div class="modal fade" id="finAssistHistoryData"
		data-backdrop="static" data-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px; height: 801.481482px;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 align="center">
						<spring:message code="app.lang.finAssistHistoryHeading" />
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive"
						style="overflow-y: scroll; overflow-x: scroll; height: auto; margin-bottom: 50px;">
						<table class="table table-bordered"
							style="width: 100%; border: 1px solid #000; margin: 15px 0; overflow: auto; height: 50px;"
							id="finAssistHistoryDatatable">
						</table>
					</div>
				</div>
				<center>
					<button type="button" class="btn btn-info" id="modalClose"
						data-dismiss="modal" aria-label="Close">
						<spring:message code="app.label.close" />
					</button>
				</center>
			</div>
		</div>
	</div>
</body>









</html>