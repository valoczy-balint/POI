<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="w" tagdir="/tags/" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	<title>Search</title>
</head>
<body>
	
	<w:wrapper>
		<h1>Search for a Poi</h1>

	<div id="searchField">
		<form:form id="searchForm">
			<fieldset>
			
				<legend>Please provide search criteria</legend>
				
				<p>
					<form:label for="name" path="name">Name</form:label><br/>
					<form:input path="name" maxlength="50" cssClass="textInput"/><br/>
				</p>
				
				<p>
					<form:label for="address" path="address">Address</form:label><br/>
					<form:input path="address" maxlength="200" cssClass="textInput"/><br/>
				</p>
				
				<p>					
					<form:select path="type" cssClass="dropDown">
						<form:options items="${types}"/>
					</form:select>
				</p>
				
				<p>			
					<input type="button" value="SearchJSON" onclick="searchJSON()"/><br/>
					<input type="button" value="Search" onclick="search()"/>
				</p>
				
			</fieldset>
		</form:form>
	</div>
	
	<div id="loading" />
	
	</w:wrapper>

	<script type="text/javascript"> 
	
	function search() {
		jq(function() {
			jq("#id").replaceWith('<div id="result">'+ response + '</div>');
			jq.post("/poi/search",	
				{ 	
					name:  jq("#name").val(),
					address: jq("#address").val(),
					type: jq("#type").val()
				},
				displayResult());
		});
	}
	
	function displayResult(data) {
		var response = jQuery.parseJSON(data);	
		
		jq("#result").replaceWith('<div id="result">'+ response + '</div>');
	}
	
	
	function searchJSON() {
		jq(function() {
			jq("#id").replaceWith('<div id="result">'+ response + '</div>');
			jq.ajax({
		          url: "/poi/search",
		          type: "POST",
		          data: {
		        	  	name:  jq("#name").val(),
						address: jq("#address").val(),
						type: jq("#type").val()
				  		},
		          dataType: "json",
		          beforeSend: function(x) {
		            if (x && x.overrideMimeType) {
		              x.overrideMimeType("application/j-son;charset=UTF-8");
		            }
		          },
		          success: displayJSONResult()
			});
		});
	}
	
	
	function displayJSONResult(data) {
		var response = jQuery.parseJSON(data);
		
		jq("#result").replaceWith('<div id="result">'+ response + '</div>');
	}
	
	jq.ajaxStart(function() {
	    $("#loading").text("Loading...");
	});
	
	jq.ajaxComplete(function() {
	    $("#loading").text("");
	});
	
	</script>

</body>
</html>