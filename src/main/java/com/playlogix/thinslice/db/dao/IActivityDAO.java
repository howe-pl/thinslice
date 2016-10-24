package com.playlogix.thinslice.db.dao;

import com.playlogix.thinslice.api.Activity;
import org.bson.types.ObjectId;

import java.util.ArrayList;

/**
 * Created by Stephen on 2016/08/23.
 */
public interface IActivityDAO extends IBaseDAO<Activity, ObjectId> {
    ArrayList<Activity> getList(ObjectId userId);
}
