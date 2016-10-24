package com.playlogix.thinslice.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public abstract class BaseMongoObject {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @Indexed(value = IndexDirection.DESC)
    private DateTime created;
    @Indexed(value = IndexDirection.DESC)
    private DateTime updated;

    public ObjectId getId() {
        return id;
    }

    @JsonIgnore
    public DateTime getLastModified() {
        return Optional.ofNullable(updated).orElse(created);
    }

    public void updateTimestamps() {
        if (this.created == null) {
            this.created = DateTime.now();
        } else {
            this.updated = DateTime.now();
        }
    }
}
