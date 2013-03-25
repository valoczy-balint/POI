<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	<script src="http://yandex.st/json2/2011-10-19/json2.js"></script>
	<title>Search</title>
</head>
<body>

	<w:wrapper>
		<div id="searchField">
			<form:form>
				<fieldset>
				
					<legend>Please provide search criteria</legend>
				<p>
					<label>Name</label><br/>
					<input  id="name" maxlength="50" class="text"/><br/>
				</p>
				
				<p>
					<label>Address</label><br/>
					<input  id="address" maxlength="200" class="text"/><br/>
				</p>
					
				<p>					
					<select class="dropDown" id="type">
						<c:forEach items="${types}" var="type">
							<option><c:out value="${type}"/></option>
						</c:forEach>
					</select>
				</p>
					
				<p>		
					<input type="button" value="Search" onclick="searchJSON()"/>
				</p>
					
				</fieldset>
			</form:form>
		</div>
	
	<div id="result" />
	
	</w:wrapper>

	<script type="text/javascript"> 
	
	function search() {
		jq(function() {
			jq("#result").replaceWith('<div id="result">Script called</div>');
			jq.post("/poi/search",	
				{ 	
					name:  jq("#name").val(),
					address : jq("#address").val(),
					type:  jq("#type").val()
				},
				function(data) {
					var response = jQuery.parseJSON(data);	
					jq("#result").replaceWith('<div id="result">Script callback called</div>');
				});
		});
	}
		
	function displayResult(data) {
		jq("#result").replaceWith('<div id="result">asd</div>');
		jq("#result").replaceWith('<div id="result">'+ response + '</div>');
	}
	
	
	function searchJSON() {
		var params= {
			name:  jq("#name").val(),
			address: jq("#address").val(),
			type: jq("#type").val()
		};
		
		jq(function() {
			jq.ajax({
				url: "/poi/search",
				type: "POST",
				data: JSON.stringify(params),
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				beforeSend: function(x) {
					if (x && x.overrideMimeType) {
						x.overrideMimeType("application/j-son;charset=UTF-8");
					}
					jq("#result").replaceWith('<div id="result">Working</div>');
		        },
				success: function (data) {
					var response = parseJSON(data);	
					//for (var i = 0, len = data.length; i < len; ++i) {
					//var results = JSON.parse(json[i]);
					//var newDiv = $("<div>" + results['1'] + results['2']  "</div>");
					
					//}
					jq("#result").replaceWith('<div id="result">'+ parseJSON(response[0]) + '</div>');
				},
				error: function (data) {
					jq("#result").replaceWith('<div id="result">Error</div>');
				}
			});
		});
	}
	
	function parseJSON(s) {
	    return JSON.parse(s, function (key, value) {
	        var type;
	        if (value && typeof value === 'object') {
	            type = value.type;
	            if (typeof type === 'string' && typeof window[type] === 'function') {
	                return new (window[type])(value);
	            }
	        }
	        return value;
	    });
	}
	</script>

</body>
</html>