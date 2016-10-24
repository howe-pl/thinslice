package com.playlogix.thinslice.db.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.WriteResult;
import com.playlogix.thinslice.api.Session;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public class SessionDAO implements ISessionDAO {
    private static LoadingCache<ObjectId, Optional<Session>> SESSION_CACHE;

    private MongoDAO<Session> mongoDAO;

    public SessionDAO(final Datastore datastore) {
        mongoDAO = new MongoDAO(Session.class, datastore);
        initSessionCache();
    }


    public Optional<Session> get(ObjectId objectId) {
        return SESSION_CACHE.getUnchecked(objectId);
    }

    public ArrayList<Session> getList() {
        return null;
    }

    public void save(Session session) {
        mongoDAO.save(session);
        SESSION_CACHE.put(session.getId(), Optional.of(session));
    }

    public boolean delete(ObjectId objectId) {
        Optional<Session> sessionOptional = mongoDAO.findById(objectId);
        if (sessionOptional.isPresent()) {
            SESSION_CACHE.invalidate(sessionOptional.get().getId());
            WriteResult writeResult = mongoDAO.deleteByQuery(mongoDAO.createQuery().filter("userId =", sessionOptional.get().getUserId()));
            if(writeResult.getN() > 0) {
                return true;
            }
        }
        return false;
    }

    private void initSessionCache() {
        SESSION_CACHE = CacheBuilder.newBuilder().recordStats().build(
                new CacheLoader<ObjectId, Optional<Session>>() {
                    @Override
                    public Optional<Session> load(final ObjectId objectId) throws Exception {
                        return SessionDAO.this.mongoDAO.findById(objectId);
                    }
                }
        );
    }
}
