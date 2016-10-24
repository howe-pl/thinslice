package com.playlogix.thinslice.services;

import com.playlogix.thinslice.db.dao.IUserDAO;
import com.playlogix.thinslice.services.domain.UserDomainService;

/**
 * Created by Stephen on 2016/08/05.
 */
public class UserService extends UserDomainService {

    public UserService(IUserDAO userDAO) {
        super(userDAO);
    }
}
