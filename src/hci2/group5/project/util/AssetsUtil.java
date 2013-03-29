package hci2.group5.project.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

/**
 * Utilities for the assets folder
 *
 */
public class AssetsUtil {

	public static InputStream getAssetInputStream(String assetFileName, Context context) throws IOException {
		InputStream inputStream = context.getAssets().open(assetFileName);
		return inputStream;
	}
}
