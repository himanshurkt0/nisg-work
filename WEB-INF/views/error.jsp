<!-- purple x moss 2020 -->
<!DOCTYPE html>
<html>
<head>
 <%--  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css2.css"> --%>
  <script src="${pageContext.request.contextPath}/jquery_api/error.js"></script>
<style>

body {
  background-color: #95c2de;
}

.mainbox {
  background-color: #95c2de;
  margin: auto;
  height: 600px;
  width: 600px;
  position: relative;
}

  .err {
    color: #ffffff;
    font-family: 'Nunito Sans', sans-serif;
    font-size: 11rem;
    position:absolute;
    left: 20%;
    top: 8%;
  }

.far {
  position: absolute;
  font-size: 8.5rem;
  left: 42%;
  top: 15%;
  color: #ffffff;
}

 .err2 {
    color: #ffffff;
    font-family: 'Nunito Sans', sans-serif;
    font-size: 11rem;
    position:absolute;
    left: 68%;
    top: 8%;
  }

.msg {
    text-align: center;
    font-family: 'Nunito Sans', sans-serif;
    font-size: 1.6rem;
    position:absolute;
    left: 16%;
    top: 45%;
    width: 75%;
  }

a {
  text-decoration: none;
  color: white;
}

a:hover {
  text-decoration: underline;
}
</style>

<script type="text/javascript">
	function onloadPage() {
		
		/* let stateObj = {
			id : "100"
		};
		window.history.replaceState(stateObj, "Page 3", "/RetireePortal/handleError.aspx");
		 */
		

		setTimeout('reloadIt()', 2000);
	}
	
	function reloadIt() {
	    if (window.location.href.substr(-2) !== "?r") {
	        /*  window.location = "https://capuat.sidbi.in/CapDesk/"; */
	    	//window.location = "https://retbs.sidbi.in/RetireePortal/";	    
	    }
	}
	
	
</script>
</head>
<body onload = "onloadPage();">
  <div class="mainbox">
    <div class="err">4</div>
    <i class="far fa-question-circle fa-spin"></i>
    <div class="err2">4</div>
    <div class="msg">${err }<p>Let's 
     <a href="logout">Go to Login</a> and try from there.</p></div>
      </div>
    </body>
   </html>   