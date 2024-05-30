<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code=""/>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- <meta charset="utf-8"> -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<%@ page import ="java.util.*"%>
  <title><spring:message code="app.lang.HomeTitle"/></title>
  <link rel="icon" href="<c:url value='/assets/img/icon.png'/>" style="width:70px;height:70px;">
  <!-- Custom fonts for this template-->
  <link href="<c:url value='/assets/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<c:url value='/assets/vendor/datatables/dataTables.bootstrap4.min.css' />">
  <!-- Custom styles for this template-->
 
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/css/sb-admin-2.min.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value='/assets/bootstrap-5.0.0/css/bootstrap.min.css' />" > 
<script src="<c:url value='/assets/js/jquery-1.12.4.min.js'/>" type="text/javascript"></script>
 <script type="text/javascript">
	function preventBack() {
		window.history.forward();
	}

	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
	function checkSession() {
		$.ajax({
			type :"GET",
			url : 'checkSessionLogout.aspx',
			success : function(data){
				var datatmp = JSON.parse(data);	
		  		$.each(datatmp,function(key,value){
		  			if(value==true){
		  				alert("Your session has been expired!");
		  				window.location.href = "logout.aspx";
		  			}
		  		});
			} 
		});
	 } 
	
	
	function callController(appName){
		
		$.ajax({
            url: "firstLevelHomeController",
            type: 'POST',
            data: {appName:appName},
            success: function(data){
            	
         }

      });
		
	}
	
</script>

<style type="text/css">
.modal-header {
	padding: 15px;
	background-color: #5cb85c;
}

body {
	padding-right: 0 !important
}
.sub-item{display:block; background:#A5D7E8; width: 78%; margin-left: 20%; border-radius:4px; margin-bottom:10px; margin-top:-5px;}
.sub-item ul{list-style:none; padding:0px;overflow-y: auto;}
.sub-item ul li{text-align:left; border-bottom:1px solid rgba(255, 255, 255, 0.5);} 
.sub-item ul li:last-child{border:none;}
.sub-item ul li a{font-size:13px; color:#000; padding:5px 5px 3px 12px; display:block;} 
.sub-item ul li a:hover{text-decoration:none; background:#395B64; color:#fff;}
.nav-link.collapsible {cursor: pointer;}
/*.nav-link.active, .nav-link.collapsible:hover {background-color: #144272; width:90%; margin:auto;}*/
.nav-link.collapsible:after {content: '\002B';color: white; font-weight: bold; float: right;margin-left: 5px;}
.nav-link.active:after {content: "\2212";}
.sub-item.content {max-height: 0; overflow: hidden; transition: max-height 0.2s ease-out;}
</style>
</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-success sidebar sidebar-dark accordion" id="accordionSidebar" style="overflow-y: scroll;overflow-x: hidden; height: 247px; margin-bottom: 20px; background-color: #146C94">

      <!-- Sidebar - Brand -->
      
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="home.html" style = "height:50px; width:110px">
        <img src="<c:url value='/assets/img/logo.png'/>">       
      </a>
      <!-- start cvpc payment process module -->
       <c:forEach var="listValue" items="${userApplications}">
      		<li class="nav-item">
        		<a class="nav-link" id = "forwardNav"  href = "<c:url value='/firstLevelController/${listValue}'/>">
          		<em></em> &nbsp; 
          		<span>${listValue}</span>
        		</a> 
      		</li>
      </c:forEach>
         
      <!-- Divider -->
    </ul>
    <!-- End of Sidebar -->
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light topbar mb-2 static-top shadow" style="background-color: #146C94 !important;">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

         <div class="d-none d-sm-inline-block form-inline mr-auto my-2 my-md-0 mw-100 navbar-search">
          <h4 class="text-white"><strong><spring:message code="app.lang.HomeTitle"/></strong></h4>   
         </div>
          <!-- Nav Item - User Information -->
           <div class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-white small" style="font-weight:bold; font-size:14px !important;"><spring:message code="app.lang.name"/> : ${username}</span>
                <img class="img-profile rounded-circle" style="width:32px;height:32px; border:2px solid #fff; margin-left:5px;" src="<c:url value='/assets/img/user-icon2.png'/>" />
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="afterLogin">
                  <i class="fas fa-home fa-sm fa-fw mr-2"></i>
                  <spring:message code="app.lang.homeButton"/>
                </a> 
                 <%--  <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="resetChangePassword.html">
                  <mi class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></mi>
                  <spring:message code="app.lang.ChangePasswordButton"/>
                </a>   --%>             
               
                <a class="dropdown-item" href="logout">
                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2"></i>
                  <spring:message code="app.lang.logoutButton"/>
                </a>
                <!--   Added By brijesh 07-09-2022 -->
                  
               <%--  <a class="dropdown-item" href="changePassword.aspx">
                   <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2"></i> 
                  <spring:message code="app.lang.changePassword"/> 
                  </a> --%>
              </div>
              </div>
            <!-- </li> -->
         
        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid px-2">

          <!-- Page Heading -->
            <c:if test="${DASHBOARD == true}">
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800"><spring:message code="app.lang.dashboard"/></h1>
          </div>
			</c:if>
         <!-- Content Row -->

          <div class="row">
			</div>
          
		<!-- JSP page includes start from here -->
            <!-- Area Chart -->
            <div class="col-xl-12 col-lg-12 np_space">
              <div class="card shadow mb-4 pa_space">              
              
              </div>
			<!-- till here -->
            </div>          
	<!-- JSP page includes start from here -->

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->     

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

 <script type="text/javascript">
		//below code use to display session expire message 
		
		var logoutUser = false;
		var timeoutHnd = null;
		var logouTimeInterval = 15 * 60 * 1000;//${timeOut * 60 * 1000}; // 4 mins               
		function onuser_activite() {
		    if (logoutUser) {		        
		    }
		    else {
		        ResetLogOutTimer();		
		    }
		}
		function OnTimeoutReached() {
		    logoutUser = true;
		    alert("Your session is about to expire! You will be logged out in 15 minutes.");
		    window.location.href = "logout.aspx";
		}
		
		function ResetLogOutTimer() {
		    clearTimeout(timeoutHnd);
		    // set new timer
		    timeoutHnd = setTimeout('OnTimeoutReached();', logouTimeInterval);
		}
		
		document.body.onclick = onuser_activite;
		timeoutHnd = setTimeout('OnTimeoutReached();', logouTimeInterval);
		
		window.setInterval(function () {
			checkSession();		       
		   }, 15 * 60 * 1000);
</script> 

  <!-- Bootstrap core JavaScript-->
  <script src="<c:url value='/assets/vendor/jquery/jquery.min.js'/>" type="text/javascript"></script>
  <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js'/>" type="text/javascript"></script>

  <!-- Core plugin JavaScript-->
  <script src="<c:url value='/assets/vendor/jquery-easing/jquery.easing.min.js'/>" type="text/javascript"></script>

  <!-- Custom scripts for all pages-->
  <script src="<c:url value='/assets/js/sb-admin-2.min.js'/>" type="text/javascript"></script>

  <!-- Page level plugins -->
  <script src="<c:url value='/assets/js/jquery.dataTables.min.js'/>" type="text/javascript"></script>

  
  <script src="<c:url value='/assets/js/jquery-ui.js'/>" type="text/javascript"></script>
<script src="<c:url value='/assets/bootstrap-5.0.0/js/bootstrap.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/assets/js/custom.js'/>" type="text/javascript"></script>
<script src="<c:url value='/assets/js/select2.min.js'/>" type="text/javascript"></script>

<script src="<c:url value='/assets/vendor/datatables/dataTables.bootstrap4.min.js'/>"></script> 
   <script src="<c:url value='/assets/js/demo/datatables-demo.js'/>"></script>  
   
   <script>
var coll = document.getElementsByClassName("collapsible");
var i;
for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    } 
  });
}
</script>
</body>
</html>