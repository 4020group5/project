package hci2.group5.project;

import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;
import hci2.group5.project.db.DatabaseService;
import hci2.group5.project.map.GoogleMapManager;
import hci2.group5.project.util.ImeUtils;

import java.util.List;

import android.app.Activity;
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

public class SearchPaneSetUp {

	private final Activity _activity;

	private ImageButton _searchButton;

	private GoogleMapManager _googleMapManager;

	public SearchPaneSetUp(Activity activity, GoogleMapManager googleMapManager) {
		_activity = activity;
		_searchButton = (ImageButton) activity.findViewById(R.id.searchButton);
		_googleMapManager = googleMapManager;
	}

	public void setUp() {
		DaoSession daoSession = DatabaseService.getDaoSession(_activity);
		setUpFoodServiceRelated(DatabaseService.getAllFoodServices(daoSession));
		setUpDepartmentRelated(DatabaseService.getAllDepartments(daoSession));
		setUpLibraryRelated(DatabaseService.getAllLibraries(daoSession));
	}

	private void setUpFoodServiceRelated(final List<FoodService> foodServices) {
		ImageButton foodButton = (ImageButton) _activity.findViewById(R.id.foodButton);
		foodButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				_googleMapManager.addFoodServiceMarkers(foodServices);
				closeSearchPane();
			}
		});

	}

	private void setUpLibraryRelated(List<Library> libraries) {
		// create a dumb Library instance as the first item in the dropdown list
		final int chooseLibraryItemPosition = 0;
		libraries.add(chooseLibraryItemPosition, new Library(Long.valueOf(0), "Please choose one...", "", 0));

		final Spinner spinnerLibrary = (Spinner) _activity.findViewById(R.id.spinnerLibrary);

		int autoCompleteListItemViewId = R.layout.autocomplete_list_item;
		final ArrayAdapter<Library> adapter = new ArrayAdapter<Library>(_activity, autoCompleteListItemViewId, libraries);

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

						closeSearchPane();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// do nothing
					}
				});
			}
		});
	}

	private void setUpDepartmentRelated(List<Department> departments) {
		final AutoCompleteTextView autoCompleteDepartments = (AutoCompleteTextView) _activity.findViewById(R.id.autoCompleteDepartments);

		int autoCompleteListItemViewId = R.layout.autocomplete_list_item;
		final ArrayAdapter<Department> adapter = new ArrayAdapter<Department>(_activity, autoCompleteListItemViewId, departments);

		autoCompleteDepartments.setAdapter(adapter);

		autoCompleteDepartments.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Department department = adapter.getItem(position);
				_googleMapManager.addDepartmentMarker(department);

				closeSearchPane();

				ImeUtils.hideSoftInput(_activity);
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

	private void closeSearchPane() {
		_searchButton.performClick();
	}
}
