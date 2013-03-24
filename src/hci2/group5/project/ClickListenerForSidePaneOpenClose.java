package hci2.group5.project;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.Button;

public class ClickListenerForSidePaneOpenClose implements View.OnClickListener {
	
	private boolean isLazyInitialized;

	// x values
	private float _buttonsXWhenPaneClosed;
	private float _buttonsXWhenPaneOpen;
	private float _sidePaneXWhenPaneClosed;
	private float  _sidePaneXWhenPaneOpen;

	// UI elements related to PaneOperation
	private Button _navButton;
	private Button _searchButton;
	private View _navAndSearchButtonContainer;
	private View _sidePane;
	
	public ClickListenerForSidePaneOpenClose(final Button navButton, final Button searchButton, final View sidePane) {
		isLazyInitialized = false;
		
		_navButton = navButton;
		_searchButton = searchButton;
		_sidePane = sidePane;
	}
	
	private void lazyInitialize() {
		if (isLazyInitialized) {
			return;
		}
		
		_navAndSearchButtonContainer = (View) _navButton.getParent();
		
		_buttonsXWhenPaneClosed = _navAndSearchButtonContainer.getX();
		_buttonsXWhenPaneOpen = _buttonsXWhenPaneClosed - _sidePane.getWidth();
		_sidePaneXWhenPaneClosed = _sidePane.getX();
		_sidePaneXWhenPaneOpen = _sidePaneXWhenPaneClosed - _sidePane.getWidth();

		isLazyInitialized = true;
	}
	
	@Override
	public void onClick(View v) {
		lazyInitialize();
		
		// get the x values we want to translate to
		float buttonsX = (SidePaneState.isClosed()) ? _buttonsXWhenPaneClosed :_buttonsXWhenPaneOpen;
		float sidePaneX = (SidePaneState.isClosed()) ? _sidePaneXWhenPaneClosed :_sidePaneXWhenPaneOpen;
		
		// build animator for navAndSearchButton
		ObjectAnimator buttonsAnimator = ObjectAnimator.ofFloat(_navAndSearchButtonContainer, "x", buttonsX);
		buttonsAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
            	_navAndSearchButtonContainer.requestLayout();
            }
        });
		
		// build animator for sidePane
		ObjectAnimator sidePaneAnimator = ObjectAnimator.ofFloat(_sidePane, "x", sidePaneX);
		sidePaneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
            	_sidePane.requestLayout();
            }
        });

		// toggle state
		if (SidePaneState.isCurrState(SidePaneState.NAV_OPEN)) {
			SidePaneState.setStatus(SidePaneState.CLOSED);
		}
		else if (SidePaneState.isClosed()) {
			SidePaneState.setStatus(SidePaneState.NAV_OPEN);
		}

		// use AnimatorSet so the animations happens at the same time
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(buttonsAnimator, sidePaneAnimator);
		animatorSet.start();
	}
}