package com.playlogix.thinslice.db.dao;

import com.playlogix.thinslice.db.BaseMongoObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public class MongoDAO<T extends BaseMongoObject> extends BasicDAO<T, ObjectId> {
    public MongoDAO(final Class<T> entityClass, final Datastore datastore) {
        super(entityClass, datastore);
    }

    public MongoDAO(final Datastore datastore) {
        super(datastore);
    }

    public Optional<T> findById(final ObjectId objectId) {
        if (objectId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(super.findOne(Mapper.ID_KEY, objectId));
    }

    public Optional<T> findByKey(final String key, final Object object) {
        if (object == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(super.findOne(key, object));
    }

    public List<T> getList() {
        return super.find().asList();
    }

    @Override
    public Key<T> save(T entity) {
        entity.updateTimestamps();
        return super.save(entity);
    }
}
