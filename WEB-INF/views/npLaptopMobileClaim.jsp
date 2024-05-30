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
<script src="<c:url value='/assets/js/dailougueBox.js'/>">
	type = "text/javascript" >
</script>
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

	/* Initializer and disabler for Laptop/Mobile Claim Section */
	$(document).ready(function() {

		var isMobilePurchased = $('#isMobilePurchased').val();
		if (isMobilePurchased == 'Y' || isMobilePurchased == 'N') {
			$("#isMobilePurchased").attr('disabled', true);
			$('#mobileClaimDate').attr('disabled', true);
			$('#mobileDetail').attr('disabled', true);
			$('#mobileClaimAmount').attr('disabled', true);
			$('#mobileClaimRemark').attr('disabled', true);
			$('#mobileBill').attr('disabled', true);
			$('#mobileAgreement1').attr('disabled', true);
			$('#submitMobileButton').attr('disabled', true);
		}

		var mobileChargesClaimed = $('#mobileCharges').val();
		if (mobileChargesClaimed == 'Y' || mobileChargesClaimed == 'N') {
			$("#mobileCharges").attr('disabled', true);
			$('#mobileNumber').attr('disabled', true);
			$('#mobileServiceProvider').attr('disabled', true);
			$('#mobileActivationDate').attr('disabled', true);
			$('#mobileChargesAgreement1').attr('disabled', true);
			$('#submitMobileChargesButton').attr('disabled', true);
		}

		var isLaptopPurchased = $('#isLaptopPurchased').val();
		var disableLaptopElements = $('#disableLaptopElements').val();
		if (isLaptopPurchased == 'Y' || disableLaptopElements == 'disabled') {
			$("#isLaptopPurchased").attr('disabled', true);
			$('#lapPurchaseDate').attr('disabled', true);
			$('#lapDetail').attr('disabled', true);
			$('#lapClaimAmount').attr('disabled', true);
			$('#lapUploadBill').attr('disabled', true);
			$('#laptopAgreement1').attr('disabled', true);
			$('#submitLaptopButton').attr('disabled', true);
		}
	});

	function mobileFormSubmit() {

		var mobileAgreement1 = $('#mobileAgreement1').is(":checked");

		if (mobileAgreement1) {

			var isMobilePurchased = $("#isMobilePurchased").val();
			var mobileClaimDate = $("#mobileClaimDate").val();
			var mobileDetail = $("#mobileDetail").val();
			var mobileClaimAmount = $("#mobileClaimAmount").val();
			var mobileClaimRemark = $("#mobileClaimRemark").val();
			var mobileBill = $("#mobileBill").val();

			if (isMobilePurchased == '') {
				var errMessage = "Please select Mobile Phone Purchased (Y/N)..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (mobileDetail == '') {
				var errMessage = "Please enter Mobile Phone Detail..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (mobileClaimAmount == '') {
				var errMessage = "Please select Mobile Phone Claim Amount..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (mobileClaimDate == '') {
				var errMessage = "Please select Mobile Phone Purchased Date..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
		}

		var formName = 'npLaptopMobileClaimForm';
		document.getElementById(formName).action = 'mobileFormSubmit';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();

	}

	function mobileChargesSubmit() {

		var mobileChargesAgreement1 = $('#mobileChargesAgreement1').is(
				":checked");

		if (mobileChargesAgreement1 == true) {
			var mobileCharges = $("#isMobilePurchased").val();
			var mobileNumber = $("#isMobilePurchased").val();
			var mobileServiceProvider = $("#isMobilePurchased").val();
			var mobileActivationDate = $("#isMobilePurchased").val();

			if (mobileCharges == '') {
				var errMessage = "Please Select Mobile Charges (Y/N)..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (mobileNumber == '') {
				var errMessage = "Please Enter Mobile Number..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (mobileServiceProvider == '') {
				var errMessage = "Please select Mobile Number Service Provider..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (mobileActivationDate == '') {
				var errMessage = "Please select Mobile Number Activation Date..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

		}

		var formName = 'npLaptopMobileClaimForm';
		document.getElementById(formName).action = 'mobileChargesSubmit';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();

	}

	function laptopFormSubmit() {

		var laptopAgreement1 = $('#laptopAgreement1').is(":checked");

		if (laptopAgreement1 == true) {
			var isLaptopPurchased = $("#isLaptopPurchased").val();
			var lapPurchaseDate = $("#lapPurchaseDate").val();
			var lapDetail = $("#lapDetail").val();
			var lapUploadBill = $("#lapUploadBill").val();
			var lapClaimAmount = $("#lapClaimAmount").val();

			if (isLaptopPurchased == '') {
				var errMessage = "Please select Laptop Purchased (Y/N)..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (lapPurchaseDate == '') {
				var errMessage = "Please enter Laptop Purchase Date..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (lapDetail == '') {
				var errMessage = "Please enter Laptop Detail..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (lapClaimAmount == '') {
				var errMessage = "Please enter Laptop Claim Amount..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (lapUploadBill == '') {
				var errMessage = "Please upload Laptop Bill..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
		}

		var formName = 'npLaptopMobileClaimForm';
		document.getElementById(formName).action = 'laptopFormSubmit';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();

	}
</script>

<body>
	<form:form id="npLaptopMobileClaimForm"
		modelAttribute="reimbursementBean" action=""
		enctype="multipart/form-data">
		<br>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.LapMobTitle1" />
			</h3>
		</div>
		<br>
		<form:hidden path="disableLaptopElements" />
		<div id="errMessage" class="form-group clearfix text-center"
			style="margin-bottom: 5px;">
			<div style="color: green; font-weight: regular;" id="errMsg"></div>
			<c:if test="${! empty message}">
				<div style="color: red; font-weight: bold;">${message}</div>
			</c:if>
			<div class="form-group clearfix text-center">
				<div id="mobileClaimMessage" style="color: red; font-weight: bold;">${mobileClaimMessage}</div>
			</div>
		</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.mobilePurchase" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isMobilePurchased" class="form-control">
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
								code="label.mobPurDt" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" onchange="validateFutureDate(this.id)"
							path="mobileClaimDate" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.mobDetail" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="mobileDetail" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.mobClaimAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="mobileClaimAmount"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.remark" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="mobileClaimRemark" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="lable.mobUploadBill" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="file" onchange="validatePDF(this.id)"
							path="mobileBill" accept=".pdf" class="form-control"
							required="required" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="mobileAgreement"
							style="float: right; width: 17px;" class="form-control" />

					</div>
					<div class="col-md-6">

						<label class="required" style="float: left;"><spring:message
								code="label.reimbursementTerms" /></label>
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-4"></div>
					<div class="col-md-1">
						<button type="button" id="submitMobileButton"
							onclick="mobileFormSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>

					</div>
					<div class="col-md-2">
						<input type="button" onclick = "popReimTerms()"
							class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>

				</div>
			</div>
			<%-- <div class="form-group row">
			<div class="col-md-12">
				<br>
				<div class="col-md-5"></div>
				<div class="col-md-2">
					<button type="submit" id = "mobileSubmitButton" onclick="mobileClaimSubmit();"
						class="btn btn-primary form-control">
						<spring:message code="app.lang.submit" />
					</button>
				</div>
			</div>
		</div> --%>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.LapMobTitle2" />
			</h3>
		</div>
		<br>
		<div id="errMessage" class="form-group clearfix text-center"
			style="margin-bottom: 5px;">
			<div style="color: green; font-weight: regular;" id="errMsg"></div>
			<c:if test="${! empty message}">
				<div style="color: red; font-weight: bold;">${message}</div>
			</c:if>
			<div class="form-group clearfix text-center">
				<div id="mobileChargesMessage"
					style="color: red; font-weight: bold;">${mobileChargesMessage}</div>
			</div>
		</div>

		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.mobileCharges" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="mobileCharges" class="form-control">
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
								code="lable.mobileNumber" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="mobileNumber"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.serviceProvider" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="mobileServiceProvider" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.mobileActivateDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="mobileActivationDate"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="mobileChargesAgreement"
							style="float: right; width: 17px;" class="form-control" />

					</div>
					<div class="col-md-6">

						<label class="required" style="float: left;"><spring:message
								code="label.reimbursementTerms" /></label>
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-4"></div>
					<div class="col-md-1">
						<button type="button" id="submitMobileChargesButton"
							onclick="mobileChargesSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>

					</div>
					<div class="col-md-2">
						<input type="button" onclick = "popReimTerms()"
							class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>

					<div id="myModal" class="modal">
						<div class="modal-content">
							<span class="close">&times;</span>
							<p>
								<spring:message code="label.viewBrTerms" />
							</p>
						</div>
					</div>
				</div>
			</div>
			<%-- <div class="form-group row">
			<div class="col-md-12">
				<br>
				<div class="col-md-5"></div>
				<div class="col-md-2">
					<button type="submit" id = "mobileChargesSubmitButton" onclick="mobileChargesClaimSubmit();"
						class="btn btn-primary form-control">
						<spring:message code="app.lang.submit" />
					</button>
				</div>
			</div>
		</div> --%>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.LapMobTitle3" />
			</h3>
		</div>
		<br>
		<div id="errMessage" class="form-group clearfix text-center"
			style="margin-bottom: 5px;">
			<div style="color: green; font-weight: regular;" id="errMsg"></div>
			<c:if test="${! empty message}">
				<div style="color: red; font-weight: bold;">${message}</div>
			</c:if>
			<div class="form-group clearfix text-center">
				<div id="laptopMessage" style="color: red; font-weight: bold;">${laptopMessage}</div>
			</div>
		</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.laptopPurchase" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isLaptopPurchased" class="form-control">
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
								code="lable.laptopPurchaseDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="lapPurchaseDate"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.lapDetail" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="lapDetail" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.lapClaimAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="lapClaimAmount"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<%-- <div class="col-md-2">
					<label class="required"><spring:message
							code="label.nextLaptopDate" /></label>
				</div>
				<div class="col-md-2">
					<form:input type = "date" path="nextLaptopDate" class = "form-control"/>
				</div>
				<div class="col-md-2"></div> --%>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.lapUploadBill" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="file" onchange="validatePDF(this.id)"
							path="lapUploadBill" accept=".pdf" class="form-control"
							required="required" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="laptopAgreement"
							style="float: right; width: 17px;" class="form-control" />

					</div>
					<div class="col-md-6">

						<label class="required" style="float: left;"><spring:message
								code="label.reimbursementTerms" /></label>
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-4"></div>
					<div class="col-md-1">
						<button type="button" id="submitLaptopButton"
							onclick="laptopFormSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>

					</div>
					<div class="col-md-2">
						<input type="button" onclick = "popReimTerms()"
							class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>

					<div id="myModal" class="modal">
						<div class="modal-content">
							<span class="close">&times;</span>
							<p>
								<spring:message code="label.viewBrTerms" />
							</p>
						</div>
					</div>
				</div>
			</div>
			<%-- <div class="form-group row">
				<div class="col-md-12">
					<br>
					<div class="col-md-5"></div>
					<div class="col-md-2">
						<button type="button" id="laptopSubmitButton"
							onclick="laptopClaimSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>
					</div>
				</div>
			</div> --%>
		</div>
	</form:form>
</body>
</html>