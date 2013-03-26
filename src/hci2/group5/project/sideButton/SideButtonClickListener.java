package hci2.group5.project.sideButton;

import hci2.group5.project.sidePane.SidePaneOperation;
import hci2.group5.project.sidePane.SidePaneState;
import android.view.View;

class SideButtonClickListener implements View.OnClickListener {

	private SidePaneOperation sidePaneOperation;

	private View anotherViewToAnimate;
	private static Float anotherViewInitX = null;

	public SideButtonClickListener(View pane, View anotherView, SidePaneState openState) {
		sidePaneOperation = new SidePaneOperation(pane, openState);
		anotherViewToAnimate = anotherView;
	}

	@Override
	public void onClick(View v) {
		lazyInitializeAnotherInitX(anotherViewToAnimate);
		sidePaneOperation.animateWithAnotherView(anotherViewToAnimate, anotherViewInitX);
	}

	private void lazyInitializeAnotherInitX(View anotherView) {
		if (anotherViewInitX == null) {
			anotherViewInitX = anotherView.getX();
		}
	}
}