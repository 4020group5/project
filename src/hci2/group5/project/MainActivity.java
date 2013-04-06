package hci2.group5.project;

import hci2.group5.project.dao.Department;
import hci2.group5.project.db.DatabaseService;
import hci2.group5.project.map.GoogleMapManager;
import hci2.group5.project.sideButton.SideButtonClickListenerFactory;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
        setUpSearchPane();
    }

	private void setUpSearchPane() {
		setUpSearchPaneForDepartmentsRelated();
	}

	private void setUpSearchPaneForDepartmentsRelated() {

		AutoCompleteTextView autoCompleteDepartments = (AutoCompleteTextView) findViewById(R.id.autoCompleteDepartments);

		List<Department> autoCompleteListData = DatabaseService.getAllDepartments(this);
		int autoCompleteListItemViewId = R.layout.autocomplete_list_item;
		final ArrayAdapter<Department> adapter = new ArrayAdapter<Department>(this, autoCompleteListItemViewId, autoCompleteListData);

		autoCompleteDepartments.setAdapter(adapter);
		autoCompleteDepartments.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Department department = adapter.getItem(position);
				_googleMapManager.addDepartmentMarker(department);

				// close search pane
				searchButton.performClick();
			}

		});
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
        _googleMapManager.setUpMyLocationButton();
    }
}
