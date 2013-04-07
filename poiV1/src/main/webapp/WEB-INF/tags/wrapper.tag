<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ tag description="Header-footer wrapper tag" pageEncoding="ISO-8859-1" %>
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
   	  	<div class="menu_item"><a href="/poi/home">Home</a>
    	</div>
   	  	<div class="menu_item"><a href="/poi/search">Search</a>
    	</div>
   	  	<div class="menu_item"><a href="/poi/add">Add</a>
    	</div>
   	  	<div class="menu_item"><a href="/poi/manage">Manage</a>
    	</div>
    </div>
    
    <div id="content">
		<jsp:doBody/>
    </div>
    
    <footer>
    	<p>V�l�czy B�lint</p>
    	<p>2012-13/2</p>
    </footer>
</div>
</body>
</html>