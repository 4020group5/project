package hci2.group5.project.sidePane;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

public class SidePaneOperation {

	private SidePaneState _openState;

	private final View _sidePaneToAnimate;
	private static Float _sidePaneInitX = null;

	public SidePaneOperation(View pane, SidePaneState openState) {
		_sidePaneToAnimate = pane;
		_openState = openState;
	}

	public void animateWithAnotherView(View anotherViewToAnimate, float anotherViewInitX) {

		lazyInitializeSidePaneInitX(_sidePaneToAnimate);

		// get side pane's translation value & new state
		float paneXTranslationValue;
		SidePaneState newState;
		if (SidePaneState.isPaneClosed()) {
			paneXTranslationValue = -_sidePaneToAnimate.getWidth();
			newState = _openState;
		}
		else if (SidePaneState.isState(_openState)) {
			paneXTranslationValue = 0;
			newState = SidePaneState.CLOSED;
		}
		else { // some pane else is open
			// first hide the current open pane
			View currOpenPane = SidePaneState.getCurrPane();
			_sidePaneToAnimate.setX(currOpenPane.getX());
			currOpenPane.setX(_sidePaneInitX);

			paneXTranslationValue = -_sidePaneToAnimate.getWidth();
			newState = _openState;
		}

		float paneX = _sidePaneInitX + paneXTranslationValue;
		ObjectAnimator sidePaneAnimator = buildViewAnimator(_sidePaneToAnimate, paneX);

		// another view animator
		float anotherViewX = anotherViewInitX + paneXTranslationValue;
		ObjectAnimator anotherViewAnimator = buildViewAnimator(anotherViewToAnimate, anotherViewX);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(sidePaneAnimator, anotherViewAnimator);
		animatorSet.start();

		if (newState == SidePaneState.CLOSED) {
			SidePaneState.setStateToClosed();
		}
		else {
			SidePaneState.setOpenState(_openState, _sidePaneToAnimate);
		}
	}

	private void lazyInitializeSidePaneInitX(View thePaneToAnimate) {
		if (_sidePaneInitX == null) {
			_sidePaneInitX = thePaneToAnimate.getX();
		}
	}

	private ObjectAnimator buildViewAnimator(final View view, float x) {
		ObjectAnimator viewAnimator = ObjectAnimator.ofFloat(view, "x", x);
		viewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
		    public void onAnimationUpdate(ValueAnimator animation) {
		    	view.requestLayout();
		    }
		});
		return viewAnimator;
	}
}
