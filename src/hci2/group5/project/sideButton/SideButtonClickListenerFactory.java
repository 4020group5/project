package hci2.group5.project.sideButton;

import hci2.group5.project.sidePane.SidePaneState;
import android.view.View;

public class SideButtonClickListenerFactory {

	public static SideButtonClickListener getNavOne(View pane, View anotherView) {
		return new SideButtonClickListener(pane, anotherView, new SidePaneState("nav"));
	}

	public static SideButtonClickListener getSearchOne(View pane, View anotherView) {
		return new SideButtonClickListener(pane, anotherView, new SidePaneState("search"));
	}
}
