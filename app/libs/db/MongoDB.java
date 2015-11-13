package libs.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import libs.helper.Helper;
import models.MongoMerchant;
import models.MongoUser;
import org.mongodb.morphia.Morphia;

public final class MongoDB {

    /**
     * Connect to DB
     * @return boolean
     */
    public static boolean connect() {

        MongoClientURI mongoURI = new MongoClientURI(Helper.getConf("mongodb.uri"));

        MorphiaObject.mongo = new MongoClient(mongoURI);
        MorphiaObject.morphia = new Morphia();
        MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(MorphiaObject.mongo, mongoURI.getDatabase());

        MorphiaObject.morphia.map(MongoMerchant.class);
        MorphiaObject.morphia.map(MongoUser.class);

        MorphiaObject.datastore.ensureIndexes();
        MorphiaObject.datastore.ensureCaps();
        return true;
    }

    /**
     * Disconnect from DB
     * @return boolean
     */
    public static boolean disconnect() {
        MorphiaObject.morphia = null;
        MorphiaObject.datastore = null;
        MorphiaObject.mongo.close();
        return true;
    }
}

