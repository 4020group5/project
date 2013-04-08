package hci2.group5.project;

import hci2.group5.project.map.GoogleMapManager;
import hci2.group5.project.sideButton.SideButtonClickListenerFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {

    private GoogleMapManager _googleMapManager;

	private ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpGoogleMap();
        initializeNavAndSearchButtonsAndPanes();
        new SearchPaneSetUp(this, _googleMapManager).setUp();
    }

    @Override
    protected void onResume() {
    	super.onResume();

    	// handle case that google map service is not available on user's device
    	_googleMapManager.initMapIfNeeded();
    }

	private void initializeNavAndSearchButtonsAndPanes() {
		View buttons = findViewById(R.id.navAndSearchButtons);
        ImageButton navButton = (ImageButton) findViewById(R.id.navButton);
        searchButton = (ImageButton) findViewById(R.id.searchButton);

        View navPane = findViewById(R.id.navPane);
        View searchPane = findViewById(R.id.searchPane);

        navButton.setOnClickListener(SideButtonClickListenerFactory.getNavOne(navPane, buttons));
        searchButton.setOnClickListener(SideButtonClickListenerFactory.getSearchOne(searchPane, buttons));
	}

    private void setUpGoogleMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        _googleMapManager = new GoogleMapManager(mapFragment);
    }
}
