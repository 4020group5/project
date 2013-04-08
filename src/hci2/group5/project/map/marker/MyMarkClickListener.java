package hci2.group5.project.map.marker;

import hci2.group5.project.map.GoogleMapManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class MyMarkClickListener implements OnMarkerClickListener{
	
	private static final float ZOOM_LEVEL = 18f;
	private GoogleMapManager manager;
	
	public MyMarkClickListener(GoogleMapManager manager) {
		this.manager=manager;
	}
	
	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		if(manager.currentZoom<ZOOM_LEVEL){
			manager._googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),ZOOM_LEVEL));
			return true;
		}
			
		else {
			return false;
		}
		
	}

}
