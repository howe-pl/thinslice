package com.playlogix.thinslice;

import com.playlogix.thinslice.db.MongoDataStoreFactory;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class LoginConfiguration extends Configuration {
    @NotNull
    private Integer sessionExpirySeconds = 1000000;//300
    public Integer getSessionExpirySeconds() {
        return sessionExpirySeconds;
    }

    @JsonProperty("mongo")
    @NotNull
    @Valid
    private MongoDataStoreFactory mongoDataStoreFactory;
    public MongoDataStoreFactory getMongoDataStoreFactory() {
        return mongoDataStoreFactory;
    }
}
