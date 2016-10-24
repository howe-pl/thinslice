package com.playlogix.thinslice.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.playlogix.thinslice.db.BaseMongoObject;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import java.security.Principal;

/**
 * Created by Stephen on 2016/08/04.
 */
@Entity(value = "session", noClassnameStored = true)
public class Session extends BaseMongoObject {
    public static String USER_ID_KEY = "userId";

    private ObjectId userId;
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getUserId() {
        return  userId;
    }
    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    @Email
    private String email;
    public String getEmail() {
        return email;
    }

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    public String getPassword() {
        return password;
    }

    public Session() {

    }

    public boolean hasExpired(int sessionExpirySeconds) {
        return DateTime.now().isAfter(getLastModified().plusSeconds(sessionExpirySeconds));
    }
}
