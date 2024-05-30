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

	/* Initializer and disabler for Non Periodic claim Section */
	$(document)
			.ready(
					function() {
						var lastBRClaim = $('#lastBriefCaseReimb').val();
						var lastBookClaimAvailed = $('#lastBookClaimAvailed')
								.val();
						var empGrade = $('#empGrade').val();
						var medicalOptClaimFlag = $('#isNpMedicalAvailed')
								.val();
						var medicalTermsAndCondition1 = $(
								'#medicalTermsAndCondition1').is(":checked")
						var BookTermsAndCondition = $('#BookTermsAndCondition1')
								.is(":checked");
						var BRTermsAndCondition = $('#BRTermsAndCondition1')
								.is(":checked");
						var npMedicalRemAmount = $('#npMedicalRemAmount').val();
						var npBRRemAmount = $('#npBRRemAmount').val();
						var npBookRemAmount = $('#npBookRemAmount').val();
						var bookVerifyStatus = $('#bookVerifyStatus').val();
						var medicalVerifyStatus = $('#medicalVerifyStatus')
								.val();

						if (lastBRClaim == 'Y' || lastBRClaim == 'N'
								|| empGrade == '4') {
							$('#briefCaseReimb').attr('disabled', true);
							$('#BRPurchaseDate').attr('disabled', true);
							$('#breifCaseCost').attr('disabled', true);
							$('#BRReason').attr('disabled', true);
							$('#BRTermsAndCondition1').attr('checked', true);
							$('#BRTermsAndCondition1').attr('disabled', true);
							$('#briefCaseSubmit').attr('disabled', true);
						}

						if ((lastBookClaimAvailed == 'Y' || lastBookClaimAvailed == 'N')
								&& bookVerifyStatus == 'N'
								|| parseInt(npBookRemAmount) <= 0) {
							$('#isNonPeriodBookAvailed').attr('disabled', true);
							$('#bookDetail').attr('disabled', true);
							$('#bookCost').attr('disabled', true);
							$('#BookTermsAndCondition1').attr('checked', true);
							$('#BookTermsAndCondition1').attr('disabled', true);
							$('#bookSubmit').attr('disabled', true);
							
						}

						if (medicalOptClaimFlag == 'Y'
								|| parseInt(npMedicalRemAmount) <= 0
								&& medicalVerifyStatus == 'N') {
							$('#isNpMedicalAvailed').attr('disabled', true);
							$('#npMedicalAmountClaimed').attr('disabled', true);
							$('#medicalTermsAndCondition1').attr('checked',
									true);
							$('#medicalTermsAndCondition1').attr('disabled',
									true);
							$('#medicalSubmit').attr('disabled',
									true);
						} else {
							$('#isNpMedicalAvailed').val('');
							$('#npMedicalAmountClaimed').val(0);
							$('#medicalTermsAndCondition1').attr('checked',
									false);
							$('#medicalTermsAndCondition1').attr('disabled',
									false);
						}

					});

	function briefCaseFormSubmit() {
		var briefCaseReimb = $('#briefCaseReimb').val();
		var BRPurchaseDate = $('#BRPurchaseDate').val();
		var breifCaseCost = $('#breifCaseCost').val();
		var npBRRemAmount = $('#npBRRemAmount').val();
		var BRTermsAndCondition1 = $('#BRTermsAndCondition1').is(":checked");
		var BRReason = $('#BRReason').val();
		var disabled = $('#BRTermsAndCondition1').is(":disabled");
		var isSubmit = true;

		if (BRTermsAndCondition1 && !disabled) {
			if (briefCaseReimb == '' || BRPurchaseDate == ''
					|| breifCaseCost == '') {
				var errMessage = "Missing Field Value !! Please Make Sure All fields values are entered correctly.......... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				isSubmit = false;
				return;
			}
			
			if (isSubmit) {
				var formName = 'nonPeriodicClaimForm';
				document.getElementById(formName).action = 'briefCaseSubmit';
				var form = document.getElementById(formName);
				waitLoader();
				form.submit();
			}

		}  else {
			var errMessage = "Please Accept Terms & Conditions.! ";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}

		

	}

	function bookClaimSubmit() {

		var BookTermsAndCondition1 = $('#BookTermsAndCondition1')
				.is(":checked");

		var isNonPeriodBookAvailed = $('#isNonPeriodBookAvailed').val();
		var disabled = $('#BookTermsAndCondition1').is(":disabled");
		var isSubmit = true;

		if (BookTermsAndCondition1 && !disabled) {
			var bookDetail = $('#bookDetail').val();
			var bookCost = $('#bookCost').val();
			var npBookRemAmount = $('#npBookRemAmount').val();
			if (isNonPeriodBookAvailed == '' || bookDetail == ''
					|| bookCost == '') {
				var errMessage = "Missing Field Value !! Please Make Sure All fields values are entered correctly.......... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				isSubmit = false;
				return;
			}

			if (parseInt(npBookRemAmount) > parseInt(bookLimit)) {
				var errMessage = "You dont have enough limit !!.......... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				isSubmit = false;
				return;
			}
			
			if (isSubmit) {
				var formName = 'nonPeriodicClaimForm';
				document.getElementById(formName).action = 'bookClaimSubmit';
				var form = document.getElementById(formName);
				waitLoader();
				form.submit();
			}
		} else {
			var errMessage = "Please Accept Terms & Conditions.! ";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}
		
		

	}
	
	

	//NON PERIODIC CLAIM SUBMIT
	function nonPeriodClaimFormSubmit() {

		var medicalTermsAndCondition1 = $('#medicalTermsAndCondition1').is(
				":checked");


		var isNpMedicalAvailed = $('#isNpMedicalAvailed').val();
		disabled = $('#medicalTermsAndCondition1').is(":disabled");
		if (medicalTermsAndCondition1 && !disabled) {
			var npMedicalAmountClaimed = $("#npMedicalAmountClaimed").val();
			var limit = $("#npMedicalRemAmount").val();
			var remainingAmount = parseInt(limit)
					- parseInt(npMedicalAmountClaimed);
			/* if (isNpMedicalAvailed == '') {
				var errMessage = "Please Opt for Medical claim First!!..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			} */
			if (remainingAmount < 0) {
				var errMessage = "Sorry!!! You dont have enough limit left for this claim..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			if (npMedicalAmountClaimed == 0) {
				var errMessage = "Please enter Amount Greater than 0 !!..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
		}

		if (medicalTermsAndCondition1 ) {
			var formName = 'nonPeriodicClaimForm';
			document.getElementById(formName).action = 'nonPeriodClaimSubmit';
			var form = document.getElementById(formName);
			waitLoader();
			form.submit();
		} else {
			var errMessage = "Please Accept Terms & Conditions.! ";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}

	}
	
	
	/* function medicalHistoryNonPeriod(){
		
		var formName = 'nonPeriodicClaimForm';
		document.getElementById(formName).action = 'nonPeriodClaimSubmit';
		var form = document.getElementById(formName);
		waitLoader();
		form.submit();
		
	} */
	
</script>
<body>
	<form:form id="nonPeriodicClaimForm" modelAttribute="reimbursementBean"
		action="">

		<form:hidden path="lastBriefCaseReimb" />
		<form:hidden path="lastBreifCaseCost" />
		<form:hidden path="lastBRPurchaseDate" />
		<form:hidden path="lastBRReason" />
		<form:hidden path="lastBookClaimAvailed" />
		<form:hidden path="lastBookCost" />
		<form:hidden path="lastBookRemark" />
		<form:hidden path="empGrade" />
		<form:hidden path="lastNpMedicalPaidFlag" />
		<form:hidden path="bookVerifyStatus" />
		<br>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.section1title" />
			</h3>
		</div>

		<div id="briefCaseMessage" class="form-group clearfix text-center"
			style="color: red; font-weight: bold;">${briefCaseMessage}</div>

		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.briefCaseReimb" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="briefCaseReimb" class="form-control">
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
								code="label.pruchaseDate" /></label>
					</div>
					<div class="col-md-2">
						<form:input type="date" path="BRPurchaseDate" id="BRPurchaseDate"
							onchange="validateFutureDate(this.id)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.breifCaseCost" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="breifCaseCost" id="breifCaseCost"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.reasonForChange" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="BRReason" id="BRReason" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.brLimit" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="maxBRLimit" readOnly="true"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>

				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">

					<br>
					<div class="col-md-3">
						<form:checkbox path="BRTermsAndCondition"
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
						<button type="button" id="briefCaseSubmit"
							onclick="briefCaseFormSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>

					</div>
					<div class="col-md-2">
						<input type="button"  onclick = "popReimTerms()"
							class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>

					<%-- <div id="myModal" class="modal">
						<div class="modal-content">
							<span class="close">&times;</span>
							<p>
								<spring:message code="label.viewBrTerms" />
							</p>
						</div>
					</div> --%>
				</div>
			</div>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.bookGrantReimb" />
			</h3>
		</div>

		<div id="bookMessage" class="form-group clearfix text-center"
			style="color: red; font-weight: bold;">${bookClaimMessage }</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>

					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.bookReimb" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isNonPeriodBookAvailed" class="form-control">
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
								code="label.bookDetail" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="bookDetails" id="bookDetail"
							class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.bookCost" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="bookCost" id="bookCost"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.bookLimit" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="bookLimit" readOnly="true"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.medicalRemainingAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npBookRemAmount" class="form-control"
							readOnly="true" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="BookTermsAndCondition"
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
						<button type="button" id="bookSubmit"
							onclick="bookClaimSubmit();"
							class="btn btn-primary form-control">
							<spring:message code="app.lang.submit" />
						</button>

					</div>
					<div class="col-md-2">
						<input type="button" onclick = "popReimTerms()"  class="btn btn-primary form-control"
							value="<spring:message code="label.viewTerms" />">
					</div>

					<%-- <div id="myModal" class="modal">
						<div class="modal-content">
							<span class="close">&times;</span>
							<p>
								<spring:message code="label.viewBookTerms" />
							</p>
						</div>
					</div> --%>
				</div>
			</div>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.medicalNonPeriodReimb" />
			</h3>
		</div>
		<form:hidden path="medicalVerifyStatus" />
		<div class="form-group clearfix text-center"
			style="color: red; font-weight: bold;">${medicalMessage }</div>
		<div class="form-group fld_hero">
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.medicalReimb" /></label>
					</div>
					<div class="col-md-2">
						<form:select path="isNpMedicalAvailed" class="form-control">
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
								code="label.medicalOtpLimit" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npMedicalOptLimit" class="form-control"
							readOnly="true" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-2">
						<label class="required"><spring:message
								code="label.medicalAmountClaimed" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npMedicalAmountClaimed"
							onkeypress="return isNumber1(event)" class="form-control" />
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<label class="required"><spring:message
								code="label.medicalRemainingAmount" /></label>
					</div>
					<div class="col-md-2">
						<form:input path="npMedicalRemAmount" class="form-control"
							readOnly="true" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>

					<div class="col-md-7"></div>
					<div class="col-md-4">
						<input type="button" disabled="disabled"
							class="btn btn-primary form-control"
							value="<spring:message
											code="label.medicalHistory" />"
							onClick="medicalHistoryNonPeriod();" />
					</div>
				</div>
			</div>
			<div class="form-group row ">
				<div class="col-md-12">
					<br>
					<div class="col-md-3">
						<form:checkbox path="medicalTermsAndCondition"
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
					<button type="button" id="medicalSubmit"
						onclick="nonPeriodClaimFormSubmit();" class="btn btn-primary form-control">
						<spring:message code="app.lang.submit" />
					</button>
					</div>
					<div class="col-md-2">
					<button type = "button" onclick = "popReimTerms()" class="btn btn-primary form-control">
						<spring:message
								code="label.viewTerms" />
					</button>
					</div>

				</div>
			</div>

		</div>

	</form:form>
</body>

</html>