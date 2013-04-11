package hci2.group5.project.map.marker;

import hci2.group5.project.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements InfoWindowAdapter {

	private LayoutInflater _inflater;

	public MyInfoWindowAdapter(LayoutInflater inflater) {
		_inflater = inflater;
	}

	@Override
	public View getInfoContents(Marker marker) {
		LinearLayout infoContents = (LinearLayout) _inflater.inflate(R.layout.marker_info_contents, null);

		TextView titleView = (TextView) infoContents.findViewById(R.id.title);
		titleView.setText(marker.getTitle());

		// so snippet can be displayed in multiple line
		TextView snippetView = (TextView) infoContents.findViewById(R.id.snippet);
		String snippet = marker.getSnippet();
		if (snippet == null) {
			infoContents.removeView(snippetView);
		}
		else {
			snippetView.setText(marker.getSnippet());
		}

		return infoContents;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}
}
