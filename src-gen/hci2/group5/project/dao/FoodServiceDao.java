package hci2.group5.project.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import hci2.group5.project.dao.FoodService;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FOOD_SERVICE.
*/
public class FoodServiceDao extends AbstractDao<FoodService, Long> {

    public static final String TABLENAME = "FOOD_SERVICE";

    /**
     * Properties of entity FoodService.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Floor = new Property(2, String.class, "floor", false, "FLOOR");
        public final static Property BuildingId = new Property(3, long.class, "buildingId", false, "BUILDING_ID");
        public final static Property Latitude = new Property(4, double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(5, double.class, "longitude", false, "LONGITUDE");
    };

    private DaoSession daoSession;


    public FoodServiceDao(DaoConfig config) {
        super(config);
    }
    
    public FoodServiceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FOOD_SERVICE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'FLOOR' TEXT NOT NULL ," + // 2: floor
                "'BUILDING_ID' INTEGER NOT NULL ," + // 3: buildingId
                "'LATITUDE' REAL NOT NULL ," + // 4: latitude
                "'LONGITUDE' REAL NOT NULL );"); // 5: longitude
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FOOD_SERVICE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, FoodService entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getFloor());
        stmt.bindLong(4, entity.getBuildingId());
        stmt.bindDouble(5, entity.getLatitude());
        stmt.bindDouble(6, entity.getLongitude());
    }

    @Override
    protected void attachEntity(FoodService entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public FoodService readEntity(Cursor cursor, int offset) {
        FoodService entity = new FoodService( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getString(offset + 2), // floor
            cursor.getLong(offset + 3), // buildingId
            cursor.getDouble(offset + 4), // latitude
            cursor.getDouble(offset + 5) // longitude
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, FoodService entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setFloor(cursor.getString(offset + 2));
        entity.setBuildingId(cursor.getLong(offset + 3));
        entity.setLatitude(cursor.getDouble(offset + 4));
        entity.setLongitude(cursor.getDouble(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(FoodService entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(FoodService entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBuildingDao().getAllColumns());
            builder.append(" FROM FOOD_SERVICE T");
            builder.append(" LEFT JOIN BUILDING T0 ON T.'BUILDING_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected FoodService loadCurrentDeep(Cursor cursor, boolean lock) {
        FoodService entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Building building = loadCurrentOther(daoSession.getBuildingDao(), cursor, offset);
         if(building != null) {
            entity.setBuilding(building);
        }

        return entity;    
    }

    public FoodService loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<FoodService> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<FoodService> list = new ArrayList<FoodService>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<FoodService> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<FoodService> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
