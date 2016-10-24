package com.playlogix.thinslice.db.dao;

import com.playlogix.thinslice.api.Activity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;

/**
 * Created by Stephen on 2016/08/23.
 */
public class ActivityDAO extends BaseDAO<Activity, ObjectId> implements IActivityDAO {
    public ActivityDAO(final Datastore datastore) {
        super(new MongoDAO(Activity.class, datastore));
    }

    public ArrayList<Activity> getList(ObjectId userId) {
        QueryResults<Activity> activities = super.mongoDAO.find(this.mongoDAO.createQuery().filter("userId =", userId));
        return new ArrayList<>(activities.asList());
    }
}
