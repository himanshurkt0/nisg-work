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

<body>
	<form:form method="POST" id="rvmeVehicleClaimForm" modelAttribute="reimbursementBean"
		action="" enctype="multipart/form-data">
		<br>
		
		<div id="rvmeMessage" class="form-group clearfix text-center" style="color: green; font-weight: bold;">${rvmeMessage}</div>
		
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.section1title" />
			</h3>
		</div>
		<form:hidden path="isElgibleForDriverSalary" />
		<form:hidden path="empGrade" />
		<form:hidden path="rvmeVerifyStatus" />
		<form:hidden path="rvmeSubmittedFmCode" />
		<form:hidden path="disableHireByBank" />
		<form:hidden path="disableResidingPlace" />
		<input type="hidden" id="claimAllowed" value="${claimAllowed }" />
		<input type="hidden" id="disablePopulateButton"
			value="${disablePopulateButton }" />
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.rvmeVehicle" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isRvmeVehicleClaim"
							onchange="disableNoVehicle()" class="form-control">
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
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.rvmeNoVehicle" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isRvmeNoVehicleClaim"
							onchange='disableVehicleForm();' class="form-control">
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
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.driverSalary" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isRvmeDriverSalary" class="form-control">
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
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.vehicleType" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="rvmeVehicleType" class="form-control">
							<form:option value="">
								<spring:message code="app.lang.select" />
							</form:option>
							<form:option value="FW">
								<spring:message code="label.fourWheeler" />
							</form:option>
							<form:option value="TW">
								<spring:message code="label.twoWheeler" />
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
								code="label.placeOfUse" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="rvmePlaceOfUse" class="form-control">
							<form:option value="">
								<spring:message code="app.lang.select" />
							</form:option>
							<c:forEach items="${rvmePlaceOfUseMap }" var="entry">
								<form:option value="${entry.key }">${entry.value }</form:option>
							</c:forEach>


						</form:select>
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.registrationNo" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="rvmeVehicleRegNumber" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.registeredAt" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="rvmeVehicleRegisteredAt" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.acquiredOn" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="rvmeVehicleAcquiredOn"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.provideByBank" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isVehicleProvidedByBank" class="form-control">
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
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.engineCapacity" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="rvmeEngineCapacity" class="form-control">
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
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.chasisNo" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="chasisNumber" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.engineNo" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="engineNumber" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.validity" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="regvalidityDate"
							onchange="validatePastDate(this.id)" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.shiftingDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="shiftingDate"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
				</div>
			</div>

			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.residingPlace" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="residingPlace" class="form-control">
							<form:option value="">
								<spring:message code="app.lang.select" />
							</form:option>
							<c:forEach items="${rvmePlaceOfUseMap }" var="entry">
								<form:option value="${entry.key }">${entry.value }</form:option>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3" style="display: none;">
						<label class="required"><spring:message
								code="label.fileName" /></label>
					</div>
					<div class="col-md-2" style="display: none;">
						<form:hidden path="fileName" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">

					<div class="col-md-9"></div>
					<div class="col-md-3" >
						<label class="required" style="text-align: left"><mark>
								<spring:message code="label.fileNameDesc" />
							</mark></label>
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label id="labelBill" class="required"><spring:message
								code="label.rcUpload" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="file" onchange="validatePDF(this.id)"
							id="rvmeBill" path="billUpload" accept=".pdf"
							class="form-control" required="required" />
					</div>

					<div class="col-md-2">
						<button id ="rvmeViewButton" class="btn btn-primary form-control">
							<spring:message code="label.view" />
						</button>

					</div>
					<div class="col-md-1"></div>
					<div class="col-md-4">
						<button id="rvmeHistoryButton"
							onclick="getRvmeHistory();"
							class="btn btn-primary form-control">
							<spring:message code="label.rvmeLastClaim" />
						</button>
					</div>
					
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="rvmeAgreement"
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
						<form:button type="button" id="rvmeSubmitButton"
							onclick="rvmeSubmit();" class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</form:button>
					</div>
					<div class="col-md-2">
						<input type="button" onclick = "popReimTerms()"  class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
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
	
	//to disable rvme claim if claim is already submitted but not verified and populate button
	$(document).ready(function() {
		var disableRvmeSubmit = $('#claimAllowed').val();
		disablePopulateButton = $('#disablePopulateButton').val();
		var fileName = $('#fileName').val();

		if (disableRvmeSubmit == 'false') {
			$('#rvmeSubmitButton').attr('disabled', true);
		} else {
			$('#rvmeSubmitButton').attr('disabled', false);
		}

		if (disablePopulateButton == "disable") {
			$('#populateLastClaimButton').attr('disabled', true);
		} else {
			$('#populateLastClaimButton').attr('disabled', false);
		}

		var isRvmeVehicleClaim = $('#isRvmeVehicleClaim').val();
		var rvmeVerifyStatus = $('#rvmeVerifyStatus').val();
		var rvmeSubmittedFmCode = $('#rvmeSubmittedFmCode').val();

		if (rvmeSubmittedFmCode == '501' &&  (rvmeVerifyStatus != 'Y' && rvmeVerifyStatus != '')) {
			$('#isRvmeVehicleClaim').attr('disabled', true);
			$('#rvmeVehicleType').attr('disabled', true);
			$('#rvmePlaceOfUse').attr('disabled', true);
			$('#rvmeVehicleRegNumber').attr('disabled', true);
			$('#rvmeVehicleRegisteredAt').attr('disabled', true);
			$('#rvmeVehicleAcquiredOn').attr('disabled', true);
			$('#isVehicleProvidedByBank').attr('disabled', true);
			$('#rvmeEngineCapacity').attr('disabled', true);
			$('#chasisNumber').attr('disabled', true);
			$('#engineNumber').attr('disabled', true);
			$('#regvalidityDate').attr('disabled', true);
			$('#shiftingDate').attr('disabled', true);
			$('#residingPlace').attr('disabled', true);
			$('#fileName').attr('disabled', true);
			$('#rvmeBill').attr('hidden', true);
			$('#labelBill').attr('hidden', true);
			$('#rvmeAgreement1').val(true);
			$('#rvmeAgreement1').attr('disabled', true);
			$('#rvmeHistoryButton').attr('disabled', true);
		} else {
			disableNoVehicle();
		}
		
		
	});

	$(document).ready(function() {
		var isRvmeVehicleClaim = $('#isRvmeVehicleClaim').val();
		var isElgibleForDriverSalary = $('#isElgibleForDriverSalary').val();
		var disableHireByBank = $('#disableHireByBank').val();
		var disableResidingPlace = $('#disableResidingPlace').val();
		
		if(disableHireByBank == 'disabled'){
			$('#isVehicleProvidedByBank').attr('disabled', true);
		}
		if(disableResidingPlace == 'disabled'){
			$('#residingPlace').attr('disabled', true);
		}

		if (isRvmeVehicleClaim == "Y") {
			disableNoVehicle();
		}

		if (isElgibleForDriverSalary == "disabled") {
			$('#isRvmeDriverSalary').attr('disabled', true);
		} else if (isElgibleForDriverSalary == "enabled") {
			$('#isRvmeDriverSalary').attr('disabled', false);
		}
	});

	function disableVehicleForm() {

		var isRvmeNoVehicleClaim = $('#isRvmeNoVehicleClaim').val();
		if (isRvmeNoVehicleClaim == 'Y') {
			$('#isRvmeVehicleClaim').attr('disabled', true);
			$('#isRvmeDriverSalary').attr('disabled', true);
			$('#rvmeVehicleType').attr('disabled', true);
			$('#rvmePlaceOfUse').attr('disabled', true);
			$('#rvmeVehicleRegNumber').attr('disabled', true);
			$('#rvmeVehicleRegisteredAt').attr('disabled', true);
			$('#rvmeVehicleAcquiredOn').attr('disabled', true);
			$('#isVehicleProvidedByBank').attr('disabled', true);
			$('#rvmeEngineCapacity').attr('disabled', true);
			$('#chasisNumber').attr('disabled', true);
			$('#engineNumber').attr('disabled', true);
			$('#regvalidityDate').attr('disabled', true);
			$('#shiftingDate').attr('disabled', true);
			$('#residingPlace').attr('disabled', true);
			$('#fileName').attr('disabled', true);
			$('#rvmeBill').attr('disabled', true);
		} else if (isRvmeNoVehicleClaim == 'N') {
			$('#isRvmeVehicleClaim').attr('disabled', false);
			$('#isRvmeDriverSalary').attr('disabled', false);
			$('#rvmeVehicleType').attr('disabled', false);
			$('#rvmePlaceOfUse').attr('disabled', false);
			$('#rvmeVehicleRegNumber').attr('disabled', false);
			$('#rvmeVehicleRegisteredAt').attr('disabled', false);
			$('#rvmeVehicleAcquiredOn').attr('disabled', false);
			$('#isVehicleProvidedByBank').attr('disabled', false);
			$('#rvmeEngineCapacity').attr('disabled', false);
			$('#chasisNumber').attr('disabled', false);
			$('#engineNumber').attr('disabled', false);
			$('#regvalidityDate').attr('disabled', false);
			$('#shiftingDate').attr('disabled', false);
			$('#residingPlace').attr('disabled', false);
			$('#fileName').attr('disabled', false);
			$('#rvmeBill').attr('disabled', false);
		}

		checkBothVehicleAreNotNo();
	}

	//to check if both vehicle and no vehicle are not selected as No
	function checkBothVehicleAreNotNo() {
		var isRvmeVehicleClaim = $('#isRvmeVehicleClaim').val();
		var isRvmeNoVehicleClaim = $('#isRvmeNoVehicleClaim').val();

		if (isRvmeVehicleClaim == 'N' && isRvmeNoVehicleClaim == 'N') {
			var errMessage = "Both RVME Vehicle and No Vehicle Cannot be same !";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#isRvmeVehicleClaim').val("");
			$('#isRvmeNoVehicleClaim').val("");
		}

	}

	function disableNoVehicle() {
		var isRvmeVehicleClaim = $('#isRvmeVehicleClaim').val();

		if (isRvmeVehicleClaim == 'Y') {
			$('#isRvmeNoVehicleClaim').val('N');
			$('#isRvmeNoVehicleClaim').attr('disabled', true);
		} else if (isRvmeVehicleClaim == 'N') {
			$('#isRvmeNoVehicleClaim').attr('disabled', false);
			
			
			//Resetting values
			$('#isRvmeVehicleClaim').val('');
			$('#isRvmeNoVehicleClaim').val('Y');
			$('#isRvmeDriverSalary').val('');
			$('#rvmeVehicleType').val('');
			$('#rvmePlaceOfUse').val('');
			$('#rvmeVehicleRegNumber').val('');
			$('#rvmeVehicleRegisteredAt').val('');
			$('#rvmeVehicleAcquiredOn').val('');
			$('#isVehicleProvidedByBank').val('');
			$('#rvmeEngineCapacity').val('');
			$('#chasisNumber').val('');
			$('#engineNumber').val('');
			$('#regvalidityDate').val('');
			$('#shiftingDate').val('');
			$('#residingPlace').val('');
			$('#fileName').val('');
			
			//Disabling elements
			$('#isRvmeVehicleClaim').attr('disabled', true);
			$('#isRvmeDriverSalary').attr('disabled', true);
			$('#rvmeVehicleType').attr('disabled', true);
			$('#rvmePlaceOfUse').attr('disabled', true);
			$('#rvmeVehicleRegNumber').attr('disabled', true);
			$('#rvmeVehicleRegisteredAt').attr('disabled', true);
			$('#rvmeVehicleAcquiredOn').attr('disabled', true);
			$('#isVehicleProvidedByBank').attr('disabled', true);
			$('#rvmeEngineCapacity').attr('disabled', true);
			$('#chasisNumber').attr('disabled', true);
			$('#engineNumber').attr('disabled', true);
			$('#regvalidityDate').attr('disabled', true);
			$('#shiftingDate').attr('disabled', true);
			$('#residingPlace').attr('disabled', true);
			$('#fileName').attr('disabled', true);
			$('#rvmeBill').attr('disabled', true);
		}

		checkBothVehicleAreNotNo();

	}

	function rvmeSubmit() {
		debugger;
		var isRvmeVehicleClaim = $('#isRvmeVehicleClaim').val();
		var isRvmeNoVehicleClaim = $('#isRvmeNoVehicleClaim').val();
		var isRvmeDriverSalary = $('#isRvmeDriverSalary').val();
		var rvmeVehicleType = $('#rvmeVehicleType').val();
		var rvmePlaceOfUse = $('#rvmePlaceOfUse').val();
		var rvmeVehicleRegNumber = $('#rvmeVehicleRegNumber').val();
		var rvmeVehicleRegisteredAt = $('#rvmeVehicleRegisteredAt').val();
		var rvmeVehicleAcquiredOn = $('#rvmeVehicleAcquiredOn').val();
		var isVehicleProvidedByBank = $('#isVehicleProvidedByBank').val();
		var rvmeEngineCapacity = $('#rvmeEngineCapacity').val();
		var chasisNumber = $('#chasisNumber').val();
		var engineNumber = $('#engineNumber').val();
		var regvalidityDate = $('#regvalidityDate').val();
		var shiftingDate = $('#shiftingDate').val();
		var residingPlace = $('#residingPlace').val();
		var fileName = $('#fileName').val();
		var rvmeBill = $('#rvmeBill').val();
		var disableHireByBank = $('#disableHireByBank').val();
		var disableResidingPlace = $('#disableResidingPlace').val();
		var termsAndCondition = $('#rvmeAgreement1').prop(
		'checked');

		if (isRvmeVehicleClaim == '' && isRvmeNoVehicleClaim == '') {
			var errMessage = "Please select vehicle or no vehicle!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}
		if (isRvmeNoVehicleClaim == '' && isRvmeVehicleClaim == '') {
			var errMessage = "Please select vehicle or no vehicle!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}
		
		if (termsAndCondition == '' || termsAndCondition == 'false') {
			var errMessage = "Please check Terms & Conditions first!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}
		
		if (isRvmeVehicleClaim == "Y") {
			if (rvmeVehicleType == '') {
				var errMessage = "Please select vehicle type!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (rvmePlaceOfUse == '') {
				var errMessage = "Please select vehicle used place!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (rvmeVehicleRegNumber == '') {
				var errMessage = "Please select vehicle registration Number!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (rvmeVehicleRegisteredAt == '') {
				var errMessage = "Please select place vehicle registered at!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (rvmeVehicleAcquiredOn == '') {
				var errMessage = "Please choose vehicle acquired date!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (isVehicleProvidedByBank == '' && disableHireByBank != "disabled") {
				var errMessage = "Please choose whether vehicle is provided by bank or not!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (rvmeEngineCapacity == '') {
				var errMessage = "Please select vehicle engine capacity!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (engineNumber == '') {
				var errMessage = "Please enter vehicle engine number!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (chasisNumber == '') {
				var errMessage = "Please enter vehicle chasis number!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (regvalidityDate == '') {
				var errMessage = "Please select vehicle validy date!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (shiftingDate == '') {
				var errMessage = "Please select vehicle shifting date!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (residingPlace == '' && disableResidingPlace != "disabled") {
				var errMessage = "Please select residing place!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (rvmeBill == '') {
				var errMessage = "Please upload RC to claim RVME!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
		}

		var empGrade = $('#empGrade').val();
		if (isRvmeDriverSalary == 'Y' && isRvmeVehicleClaim == 'N'
				&& empGrade != 'G') {
			var errMessage = "Driver salary can be selected only if RVME is selected.";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}

		waitLoader();
		var formName = 'rvmeVehicleClaimForm';
		document.getElementById(formName).action = 'rvmeVehicleSave';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
	}
	
	function getRvmeHistory(){
		
		$.ajax({

			url : "getRvmeHistory",
			type : "POST",
			data : {
				
			},
			success : function(data) {
				var length = data.length;
				for (var i = 0; i < length; i++) {
					var editData = dataItem = JSON
							.stringify(data[i]);
					if(length > 0){
						$('#isRvmeVehicleClaim').val('Y');
						$('#isRvmeNoVehicleClaim').val('N');
						$('#rvmeVehicleType').val(data[i][0]);
						$('#rvmeVehicleRegNumber').val(data[i][1]);
						$('#rvmeVehicleRegisteredAt').val(data[i][2]);
						$('#rvmePlaceOfUse').val(data[i][3]);
						$('#rvmeVehicleAcquiredOn').val(data[i][4]);
						$('#isVehicleProvidedByBank').val(data[i][5]);
						$('#rvmeEngineCapacity').val(data[i][6]);
						$('#chasisNumber').val(data[i][7]);
						$('#engineNumber').val(data[i][8]);
						$('#regvalidityDate').val(data[i][9]);
						$('#shiftingDate').val(data[i][10]);
					}
					else {
						$('#isRvmeNoVehicleClaim').val('Y');
						disableNoVehicle();
					}	
				}
				
			}

		});
		
	}
	
</script>
</html>