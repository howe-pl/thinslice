package com.playlogix.thinslice.db.dao;

import com.mongodb.WriteResult;
import com.playlogix.thinslice.api.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public class UserDAO implements IUserDAO {
    private MongoDAO<User> mongoDAO;

    public UserDAO(final Datastore datastore) {
        mongoDAO = new MongoDAO(User.class, datastore);
    }

    public Optional<User> get(ObjectId objectId) {
        return mongoDAO.findById(objectId);
    }

    public Optional<User> get(String email) {
        return Optional.ofNullable(mongoDAO.findOne(this.mongoDAO.createQuery().filter("email =", email)));
    }

    public Optional<User> get(String email, String password) {
        return Optional.ofNullable(mongoDAO.findOne(this.mongoDAO.createQuery().filter("email =", email).filter("password =", password)));
    }

    public ArrayList<User> getList() {
        return null;
    }

    public void save(User entity) {
        mongoDAO.save(entity);
    }

    public boolean delete(ObjectId objectId) {
        WriteResult writeResult = mongoDAO.deleteById(objectId);
        return writeResult.getN() > 0;
    }
}
