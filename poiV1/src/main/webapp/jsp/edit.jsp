<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="w" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../css/index.css" rel="stylesheet" type="text/css"/>
	<script src="../resource/jquery-1.9.1.min.js"></script>
	
	<script type="text/javascript">
		var jq = jQuery.noConflict();
	</script>
	
	<title>Edit Poi</title>
</head>
<body>


													<!-- TODO Merge this shit with the add page, css as well -->


	<w:wrapper>
		<form:form action="/edit" modelAttribute="poi" method="post" enctype="multipart/form-data" id="editForm">
		<fieldset>
		
			<legend>Poi information</legend>
				
				<form:hidden path="id"/>
				
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
				<form:select path="rating">
					<form:options items="${rating}" />
				</form:select>
			</p>
			
			<p>
				<input type="submit" value="Upload"/>
			</p>
			
		</fieldset>
	</form:form>
	
	<div id="editMap"></div>		
	</w:wrapper>
	
	<script type = "text/javascript">
		var map;
		var position;
		var markers = new Array();
		
		function initialize() {
			var position = new google.maps.LatLng(jq("#latitude").val(), jq("#longitude").val());
			
			var mapProp = {
				center:position,
				zoom:5,
				mapTypeId:google.maps.MapTypeId.ROADMAP
				};
			map = new google.maps.Map(document.getElementById("editMap"), mapProp);
			
			addMarker(position);
			
			google.maps.event.addListener(map, 'click', function(event) {
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
</body>
</html>