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
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Grant</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/assets/css/jquery.dialogue-ui.css' />">
<script src="<c:url value='/assets/js/dailougueBox.js'/>"
	type="text/javascript"></script>
<script src="<c:url value='/assets/js/modal.min.js'/>"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {

		var notifyMsg = $('#notifyMsg').val();
		if (notifyMsg != "") {
			$("#dialogOk").dialog();
			$("#btnShowOK").click(function() {
				$('#dialogOk').dialog('close');
			});
		}

	});

	$(function() {
		$("#btnShowOK").click(function() {
			$('#dialogOk').dialog('close');
		});
	});

	$(document)
			.ready(
					function() {
						var empGrade = $('#empGrade').val();
						var category = $('#category').val();
						var empStatus = $('#empStatus').val();

						if (category == 'SC' || category == 'ST') {
							if ((empGrade == 'A' || empGrade == '3')
									&& empStatus != 'C') {
								var errMessage = "Financial Assistance Reimbursement is applicable for confirmed employee only.!";
								$('#paraId').html(errMessage);
								$("#dialogOk").dialog();
								var form = document
										.getElementById("bookGrantForm");
								var elements = form.elements;
								for (var i = 0, len = elements.length; i < len; ++i) {
									elements[i].readOnly = true;
								}
								return;
							}
						} else {
							var errMessage = "You are not authorised to apply for Financial Assistance Reimbursement.!";
							$('#paraId').html(errMessage);
							$("#dialogOk").dialog();
							var form = document.getElementById("bookGrantForm");
							var elements = form.elements;
							for (var i = 0, len = elements.length; i < len; ++i) {
								elements[i].readOnly = true;
							}
							return;
						}
						if (empGrade != '3' || empGrade != '4') {
							// $('#empGrade').val('Z');
							$('#bookGrantCommonGrade').val('Z');
						}

					});

	function validateCoursePersue() {
		var coursePersuedCode = $('#coursePersuedCode').val();
		var coursePassedCode = $('#coursePassedCode').val();
		var dependentCode = $('#dependentCode').val();

		var dependentIdValueArray = dependentCode.split('-');

		if (dependentCode == "") {
			var errMessage = "Please Select Dependent.!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#dependentCode').val("");
			return;
		}

		if (coursePassedCode == "") {
			var errMessage = "Please Select Course Passed.!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#coursePassedCode').val("");
			return;
		}

		if (parseInt(coursePersuedCode) <= parseInt(coursePassedCode)) {
			var errMessage = "Pls Select Correct Course to Pursue For "
					+ dependentIdValueArray[1] + " .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#coursePersuedCode').val("");
			$('#AFA_SEL_DEPD').val("N");
			return;
		}

		$('#payment').val('');

	}

	function validatePercentage() {

		var percentage = $('#percentage').val();
		var grade = $('#bookGrantCommonGrade').val();
		var dependentCode = $('#dependentCode').val();
		if (dependentCode == "") {
			var errMessage = "Please Select Dependent.!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#percentage').val("");
			return;
		}
		var dependentIdValueArray = dependentCode.split('-');

		if (grade == '3' || grade == '4') {
			if (parseInt(percentage) >= 70 || parseInt(percentage) < 50) {
				var errMessage = "Marks entered is not eligible for Book Grant for "
						+ dependentIdValueArray[1] + " .!";
				$('#paraId').html(errMessage);
				$("#dialogOk").dialog();
				$('#percentage').val("");
				$('#AFA_SEL_DEPD').val("N");
				return;
			}
		} else if (grade == 'Z'
				&& (parseInt(percentage) >= 75 || parseInt(percentage) < 50)) {
			var errMessage = "Marks entered is not eligible for Book Grant for "
					+ dependentIdValueArray[1] + " .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#percentage').val("");
			$('#AFA_SEL_DEPD').val("N");
			return;
		} else if (grade == 'Z'
				&& (parseInt(percentage) >= 70 && parseInt(percentage) <= 75)) {
			var errMessage = "Marks entered is not eligible for Book Grant for "
					+ dependentIdValueArray[1] + " .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#percentage').val("");
			$('#AFA_SEL_DEPD').val("N");
			return;
		}
		$('#payment').val('');
	}

	function validateDate() {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();

		if (startDate == "") {
			var errMessage = "Please Select Session Start Date .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		var arr1 = startDate.split('-');
		var arr2 = arr1[0] + '/' + arr1[1] + '/' + '01';
		var arrs1 = endDate.split('-');
		var arrs2 = arrs1[0] + '/' + arrs1[1] + '/' + '31';
		date1 = new Date(arr2);
		date2 = new Date(arrs2);
		var time_difference = date2.getTime() - date1.getTime();
		var days_difference = time_difference / (1000 * 60 * 60 * 24);

		var noOfDays = days_difference;
		if (noOfDays < 0) {
			alert("Session End Date cannot be less than Session Start Date");
			$('#endDate').val("");
			return;
		}
		var months = Math.floor(noOfDays / 30);
		if (parseInt(months) > 12) {
			alert("Session Duration cannot be more than 12 Months.");
			$('#endDate').val("");
			return;
		} else {
			$('#duration').val(months);
		}
		$('#payment').val('');
	}

	function getPaymentvalue() {
		var coursePersued = $('#coursePersuedCode').val();
		var coursePassed = $('#coursePassedCode').val();
		var bookGrantCommonGrade = $('#bookGrantCommonGrade').val();
		var percentage = $('#percentage').val();
		var courseYear = $('#courseYear').val();
		var startDate = $('#startDate').val();

		if (coursePersued == "") {
			var errMessage = "Please Select coursePersued .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}
		if (coursePassed == "") {
			var errMessage = "Please Select coursePassed  .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}
		if (empGrade == "") {
			var errMessage = "Technical Issue !! Employee Grade not available .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		if (startDate == "") {
			var errMessage = "Please Select Session Start Date .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		if (percentage == "" || parseInt(percentage) <= 0) {
			var errMessage = "Invalid Percentage .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		//	var coursePersueArray = coursePersued.split('-');
		//	var coursePassArray = coursePassed.split('-');
		$.ajax({
			url : 'getBookGrantPaymentValue',
			type : 'POST',
			data : {
				coursePassed : coursePassed,
				coursePersued : coursePersued,
				bookGrantCommonGrade : bookGrantCommonGrade,
				courseYear : courseYear,
				startDate : startDate,
				percentage : percentage,
				appConstantCode : '554',
			},

			success : function(data) {

				$('#payment').val(data);
			}

		});
	}

	function submitForm() {

		var coursePersued = $('#coursePersuedCode').val();
		var coursePassed = $('#coursePassedCode').val();
		var percentage = $('#percentage').val();
		var courseYear = $('#courseYear').val();
		var startDate = $('#startDate').val();
		var courseYear = $('#courseYear').val();

		if (coursePersued == "") {
			var errMessage = "Please Select coursePersued .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}
		if (coursePassed == "") {
			var errMessage = "Please Select coursePassed  .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		if (startDate == "") {
			var errMessage = "Please Select Session Start Date .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		if (endDate == "") {
			var errMessage = "Please Select Session End Date .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		if (percentage == "" || parseInt(percentage) < 0) {
			var errMessage = "Invalid Percentage .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		if (courseYear == "") {
			var errMessage = "Please Select Course Year .!";
			$('#paraId').html(errMessage);
			$("#dialogOk").dialog();
			$('#endDate').val("");
			return;
		}

		$('#bookGrantForm').submit();

	}

	function ajaxCallForBookGrant() {
		var trRow = "";

		$
				.ajax({
					type : "GET",
					url : "getBookGrantSCSTData",
					data : {
						valThis : "test"
					},
					success : function(data) {
						if (data.length != 0) {
							trRow = trRow
									+ ("<thead><tr class='firt_table_row'>"
											+ "<th><spring:message code='app.lang.empCode'/></th>"
											+ "<th><spring:message code='label.sNo'/></th>"
											+ "<th><spring:message code='label.relation'/></th>"
											+ "<th><spring:message code='label.dependentName'/></th>"
											+ "<th><spring:message code='label.coursePassCode'/></th>"
											+ "<th><spring:message code='label.coursePersueCode'/></th>"
											+ "<th><spring:message code='label.percentage'/></th>"
											+ "<th><spring:message code='label.sessionStart'/></th>"
											+ "<th><spring:message code='label.sessionEnd'/></th>"
											+ "<th><spring:message code='label.createdDate'/></th>"
											+ "<th><spring:message code='label.verifyDate'/></th>"
											+ "<th><spring:message code='label.verifyState'/></th>"
											+ "<th><spring:message code='label.year'/></th>"
											+ "<th><spring:message code='label.amount'/></th>"
											+ "<th><spring:message code='label.code'/></th>"
											+ "<th><spring:message code='label.bill'/></th>"
											+ "<th><spring:message code='label.viewBill'/></th>"
											+ "<th><spring:message code='label.edit'/></th>");

							var length = data.length;
							for (var i = 0; i < length; i++) {
								var editData = dataItem = JSON
										.stringify(data[i]);

								trRow = trRow + "<tr>";
								for (var j = 0; j < data[i].length; j++) {
									trRow = trRow
											+ ("<td>" + data[i][j] + "</td>");
								}

								var array = editData.split(",");
								var element = array[11].substr(1,
										array[11].length - 2);
								trRow = trRow
										+ "<td><button class='btn btn-primary form-control'><a href = 'downloadbookGrantBill/{"+data[i][0]+"}/{"+data[i][1]+"}' target='_blank' style = 'color:white;' ><spring:message code='label.view'/></a></button></td>";
								if (element == 'Y') {
									trRow = trRow
											+ "<td><button disabled = 'true' id = 'editButton' class='btn btn-primary form-control' onclick = 'fillScholarshipData("
											+ editData
											+ ");'><spring:message code='label.edit'/></button></td></tr>";

								} else {
									trRow = trRow
											+ "<td><button id = 'editButton' class='btn btn-primary form-control' onclick = 'fillBookGrantData("
											+ editData
											+ ");'><spring:message code='label.edit'/></button></td></tr>";
								}
							}
							trRow = trRow + "</tbody>";
						}
						$('#finAssistHistoryDatatable').html(trRow);
						$('#waitId').hide();
					}
				});
	}
	function waitLoader() {
		$('#waitId').show();
	}

	function fillBookGrantData(value) {

		var empCode = value[0];
		var srNo = value[1];
		$
				.ajax({
					type : "GET",
					url : 'getBookGrantDataToEdit',
					data : {
						empCode : empCode,
						srNo : srNo
					},
					success : function(editData) {
						if (editData != null) {
							var data = JSON.stringify(editData);
							data = data.slice(2, -2);
							var dataArray = data.split(',');
							dependentCode = dataArray[2] + "-"
									+ dataArray[12].slice(1, -1);
							$(
									"#dependentCode option[value = '"
											+ dependentCode + "']").attr(
									'selected', true);
							$(
									'#coursePersuedCode option[value = '
											+ dataArray[4] + ']').attr(
									'selected', true);
							$(
									'#coursePassedCode option[value = '
											+ dataArray[3] + ']').attr(
									'selected', true);
							$('#percentage').val(dataArray[5]);
							$('#payment').val(dataArray[9]);
							var startDate = dataArray[6].slice(1, -1);
							var endDate = dataArray[7].slice(1, -1);
							$('#startDate').val(startDate);
							$('#endDate').val(endDate);
							var blob = new Blob([ editData[0][11] ], {
								type : "application/pdf"
							});
							//	var fileName = "blobFile.pdf";
							//	 const newFile = new File([blob], fileName, {type: 'application/pdf'});

							//  $('#billUpload').val(fileName);
							$('#lv_sr_no').val(srNo);
							validateDate();
							debugger;
							//	$("#finAssistHistoryData").removeClass('modal fade show').addClass('modal fade hide');
							var closeModal = document
									.getElementById("modalClose");
							closeModal.click();
							document.getElementById("finAssistHistoryData").style.display = "none";

						}
					}
				});

	}
</script>
<style type="text/css">
.notification {
	background-color: white;
	color: black;
	text-decoration: none;
	padding: 4px 7px;
	position: relative;
	display: inline-block;
	border-radius: 2px;
}

.notification .badge {
	position: absolute;
	top: -10px;
	right: -10px;
	padding: 5px 10px;
	border-radius: 50%;
	background-color: red;
	color: white;
}
</style>
<style>
div.scrollBar {
	margin: 0px;
	padding: 0px;
	width: 100%;
	height: 100%;
	overflow: auto;
	text-align: justify;
}

.modal-body div.scrollBar {
	height: 350px;
}

.loader-main {
	position: fixed;
	text-align: center;
	width: 100%;
	left: 0;
	vertical-align: middle;
	right: 0;
	z-index: 99999;
	background: rgba(0, 0, 0, 0.56);
	top: 0;
	bottom: 0;
}

.loader {
	top: 0;
	bottom: 0;
	display: inline-block;
	margin: auto;
	border: 16px solid #b8ffc7;
	border-radius: 50%;
	border-bottom: 16px solid #71baea;
	border-top: 16px solid #71baea;
	width: 120px;
	height: 120px;
	-webkit-animation: spin 2s linear infinite;
	animation: spin 2s linear infinite;
	position: absolute;
}

.loader {
	border: 16px solid #f3f3f3;
	border-radius: 50%;
	border-top: 16px solid blue;
	border-right: 16px solid green;
	border-bottom: 16px solid red;
	border-left: 16px solid pink;
	width: 120px;
	height: 120px;
	-webkit-animation: spin 2s linear infinite;
	animation: spin 2s linear infinite;
}

@
-webkit-keyframes spin { 0% {
	-webkit-transform: rotate(0deg);
}

100%
{
-webkit-transform
:
 
rotate
(360deg);
 
}
}
@
keyframes spin { 0% {
	transform: rotate(0deg);
}

100%
{
transform
:
 
rotate
(360deg);
 
}
}

/* The Modal (background) */
/
* .modal {
	display: none;
	position: fixed;
	z-index: 1;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
	-webkit-animation-name: fadeIn;
	-webkit-animation-duration: 0.4s;
	animation-name: fadeIn;
	animation-duration: 0.4s
}

body {
	padding-right: 0 !important
}

.modal-content {
	top: calc(100% - 90%);
	left: calc(100% - 90%);
	background-color: #fefefe;
	width: 100%;
	max-height: 800px;
	overflow: auto;
	-webkit-animation-name: slideIn;
	-webkit-animation-duration: 0.4s;
	animation-name: slideIn;
	animation-duration: 0.4s
}
</style>
</head>
<body id="page-top">

	<!-- Main Content -->
	<div id="content">
		<div id="dialogOk" style="display: none;" align="center">
			<p style="font-size: 12px; font-weight: bold;" id="paraId">${notifyMsg }</p>
			<div class="" style="clear: both;">
				<input type="button" id="btnShowOK" value="OK"
					class="btn btn-primary" />

			</div>
		</div>
		<div id="waitId" class="loader-main" style="display: none">
			<div class="loader"></div>
			<span>Please Wait..</span>
		</div>
		<div id="dialog" style="display: none;" align="center">
			<p style="font-size: 12px; font-weight: bold;" id="paraSubId">
				<spring:message code="app.lang.submitMsg" />
			</p>
			<div class="" style="clear: both;">
				<input type="button" id="btnShowYes" value="Yes"
					class="btn btn-primary" /> <input type="button" id="btnShowNo"
					value="No" class="btn btn-primary" />
			</div>
		</div>
		<div
			class="card-header d-flex flex-row align-items-center justify-content-between">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="app.lang.customer.details" />
			</h3>
		</div>
		<form:form method="POST" modelAttribute="reimbursementBean"
			id="bookGrantForm" action="BookGrantSaveUpdate"
			enctype="multipart/form-data">
			<form:input type="hidden" path="bookGrantCommonGrade"
				id="bookGrantCommonGrade" />
			<form:input type="hidden" path="lv_sr_no" id="lv_sr_no" />
			<form:input type="hidden" path="AFA_SEL_DEPD" id="AFA_SEL_DEPD" />
			<form:input type="hidden" path="empStatus" id="empStatus" />
			<form:input type="hidden" path="dependentRelation"
				id="dependentRelation" />
			<input type="hidden" name="notifyMsg" id="notifyMsg"
				value="${notifyMsg }" />
			<input type="hidden" name="isUpdate" id="isUpdate" value="" />
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
									code="app.lang.customer.category" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="category" id="category" value="${catCode }"
								class="form-control" readonly="true" />
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.grade" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="empGrade" id="empGrade" value="${empGrade }"
								class="form-control" readonly="true" />
						</div>
					</div>
				</div>

			</div>

			<div
				class="card-header d-flex flex-row align-items-center justify-content-between">
				<h3 class="m-0 font-weight-bold text-white">
					<spring:message code="app.lang.course.dependents" />
				</h3>
				<button type="button" class="btn btn-primary form-control"
					data-toggle="modal" data-target="#finAssistHistoryData"
					onclick="waitLoader();ajaxCallForBookGrant();" style="width: 50px;">
					<spring:message code="app.lang.history" />
				</button>
				<%-- <a data-bs-toggle="modal" data-bs-target="#finAssistHistoryData" onclick="ajaxCallForFinAssist();" href=""><h3 class="m-0 font-weight-bold text-white">
						<spring:message code="app.lang.history" />
					</h3></a> --%>
			</div>
			<div class="form-group fld_hero">
				<div class="form-group row">
					<div class="col-md-12">
						<br>

						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.dependents" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="dependentCode" id="dependentCode"
								style="max-width: 100%;" class="form-control"
								selected="${dependentValue }">
								<option value=""><spring:message code="app.lang.select" /></option>
								<c:forEach items="${reimbursementBean.dependentList }"
									var="entry">
									<option value="${entry.key }-${entry.value }">${entry.value }</option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.course.passed" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="coursePassedCode" id="coursePassedCode"
								style="max-width: 100%;" class="form-control"
								onchange="validateCoursePersue();">
								<option value=""><spring:message code="app.lang.select" /></option>
								<c:forEach items="${reimbursementBean.coursePassedList }"
									var="entry">
									<option value="${entry.key }">${entry.value }</option>
								</c:forEach>
							</form:select>
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.emp.course.persued" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="coursePersuedCode" id="coursePersuedCode"
								style="max-width: 100%;" class="form-control"
								onchange="validateCoursePersue();">
								<option value=""><spring:message code="app.lang.select" /></option>
								<c:forEach items="${reimbursementBean.coursePersueList }"
									var="entry">
									<option value="${entry.key }">${entry.value }</option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<br>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.percentage" /></label>

						</div>
						<div class="col-md-2">
							<form:input path="percentage" id="percentage"
								onkeypress="return isNumber1(event)"
								onchange="validatePercentage();" class="form-control" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.startDate" /></label>
						</div>
						<div class="col-md-2">
							<form:input type="date" path="startDate" id="startDate"
								class="form-control" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.endDate" /></label>
						</div>
						<div class="col-md-2">
							<form:input type="date" path="endDate" id="endDate"
								onchange="validateDate();getPaymentvalue();"
								class="form-control" />
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<br>
						<%-- <div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.year" /></label>
						</div>
						<div class="col-md-2">
							<form:select path="courseYear" id="courseYear"
								onchange="getPaymentvalue();" class="form-control">
								<option value=""><spring:message code="app.lang.select" /></option>
								<option value="1">I</option>
								<option value="2">II</option>
								<option value="3">III</option>
							</form:select>
						</div> --%>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.duration" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="duration" id="duration" class="form-control"
								readOnly="true" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.payment" /></label>
						</div>
						<div class="col-md-2">
							<form:input path="payment" id="payment" class="form-control"
								readOnly="true" />
						</div>
						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.uploadBill" /></label>
						</div>
						<div class="col-md-2">
							<form:input type="file" onchange="checkFileSize()"
								path="billUpload" id="billUpload" accept=".pdf"
								class="form-control" required="required" />
						</div>

					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12">
						<br>


						<div class="col-md-2">
							<label class="required"><spring:message
									code="app.lang.verifyRemark" /></label>
						</div>
						<div class="col-md-6">
							<form:input path="remark" id="remark" class="form-control" />
						</div>
					</div>
				</div>

				<div class="form-group row">
					<div class="col-md-12">
						<br>
						<center>
							<button type="submit" onclick="waitLoader();submitForm();"
								class="btn btn-primary">
								<spring:message code="app.lang.submit" />
							</button>
						</center>
					</div>
				</div>
		</form:form>
	</div>


	<!-- End of Main Content -->


	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>




	<!-- <script>
		var coll = document.getElementsByClassName("collapsible");
		var i;
		for (i = 0; i < coll.length; i++) {
			coll[i].addEventListener("click", function() {
				this.classList.toggle("active");
				var content = this.nextElementSibling;
				if (content.style.maxHeight) {
					content.style.maxHeight = null;
				} else {
					content.style.maxHeight = content.scrollHeight + "px";
				}
			});
		}
	</script> -->

	<div class="modal fade" id="finAssistHistoryData"
		data-backdrop="static" data-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px; height: 801.481482px;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 align="center">
						<spring:message code="app.lang.bookGrantHistoryHeading" />
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive"
						style="overflow-y: scroll; overflow-x: scroll; height: auto; margin-bottom: 50px;">
						<table class="table table-bordered"
							style="width: 100%; border: 1px solid #000; margin: 15px 0; overflow: auto; height: 50px;"
							id="finAssistHistoryDatatable">
						</table>
					</div>
				</div>
				<center>
					<button type="button" class="btn btn-primary" id="modalClose"
						data-dismiss="modal" aria-label="Close">
						<spring:message code="app.label.close" />
					</button>
				</center>
			</div>
		</div>
	</div>
</body>
</html>