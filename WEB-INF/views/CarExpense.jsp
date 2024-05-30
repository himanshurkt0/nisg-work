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

		var carShiftedSelected = $("#carShifted").val();
		if (carShiftedSelected == "Y") {
			$('#carShifted').prop("disabled", true);
			$('#carShiftDate').prop("disabled", true);
			$('#carDriver').prop("disabled", true);
			$('#carDistance').prop("disabled", true);
			$('#carRatePerKm').prop("disabled", true);
			$('#carTotal').prop("disabled", true);
			$('#decBasicAmount').prop("disabled", true);
			$('#carClaimAmount').prop("disabled", true);
		} else {
			$('#carShiftDate').prop("disabled", false);
			$('#carDriver').prop("disabled", false);
			$('#carDistance').prop("disabled", false);
			$('#carRatePerKm').prop("disabled", false);
			$('#carTotal').prop("disabled", false);
			$('#decBasicAmount').prop("disabled", false);
			$('#carClaimAmount').prop("disabled", false);
		}

	});

	function getSubTotal() {
		var carRatePerKm = $("#carRatePerKm").val();
		var decBasicAmount = $("#decBasicAmount").val();

		var carDistance = $("#carDistance").val();

		var carSubTotal = parseInt(carRatePerKm) * parseInt(carDistance);
		var carTotal = parseInt(carSubTotal) + parseInt(decBasicAmount);
		$("#carTotal").val(carSubTotal);
		$("#carClaimAmount").val(carTotal);
		
	}

	function disableFormElements() {
		var carShiftedSelected = $("#carShifted").val();
		if (carShiftedSelected == "N") {
			$('#carShiftDate').prop("disabled", true);
			$('#carDriver').prop("disabled", true);
			$('#carDistance').prop("disabled", true);
			$('#carRatePerKm').prop("disabled", true);
			$('#carTotal').prop("disabled", true);
			$('#decBasicAmount').prop("disabled", true);
			$('#carClaimAmount').prop("disabled", true);
		} else {
			$('#carShiftDate').prop("disabled", false);
			$('#carDriver').prop("disabled", false);
			$('#carDistance').prop("disabled", false);
			$('#carRatePerKm').prop("disabled", false);
			$('#carTotal').prop("disabled", false);
			$('#decBasicAmount').prop("disabled", false);
			$('#carClaimAmount').prop("disabled", false);
		}

		var carShiftDate = $('#carShiftDate').val();
		if (carShiftDate == "") {
			$('#carDriver').prop("disabled", true);
			$('#carDistance').prop("disabled", true);
			$('#carRatePerKm').prop("disabled", true);
			$('#carTotal').prop("disabled", true);
			$('#decBasicAmount').prop("disabled", true);
			$('#carClaimAmount').prop("disabled", true);
		} else {
			$('#carDriver').prop("disabled", false);
			$('#carDistance').prop("disabled", false);
			$('#carRatePerKm').prop("disabled", false);
			$('#carTotal').prop("disabled", false);
			$('#decBasicAmount').prop("disabled", false);
			$('#carClaimAmount').prop("disabled", false);
		}
	}

	function waitLoader() {
		$('#waitId').show();
	}

	function saveCarExpenseForm() {
		var formName = 'CarExpenseForm';
		document.getElementById(formName).action = 'saveCarExpenseForm';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
	}

	function getRate(id) {
		var selectedButton = document.getElementById(id).value;
		var carShiftDate = document.getElementById("carShiftDate").value;
		waitLoader();
		$.ajax({
			type : "post",
			url : "getCarExpenseRate", //this is my servlet
			data : {
				selectedButton : selectedButton,
				carShiftDate : carShiftDate
			},
			success : function(msg) {
				var value1 = msg.split("-")[0];
				var value2 = '0';

				if (selectedButton == 'D') {
					value2 = msg.split("-")[1];
				}

				$("#carRatePerKm").val(value1);
				$("#decBasicAmount").val(value2);
				$('#carSubmitButton').prop("disabled", false);
				getSubTotal();
				$('#waitId').hide();
			}
		});
	}
</script>

<body>
	<form:form id="CarExpenseForm" modelAttribute="reimbursementBean"
		action="" method="POST" enctype="multipart/form-data">
		<form:hidden path="isVerified" />
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
						<form:input path="typeOfTransfer" class="form-control"
							readonly="true" />
					</div>
					<div class="col-md-3"></div>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.accomodationType" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="accomodationType" class="form-control"
							readonly="true" />
					</div>
				</div>
			</div>

		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.carExpenseTitle" />
			</h3>
		</div>
		<div class="form-group clearfix text-center" id="transportMsg"
			style="color: red; font-weight: bold;">${transportMsg}</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.carShifted" /></label>

					</div>
					<div class="col-md-2">
						<form:select path="carShifted" class="form-control"
							onChange="disableFormElements();">
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
								code="label.carShiftDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="carShiftDate"
							onChange="disableFormElements()" class="form-control" />
					</div>
				</div>
			</div>
			<br>
			<div style="background-color: white">
				<div class="form-group row">
					<div class="col-md-12">

						<div class="col-md-2">
							<label class="required"><spring:message
									code="label.distanceCovered" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="carDistance" 
								class="form-control" />
						</div>
						<div class="col-md-2"></div>
						<div class="col-md-3">
							<label class="required"><spring:message
									code="label.carDriver" /></label>

						</div>
						<div class="col-md-2">
							<form:select path="carDriver" class="form-control"
								onChange="getRate(this.id);">
								<form:option value="">
									<spring:message code="app.lang.select.none" />
								</form:option>
								<form:option value="R">
									<spring:message code="label.Others" />
								</form:option>
								<form:option value="D">
									<spring:message code="Label.DriveBy" />
								</form:option>
							</form:select>
						</div>

					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					
					
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.ratePerKm" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="carRatePerKm" readOnly="true"
							class="form-control" />
					</div>
					
					<div class="col-md-2"></div>
					
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.decBasicAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="decBasicAmount" readOnly="true"
							class="form-control" />
					</div>
					
					
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.carTotal" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="carTotal" readOnly="true" class="form-control" />
					</div>
					
					<div class="col-md-2"></div>
					
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.carClaimAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="carClaimAmount" readOnly="true"
							class="form-control" />
					</div>
					
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.remarkByCap" /></label>
					</div>
					<div class="col-md-4">
						<form:input path="remarkByCap" class="form-control"
							readOnly="true" />
					</div>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-md-12">
					<br>
					<div class="col-md-5"></div>
					<div class="col-md-2">
						<button id="carSubmitButton" onclick="saveCarExpenseForm();" disabled = "true"
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