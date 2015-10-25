package project.celine.infinitescroll.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import project.celine.infinitescroll.model.RecordEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECORD_ENTITY".
*/
public class RecordEntityDao extends AbstractDao<RecordEntity, Long> {

    public static final String TABLENAME = "RECORD_ENTITY";

    /**
     * Properties of entity RecordEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RecordId = new Property(1, Long.class, "recordId", false, "RECORD_ID");
        public final static Property Note = new Property(2, String.class, "note", false, "NOTE");
        public final static Property Currency = new Property(3, String.class, "currency", false, "CURRENCY");
        public final static Property Sender = new Property(4, String.class, "sender", false, "SENDER");
        public final static Property Recipient = new Property(5, String.class, "recipient", false, "RECIPIENT");
        public final static Property Amount = new Property(6, Integer.class, "amount", false, "AMOUNT");
        public final static Property Created = new Property(7, java.util.Date.class, "created", false, "CREATED");
    };


    public RecordEntityDao(DaoConfig config) {
        super(config);
    }
    
    public RecordEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECORD_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"RECORD_ID\" INTEGER," + // 1: recordId
                "\"NOTE\" TEXT," + // 2: note
                "\"CURRENCY\" TEXT," + // 3: currency
                "\"SENDER\" TEXT," + // 4: sender
                "\"RECIPIENT\" TEXT," + // 5: recipient
                "\"AMOUNT\" INTEGER," + // 6: amount
                "\"CREATED\" INTEGER);"); // 7: created
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECORD_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RecordEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long recordId = entity.getRecordId();
        if (recordId != null) {
            stmt.bindLong(2, recordId);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(3, note);
        }
 
        String currency = entity.getCurrency();
        if (currency != null) {
            stmt.bindString(4, currency);
        }
 
        String sender = entity.getSender();
        if (sender != null) {
            stmt.bindString(5, sender);
        }
 
        String recipient = entity.getRecipient();
        if (recipient != null) {
            stmt.bindString(6, recipient);
        }
 
        Integer amount = entity.getAmount();
        if (amount != null) {
            stmt.bindLong(7, amount);
        }
 
        java.util.Date created = entity.getCreated();
        if (created != null) {
            stmt.bindLong(8, created.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public RecordEntity readEntity(Cursor cursor, int offset) {
        RecordEntity entity = new RecordEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // recordId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // note
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // currency
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // sender
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // recipient
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // amount
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // created
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RecordEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRecordId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNote(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCurrency(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSender(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRecipient(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAmount(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCreated(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RecordEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RecordEntity entity) {
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
    
}