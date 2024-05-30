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

	$(function() {

		var incidentalClaimed = $("#incidentalClaimed").val();

		if (incidentalClaimed == "Y") {
			$('#incidentalClaimed').prop("disabled", true);
			$('#incidentalPaymentMode').prop("disabled", true);
			$('#incidentalFamilyShiftDate').prop("disabled", true);
			$('#incidentalAgreement1').prop("disabled", true);
			$('#incidentalAgreement1').prop("checked", true);
			$('#incidentalSubmit').prop("disabled", true);
		}

	});

	function enableNextFormElement(id) {
		var elementValue = $("#" + id).val();

		if (id == "incidentalClaimed" && elementValue != "") {
			$('#incidentalPaymentMode').prop("disabled", false);
		}

		if (id == "incidentalClaimed"
				&& (elementValue == "" || elementValue == "N")) {
			$('#incidentalPaymentMode').prop("disabled", true);
			$('#incidentalFamilyShiftDate').prop("disabled", true);
			$('#incidentalAgreement1').prop("disabled", true);
			$('#incidentalAgreement1').prop("checked", true);
			$('#incidentalSubmit').prop("disabled", true);
		}

		if (id == "incidentalPaymentMode" && elementValue != "") {
			$('#incidentalFamilyShiftDate').prop("disabled", false);
			$('#incidentalAgreement1').prop("disabled", false);
			$('#incidentalAgreement1').prop("checked", false);
			$('#incidentalSubmit').prop("disabled", false);
		}

		if (id == "incidentalPaymentMode" && elementValue == 'H') {
			$('#incidentalFamilyShiftFalg').val("N");
		}

		if (id == "incidentalPaymentMode" && elementValue == '') {
			$('#incidentalFamilyShiftFalg').val('');
			$('#incidentalFamilyShiftDate').prop("disabled", true);
			$('#incidentalAgreement1').prop("disabled", true);
			$('#incidentalAgreement1').prop("checked", true);
			$('#incidentalSubmit').prop("disabled", true);
		}

		if (id == "incidentalPaymentMode" && elementValue == 'F') {
			$('#incidentalFamilyShiftFalg').val("Y");
		}
	}

	function saveIncidentalExpense() {

		var formName = 'IncidentalExpenseForm';
		document.getElementById(formName).action = "saveIncidentalExpense";
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
	}
</script>

<body>
	<form:form id="IncidentalExpenseForm"
		modelAttribute="reimbursementBean" action=""
		enctype="multipart/form-data">
		<form:hidden path="isVerified" />
		<br>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.tabletTitle" />
			</h3>
		</div>
		<div class="form-group clearfix text-center" id="incidentalMessage"
			style="color: red; font-weight: bold;">${incidentalMessage}</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.incidentalClaimed" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="incidentalClaimed"
							onChange="enableNextFormElement(this.id)" class="form-control">
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
								code="label.incidentalPaymentMode" /></label>
					</div>

					<div class="col-md-2">
						<form:select path="incidentalPaymentMode"
							onChange="enableNextFormElement(this.id)" class="form-control"
							disabled="true">
							<form:option value="">
								<spring:message code="app.lang.select.none" />
							</form:option>
							<form:option value="F">
								<spring:message code="label.Full" />
							</form:option>
							<form:option value="H">
								<spring:message code="label.Half" />
							</form:option>
						</form:select>
					</div>

				</div>
			</div>
			<br>
			<div class="form-group row ">
				<div class="col-md-12">
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.incidentalFamilyShiftFalg" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="incidentalFamilyShiftFalg" readOnly="true"
							class="form-control">
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
								code="label.incidentalFamilyShiftDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="incidentalFamilyShiftDate"
							disabled="true" onchange="validateFutureDate(this.id)"
							class="form-control" />
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
						<button id="incidentalSubmit" onclick=" saveIncidentalExpense();"
							disabled="true" class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>