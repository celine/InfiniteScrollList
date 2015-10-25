package project.celine.infinitescroll.data;

/**
 * Created by celine on 2015/10/24.
 */

import java.util.Date;

import project.celine.infinitescroll.model.RecordEntity;

public class Record {

    Date created;
    Source source;
    long id;
    Destination destination;
    public Date getCreated() {
        return created;
    }

    public Source getSource() {
        return source;
    }

    public long getId() {
        return id;
    }

    public RecordEntity toEntity() {
        RecordEntity entity = new RecordEntity();
        entity.setRecordId(id);
        entity.setCreated(created);
        if(destination != null) {
            entity.setAmount(destination.getAmount());
            entity.setCurrency(destination.getCurrency());
            entity.setRecipient(destination.getRecipient());
        }
        if(source!= null){
            entity.setNote(source.getNote());
            entity.setSender(source.getSender());
        }
        return entity;
    }


    public static class Source {
        String sender;
        String note;

        public String getSender() {
            return sender;
        }

        public String getNote() {
            return note;
        }
    }

    public static class Destination {
        String recipient;
        int amount;
        String currency;

        public int getAmount() {
            return amount;
        }

        public String getRecipient() {
            return recipient;
        }

        public String getCurrency() {
            return currency;
        }

    }
}
