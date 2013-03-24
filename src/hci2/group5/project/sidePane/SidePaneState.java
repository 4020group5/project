package hci2.group5.project.sidePane;

import android.view.View;

public class SidePaneState {

	public static SidePaneState CLOSED = new SidePaneState("closed");

	private static SidePaneState currState = CLOSED;
	private static View currPane;

	public SidePaneState(String state) {}

	public static void setOpenState(SidePaneState state, View pane) {
		currState = state;
		currPane = pane;
	}

	public static void setStateToClosed() {
		currState = CLOSED;
	}

	public static boolean isState(SidePaneState state) {
		return currState == state;
	}

	public static boolean isPaneClosed() {
		return currState == CLOSED;
	}

	public static View getCurrPane() {
		return currPane;
	}
}
