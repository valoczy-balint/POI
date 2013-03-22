<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ tag description="Header-footer wrapper tag" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>

<body>
  <div class="container">
  	<header>
        <div id="header_logo">
        	<img src="resource/logo.png" width="200"/>
        </div>
        <div id="header_title">
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
    <footer>FOOTER
    </footer>
</div>
</body>
</html>