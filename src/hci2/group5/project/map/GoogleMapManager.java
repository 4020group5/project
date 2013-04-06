package hci2.group5.project.map;

import hci2.group5.project.dao.Department;
import hci2.group5.project.map.marker.MarkerFactory;
import hci2.group5.project.util.MapViewUtil;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;

public class GoogleMapManager {

	/**
	 * A zoom level that building shape and building name are shown.
	 */
	private static final float DECENT_ZOOM_LEVEL = 17f;

	private GoogleMap _googleMap;
	private MapFragment _mapFragment;

	private UiManager _uiManager;
	private MarkerManager _markerManager;

	public GoogleMapManager(MapFragment mapFragment) {
		_mapFragment = mapFragment;
		_googleMap = mapFragment.getMap();

		_uiManager = new UiManager();
		_markerManager = new MarkerManager(_googleMap);
	}

	public void setUpMyLocationButton() {
		_uiManager.setUpGoogleMapMyLocation(_mapFragment, _googleMap);
	}

	public void addDepartmentMarker(Department department) {
		_markerManager.removeLastAddedDepartmentMarkerIfNeeded();
		_markerManager.addDepartmentMarker(department);
		_markerManager.showLastAddedDepartmentMarkerInfowindow();
		_googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(department.getLocation().toLatLng(), DECENT_ZOOM_LEVEL));
	}
}

class UiManager {

	public void setUpGoogleMapMyLocation(MapFragment mapFragment, GoogleMap googleMap) {
		// make the button show up
		googleMap.setMyLocationEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		// move the button to bottom left
		MapViewUtil.putMyLocationButtonBottomLeft(mapFragment);
	}
}

class MarkerManager {

	private GoogleMap _googleMap;

	private Marker _lastPlacedDepartmentMarker;

	public MarkerManager(GoogleMap googleMap) {
		_googleMap = googleMap;
	}

	public void removeLastAddedDepartmentMarkerIfNeeded() {
		if (_lastPlacedDepartmentMarker != null) {
			_lastPlacedDepartmentMarker.remove();
		}
	}

	public void addDepartmentMarker(Department department) {
		Marker anotherMarker = _googleMap.addMarker(MarkerFactory.getDepartmentMarker(department));
		_lastPlacedDepartmentMarker = anotherMarker;
	}

	public void showLastAddedDepartmentMarkerInfowindow() {
		if (_lastPlacedDepartmentMarker != null) {
			_lastPlacedDepartmentMarker.showInfoWindow();
		}
	}
}