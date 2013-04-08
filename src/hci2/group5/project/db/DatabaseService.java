package hci2.group5.project.db;

import hci2.group5.project.dao.Building;
import hci2.group5.project.dao.BuildingDao;
import hci2.group5.project.dao.DaoMaster;
import hci2.group5.project.dao.DaoSession;
import hci2.group5.project.dao.Department;
import hci2.group5.project.dao.FoodService;
import hci2.group5.project.dao.Library;

import java.util.List;

import android.content.Context;
import de.greenrobot.dao.query.Query;

public class DatabaseService {

	/**
	 * Basically, DaoSession object will cache query result.
	 *
	 * @see de.greenrobot.dao.AbstractDaoSession
	 */
	public static DaoSession getDaoSession(Context context) {
		DaoMaster daoMaster = new DaoMaster(new DatabaseOpenHelper(context).getReadableDatabase());
		DaoSession daoSession = daoMaster.newSession();
		return daoSession;
	}

	public static List<Department> getAllDepartments(DaoSession daoSession) {
		return daoSession.getDepartmentDao().queryBuilder().list();
	}

	public static List<Library> getAllLibraries(DaoSession daoSession) {
		return daoSession.getLibraryDao().queryBuilder().list();
	}

	public static List<FoodService> getAllFoodServices(DaoSession daoSession) {
		return daoSession.getFoodServiceDao().queryBuilder().list();
	}

	public static List<Building> getAllBuildings(DaoSession daoSession) {
		return daoSession.getBuildingDao().queryBuilder().list();
	}

	public static String getBuildingSupplementInfo(DaoSession daoSession, String buildingName) {
		BuildingDao buildingDao = daoSession.getBuildingDao();
		Query<Building> query = buildingDao.queryBuilder().where(BuildingDao.Properties.Name.eq(buildingName)).build();
		Building building = query.list().get(0);

		return building.getSupplementaryInfo();
	}
}
