package com.playlogix.thinslice.api;

import com.playlogix.thinslice.db.BaseMongoObject;
import org.mongodb.morphia.annotations.Entity;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Stephen on 2016/10/21.
 */
@Entity(value = "test", noClassnameStored = true)
public class Test extends BaseMongoObject {
    private Collection<String> collection = new HashSet<>();

    public Test() {

    }

    public Test(Collection<String> collection) {
        this.collection = collection;
    }

    public Collection<String> getCollection() {
        return collection;
    }
}
