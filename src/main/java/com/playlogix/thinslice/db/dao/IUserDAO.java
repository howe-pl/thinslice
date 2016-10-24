package com.playlogix.thinslice.db.dao;

import com.playlogix.thinslice.api.User;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public interface IUserDAO extends IBaseDAO<User, ObjectId> {
    Optional<User> get(String email);
    Optional<User> get(String email, String password);
}
