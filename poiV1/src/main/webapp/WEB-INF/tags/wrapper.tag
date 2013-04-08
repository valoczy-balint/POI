<%@ tag description="Header-footer wrapper tag" pageEncoding="UTF-8" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> -->
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Wrapper</title>
</head>

<body>
  <div class="container">
  	<header>
        	<!-- <img src="resource/globe_logo.png" id="header_logo"/> -->
        <div id="header_title" >
        	<h1>POI</h1>
        </div>
	</header>
	
    <div id="menubar">
    	<ul>
    		<li class="menu_item"><a href="/poi/home"><span class="menu_text">Home</span></a></li>
    		<li class="menu_item"><a href="/poi/search"><span class="menu_text">Search</span></a></li>
    		<li class="menu_item"><a href="/poi/add"><span class="menu_text">Add</span></a></li>
    		<li class="menu_item"><a href="/poi/manage"><span class="menu_text">Manage</span></a></li>
    	</ul>
    </div>
    
    <div id="content">
		<jsp:doBody/>
    </div>
    
    <footer>
    	<p>Válóczy Bálint</p>
    	<p>2012-13/2</p>
    </footer>
</div>
</body>
</html>