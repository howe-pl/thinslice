package com.playlogix.thinslice.services;

import com.playlogix.thinslice.api.Test;
import com.playlogix.thinslice.db.dao.ITestDAO;
import com.playlogix.thinslice.services.domain.TestDomainService;

import java.util.List;

/**
 * Created by Stephen on 2016/10/21.
 */
public class TestService extends TestDomainService {
    public TestService(ITestDAO testDAO) {
        super(testDAO);
    }

    public List<Test> test() {
        return super.test();
    }
}
