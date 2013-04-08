package hci2.group5.project.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;

public class MyMapClickListener implements OnMapClickListener {
	
	private static final float ZOOM_LEVEL = 18f;
	private GoogleMapManager manager;
	
	public MyMapClickListener(GoogleMapManager manager) {
		this.manager=manager;
	}
	
	@Override
	public void onMapClick(LatLng position) {
		// TODO Auto-generated method stub
		if(manager.currentZoom<ZOOM_LEVEL){
			this.manager._googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,ZOOM_LEVEL));
		}
	}

}
