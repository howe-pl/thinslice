package com.playlogix.thinslice.auth;

import com.google.common.base.Strings;
import com.playlogix.thinslice.api.Session;
import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.services.SessionService;
import com.playlogix.thinslice.services.UserService;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Created by Stephen on 2016/08/04.
 */
public class LoginAuthenticator implements Authenticator<String, User> {
    private final SessionService sessionService;
    private final UserService userService;

    public LoginAuthenticator(final SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public Optional<User> authenticate(final String accessToken) throws AuthenticationException {
        if (Strings.isNullOrEmpty(accessToken) || !ObjectId.isValid(accessToken)) {
            return Optional.empty();
        }
        final Optional<Session> sessionOptional = sessionService.readOrExpireSession(new ObjectId(accessToken));
        if(!sessionOptional.isPresent()) {
            return Optional.empty();
        }
        return this.userService.getUser(sessionOptional.get().getUserId());
    }
}
