package hci2.group5.project.dao;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table FOOD_SERVICE.
 */
public class FoodService {

    private Long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String floor;
    private long buildingId;
    private double latitude;
    private double longitude;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient FoodServiceDao myDao;

    private Building building;
    private Long building__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    private Location location;
    // KEEP FIELDS END

    public FoodService() {
    }

    public FoodService(Long id) {
        this.id = id;
    }

    public FoodService(Long id, String name, String floor, long buildingId, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.buildingId = buildingId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFoodServiceDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getFloor() {
        return floor;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /** To-one relationship, resolved on first access. */
    public Building getBuilding() {
        long __key = this.buildingId;
        if (building__resolvedKey == null || !building__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BuildingDao targetDao = daoSession.getBuildingDao();
            Building buildingNew = targetDao.load(__key);
            synchronized (this) {
                building = buildingNew;
            	building__resolvedKey = __key;
            }
        }
        return building;
    }

    public void setBuilding(Building building) {
        if (building == null) {
            throw new DaoException("To-one property 'buildingId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.building = building;
            buildingId = building.getId();
            building__resolvedKey = buildingId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    public Location getLocation() {
    	if (location == null) {
    		location = new Location();

    		location.setLatitude(getLatitude());
    		location.setLongitude(getLongitude());
		}

		return location;
	}

    public String getHumanReadableFloor() {
    	String rawFloor = getFloor();
    	String humanReadableFloor = rawFloor;

    	if (rawFloor.length() == 1) {
			char c = rawFloor.charAt(0);
			if (Character.isDigit(c)) {
				humanReadableFloor = "Floor " + rawFloor;
			}
		}

		return humanReadableFloor;
	}
    // KEEP METHODS END

}