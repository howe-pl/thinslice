package com.playlogix.thinslice.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.playlogix.thinslice.db.BaseMongoObject;
import org.hibernate.validator.constraints.Email;
import org.mongodb.morphia.annotations.Indexed;

import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * Created by Stephen on 2016/08/02.
 */
public class User extends BaseMongoObject implements Principal {
    private static final String PRINCIPAL_NAME = "User";

    @Indexed(unique = true)
    @Email
    private String email;
    public String getEmail() {
        return email;
    }

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return PRINCIPAL_NAME;
    }

    public User() {

    }

    public User(String email) {
        this.email = email;
    }

    public boolean hasSecurityRole(final String securityRoleToCheckFor) {
        return true;
    }
}
