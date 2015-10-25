package project.celine.infinitescroll.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import project.celine.infinitescroll.model.dao.DaoMaster;
import project.celine.infinitescroll.model.dao.DaoSession;

/**
 * Created by celine on 2015/10/25.
 */
public class DbHelper {

    private DaoSession daoSession;
    private DaoMaster daoMaster;

    public DbHelper(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "recordDb", null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}

