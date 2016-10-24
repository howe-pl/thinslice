package com.playlogix.thinslice.services;

import com.playlogix.thinslice.LoginConfiguration;
import com.playlogix.thinslice.api.Session;
import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.db.dao.ISessionDAO;
import com.playlogix.thinslice.services.domain.SessionDomainService;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Created by Stephen on 2016/08/04.
 */
public class SessionService extends SessionDomainService {
    private LoginConfiguration configuration;
    UserService userService;

    public SessionService(LoginConfiguration configuration, ISessionDAO sessionDAO, UserService userService) {
        super(sessionDAO);
        this.configuration = configuration;
        this.userService = userService;
    }

    public Optional<Session> readOrExpireSession(ObjectId objectId) {
        if (objectId == null || !ObjectId.isValid(objectId.toString())) {
            return Optional.empty();
        }
        Optional<Session> sessionOptional = getSession(objectId);
        if (!sessionOptional.isPresent()) {
            //Session does not exist
            return Optional.empty();
        } else if(sessionOptional.get().hasExpired(configuration.getSessionExpirySeconds())) {
            //Session has expired so delete it
            deleteSession(objectId);
            return Optional.empty();
        } else {
            //Updates timestamps and saves
            saveSession(sessionOptional.get());
            return sessionOptional;
        }
    }

    public Optional<Session> createSession(Session session) {
        Optional<User> userOptional = this.userService.getUser(session.getEmail(), session.getPassword());
        if(userOptional.isPresent()) {
            session.setUserId(userOptional.get().getId());
            super.saveSession(session);
            return Optional.ofNullable(session);
        }
        return Optional.empty();
    }
}
