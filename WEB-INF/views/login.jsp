<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<!doctype html>
<html lang="en">
  <head>
  	<title>Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

	</head>
	
	<script type="text/javascript">
	
	function submitForm(){
		$('#loginValidate').submit();
	}
	
	</script>
	
	<body>
	<section class="ftco-section">
		<div class="container">
			<div id="errMessage" class="form-group clearfix text-center"
					style="margin-bottom: 5px;">
					<div style="color: green; font-weight: regular;" id="errMsg"></div>
						<c:if test="${! empty message}">
							<div style="color: red; font-weight: bold;">${error}</div>
						</c:if>
					<div class="form-group clearfix text-center">
						<div id="mobileChargesMessage" style="color: red; font-weight: bold;">${error}</div>
						</div>
					</div>
			<div class="row justify-content-center">
				<div class="col-md-12 col-lg-10">
					<div class="wrap d-md-flex">
						<div class="img" style="background-image: url(${pageContext.request.contextPath}/resources/images/sidbi.jpg;">
			      </div>
						<div class="login-wrap p-4 p-md-5">
			      	<div class="d-flex">
			      		<div class="w-100">
			      			<h3 class="mb-4">Sign In</h3>
			      		</div>
								
			      	</div>
		<form:form method="POST" action="login" id="loginValidate" modelAttribute="LoginBean" >
			      		<div class="form-group mb-3">
			      			<label class="label" for = "username">Username</label>
			      			<form:input type="text" path="username" class="form-control" placeholder="Username" autocomplete="true"></form:input>
			      		</div>
		            <div class="form-group mb-3">
		            	<label class="label" for="password">Password</label>
		              <form:input type="password" path ="password" class="form-control" placeholder="Password" autocomplete="true"></form:input>
		            </div>
		            <div class="form-group">
		            	<button type="submit" onClick = "return submitForm()" class="form-control btn btn-primary rounded submit px-3">Sign In</button>
		            </div>
		            
		          </form:form>
		        </div>
		      </div>
				</div>
			</div>
			</div>
	</section>

    <script src="<c:url value='/assets/js/jquery-1.12.4.min.js'/>" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/assets/bootstrap-5.0.0/css/bootstrap.min.css' />" > 
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

	</body>
</html>

