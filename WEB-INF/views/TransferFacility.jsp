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
	});

	function waitLoader() {
		$('#waitId').show();
	}
	
	
	function callTabController(controllerName,checkEligibity) {
		debugger;
		if (checkEligibity == 'disable') {
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
	
	//Non Period claim starts here

	function bookGrantHistory() {
		alert("Inside");
		return false;
	}
	
</script>

<style>
body {
	font-family: Arial, Helvetica, sans-serif;
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
			<li><a data-toggle='${IncidentalExpense }'
				onclick="callTabController('IncidentalExpense','${lastTransFlag }');"
				href="#IncidentalExpense" style="color: #146C94;"><b><spring:message
							code="app.lang.IncidentalExpense" /></b></a></li>
			<li><a data-toggle='${TransPortExpense }'
				onclick="callTabController('TransPortExpense','${lastTransFlag }');"
				href="#TransPortExpense" style="color: #146C94;"><b><spring:message
							code="app.lang.TransPortExpense" /></b></a></li>
			<li><a data-toggle="${CarExpense }"
				onclick="callTabController('CarExpense','${lastTransFlag }');"
				href="#CarExpense" style="color: #146C94;"><b><spring:message
							code="app.lang.CarExpense" /></b></a></li>
			<li><a data-toggle="${VehicleRegistrationTransfer }"
				onclick="callTabController('VehicleRegistrationTransfer','${lastTransFlag }');"
				href="#VehicleRegistrationTransfer" style="color: #146C94;"><b><spring:message
							code="app.lang.VehicleRegistration" /></b></a></li>
			
		</ul>
		<input type="hidden" id="notifyMsg" value="${notifyMsg}" />
		<input type="hidden" id="tabOpened" value="${tabOpened}" />
		<div class="tab-content">
			<div id="IncidentalExpense" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="IncidentalExpense.jsp"/>
			</div>
			<div id="TransPortExpense" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="TransPortExpense.jsp"/>
			</div>
			<div id="CarExpense" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="CarExpense.jsp"/>
			</div>
			<div id="VehicleRegistrationTransfer" class="tab-pane fade"
				style="overflow-y: scroll; overflow-x: hidden; height: 450px; margin-bottom: 20px;">
				<jsp:include page="VehicleRegistrationTransfer.jsp"/>
			</div>
		</div>
	</div>

</body>

</html>