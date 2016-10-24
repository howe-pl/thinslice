package com.playlogix.thinslice.db.dao;

import com.mongodb.WriteResult;
import com.playlogix.thinslice.api.Test;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.List;
import java.util.Optional;

/**
 * Created by Stephen on 2016/10/21.
 */
public class TestDAO implements ITestDAO {
    private MongoDAO<Test> mongoDAO;

    public TestDAO(final Datastore datastore) {
        mongoDAO = new MongoDAO(Test.class, datastore);
    }

    public Optional<Test> get(ObjectId objectId) {
        return mongoDAO.findById(objectId);
    }

    public List<Test> getList() {
        return mongoDAO.getList();
    }

    public void save(Test entity) {
        mongoDAO.save(entity);
    }

    public boolean delete(ObjectId objectId) {
        WriteResult writeResult = mongoDAO.deleteById(objectId);
        return writeResult.getN() > 0;
    }
}
