package com.playlogix.thinslice.services.domain;

import com.playlogix.thinslice.api.Activity;
import com.playlogix.thinslice.db.dao.IActivityDAO;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/23.
 */
public class ActivityDomainService {
    IActivityDAO activityDAO;

    public ActivityDomainService(IActivityDAO activityDAO) {
        this.activityDAO = activityDAO;
    }

    public Optional<Activity> getActivity(ObjectId objectId) {
        return this.activityDAO.get(objectId);
    }

    public ArrayList<Activity> getActivities(ObjectId userId) {
        return this.activityDAO.getList(userId);
    }

    public void saveActivity(Activity activity) {
        this.activityDAO.save(activity);
    }

    public boolean deleteActivity(ObjectId objectId) {
        return this.activityDAO.delete(objectId);
    }
}
