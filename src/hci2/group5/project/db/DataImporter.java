package hci2.group5.project.db;

import hci2.group5.project.dao.BuildingDao;
import hci2.group5.project.dao.DepartmentDao;
import hci2.group5.project.dao.FacultyDao;
import hci2.group5.project.dao.FoodServiceDao;
import hci2.group5.project.dao.LibraryDao;
import hci2.group5.project.dao.LocationDao;
import hci2.group5.project.util.AssetsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Imports all data from csv files, e.g buildings and departments.
 *
 */
class DataImporter {

	private Context _context;

	private SQLiteDatabase _writableDb;

	public DataImporter(Context context, SQLiteDatabase writableDb) {
		_context = context;
		_writableDb = writableDb;
	}

	public void importAllData() throws IOException {
		_writableDb.beginTransaction();
		try {
			importFaculties();
			importLocations();
			importBuildings();
			importDepartments();

			importLibraries();
			importFoodServices();

			_writableDb.setTransactionSuccessful();
		}
		finally {
			_writableDb.endTransaction();
		}
	}

	private void importFaculties() throws IOException {
		String csvFileName = "faculties.csv";
		String preparedInsertSqlStatement = String.format("INSERT INTO %s (%s) VALUES (?);",
															FacultyDao.TABLENAME,
															FacultyDao.Properties.Name.columnName);

		importFromCsvFile(csvFileName, preparedInsertSqlStatement);
	}

	private void importLocations() throws IOException {
		String csvFileName = "locations.csv";
		String preparedInsertSqlStatement = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);",
															LocationDao.TABLENAME,
															LocationDao.Properties.Latitude.columnName,
															LocationDao.Properties.Longitude.columnName);

		importFromCsvFile(csvFileName, preparedInsertSqlStatement);
	}

	private void importBuildings() throws IOException {
		String csvFileName = "buildings.csv";
		String preparedInsertSqlStatement = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?);",
															BuildingDao.TABLENAME,
															BuildingDao.Properties.Name.columnName,
															BuildingDao.Properties.LocationId.columnName,
															BuildingDao.Properties.BuiltBy.columnName,
															BuildingDao.Properties.BuiltYear.columnName,
															BuildingDao.Properties.SupplementaryInfo.columnName);

		importFromCsvFile(csvFileName, preparedInsertSqlStatement);
	}

	private void importDepartments() throws IOException {
		String csvFileName = "departments.csv";
		String preparedInsertSqlStatement = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);",
															DepartmentDao.TABLENAME,
															DepartmentDao.Properties.FacultyId.columnName,
															DepartmentDao.Properties.Name.columnName,
															DepartmentDao.Properties.BuildingId.columnName);

		importFromCsvFile(csvFileName, preparedInsertSqlStatement);
	}

	private void importLibraries() throws IOException {
		String csvFileName = "libraries.csv";
		String preparedInsertSqlStatement = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);",
															LibraryDao.TABLENAME,
															LibraryDao.Properties.Name.columnName,
															LibraryDao.Properties.Room.columnName,
															LibraryDao.Properties.BuildingId.columnName);

		importFromCsvFile(csvFileName, preparedInsertSqlStatement);
	}

	private void importFoodServices() throws IOException {
		String csvFileName = "food_services.csv";
		String preparedInsertSqlStatement = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?);",
															FoodServiceDao.TABLENAME,
															FoodServiceDao.Properties.Name.columnName,
															FoodServiceDao.Properties.Floor.columnName,
															FoodServiceDao.Properties.BuildingId.columnName,
															FoodServiceDao.Properties.Latitude.columnName,
															FoodServiceDao.Properties.Longitude.columnName);

		importFromCsvFile(csvFileName, preparedInsertSqlStatement);
	}

	private void importFromCsvFile(String csvFileName, String preparedInsertSqlStatement) throws IOException {

		// make CSVReader ready
		CSVReader reader = _getCSVReader(AssetsUtil.getAssetInputStream(csvFileName, _context));
		// skip header line
		reader.readNext();

		// get ready for the insert statement and it's bound args
		String preparedStatement = preparedInsertSqlStatement;
		// call CSVReader.readAll() thanks to SQLiteStatement.bindAllArgsAsStrings(String[] bindArgs)
		List<String[]> preparedStatementArgsList = reader.readAll();

		// execute insert sql statements
		for (String[] bindArgs: preparedStatementArgsList) {
			SQLiteStatement statement = _writableDb.compileStatement(preparedStatement);
			statement.bindAllArgsAsStrings(bindArgs);
			statement.execute();
		}
	}

	private static CSVReader _getCSVReader(InputStream inputStream) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		CSVReader reader = new CSVReader(inputStreamReader);
		return reader;
	}
}
