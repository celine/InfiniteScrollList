package project.celine.infinitescroll.generator;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class RecordDaoGenerator extends DaoGenerator {
    private static final String ROOT_PACKAGE = "project.celine.infinitescroll.model";
    private static final String DAO_PACKAGE = "project.celine.infinitescroll.model.dao";
    private static final String TEST_PACKAGE = "project.celine.infinitescroll.model";

    public RecordDaoGenerator() throws IOException {
    }


    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, ROOT_PACKAGE);
        schema.setDefaultJavaPackageTest(TEST_PACKAGE);
        schema.setDefaultJavaPackageDao(DAO_PACKAGE);
        schema.enableKeepSectionsByDefault();
        addRecord(schema);
        try {
            new DaoGenerator().generateAll(schema, "app/src/main/java/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void addRecord(Schema schema){
        Entity entity = schema.addEntity("RecordEntity");
        entity.addIdProperty().autoincrement();
        entity.addLongProperty("recordId").unique();
        entity.addStringProperty("note");
        entity.addStringProperty("currency");
        entity.addStringProperty("sender");
        entity.addStringProperty("recipient");
        entity.addIntProperty("amount");
        entity.addDateProperty("created");
    }

}