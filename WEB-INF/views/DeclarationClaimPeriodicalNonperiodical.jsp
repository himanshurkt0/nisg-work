<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/assets/css/jquery.dialogue-ui.css' />">
<script src="<c:url value='/assets/js/dailougueBox.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/assets/js/modal.min.js'/>"
	type="text/javascript"></script>
</head>

<script>
	$(function() {
		var notifyMsg = $('#notifyMsg').val();
		if (notifyMsg != "") {
			$("#dialogOk").dialog();
		}

		$("#btnShowOK").click(function() {
			$('#dialogOk').dialog('close');
		});
		

		var tabOpened = $('#tabOpened').val();
		$('.nav-tabs a[href="#' + tabOpened + '"]').tab('show');
		
		
		//Disable laptop Tab
		
	});

	function waitLoader() {
		$('#waitId').show();
	}
	
	
	function callTabController(controllerName,checkEligibity) {
		debugger;
		if (checkEligibity == '') {
			var errMessage = "You are not Elibile for this Claim !";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		} else {
			formName = controllerName + 'Form';
			document.getElementById(formName).action = controllerName;
			var form = document.getElementById(formName);
			waitLoader();
			form.submit();
		}
		

	}
	
	function checkEmail(pwdVal){
		
		var pwd=pwdVal.value;
		var specialChar =/^[a-z\d\,\s]+$/i;
		if(!pwd.match(specialChar)){
			document.getElementById("hotalName").value = "";
			 document.getElementById('hotalName').focus();
			 alert("please enter valid comment only alphabet ,numeric, comma and space are allowed.!!");
			return false;
		}
		return true;
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
	
	function validateFutureDate(id){
    	var date = document.getElementById(id).value;
		const inputDate = new Date(date); 
		//alert(inputDate);

		// Get the current date
		const currentDate = new Date();

		// Compare the input date with the current date
		if (inputDate > currentDate) {
			var errMessage = "Date cannot be in Future! Please Recheck date!!!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$("#"+id).val("");
			return;
		}
	}
	
	function validatePastDate(id){
    	var date = document.getElementById(id).value;
		const inputDate = new Date(date); 
		//alert(inputDate);

		// Get the current date
		const currentDate = new Date();

		// Compare the input date with the current date
		if (inputDate < currentDate) {
			var errMessage = "Date cannot be in Past! Please Recheck date!!!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$("#"+id).val("");
			return;
		}
	}
	
	function validatePDF(id){
		debugger;
	    var filepath = document.getElementById(id).value;
		  var fileExt = filepath.split('.').pop();
		  if(filepath == '' ){
				alert("Please select pdf file");
				return false;
			} 
		 if(fileExt.toLowerCase() != 'pdf' ){
			 document.getElementById(id).value = "";
			 document.getElementById(id).focus();
			return false;
		} 
		var sizeinbytes = document.getElementById(id).files[0].size;
		if(sizeinbytes>1048576)
		{
			alert("pdf file - max.size 1MB");
			return false;
		}
		  return true;
	}
	
	function validateLength(id){
		var element = $('#'+id).val();
		if(element.length > 100){
			alert("Input cannot be more than length of 100 !");
			return false;
		}
	}
	
	function popReimTerms(){
		var errMessage = "The reimbursement received during a particular financial year will be spent for purchase of newspapers, periodicals and books on the subjects relevant to the Bank.";
		$('#paraId').html(errMessage);
		$("#dialogOk").dialog();
		return;
	}
	
	//Non Period claim starts here

	function bookGrantHistory() {
		alert("Inside");
		return false;
	}
	
</script>

<style>
body {
	font-family: Arial;
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

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* Style the tab */
.tab {
	overflow-x: scroll;
	overflow-y: hidden;
	width: 100%;
	height: 60px;
	border: 1px solid black;
	background-color: #146C94;
	border: 1px solid black;
}

/* Style the buttons inside the tab */
.tab button {
	background-color: inherit;
	float: left;
	border: none;
	outline: none;
	cursor: pointer;
	padding: 14px 16px;
	transition: 0.3s;
	font-size: 15px;
	color: white;
}

/* Change background color of buttons on hover */
.tab button:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
	background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
	display: none;
	padding: 6px 12px;
	border: 1px solid #ccc;
	border-top: none;
}

/* Style the close button */
.topright {
	float: right;
	cursor: pointer;
	font-size: 28px;
}

.topright:hover {
	color: red;
}
</style>

<body>

	<div class="container">
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
		<ul class="nav nav-tabs">
			<li><a data-toggle='${RegularClaim }'
				onclick="callTabController('regularClaim','${RegularClaim }');"
				href="#regularClaim" style="color: #146C94;"><b><spring:message
							code="app.lang.regularClaim" /></b></a></li>
			<li><a data-toggle='${NonPeriodicClaim }'
				onclick="callTabController('nonPeriodicClaim','${NonPeriodicClaim }');"
				href="#nonPeriodicClaim" style="color: #146C94;"><b><spring:message
							code="app.lang.nonPeriodicClaims" /></b></a></li>
			<li><a data-toggle="${rvmeVehicleClaim }"
				onclick="callTabController('rvmeVehicleClaim','${rvmeVehicleClaim }');"
				href="#rvmeVehicleClaim" style="color: #146C94;"><b><spring:message
							code="app.lang.rvme" /></b></a></li>
			<li><a data-toggle="${VehicleInsurance }"
				onclick="callTabController('npVehicaleInsuranceClaim','${VehicleInsurance }');"
				href="#npVehicaleInsuranceClaim" style="color: #146C94;"><b><spring:message
							code="app.lang.vehicleInsuranceClaim" /></b></a></li>
			<li><a data-toggle="${LaptopMobileClaim}"
				onclick="callTabController('npLaptopMobileClaim','${LaptopMobileClaim }');"
				href="#npLaptopMobileClaim" style="color: #146C94;"><b><spring:message
							code="app.lang.LapMobReimb" /></b></a></li>
			<li><a data-toggle="${TabIpadClaim}"
				onclick="callTabController('tabIpadClaim','${TabIpadClaim }');"
				href="#tabIpadClaim" style="color: #146C94;"><b><spring:message
							code="app.lang.tabIpadClaim" /></b></a></li>

			
		</ul>
		<input type="hidden" id="notifyMsg" value="${notifyMsg}" />
		<input type="hidden" id="tabOpened" value="${tabOpened}" />
		<div class="tab-content">
			<div id="regularClaim" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="RegularClaimNP.jsp"/>
			</div>
			<div id="nonPeriodicClaim" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="nonPeriodicClaim.jsp"/>
			</div>
			<div id="rvmeVehicleClaim" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="rvmeVehicleClaim.jsp"/>
			</div>
			<div id="npVehicaleInsuranceClaim" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="npVehicaleInsuranceClaim.jsp"/>
			</div>
			<div id="npLaptopMobileClaim" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="npLaptopMobileClaim.jsp"/>
			</div>
			<div id="tabIpadClaim" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="npTabIpadClaim.jsp"/>
			</div>
			
			
		</div>
	</div>

</body>
</html>