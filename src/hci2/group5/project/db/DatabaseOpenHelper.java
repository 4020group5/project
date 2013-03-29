package hci2.group5.project.db;

import hci2.group5.project.dao.DaoMaster;

import java.io.IOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseOpenHelper extends DaoMaster.OpenHelper {

	private static final String DB_NAME = "db";

	private Context _context;

	public DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null);
		_context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);

		importData(db);
	}

	private void importData(SQLiteDatabase db) {
		try {
			new DataImporter(_context, db).importAllData();
		} catch (IOException e) {
			Log.e(DatabaseOpenHelper.class.getSimpleName(), e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DaoMaster.dropAllTables(db, true);
		onCreate(db);
	}
}
