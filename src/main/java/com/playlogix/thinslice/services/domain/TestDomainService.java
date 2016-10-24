package com.playlogix.thinslice.services.domain;

import com.playlogix.thinslice.api.Test;
import com.playlogix.thinslice.db.dao.ITestDAO;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Stephen on 2016/10/21.
 */
public class TestDomainService {
    private ITestDAO testDAO;

    public TestDomainService(ITestDAO testDAO) {
        this.testDAO = testDAO;
    }

    public List<Test> test() {
        List<Test> list = testDAO.getList();

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("Hello");
        Test test = new Test(hashSet);
        testDAO.save(test);

        return list;
    }
}
