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
		
		<div id="result" >
			<div id="list" > </div>
			<div id="map"> </div>
		</div>
	</w:wrapper>

	<script type="text/javascript"> 	
		function searchJSON() {
			var params= {
				name:  jq("#name").val(),
				address: jq("#address").val(),
				type: jq("#type").val()
			};
			
			var resultClone = jq("#result").clone();									//the clone is used to restore the result div
			
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
						jq("#searchField").fadeOut("slow", function() {					//fade out the search field
							jq("#result").replaceWith(resultClone);						//restore the result div
						
							jq("#list").hide();
							//jq("#map").hide();
							
							jq.each(data, function(index, listItem) {					//process result list items
								jq("#list").append(
										'<div class="listItem"><table>' + 
											'<tr>' + 
											//'<td>' + index + '</td>' + 
											'<td>' + listItem.name + '</td>' + 
											'<td>' + listItem.address + '</td>' + 
											'</tr><tr>' +
											'<td>' + listItem.type + '</td>' +
											'<td>' + listItem.rating + '</td>' +
											//'<td>' + listItem.imagePath + '</td>' + 
											'</tr>' +
										'</table></div>'
								);
							});
							
							jq("#list").fadeIn("slow");									//fade in the created list
							
							var script = document.createElement("script");
							script.type = "text/javascript";
							script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyCBgeK9uzOoF0DNa69eXp6i-nd9N7EZczg&sensor=false&callback=initialize";
							document.body.appendChild(script);
							//jq("#map").fadeIn("slow");
						});
					},
					error: function (data) {
						jq("#list").replaceWith('<div id="list">Error</div>');
					}
				});
			});
		}
					
		function initialize()
		{
			var mapProp = {
				center:new google.maps.LatLng(51.508742,-0.120850),
				zoom:5,
				mapTypeId:google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById("map"), mapProp);
		}
	
		function loadScript()
		{
			var script = document.createElement("script");
			script.type = "text/javascript";
			script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyCBgeK9uzOoF0DNa69eXp6i-nd9N7EZczg&sensor=false&callback=initialize";
			document.body.appendChild(script);
		}
	</script>
	
	<!-- 
	<script  src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCBgeK9uzOoF0DNa69eXp6i-nd9N7EZczg&sensor=false">
		function initialize() {
			var mapProp = {
					  center:new google.maps.LatLng(51.508742,-0.120850),
					  zoom:5,
					  mapTypeId:google.maps.MapTypeId.ROADMAP
					  };
			var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
		}
	</script> 
	-->

</body>
</html>