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

	function getViPaymentValue() {
		var npInsurancePremiumPaidt = $('#npInsurancePremiumPaidt').val();

		if (npInsurancePremiumPaidt != '') {
			$('#npInsuranceRefundAmount').val(npInsurancePremiumPaidt);
		} else {
			var errMessage = "Error! Vehicle Insurance Amount not available";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}
	}

	//TO ENABLE DISABLE VEHICLE INSURANCE TAB
	$(document)
			.ready(
					function() {
						var vehicleInsuranceProceed = $(
								'#vehicleInsuranceProceed').val();
						var npInsurancePaidStatus = $('#npInsurancePaidStatus')
								.val();
						var npInsuranceVerifyStatus = $(
								'#npInsuranceVerifyStatus').val();
						var npInsuranceRefundVerifyStatus = $(
								'#npInsuranceRefundVerifyStatus').val();
						var npInsuranceRefundStatus = $(
								'#npInsuranceRefundStatus').val();
						var viDisabled = $('#viDisabled').val();

						if ((npInsuranceVerifyStatus == 'N' || npInsuranceVerifyStatus == 'I')
								&& npInsurancePaidStatus == 'N') {
							$('#vehicleInsuranceProceed').val('U');
						}

						if (npInsuranceVerifyStatus == 'Y'
								&& npInsurancePaidStatus == 'N') {
							$('#vehicleInsuranceProceed').val('X');
						}

						//TO DISPLAY THE DETAILS BUT NOW ALLOWED TO UPDATE SINCE REFUND VERIFIED 
						if (npInsuranceVerifyStatus == 'Y'
								&& npInsurancePaidStatus == 'Y'
								&& npInsuranceRefundStatus == 'Y'
								&& npInsuranceRefundVerifyStatus == 'N') {
							$('#vehicleInsuranceProceed').val('X');
						}

						if (vehicleInsuranceProceed != 'I'
								&& vehicleInsuranceProceed != 'U'
								&& vehicleInsuranceProceed != '') {
							$('#vehicleInsuranceProceed').val('X');
						}

						var vehicleInsuranceProceed = $(
								'#vehicleInsuranceProceed').val();

						if (vehicleInsuranceProceed == 'I'
								|| vehicleInsuranceProceed == 'U') {
							$('#isNpInsuranceClaimed').attr('disabled', false);
							$('#npPolicyNumber').attr('disabled', false);
							$('#npInsuranceProviderName').attr('disabled',
									false);
							$('#npInsuranceType').attr('disabled', false);
							$('#npInsuranceFrom').attr('disabled', false);
							$('#npInsuranceTill').attr('disabled', false);
							$('#npInsurancePremiumPaidt').attr('disabled',
									false);
							$('#npInsuranceRemark').attr('disabled', false);
							$('#insuranceAgreement1').attr('disabled', false);
							$('#insuranceSubmitButton').attr('disabled', false);
						} else if (vehicleInsuranceProceed == 'X') {
							var errMessage = "Either you have not entered vehicle details or vehicle details have not been verified. !";
							$('#npInsuranceRefundDate').attr('disabled', true);
							$('#npInsuranceRefund').attr('disabled', true);
							$('#npInsuranceRefundAmount')
									.attr('disabled', true);
							$('#refundAgreement1').attr('disabled', true);
							$('#refundSubmitButton').attr('disabled', true);
							$('#viPaymentButton').attr('disabled', true);
						} else {
							var errMessage = "You have already claimed for Vehicle Insurance !";
							$('#viMessage').html(errMessage);
							$('#isNpInsuranceClaimed').attr('disabled', true);
							$('#npPolicyNumber').attr('disabled', true);
							$('#npInsuranceProviderName')
									.attr('disabled', true);
							$('#npInsuranceType').attr('disabled', true);
							$('#npInsuranceFrom').attr('disabled', true);
							$('#npInsuranceTill').attr('disabled', true);
							$('#npInsurancePremiumPaidt')
									.attr('disabled', true);
							$('#npInsuranceRemark').attr('disabled', true);
							$('#insuranceAgreement1').attr('disabled', true);
							$('#insuranceSubmitButton').attr('disabled', true);

						}

						if (viDisabled == 'true') {
							var errMessage = "Either you have not entered vehicle details or vehicle details have not been verified. !";
							$('#isNpInsuranceClaimed').attr('disabled', true);
							$('#npPolicyNumber').attr('disabled', true);
							$('#npInsuranceProviderName')
									.attr('disabled', true);
							$('#npInsuranceType').attr('disabled', true);
							$('#npInsuranceFrom').attr('disabled', true);
							$('#npInsuranceTill').attr('disabled', true);
							$('#npInsurancePremiumPaidt')
									.attr('disabled', true);
							$('#npInsuranceRemark').attr('disabled', true);
							$('#insuranceAgreement1').attr('disabled', true);
							$('#insuranceSubmitButton').attr('disabled', true);
							$('#npInsuranceRefundDate').attr('disabled', true);
							$('#npInsuranceRefund').attr('disabled', true);
							$('#npInsuranceRefundAmount')
									.attr('disabled', true);
							$('#refundAgreement1').attr('disabled', true);
							$('#refundSubmitButton').attr('disabled', true);
							$('#viPaymentButton').attr('disabled', true);
							$('#viMessage').html(errMessage);
						}

					});

	//VEHICLE INSURANCE REFUND SUBMIT FUNCTION
	function vehicleInsuranceRefundClaimSubmit() {
		var npInsuranceRefundDate = $('#npInsuranceRefundDate').val();
		var npInsuranceRefund = $('#npInsuranceRefund').val();
		var npInsuranceRefundAmount = $('#npInsuranceRefundAmount').val();

		if (npInsuranceRefundDate == '' || npInsuranceRefund == ''
				|| npInsuranceRefundAmount == '') {
			var errMessage = "Missing Field Value !! Please Make Sure All fields values are entered correctly.......... ";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}

		waitLoader();
		var formName = 'npVehicaleInsuranceClaimForm';
		document.getElementById(formName).action = 'npVehicaleInsuranceRefundSubmit';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
	}

	//VEHICLE INSURANCE SUBMIT FUNCTION
	function vehicleInsuranceClaimSubmit() {
		var isNpInsuranceClaimed = $('#isNpInsuranceClaimed').val();
		var npPolicyNumber = $('#npPolicyNumber').val();
		var npInsuranceProviderName = $('#npInsuranceProviderName').val();
		var npInsuranceType = $('#npInsuranceType').val();
		var npInsuranceFrom = $('#npInsuranceFrom').val();
		var npInsuranceTill = $('#npInsuranceTill').val();
		var npInsurancePremiumPaidt = $('#npInsurancePremiumPaidt').val();
		var billUpload = $('#vehicleBill').val();
		var insuranceAgreement1 = $('#insuranceAgreement1').is(":checked");

		if (insuranceAgreement1) {
			if (billUpload == '' || isNpInsuranceClaimed == ''
					|| npPolicyNumber == '' || npInsuranceProviderName == ''
					|| npInsuranceType == '' || npInsuranceFrom == ''
					|| npInsuranceTill == '' || npInsurancePremiumPaidt == '') {
				var errMessage = "Missing Field Value !! Please Make Sure All fields values are entered correctly..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			waitLoader();
			var formName = 'npVehicaleInsuranceClaimForm';
			document.getElementById(formName).action = 'npVehicaleInsuranceClaimSave';
			var form = document.getElementById(formName);
			waitLoader();
			form.submit();

		} else {
			var errMessage = "Please select Terms & Conditions before submitting!!..... ";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}

	}
</script>


<body>
	<form:form id="npVehicaleInsuranceClaimForm"
		modelAttribute="reimbursementBean" action=""
		enctype="multipart/form-data">
		<br>
		<form:hidden path="vehicleInsuranceProceed" />
		<form:hidden path="npInsurancePaidStatus" />
		<form:hidden path="npInsuranceVerifyStatus" />
		<form:hidden path="npInsuranceRefundStatus" />
		<form:hidden path="npInsuranceRefundVerifyStatus" />
		<form:hidden path="viDisabled" />

		<div id="viMessage" class="form-group clearfix text-center"
			style="color: red; font-weight: bold;">${viMessage}</div>

		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.section1title" />
			</h3>
		</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.insuranceProvider" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isNpInsuranceClaimed" class="form-control">
							<form:option value="">
								<spring:message code="app.lang.select" />
							</form:option>
							<form:option value="Y">
								<spring:message code="app.lang.yes" />
							</form:option>
							<form:option value="N">
								<spring:message code="app.lang.no" />
							</form:option>
						</form:select>
					</div>
					<div class="col-md-3"></div>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.policyNumber" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npPolicyNumber" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.insuranceProviderName" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npInsuranceProviderName" class="form-control" />
					</div>
					<div class="col-md-3"></div>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.insuranceType" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="npInsuranceType" class="form-control">
							<form:option value="">
								<spring:message code="app.lang.select" />
							</form:option>
							<form:option value="CO">
								<spring:message code="label.comprehensive" />
							</form:option>
							<form:option value="TP">
								<spring:message code="label.thirdParty" />
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
								code="label.InsuranceFrom" /></label>
					</div>

					<div class="col-md-2">
						<form:input type="date" path="npInsuranceFrom"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
					<div class="col-md-3"></div>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.InsuranceTill" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="npInsuranceTill"
							onchange="validatePastDate(this.id)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.InsurancePremPaid" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npInsurancePremiumPaidt"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
					<div class="col-md-3"></div>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.InsuranceRemark" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npInsuranceRemark" class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.billUpload" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="file" onchange="validatePDF(this.id)"
							id="vehicleBill" path="billUpload" accept=".pdf"
							class="form-control" required="required" />
					</div>

					<div class="col-md-2">
						<button disabled="disabled" class="btn btn-primary form-control">
							<spring:message code="label.view" />
						</button>

					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<button disabled="disabled" id="insuranceHistoryButton"
							onclick="waitLoader();getInsuranceHistory();"
							class="btn btn-primary form-control">
							<spring:message code="label.InsuranceHistory" />
						</button>
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="insuranceAgreement"
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
					<div class="col-md-4"></div>
					<div class="col-md-1">

						<form:button type="button" id="insuranceSubmitButton"
							onclick="vehicleInsuranceClaimSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</form:button>
					</div>
					<div class="col-md-2">
						<input type="button" onclick="popReimTerms()"
							class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>
				</div>
			</div>

		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.insuranceRefundTitle" />
			</h3>
		</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.InsuranceRefund" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="npInsuranceRefund" class="form-control">
							<form:option value="">
								<spring:message code="app.lang.select" />
							</form:option>
							<form:option value="Y">
								<spring:message code="app.lang.yes" />
							</form:option>
						</form:select>
					</div>

					<div class="col-md-2"></div>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.InsuranceRefundDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" onchange="validateFutureDate(this.id)"
							path="npInsuranceRefundDate" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.InsuranceRefundAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npInsuranceRefundAmount"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>

					<div class="col-md-3">
						<button type="button" id="viPaymentButton"
							class="btn btn-primary form-control"
							onclick="getViPaymentValue()">
							<spring:message code="label.goWithViAmount" />
						</button>
					</div>
				</div>
			</div>

			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="refundAgreement"
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
					<div class="col-md-4"></div>
					<div class="col-md-1">
						<button type="button" id="refundSubmitButton"
							onclick="vehicleInsuranceRefundClaimSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>
					</div>
					<div class="col-md-2">
						<input type="button" onclick="popReimTerms()"
							class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>
				</div>
			</div>
		</div>
	</form:form>

</body>
</html>