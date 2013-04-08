package hci2.group5.project.map;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.LatLng;

public class MyLocationChangeListener implements OnMyLocationChangeListener{
	
	GoogleMap _googleMap;
	private static final float ZOOM_LEVEL = 18f;
	
	public MyLocationChangeListener (GoogleMap _googleMap) {
		this._googleMap=_googleMap;
	}
	@Override
	public void onMyLocationChange(Location location) {
		// TODO Auto-generated method stub
		LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
		this._googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, ZOOM_LEVEL));
	}

}
