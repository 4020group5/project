package hci2.group5.project.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class ImeUtils {

	public static void hideSoftInput(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getCurrentFocus().getWindowToken(), 0);
	}
}
