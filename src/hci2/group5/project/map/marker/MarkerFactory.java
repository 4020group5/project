package hci2.group5.project.map.marker;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerFactory {

	public static final String BUILDING_MARKER_CLICK_FOR_MORE = "(click for more)";

	public static MarkerOptions getDepartmentMarker(Department department) {
		return new MarkerOptions().position(department.getLocation().toLatLng())
								  .title(department.getName())
								  .snippet(department.getFaculty().getName());
	}

	public static MarkerOptions getLibraryMarker(Library library) {
		return new MarkerOptions().position(library.getLocation().toLatLng())
								  .title(library.getName())
								  .snippet(library.getAddress());
	}

	public static MarkerOptions getFoodServiceMarker(FoodService foodService) {
		String snippet;

		if (foodService.getFloor().isEmpty()) {
			snippet = foodService.getBuilding().getName();
		}
		else {
			snippet = foodService.getHumanReadableFloor() + ", " + foodService.getBuilding().getName();
		}

		return new MarkerOptions().position(foodService.getLocation().toLatLng())
								  .title(foodService.getName())
								  .snippet(snippet);
	}

	public static MarkerOptions getBuildingMarker(Building building, BitmapDescriptor icon) {
		MarkerOptions buildingMarkerOptions = new MarkerOptions().icon(icon)
				.position(building.getLocation().toLatLng())
				.title(building.getName());


		setSnippetIfNeeded(building, buildingMarkerOptions);

		return buildingMarkerOptions;
	}

	private static void setSnippetIfNeeded(Building building, MarkerOptions buildingMarkerOptions) {

		String snippet;

		if (building.hasBuiltInfo() && building.hasSupplementaryInfo()) {
			snippet = building.getBuiltInfo() + " " + BUILDING_MARKER_CLICK_FOR_MORE;
		}
		else if (building.hasBuiltInfo()) { // only has built info
			snippet = building.getBuiltInfo();
		}
		else if (building.hasSupplementaryInfo()) { // only has supplementary info
			snippet = BUILDING_MARKER_CLICK_FOR_MORE;
		}
		else { // has neither built info nor supplementary info
			snippet = "";
		}

		if ( ! snippet.isEmpty()) {
			buildingMarkerOptions.snippet(snippet);
		}
	}
}