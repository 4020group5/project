package hci2.group5.project.map.marker;

import hci2.group5.project.dao.Department;
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
}
