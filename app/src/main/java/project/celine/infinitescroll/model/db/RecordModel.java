package project.celine.infinitescroll.model.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import project.celine.infinitescroll.data.Record;
import project.celine.infinitescroll.model.RecordEntity;
import project.celine.infinitescroll.model.dao.DaoMaster;
import project.celine.infinitescroll.model.dao.DaoSession;
import project.celine.infinitescroll.model.dao.RecordEntityDao;

/**
 * Created by celine on 2015/10/25.
 */
public class RecordModel {
    RecordEntityDao recordEntityDao;
    public RecordModel(Context context){
       DbHelper dbHelper = new DbHelper(context);
        DaoSession daoSession = dbHelper.getDaoSession();
         recordEntityDao = daoSession.getRecordEntityDao();
    }
    public  void saveRecordList(List<Record> recordList){
        List<RecordEntity>recordEntities = new ArrayList<>();
        for(Record record:recordList){
            recordEntities.add(record.toEntity());
        }
        recordEntityDao.insertOrReplaceInTx(recordEntities);
    }

    public List<RecordEntity>getRecordEntities(long from, long to){
        Query<RecordEntity> query=  recordEntityDao.queryBuilder().where(RecordEntityDao.Properties.RecordId.ge(from),RecordEntityDao.Properties.RecordId.le(to)).build();
        return query.list();
    }
}
