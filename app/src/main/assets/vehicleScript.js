var map;
function initmap() {
	// set up the map
	var winHeight = $( window ).height();
	$("#map").css("height",winHeight+"px")
	map = new L.Map('map');

	// create the tile layer with correct attribution
	var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
	var osmAttrib='Map data <a href="http://openstreetmap.org">OpenStreetMap</a> contributors';
	var osm = new L.TileLayer(osmUrl, {attribution: osmAttrib});		

	// start the map in South-East England
	map.setView(new L.LatLng(-8.1119914, -79.0366357),0);
	map.addLayer(osm);
	console.log('ok')
}

function updateLocation(lat,lng,time){ //alert(lat+" - "+lng);
	map.panTo(new L.LatLng(lat,lng));
	var marker = L.marker([lat, lng]).addTo(map);
	marker.bindPopup("<b>MI UBICACION:</b><br>"+time).openPopup();
}

$( document ).ready(function() {
    initmap();
});
