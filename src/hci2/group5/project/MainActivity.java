package hci2.group5.project;

import hci2.group5.project.dao.Department;
import hci2.group5.project.db.DatabaseService;
import hci2.group5.project.sideButton.SideButtonClickListenerFactory;
import hci2.group5.project.util.MapViewUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {

    private GoogleMap _googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpGoogleMap();
        initializeNavAndSearchButtonsAndPanes();
        setUpSearchPane();
    }

	private void setUpSearchPane() {
		setUpSearchPaneForDepartmentsRelated();
	}

	private void setUpSearchPaneForDepartmentsRelated() {
		ArrayAdapter<Department> adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_list_item_1, DatabaseService.getAllDepartments(this));
		AutoCompleteTextView autoCompleteDepartments = (AutoCompleteTextView) findViewById(R.id.autoCompleteDepartments);
		autoCompleteDepartments.setAdapter(adapter);
	}

	private void initializeNavAndSearchButtonsAndPanes() {
		View buttons = findViewById(R.id.navAndSearchButtons);
        ImageButton navButton = (ImageButton) findViewById(R.id.navButton);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);

        View navPane = findViewById(R.id.navPane);
        View searchPane = findViewById(R.id.searchPane);

        navButton.setOnClickListener(SideButtonClickListenerFactory.getNavOne(navPane, buttons));
        searchButton.setOnClickListener(SideButtonClickListenerFactory.getSearchOne(searchPane, buttons));
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
