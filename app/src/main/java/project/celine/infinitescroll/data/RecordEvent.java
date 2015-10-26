package project.celine.infinitescroll.data;

import java.util.List;

import project.celine.infinitescroll.model.RecordEntity;

/**
 * Created by celine on 2015/10/26.
 */
public class RecordEvent {
    public long getStart() {
        return start;
    }

    long start;
    public List<RecordEntity> getRecordEntities() {
        return recordEntities;
    }

    List<RecordEntity> recordEntities;

    public RecordEvent(long start, List<RecordEntity> recordEntities) {
        this.recordEntities = recordEntities;
        this.start = start;
    }
}
