package hci2.group5.project.map;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;
import hci2.group5.project.db.DatabaseService;
import hci2.group5.project.map.marker.MarkerFactory;
import hci2.group5.project.map.marker.MyInfoWindowAdapter;
import hci2.group5.project.map.marker.MyMarkClickListener;
import hci2.group5.project.util.MapViewUtil;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;

public class GoogleMapManager {

	/**
	 * A zoom level that building shape and building name are shown.
	 */
	private static final float DECENT_ZOOM_LEVEL = 17f;

	public GoogleMap _googleMap;
	private MapFragment _mapFragment;

	private UiManager _uiManager;
	private MarkerManager _markerManager;
	private MyMarkClickListener markerClickListener;
	private MyMapClickListener mapClickListener;
	private MyCameraChangeListener cameraChangeListener;

	public float currentZoom;
	
	public GoogleMapManager(MapFragment mapFragment) {
		_mapFragment = mapFragment;
		initMapIfNeeded();
		initListener();
	}
	
	public void initListener() {
		markerClickListener=new MyMarkClickListener(this);
		_googleMap.setOnMarkerClickListener(markerClickListener);
		mapClickListener=new MyMapClickListener(this);
		_googleMap.setOnMapClickListener(mapClickListener);
		cameraChangeListener=new MyCameraChangeListener(this);
		_googleMap.setOnCameraChangeListener(cameraChangeListener);
		
	}
	
	public void initMapIfNeeded() {
		if (_googleMap == null) {
			// 2 cases now. It's either google play service is unavailable or not really initialized

			_googleMap = _mapFragment.getMap();
			if (_googleMap == null) {
				// google play service is not available on user's device, we simply return.
				// activity will call this method in onResume()
				return;
				// then MapFragment will show a label and a button directing user to download google play service
			}

			_uiManager = new UiManager();
			_markerManager = new MarkerManager(_googleMap);

			setUpMyLocationButton();
	        addBuildingMarkers();
		}
	}

	public void addBuildingMarkers() {
    	DaoSession daoSession = DatabaseService.getDaoSession(_mapFragment.getActivity());
    	List<Building> buildings = DatabaseService.getAllBuildings(daoSession);

    	_googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter(_mapFragment.getActivity()));
    	_markerManager.addBuildingMarkers(buildings);
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

	public void addFoodServiceMarkers(List<FoodService> foodServices) {
		_markerManager.removeAllMarkersIfNeeded();
		_markerManager.addFoodServiceMarkers(foodServices);
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

	private List<Marker> _lastAddedMarkers;

	public MarkerManager(GoogleMap googleMap) {
		_googleMap = googleMap;
		_lastAddedMarkers = new ArrayList<Marker>();
	}

	public void addBuildingMarkers(List<Building> buildings) {
		for (Building building : buildings) {
			_googleMap.addMarker(MarkerFactory.getBuildingMarker(building));
		}
	}

	public void addFoodServiceMarkers(List<FoodService> foodServices) {
		for (FoodService foodService : foodServices) {
			Marker anotherMarker = _googleMap.addMarker(MarkerFactory.getFoodServiceMarker(foodService));
			_markerAdded(anotherMarker);
		}

	}

	public void addLibraryMarker(Library library) {
		Marker anotherMarker = _googleMap.addMarker(MarkerFactory.getLibraryMarker(library));
		_markerAdded(anotherMarker);
	}

	private void _markerAdded(Marker marker) {
		_lastAddedMarkers.add(marker);
	}

	public void removeAllMarkersIfNeeded() {
		if ( ! _lastAddedMarkers.isEmpty()) {
			for (Marker marker : _lastAddedMarkers) {
				marker.remove();
			}
			_lastAddedMarkers.clear();
		}
	}

	public void addDepartmentMarker(Department department) {
		Marker anotherMarker = _googleMap.addMarker(MarkerFactory.getDepartmentMarker(department));
		_markerAdded(anotherMarker);
	}

	public void showLastAddedMarkerInfowindow() {
		if ( ! _lastAddedMarkers.isEmpty()) {
			Marker lastAddedMarker = _lastAddedMarkers.get(_lastAddedMarkers.size() - 1);
			lastAddedMarker.showInfoWindow();
		}
	}
}