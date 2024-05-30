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
		

	});

	function waitLoader() {
		$('#waitId').show();
	}

</script>
	
<body>
<form:form id="VehicleRegistrationTransferForm" modelAttribute="reimbursementBean"
	action="" enctype="multipart/form-data">
	
	<br>
	<div class="form-group fld_hero">
				<div class="form-group row ">
					<div class="col-md-12">
						<br>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.customer.name" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="empName" 
								class="form-control" readonly="true" />
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.empCode" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="empCode"
								class="form-control" readonly="true" />
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<div class="col-md-2">
							<label class="required"><spring:message
									code="label.typeOfTransfer" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="typeOfTransfer"  value = "${typeOfTransfer }"
								class="form-control" readonly="true" />
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="label.accomodationType" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="accomodationType"
								class="form-control" readonly="true" />
						</div>
					</div>
				</div>

			</div>
	<div
		class="card-header d-flex flex-row align-items-center justify-content-between">
		<h3 class="m-0 font-weight-bold text-white">
			<spring:message code="label.vehicleReRegistrationTitle" />
		</h3>
	</div>
	<div class="form-group clearfix text-center" id="transportMsg" style="color: red; font-weight: bold;">${transportMsg}</div>
	<div class="form-group fld_hero">
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.oldRegNumber" /></label>
					
				</div>
				<div class="col-md-2">
					<form:input path="oldRegNumber" onClick = "" class="form-control" />
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.newRegNumber" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="newRegNumber" onClick = "" class="form-control" />
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.newRegDate" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="newRegDate" class = "form-control"/>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.regCost" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="regCost"  class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.regStatus" /></label>
				</div>
				<div class="col-md-2">
					<form:input  path="regStatus" class = "form-control"/>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.remarkByCap" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="regRemarkByCap"  class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-3" style="display: none;">
						<label class="required"><spring:message
								code="label.fileName" /></label>
					</div>
					<div class="col-md-2" style="display: none;">
						<form:hidden path="regFile" class="form-control" />
					</div>
				
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-12">
				<br>
				<div class="col-md-5"></div>
				<div class="col-md-2">
					<button id = "regSubmitButton" onclick="regSubmitFunction();"
						class="btn btn-primary form-control">
						<spring:message code="label.apply" />
					</button>
				</div>
			</div>
		</div>
	</div>
</form:form>
</body>
</html>