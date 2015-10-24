package project.celine.infinitescroll.data;

/**
 * Created by celine on 2015/10/24.
 */

import java.util.Date;

public class Record {
    Date created;
    Source source;
    int id;

    public static class Source {
        String sender;
        String note;
    }

    public static class Destination {
        String recipient;
        int amount;
        String currency;

    }
}
