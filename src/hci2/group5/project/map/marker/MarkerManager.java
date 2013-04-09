package hci2.group5.project.map.marker;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Marker;

public class MarkerManager {

	private GoogleMap _googleMap;

	private List<Marker> _lastAddedMarkers;

	public MarkerManager(GoogleMap googleMap) {
		_googleMap = googleMap;
		_lastAddedMarkers = new ArrayList<Marker>();
	}

	public void addBuildingMarkers(List<Building> buildings, BitmapDescriptor icon) {
		for (Building building : buildings) {
			_googleMap.addMarker(MarkerFactory.getBuildingMarker(building, icon));
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