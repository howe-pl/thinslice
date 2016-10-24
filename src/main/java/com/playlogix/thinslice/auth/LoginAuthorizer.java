package com.playlogix.thinslice.auth;

import com.google.common.base.Strings;
import com.playlogix.thinslice.api.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by Stephen on 2016/08/05.
 */
public class LoginAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(final User principal, final String role) {
        if ((principal == null) || Strings.isNullOrEmpty(role)) {
            return false;
        } else {
            return principal.hasSecurityRole(role);
        }
    }
}
