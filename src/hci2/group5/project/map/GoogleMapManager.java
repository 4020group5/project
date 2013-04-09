package hci2.group5.project.map;

import hci2.group5.project.R;
import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;
import hci2.group5.project.db.DatabaseService;
import hci2.group5.project.map.marker.MarkerManager;
import hci2.group5.project.map.marker.MyOnInfoWindowClickListener;
import hci2.group5.project.util.MapViewUtil;

import java.util.List;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
		initMapIfNeeded();
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

			try {
				MapsInitializer.initialize(_mapFragment.getActivity());
			} catch (GooglePlayServicesNotAvailableException e) {
				Log.e(GoogleMapManager.class.getSimpleName(), e.toString());
				return;
			}

			_uiManager = new UiManager();
			_markerManager = new MarkerManager(_googleMap);

			setUpMyLocationButton();
	        addBuildingMarkers();
	        setListeners();
		}
	}

	private void setListeners() {
		_googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				if (_googleMap.getCameraPosition().zoom < DECENT_ZOOM_LEVEL) {
					latLngZoom(marker.getPosition(), DECENT_ZOOM_LEVEL);
					return true;
				}
				else {
					return false;
				}
			}
		});

		_googleMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng position) {
				if (_googleMap.getCameraPosition().zoom < DECENT_ZOOM_LEVEL) {
					latLngZoom(position, DECENT_ZOOM_LEVEL);
				}
			}
		});

		_googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
			@Override
			public void onMyLocationChange(Location location) {
				LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
				latLngZoom(position, DECENT_ZOOM_LEVEL);
			}
		});

		_googleMap.setOnInfoWindowClickListener(new MyOnInfoWindowClickListener(_mapFragment.getActivity()));
	}

	private void latLngZoom(LatLng latLng, float zoom) {
		_googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
	}

	public void addBuildingMarkers() {
    	DaoSession daoSession = DatabaseService.getDaoSession(_mapFragment.getActivity());
    	List<Building> buildings = DatabaseService.getAllBuildings(daoSession);

    	BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_building_clickable_icon);
    	_markerManager.addBuildingMarkers(buildings, icon);
	}

	public void setUpMyLocationButton() {
		_uiManager.setUpGoogleMapMyLocation(_mapFragment, _googleMap);
	}

	public void addDepartmentMarker(Department department) {
		_markerManager.removeAllMarkersIfNeeded();
		_markerManager.addDepartmentMarker(department);
		_markerManager.showLastAddedMarkerInfowindow();
		latLngZoom(department.getLocation().toLatLng(), DECENT_ZOOM_LEVEL);
	}

	public void addLibraryMarker(Library library) {
		_markerManager.removeAllMarkersIfNeeded();
		_markerManager.addLibraryMarker(library);
		_markerManager.showLastAddedMarkerInfowindow();
		latLngZoom(library.getLocation().toLatLng(), DECENT_ZOOM_LEVEL);
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