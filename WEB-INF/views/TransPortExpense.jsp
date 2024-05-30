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
	
	function incidentalFormSubmit(){
		var formName = 'TransPortExpenseForm';
		document.getElementById(formName).action = 'saveTransPortExpenseForm';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
	}

</script>
	
<body>
<form:form id="TransPortExpenseForm" modelAttribute="reimbursementBean"
	action="" enctype="multipart/form-data">
	<form:hidden path="isVerified"/>
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
									code="label.typeOfTransfer" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="typeOfTransfer" 
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
			<spring:message code="label.transportExpenseTitle" />
		</h3>
	</div>
	<div class="form-group clearfix text-center" id="transportMsg" style="color: red; font-weight: bold;">${transportMsg}</div>
	<div class="form-group fld_hero">
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.houseHoldGoodDetails" /></label>
					
				</div>
				<div class="col-md-2">
					<form:select path="houseHoldGoodApplicable" class="form-control">
						<form:option value="">
							<spring:message code="app.lang.select.none" />
						</form:option>
						<form:option value="Y">
							<spring:message code="app.lang.yes" />
						</form:option>
						<form:option value="N">
							<spring:message code="app.lang.no" />
						</form:option>
					</form:select>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.familyShifted" /></label>
				</div>
				<div class="col-md-2">
					<form:select path="familyShifted" class="form-control">
						<form:option value="">
							<spring:message code="app.lang.select.none" />
						</form:option>
						<form:option value="Y">
							<spring:message code="app.lang.yes" />
						</form:option>
						<form:option value="N">
							<spring:message code="app.lang.no" />
						</form:option>
					</form:select>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.goodShiftDate" /></label>
				</div>
				<div class="col-md-2">
					<form:input type = "date" path="goodShiftDate" class = "form-control"/>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.transportCost" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="transportCost"  class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.eligibleDeclarationAmount" /></label>
				</div>
				<div class="col-md-2">
					<form:input  path="eligibleDeclarationAmount" class = "form-control"/>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.distanceCovered" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="distanceCovered"  class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.grossWeight" /></label>
				</div>
				<div class="col-md-2">
					<form:input  path="grossWeight" class = "form-control"/>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.totalFare" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="totalTransportFare"  class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.totalCost" /></label>
				</div>
				<div class="col-md-2">
					<form:input  path="totalTransportCost" class = "form-control"/>
				</div>
				
			</div>
		</div>
		
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-3">
					<form:checkbox path="incidentalAgreement"
						style="float: right; width: 17px;" class="form-control" />

				</div>
				<div class="col-md-6">

					<label class="required" style="float: left;"><spring:message
							code="label.reimbursementTerms" /></label>
				</div>

			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-12">
				<br>
				<div class="col-md-5"></div>
				<div class="col-md-2">
					<button id = "incidentalSubmit" onclick="incidentalFormSubmit();"
						class="btn btn-primary form-control">
						<spring:message code="app.lang.submit" />
					</button>
				</div>
			</div>
		</div>
	</div>
</form:form>
</body>
</html>