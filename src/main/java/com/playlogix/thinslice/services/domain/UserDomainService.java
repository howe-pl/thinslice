package com.playlogix.thinslice.services.domain;

import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.db.dao.IUserDAO;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public class UserDomainService {
    private IUserDAO userDAO;

    public UserDomainService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> getUser(ObjectId objectId) {
        return this.userDAO.get(objectId);
    }

    public Optional<User> getUser(String email) {
        return this.userDAO.get(email);
    }

    public Optional<User> getUser(String email, String password) {
        return this.userDAO.get(email, password);
    }

    public void postUser(User user) {
        this.userDAO.save(user);
    }
}
