package hci2.group5.project.map.marker;

import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.db.DatabaseService;
import android.app.AlertDialog;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MyOnInfoWindowClickListener implements GoogleMap.OnInfoWindowClickListener {

	private Context _context;

	public MyOnInfoWindowClickListener(Context context) {
		_context = context;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		String snippet = marker.getSnippet();
		if (snippet != null && snippet.endsWith(MarkerFactory.BUILDING_MARKER_CLICK_FOR_MORE)) {
			DaoSession daoSession = DatabaseService.getDaoSession(_context);
			String buildingSupplementInfo = DatabaseService.getBuildingSupplementInfo(daoSession, marker.getTitle());
			showMoreInfoDialog(marker.getTitle(), buildingSupplementInfo);
		}
	}

	private void showMoreInfoDialog(String buildingName, String buildingSupplementInfo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(_context)
			.setTitle(buildingName)
			.setMessage(buildingSupplementInfo)
			.setCancelable(true);
		builder.create().show();
	}
}
