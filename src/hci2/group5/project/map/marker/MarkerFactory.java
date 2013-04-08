package hci2.group5.project.map.marker;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;

import java.util.List;

<<<<<<< HEAD
import com.google.android.gms.maps.model.BitmapDescriptor;
=======
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
>>>>>>> fbb3ef8c0a90aaa9d8291a04e5f2052b8dc6f821
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

	public static MarkerOptions getBuildingMarker(Building building, BitmapDescriptor icon) {
		String departmentStr = "";
		List<Department> departments = building.getDepartments();
		for (int i = 0; i < departments.size(); i++) {
			departmentStr = departmentStr + departments.get(i).getName() + " ";
		}

		String info = "Builder: " + building.getBuiltBy() + "\nBuilt year: "
				+ building.getBuiltYear() + "\nDepartments: " + departmentStr
				+ "\nSupply information: " + building.getSupplementaryInfo();
		return new MarkerOptions().position(building.getLocation().toLatLng()).title(building.getName()).snippet(info).icon(icon);
	}
}
