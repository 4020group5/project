package hci2.group5.project;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.DaoMaster;
import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.db.DatabaseOpenHelper;
import hci2.group5.project.sideButton.SideButtonClickListenerFactory;
import hci2.group5.project.util.MapViewUtil;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {

    private GoogleMap _googleMap;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpGoogleMap();

        View buttons = findViewById(R.id.navAndSearchButtons);
        ImageButton navButton = (ImageButton) findViewById(R.id.navButton);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);

        View navPane = findViewById(R.id.navPane);
        View searchPane = findViewById(R.id.searchPane);

		navButton.setOnClickListener(SideButtonClickListenerFactory.getNavOne(navPane, buttons));
		searchButton.setOnClickListener(SideButtonClickListenerFactory.getSearchOne(searchPane, buttons));

		DaoMaster daoMaster = new DaoMaster(new DatabaseOpenHelper(this).getReadableDatabase());
		DaoSession daoSession = daoMaster.newSession();
		List<Building> buildings = daoSession.getBuildingDao().queryBuilder().list();
		Toast.makeText(this, buildings.get(0).getLocation().getLatitude() + "", Toast.LENGTH_LONG).show();
    }

	private void setUpGoogleMap() {
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        _googleMap = mapFragment.getMap();
        setUpGoogleMapMyLocation(mapFragment);
	}

	private void setUpGoogleMapMyLocation(MapFragment mapFragment) {
		// make the button show up
		_googleMap.setMyLocationEnabled(true);
        _googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        // move the button to bottom left
        MapViewUtil.putMyLocationButtonBottomLeft(mapFragment);
	}
}
