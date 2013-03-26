package hci2.group5.project.sideButton;

import hci2.group5.project.sidePane.SidePaneOperation;
import hci2.group5.project.sidePane.SidePaneState;
import android.view.View;

class SideButtonClickListener implements View.OnClickListener {

	private SidePaneOperation sidePaneOperation;

	private View anotherViewToAnimate;

	public SideButtonClickListener(View pane, View anotherView, SidePaneState openState) {
		sidePaneOperation = new SidePaneOperation(pane, openState);
		anotherViewToAnimate = anotherView;
	}

	@Override
	public void onClick(View v) {
		sidePaneOperation.animateWithAnotherView(anotherViewToAnimate);
	}
}