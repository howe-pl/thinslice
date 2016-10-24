package com.playlogix.thinslice.services;

import com.playlogix.thinslice.db.dao.IActivityDAO;
import com.playlogix.thinslice.services.domain.ActivityDomainService;

/**
 * Created by Stephen on 2016/08/23.
 */
public class ActivityService extends ActivityDomainService {
    public ActivityService(IActivityDAO activityDAO) {
        super(activityDAO);
    }
}
