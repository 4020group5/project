package hci2.group5.project.map;

import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.Library;
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
		_markerManager.removeAllMarkersIfNeeded();
		_markerManager.addDepartmentMarker(department);
		_markerManager.showLastAddedMarkerInfowindow();
		_googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(department.getLocation().toLatLng(), DECENT_ZOOM_LEVEL));
	}

	public void addLibraryMarker(Library library) {
		_markerManager.removeAllMarkersIfNeeded();
		_markerManager.addLibraryMarker(library);
		_markerManager.showLastAddedMarkerInfowindow();
		_googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(library.getLocation().toLatLng(), DECENT_ZOOM_LEVEL));
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

	private Marker _lastAddedMarker;

	public MarkerManager(GoogleMap googleMap) {
		_googleMap = googleMap;
	}

	public void addLibraryMarker(Library library) {
		Marker anotherMarker = _googleMap.addMarker(MarkerFactory.getLibraryMarker(library));
		_lastAddedMarker = anotherMarker;
	}

	public void removeAllMarkersIfNeeded() {
		if (_lastAddedMarker != null) {
			_lastAddedMarker.remove();
		}
	}

	public void addDepartmentMarker(Department department) {
		Marker anotherMarker = _googleMap.addMarker(MarkerFactory.getDepartmentMarker(department));
		_lastAddedMarker = anotherMarker;
	}

	public void showLastAddedMarkerInfowindow() {
		if (_lastAddedMarker != null) {
			_lastAddedMarker.showInfoWindow();
		}
	}
}