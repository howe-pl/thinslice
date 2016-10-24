package com.playlogix.thinslice.services.domain;

import com.playlogix.thinslice.api.Session;
import com.playlogix.thinslice.db.dao.ISessionDAO;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public class SessionDomainService {
    private ISessionDAO sessionDAO;

    public SessionDomainService(ISessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public void saveSession(final Session session) {
        sessionDAO.save(session);
    }

    public Optional<Session> getSession(ObjectId objectId) {
        return sessionDAO.get(objectId);
    }

    public boolean deleteSession(ObjectId objectId) {
        return sessionDAO.delete(objectId);
    }
}
