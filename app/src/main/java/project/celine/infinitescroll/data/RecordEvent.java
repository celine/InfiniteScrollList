package project.celine.infinitescroll.data;

import java.util.List;

import project.celine.infinitescroll.model.RecordEntity;

/**
 * Created by celine on 2015/10/26.
 */
public class RecordEvent {
    public List<RecordEntity> getRecordEntities() {
        return recordEntities;
    }

    List<RecordEntity> recordEntities;

    public RecordEvent(List<RecordEntity> recordEntities) {
        this.recordEntities = recordEntities;
    }
}
