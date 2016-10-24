package com.playlogix.thinslice.db.dao;

import com.mongodb.WriteResult;
import com.playlogix.thinslice.db.BaseMongoObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/23.
 */
public class BaseDAO<T extends BaseMongoObject, K extends ObjectId> {
    protected MongoDAO<T> mongoDAO;

    public BaseDAO() {

    }

    public BaseDAO(MongoDAO<T> mongoDAO) {
        this.mongoDAO = mongoDAO;
    }

    public Optional<T> get(K objectId) {
        return mongoDAO.findById(objectId);
    }

    public ArrayList<T> getList() {
        return new ArrayList<T>(mongoDAO.getList());
    }

    public void save(T entity) {
        mongoDAO.save(entity);
    }

    public boolean delete(K objectId) {
        WriteResult writeResult = mongoDAO.deleteById(objectId);
        return writeResult.getN() > 0;
    }
}
