package hci2.group5.project.map.marker;

import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;

import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerFactory {

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
}
