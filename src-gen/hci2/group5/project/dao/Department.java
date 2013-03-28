package hci2.group5.project.dao;

import hci2.group5.project.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DEPARTMENT.
 */
public class Department {

    private Long id;
    /** Not-null value. */
    private String name;
    private long buildingId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DepartmentDao myDao;

    private Building building;
    private Long building__resolvedKey;


    public Department() {
    }

    public Department(Long id) {
        this.id = id;
    }

    public Department(Long id, String name, long buildingId) {
        this.id = id;
        this.name = name;
        this.buildingId = buildingId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDepartmentDao() : null;
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

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
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

}