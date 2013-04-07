package hci2.group5.project;

import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.Library;
import hci2.group5.project.db.DatabaseService;
import hci2.group5.project.map.GoogleMapManager;
import hci2.group5.project.sideButton.SideButtonClickListenerFactory;
import hci2.group5.project.util.ImeUtils;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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
		DaoSession daoSession = DatabaseService.getDaoSession(this);
		setUpSearchPaneForDepartmentsRelated(DatabaseService.getAllDepartments(daoSession));
		setUpSearchPaneForLibrariesRelated(DatabaseService.getAllLibraries(daoSession));
	}

	private void setUpSearchPaneForLibrariesRelated(List<Library> libraries) {
		// create a dumb Library instance as the first item in the dropdown list
		final int chooseLibraryItemPosition = 0;
		libraries.add(chooseLibraryItemPosition, new Library(Long.valueOf(0), "Please choose one...", "", 0));

		final Spinner spinnerLibrary = (Spinner) findViewById(R.id.spinnerLibrary);

		int autoCompleteListItemViewId = R.layout.autocomplete_list_item;
		final ArrayAdapter<Library> adapter = new ArrayAdapter<Library>(this, autoCompleteListItemViewId, libraries);

		spinnerLibrary.setAdapter(adapter);

		// keeps onItemSelected from firing off on a newly instantiated Spinner
		spinnerLibrary.post(new Runnable() {
			public void run() {
				spinnerLibrary.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						if (position == chooseLibraryItemPosition) {
							return;
						}

						Library library = adapter.getItem(position);
						_googleMapManager.addLibraryMarker(library);

						// close search pane
						searchButton.performClick();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// do nothing
					}

				});
			}
		});
	}

	private void setUpSearchPaneForDepartmentsRelated(List<Department> departments) {
		final AutoCompleteTextView autoCompleteDepartments = (AutoCompleteTextView) findViewById(R.id.autoCompleteDepartments);

		int autoCompleteListItemViewId = R.layout.autocomplete_list_item;
		final ArrayAdapter<Department> adapter = new ArrayAdapter<Department>(this, autoCompleteListItemViewId, departments);

		autoCompleteDepartments.setAdapter(adapter);

		final Activity thisActivity = this;
		autoCompleteDepartments.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Department department = adapter.getItem(position);
				_googleMapManager.addDepartmentMarker(department);

				// close search pane
				searchButton.performClick();

				ImeUtils.hideSoftInput(thisActivity);
			}

		});

		autoCompleteDepartments.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean isHandled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					autoCompleteDepartments.showDropDown();
					isHandled = true;
				}

				return isHandled;
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
