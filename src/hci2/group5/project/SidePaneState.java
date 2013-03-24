package hci2.group5.project;

public enum SidePaneState {

	CLOSED, NAV_OPEN, SEARCH_OPEN;

	private static SidePaneState currState = CLOSED;

	public static void setStatus(SidePaneState newState) {
		currState = newState;
	}

	public static boolean isCurrState(SidePaneState state) {
		return currState == state;
	}

	public static boolean isClosed() {
		return currState == CLOSED;
	}
}
