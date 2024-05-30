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
<link rel="stylesheet" type="text/css"
	href="<c:url value='/assets/css/main.loader.css' />">
<script src="<c:url value='/assets/js/dailougueBox.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/assets/js/modal.min.js'/>"
	type="text/javascript"></script>

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

	$(document)
			.ready(
					function() {

						var lastTelephone = $('#lastTelephone').val();
						var lastOffEntExpense = $('#lastOffEntExpense').val();
						var lastCarCleaning = $('#lastCarCleaning').val();
						var lastResidenceAllowance = $(
								'#lastResidenceAllowance').val();
						var lastWashingAllowance = $('#lastWashingAllowance')
								.val();
						var lastNewspaper = $('#lastNewspaper').val();
						var lastHouseHoldHelp = $('#lastHouseHoldHelp').val();
						var lastDataCharges = $('#lastDataCharges').val();

						if (lastTelephone == 'Y') {
							$('#serviceProvider').attr('disabled', true);
							$('#isTelephoneAvailed').attr('disabled', true);
							$('#telephoneNo').attr('disabled', true);
							$('#telephoneInstallationDate').attr('disabled',
									true);
							$('#regularClaimTermsAndCondition1').attr(
									'disabled', true);
							$('#regularClaimTermsAndCondition1').attr(
									'checked', true);
							$('#telephoneButton').attr('disabled', true);
						
							$('#telephoneHistoryButton').attr('disabled', true);
						}

						if (lastNewspaper == 'Y' || lastNewspaper == 'N'
								|| lastNewspaper == 'disabled') {
							$('#isNewsPaperAvailed').attr('disabled', true);
						}
						if (lastDataCharges == 'Y' || lastDataCharges == 'N'
								|| lastDataCharges == 'disabled') {
							$('#isDataChargesAvailed').attr('disabled', true);
						}
						if (lastHouseHoldHelp == 'Y'
								|| lastHouseHoldHelp == 'N'
								|| lastHouseHoldHelp == 'disabled') {
							$('#isHouseHoldHelpAvailed').attr('disabled', true);
						}

						if (lastOffEntExpense == 'Y'
								|| lastOffEntExpense == 'N'
								|| lastOffEntExpense == 'disabled') {
							$('#isOfficialEntExpenceAvailed').attr('disabled',
									true);
						}

						if (lastCarCleaning == 'Y' || lastCarCleaning == 'N'
								|| lastCarCleaning == 'disabled') {
							$('#isCarCleaningAvailed').attr('disabled', true);
						}

						if (lastResidenceAllowance == 'Y'
								|| lastResidenceAllowance == 'N'
								|| lastResidenceAllowance == 'disabled') {
							$('#isResidenceOfficeAllowanceAvailed').attr(
									'disabled', true);
						}

						if (lastWashingAllowance == 'Y'
								|| lastWashingAllowance == 'N'
								|| lastWashingAllowance == 'disabled') {
							$('#isWashingAllowanceAvailed').attr('disabled',
									true);
						}

					});

	function submitTelephoneForm() {

		var lastTelephone = $('#lastTelephone').val();
		var isTelephoneAvailed = $('#isTelephoneAvailed').val();
		var telephoneInstallationDate = $('telephoneInstallationDate').val();
		var termsAndCondition = $('#regularClaimTermsAndCondition1').prop(
				'checked');
		var issubmit = true;

		if (termsAndCondition == false) {
			var errMessage = "Please accept Terms and Conditions.";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			issubmit = false;
			return;
		}

		if (lastTelephone == "Y") {

			var serviceProvider = $('#serviceProvider').val();
			var telephoneNo = $('#telephoneNo').val();
			var dataCharges = $('#dataCharges').val();
			var installationDate = $('#telephoneInstallationDate').val();
			var currentDate = new Date();

			var arr1 = installationDate.split('-');
			var arr2 = arr1[0] + '/' + arr1[1] + '/' + arr1[2];
			date1 = new Date(arr2);
			var time_difference = date1.getTime() - currentDate.getTime();
			var days_difference = time_difference / (1000 * 60 * 60 * 24);

			if (days_difference > 0) {
				var errMessage = "Installation Date cannot be greater than Current Date";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

			if (serviceProvider == "" || dataCharges == "" || telephoneNo == "") {
				var errMessage = "Please Enter Telephone Information!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}

		}
		
		if(issubmit){
			waitLoader();
			var formName = 'regularClaimForm';
			document.getElementById(formName).action = 'declarationRegularClaimTelephoneSubmit';
			var form = document.getElementById(formName);
			waitLoader();
			form.submit();
		}
		

	}
	
	function populateTelephoneHistory(){
		
		$.ajax({

			url : "getTelephoneHistory",
			type : "POST",
			data : {
				
			},
			success : function(data) {
				var length = data.length;
				for (var i = 0; i < length; i++) {
					var editData = dataItem = JSON
							.stringify(data[i]);
					if(length > 0){
						$('#isTelephoneAvailed').val('Y');
						$('#telephoneNo').val(data[i][0]);
						$('#serviceProvider').val(data[i][1]);
						$('#telephoneInstallationDate').val(data[i][2]);
					}
					else {
						var errMessage = "No Previous Data is Available.!";
						$('#paraId').html(errMessage);
						$("#dialogOk").dialog();
						return;
					}	
				}
				
			}

		});
	}

	function submitRegularForm() {

		var newsPaper = $('#isNewsPaperAvailed').val();
		var officialEntExpense = $('#isOfficialEntExpenceAvailed').val();
		var washingAllowance = $('#isWashingAllowanceAvailed').val();
		var residenceAllowance = $('#isResidenceOfficeAllowanceAvailed').val();
		var carCleaning = $('#isCarCleaningAvailed').val();
		var houseHoldHelp = $('#isHouseHoldHelpAvailed').val();
		var termsAndCondition = $('#regularClaimTermsAndCondition').prop(
				'checked'); //  $('#regularClaimTermsAndCondition').val();

		if (termsAndCondition == false) {
			var errMessage = "Please accept terms and condition.";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}

		waitLoader();
		var formName = 'regularClaimForm';
		document.getElementById(formName).action = 'declarationRegularClaimSubmit';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
	}
</script>

<body>
	<form:form id="regularClaimForm" modelAttribute="reimbursementBean"
		action="">
		<br>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.section1title" />
			</h3>
		</div>
		<!-- Place All hidden fields Here -->
		<form:hidden path="lastTelephone" />
		<form:hidden path="lastNewspaper" />
		<form:hidden path="lastHouseHoldHelp" />
		<form:hidden path="lastOffEntExpense" />
		<form:hidden path="lastWashingAllowance" />
		<form:hidden path="lastResidenceAllowance" />
		<form:hidden path="lastCarCleaning" />
		<form:hidden path="lastDataCharges" />
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.newspaper" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isNewsPaperAvailed" class="form-control">
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
								code="label.houseClean" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isHouseHoldHelpAvailed" class="form-control">
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
								code="label.entertainmentExp" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isOfficialEntExpenceAvailed"
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
								code="label.washingAllowance" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isWashingAllowanceAvailed" class="form-control">
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
								code="label.residenceOfficeAllowance" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isResidenceOfficeAllowanceAvailed"
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
								code="label.carCleaning" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isCarCleaningAvailed" class="form-control">
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
								code="label.dataCharges" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isDataChargesAvailed" class="form-control">
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
					<div class="col-md-3">
						<form:checkbox path="regularClaimTermsAndCondition"
							id="regularClaimTermsAndCondition"
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
						<input type="button" class="btn btn-primary"
							value="<spring:message code="app.lang.submit" />"
							onClick="submitRegularForm();" />
					</div>


					<div class="col-md-2">
						<input type ="button" onclick = "popReimTerms()" class="btn btn-primary form-control" value="<spring:message code="label.viewTerms" />" >
					</div>
					
				</div>
			</div>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.telephone" />
			</h3>
		</div>
		<div class="form-group fld_hero">
			<br>
			<div class="form-group row ">

				<div class="col-md-12">
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.telephoneMobile" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isTelephoneAvailed" class="form-control">
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
					<div class="col-md-5">
						<input type="button" id = "telephoneHistoryButton"
							class="btn btn-primary form-control"
							value="<spring:message
											code="label.populateTelephoneHistory" />"
							onClick="populateTelephoneHistory();" />
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>

					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.telephoneInstallationDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="telephoneInstallationDate"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
					<div class="col-md-2"></div>

					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.serviceProvider" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="serviceProvider" id="serviceProvider"
							class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>

					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.telephoneNo" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="telephoneNo" id="telephoneNo"
							onkeypress="return isNumber1(event)" onChange = "validateLength(this.id)" class="form-control" />
					</div>
					<div class="col-md-2"></div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="regularClaimTermsAndCondition1"
							id="regularClaimTermsAndCondition1"
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
						<input type="button" id="telephoneButton" class="btn btn-primary"
							value="<spring:message code="app.lang.submit" />"
							onClick="submitTelephoneForm();" />
					</div>
					<div class="col-md-2">
						<input type ="button" onclick = "popReimTerms()" class="btn btn-primary form-control" value="<spring:message code="label.viewTerms" />" >
					</div>
					

				</div>
			</div>
		</div>
	</form:form>
</body>


</html>