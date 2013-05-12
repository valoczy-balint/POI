<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/index.css" rel="stylesheet" type="text/css"/>
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="resource/jquery-1.9.1.min.js"></script>
	
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	
	<title>Add POI</title>
</head>

<body>

<w:wrapper>
	<form:form action="/add" modelAttribute="poi" method="post" enctype="multipart/form-data" id="addForm">
		<fieldset>
		
			<legend>Poi information</legend>
			
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
				<form:label for="longitude" path="longitude">Longitude</form:label><br/>
				<form:input path="longitude" maxlength="10" cssClass="textInput" id="longitude"/><br/>
			</p>
			
			<p>
				<form:label for="latitude" path="latitude">Latitude</form:label><br/>
				<form:input path="latitude" maxlength="10" cssClass="textInput" id="latitude"/><br/>
			</p>
			
			<p>
				<form:label for="image" path="image">Image</form:label><br/>
				<form:input cssClass="fileInput" type="file" path="image"/><br/>
			</p>
		
			<p>
				<form:label for="video" path="video">Video</form:label><br/>
				<form:input cssClass="fileInput" type="file" path="video"/><br/>
			</p>
			
			<p>
				<form:label for="video" path="video">Rating</form:label><br/>
				<form:select path="rating">
					<form:options items="${rating}" />
				</form:select>
			</p>
			
			<p>
				<input type="submit" value="Upload"/>
			</p>
			
		</fieldset>
	</form:form>
	
	<div id="addMap">
		
	</div>
	
	<script type = "text/javascript">
		var map;
		var position;
		var markers = new Array();
		
		function initialize() {
			var mapProp = {
				center:new google.maps.LatLng(51.508742,-0.120850),
				zoom:5,
				mapTypeId:google.maps.MapTypeId.ROADMAP
				};
			map = new google.maps.Map(document.getElementById("addMap"), mapProp);
			
			
			google.maps.event.addListener(map, 'click', function(event) {
				//position = new google.maps.LatLng(event.latLng.lat(), event.latLng.lng());

				addMarker(event.latLng);
				setLatLng(event.latLng);
				
				if(markers.length > 1) 	
					markers[markers.length-2].setMap(null);	
		    });
		}
		
		function addMarker(position) {
			var marker = new google.maps.Marker({
		        position: position,
		        map: map
		    });
			markers.push(marker);
		}
		
		function setLatLng(position) {
			jq("#latitude").val(position.lat());
			jq("#longitude").val(position.lng());
		}
	</script>
		
	<script type = "text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCBgeK9uzOoF0DNa69eXp6i-nd9N7EZczg&sensor=false&callback=initialize">
	</script>
	
</w:wrapper>
</body>
</html>