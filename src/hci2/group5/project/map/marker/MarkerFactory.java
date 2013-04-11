package hci2.group5.project.map.marker;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerFactory {

	public static final String BUILDING_MARKER_CLICK_FOR_MORE = "Click for supplementary information";

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

		StringBuilder snippetBuilder = new StringBuilder();

		// built info
		if (building.hasBuiltInfo()) {
			snippetBuilder.append(building.getBuiltInfo());
		}

		// departments
		if (building.hasDepartments()) {
			StringBuilder departmentsBuilder = new StringBuilder();

			if (snippetBuilder.length() != 0) {
				departmentsBuilder.append('\n');
			}

			departmentsBuilder.append("Departments:\n");
			for (Department d : building.getDepartments()) {
				departmentsBuilder.append("　");
				departmentsBuilder.append(d.getName());
				departmentsBuilder.append('\n');
			}
			snippetBuilder.append(departmentsBuilder);
		}

		// supplementary info
		if (building.hasSupplementaryInfo()) {
			if (snippetBuilder.length() != 0) {
				snippetBuilder.append('\n');
			}
			snippetBuilder.append(BUILDING_MARKER_CLICK_FOR_MORE);
		}

		// set snippet?
		if (snippetBuilder.length() != 0) {
			buildingMarkerOptions.snippet(snippetBuilder.toString());
		}
	}
}