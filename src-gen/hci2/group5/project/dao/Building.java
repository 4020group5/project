package hci2.group5.project.dao;

import java.util.List;
import hci2.group5.project.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table BUILDING.
 */
public class Building {

    private Long id;
    /** Not-null value. */
    private String name;
    private long locationId;
    /** Not-null value. */
    private String builtBy;
    private int builtYear;
    private String supplementaryInfo;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient BuildingDao myDao;

    private Location location;
    private Long location__resolvedKey;

    private List<Department> departments;

    public Building() {
    }

    public Building(Long id) {
        this.id = id;
    }

    public Building(Long id, String name, long locationId, String builtBy, int builtYear, String supplementaryInfo) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
        this.builtBy = builtBy;
        this.builtYear = builtYear;
        this.supplementaryInfo = supplementaryInfo;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBuildingDao() : null;
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

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /** Not-null value. */
    public String getBuiltBy() {
        return builtBy;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setBuiltBy(String builtBy) {
        this.builtBy = builtBy;
    }

    public int getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(int builtYear) {
        this.builtYear = builtYear;
    }

    public String getSupplementaryInfo() {
        return supplementaryInfo;
    }

    public void setSupplementaryInfo(String supplementaryInfo) {
        this.supplementaryInfo = supplementaryInfo;
    }

    /** To-one relationship, resolved on first access. */
    public Location getLocation() {
        long __key = this.locationId;
        if (location__resolvedKey == null || !location__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            Location locationNew = targetDao.load(__key);
            synchronized (this) {
                location = locationNew;
            	location__resolvedKey = __key;
            }
        }
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new DaoException("To-one property 'locationId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.location = location;
            locationId = location.getId();
            location__resolvedKey = locationId;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Department> getDepartments() {
        if (departments == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DepartmentDao targetDao = daoSession.getDepartmentDao();
            List<Department> departmentsNew = targetDao._queryBuilding_Departments(id);
            synchronized (this) {
                if(departments == null) {
                    departments = departmentsNew;
                }
            }
        }
        return departments;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetDepartments() {
        departments = null;
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

}
