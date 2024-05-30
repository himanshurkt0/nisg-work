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
<script type="text/javascript">
	$(function() {
		
		var notifyMsg = $('#notifyMsg').val();
		if (notifyMsg != "") {
			$("#dialogOk").dialog();
			$("#btnShowOK").click(function() {
			$('#dialogOk').dialog('close');
			});
		}
		
		$(document).ready(function() {
			$('#reimbursementTableList').DataTable();
		});
	});
	
	
	function waitLoader(){
		   $('#waitId').show();  
	}
	
	function checkBookGrant() {
		var datatable = document
				.getElementById("reimbursementTableList_filter").value;
		var tagname = document.getElementsByTagName("search").value;
		var tagByclassName = $('.form-control form-control-sm');
		alert(tagByclassName);
		$('.form-control form-control-sm').val("554");
	}

	function approveForm() {

		$('#reimbursementVerification').attr("action",
				"approveReimbursementFinAssist");
		$('#reimbursementVerification').submit();
	}

	function rejectForm() {

		$('#reimbursementVerification').attr("action",
				"rejectReimbursementFinAssist");
		$('#reimbursementVerification').submit();
	}
</script>
<style>
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

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
</head>
<body id="page-top">
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
			class="card-header d-flex flex-row align-items-center justify-content-between"
			style="background-color: #146C94">
			<h3 class="m-0 font-weight-bold text-white">
				<spring:message code="label.reimbursementDetails" />
			</h3>
		</div>

		<form:form method="POST" modelAttribute="reimbursementBean"
			id="reimbursementVerification" action=""
			enctype="multipart/form-data">
			<input type="hidden" name="notifyMsg" id="notifyMsg"
				value="${notifyMsg }" />
			<div class="row py-5">
				<div class="col-lg-12 mx-auto">
					<div class="card rounded shadow border-0">
						<div class="card-body p-5 bg-yellow rounded">
							<div class="table-responsive" style="width: auto;">
								<table id="reimbursementTableList" style="width: auto"
									class="table table-striped table-bordered">
									<thead class="thead-light">
										<tr>
											<th></th>
											<th><spring:message code='app.lang.empCode' /></th>
											<th><spring:message code='app.lang.custName' /></th>
											<th><spring:message code='label.reimbursementType' /></th>
											<th><spring:message code='label.sNo' /></th>
											<th><spring:message code='label.relation' /></th>
											<th><spring:message code='label.dependentName' /></th>
											<th><spring:message code='label.coursePassCode' /></th>
											<th><spring:message code='label.coursePersueCode' /></th>
											<th><spring:message code='label.percentage' /></th>
											<th><spring:message code='label.sessionStart' /></th>
											<th><spring:message code='label.sessionEnd' /></th>
											<th><spring:message code='label.createdDate' /></th>
											<th><spring:message code='label.verifyDate' /></th>
											<th><spring:message code='label.verifyState' /></th>
											<th><spring:message code='label.year' /></th>
											<th><spring:message code='label.amount' /></th>
											<th><spring:message code='label.bill' /></th>

										</tr>
									</thead>
									<tbody>
										<c:forEach
											items="${reimbursementBean.reimbursementVerificationList }"
											var="entry">

											<tr>
												<td><form:checkbox path="approveRejectList"
														value="${entry.empCode}-${entry.srNo }-${entry.fmCode}-${entry.verifyStatus}-${entry.dependentCode}"
														class="form-control"></form:checkbox></td>
												<td>${entry.empCode }</td>
												<td>${empName }</td>
												<c:choose>
													<c:when test="${entry.fmCode=='555'}">
														<td><spring:message code='label.financialAssist' /></td>
													</c:when>
													<c:when test="${entry.fmCode=='554'}">
														<td><spring:message code='label.bookGrant' /></td>
													</c:when>
													<c:otherwise>
														<td><spring:message code='label.scholarship' /></td>
													</c:otherwise>
												</c:choose>
												<td>${entry.srNo }</td>
												<td>${entry.dependentRelation }</td>
												<td>${entry.dependentName }</td>
												<c:choose>
													<c:when test="${entry.coursePassedCode=='1'}">
														<td><spring:message code='label.courseDesc1' /></td>
													</c:when>
													<c:when test="${entry.coursePassedCode=='2'}">
														<td><spring:message code='label.courseDesc2' /></td>
													</c:when>
													<c:when test="${entry.coursePassedCode=='3'}">
														<td><spring:message code='label.courseDesc3' /></td>
													</c:when>
													<c:when test="${entry.coursePassedCode=='4'}">
														<td><spring:message code='label.courseDesc4' /></td>
													</c:when>
													<c:when test="${entry.coursePassedCode=='5'}">
														<td><spring:message code='label.courseDesc5' /></td>
													</c:when>
													<c:otherwise>
														<td><spring:message code='label.courseDesc6' /></td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${entry.coursePersueCode=='1'}">
														<td><spring:message code='label.courseDesc1' /></td>
													</c:when>
													<c:when test="${entry.coursePersueCode=='2'}">
														<td><spring:message code='label.courseDesc2' /></td>
													</c:when>
													<c:when test="${entry.coursePersueCode=='3'}">
														<td><spring:message code='label.courseDesc3' /></td>
													</c:when>
													<c:when test="${entry.coursePersueCode=='4'}">
														<td><spring:message code='label.courseDesc4' /></td>
													</c:when>
													<c:when test="${entry.coursePersueCode=='5'}">
														<td><spring:message code='label.courseDesc5' /></td>
													</c:when>
													<c:otherwise>
														<td><spring:message code='label.courseDesc6' /></td>
													</c:otherwise>
												</c:choose>
												<td>${entry.perecentage }</td>
												<td>${entry.startYear }</td>
												<td>${entry.endYear }</td>
												<td>${entry.createdDate }</td>
												<td>${entry.verifiedDate }</td>
												<td>${entry.verifyStatus }</td>
												<td>${entry.courseYear }</td>
												<td>${entry.amount }</td>
												<td><button class="btn btn-primary form-control">
														<a target='_blank'
															href=' downloadBill/{${entry.empCode }}/{${entry.srNo }}/{${entry.fmCode }}' style ='color:white;'>View</a>
													</button></td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-md-12">
					<div class="col-md-5"></div>
					<div class="col-md-1">
						<center>
							<button type="submit" onclick="waitLoader();approveForm();"
								class="btn btn-primary">
								<spring:message code="app.lang.approve" />
							</button>
						</center>
					</div>
					<div class="col-md-1">
						<center>
							<button type="submit" onclick="waitLoader();rejectForm();"
								class="btn btn-primary">
								<spring:message code="app.lang.reject" />
							</button>
						</center>
					</div>
					<div class="col-md-5"></div>
				</div>
			</div>

		</form:form>

	</div>

</body>
</html>