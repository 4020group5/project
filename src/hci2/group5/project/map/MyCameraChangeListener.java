package hci2.group5.project.map;

import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;

public class MyCameraChangeListener implements OnCameraChangeListener {

	GoogleMapManager manager;
	
	public MyCameraChangeListener(GoogleMapManager manager) {
		this.manager=manager;
	}
	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		manager.currentZoom=position.zoom;
	}

}
