package com.farming.testsuite;

import com.farming.app.service.ClientServiceImplUnitTest;
import com.farming.app.service.FarmServiceImplUnitTest;
import com.farming.app.service.FieldServiceImplUnitTest;
import com.farming.app.service.OrganisationServiceImplUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrganisationServiceImplUnitTest.class, ClientServiceImplUnitTest.class,
                FarmServiceImplUnitTest.class, FieldServiceImplUnitTest.class})

public class ServiceTestSuite {

}