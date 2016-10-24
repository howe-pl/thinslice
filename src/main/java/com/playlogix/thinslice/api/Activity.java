package com.playlogix.thinslice.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.playlogix.thinslice.db.BaseMongoObject;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by Stephen on 2016/08/23.
 */
public class Activity extends BaseMongoObject {
    @NotNull
    private ObjectId userId;
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId getUserId() {
        return  userId;
    }
    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    @NotEmpty
    private String name;
    public String getName() {
        return name;
    }

    @NotNull
    private Timestamp time;
    public Timestamp getTime() {
        return time;
    }

    @NotEmpty
    private String day;
    public String getDay() {
        return day;
    }

    @NotNull
    private Integer duration;
    public Integer getDuration() {
        return duration;
    }
}
