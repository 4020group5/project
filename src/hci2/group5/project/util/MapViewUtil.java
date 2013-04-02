package hci2.group5.project.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.MapFragment;

public class MapViewUtil {

	public static void putMyLocationButtonBottomLeft(MapFragment mapFragment) {
		View myLocationButton = _getMyLocationButton(mapFragment);


		// build bottom left layout params
		RelativeLayout.LayoutParams currentTopRightLayoutParams = (RelativeLayout.LayoutParams)myLocationButton.getLayoutParams();

		RelativeLayout.LayoutParams bottomLeftLayoutParams = new RelativeLayout.LayoutParams(currentTopRightLayoutParams);
		bottomLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bottomLeftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		 // set margin values
		int leftMargin = currentTopRightLayoutParams.rightMargin;
		int bottomMargin = currentTopRightLayoutParams.topMargin;
		bottomLeftLayoutParams.setMargins(leftMargin, 0, 0, bottomMargin);


		// set layout params
		myLocationButton.setLayoutParams(bottomLeftLayoutParams);
	}

	private static View _getMyLocationButton(MapFragment mapFragment) {
		// the indexes below are found by debugging
		ViewGroup viewGroup1 = (ViewGroup) mapFragment.getView();
		ViewGroup viewGroup2 = (ViewGroup) viewGroup1.getChildAt(0);
		ViewGroup myPostionButtonContainer = (ViewGroup) viewGroup2.getChildAt(1);
		View myPositionButton = (View) myPostionButtonContainer.getChildAt(0);
		return myPositionButton;
	}
}
