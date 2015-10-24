package project.celine.infinitescroll.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by celine on 2015/10/24.
 */

/**
 *      "recipient":"Justin",

 "amount":39895,

 "currency":"YEN"
 */
@Table(name = "Record")
public class Record {
    @Column(name = "created")
     Date created;
    @Column(name="sender")
    String sender;
    @Column(name="note")
    String note;
    @Column(name="recipient")
    String recipient;
    @Column(name="amount")
    String amount;
    @Column(name="currency")
    String currency;


}
