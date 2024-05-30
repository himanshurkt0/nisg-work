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
	
	/* Initializer and disabler for Laptop/Mobile Claim Section */
	$(document).ready(function() {
		var isTabClaimed = $('#isTabClaimed').val();
		if(isTabClaimed == 'Y' || isTabClaimed == 'N'){
			$("#isTabClaimed").attr('disabled', true);
			$('#tabClaimDate').attr('disabled', true);
			$('#tabDetail').attr('disabled', true);
			$('#tabClaimAmount').attr('disabled', true);
			$('#tabClaimAmount').attr('disabled', true);
			$('#tabBill').attr('disabled', true);
			$('#remark').attr('disabled', true);
			$('#tabAgreement1').attr('checked', true);
			$('#tabAgreement1').attr('disabled', true);
			$('#tabSubmitButton').attr('disabled', true);
		}
	});


	
	function tabClaimSubmit(){
		
		var tabAgreement1 = $("#tabAgreement1").val();
		
		if(tabAgreement1 == "true"){
			var isTabClaimed = $("#isTabClaimed").val();
			var tabClaimDate = $("#tabClaimDate").val();
			var tabDetail = $("#tabDetail").val();
			var tabClaimAmount = $("#tabClaimAmount").val();
			var uploadBill = $("#tabBill").val();
			
			if(isTabClaimed == ''){
				var errMessage = "Please select Tablet Purchased (Y/N)..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			
			if(tabClaimDate == ''){
				var errMessage = "Please enter Tablet Purchase Date..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			
			if(tabDetail == ''){
				var errMessage = "Please enter Tablet Detail..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			
			if(tabClaimAmount == ''){
				var errMessage = "Please enter Tablet Claim Amount..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			
			if(uploadBill == ''){
				var errMessage = "Please upload Tablet Bill..... ";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				return;
			}
			
			waitLoader();
			var formName = 'tabIpadClaimForm';
			document.getElementById(formName).action = 'tabIpadClaimSave';
			var form = document.getElementById(formName);
			waitLoader();
			form.submit();
		} else {
			var errMessage = "Please accept Terms & Condition !!!..... ";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			return;
		}
	}
	
</script>
	
<body>
<form:form id="tabIpadClaimForm" modelAttribute="reimbursementBean"
	action="" enctype="multipart/form-data">
	<form:hidden path="isVerified"/>
	<br>
	<div
		class="card-header d-flex flex-row align-items-center justify-content-between">
		<h3 class="m-0 font-weight-bold text-white">
			<spring:message code="label.tabletTitle" />
		</h3>
	</div>
	<div class="form-group clearfix text-center" id="tabMessage" style="color: red; font-weight: bold;">${tabMessage}</div>
	<div class="form-group fld_hero">
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.tabClaimed" /></label>
				</div>
				<div class="col-md-2">
					<form:select path="isTabClaimed" class="form-control">
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
							code="label.tabClaimDate" /></label>
				</div>
				<div class="col-md-2">
					<form:input type = "date" path="tabClaimDate" onchange="validateFutureDate(this.id)"  class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-2">
					<label class="required"><spring:message
							code="label.tabDetail" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="tabDetail" class = "form-control"/>
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.claimAmount" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="tabClaimAmount" onkeypress="return isNumber1(event)"  class = "form-control"/>
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
					id="tabBill" path="uploadBill" accept=".pdf"
					class="form-control" required="required" />
				</div>
				<div class="col-md-2"></div>
				<div class="col-md-3">
					<label class="required"><spring:message
							code="label.remark" /></label>
				</div>
				<div class="col-md-2">
					<form:input path="remark" class = "form-control"/>
				</div>
			</div>
		</div>
		<div class="form-group row ">
			<div class="col-md-12">
				<br>
				<div class="col-md-3">
					<form:checkbox path="tabAgreement"
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
					<button id = "tabSubmitButton" onclick="tabClaimSubmit();"
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