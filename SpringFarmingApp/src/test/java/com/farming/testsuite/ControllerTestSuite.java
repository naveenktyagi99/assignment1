package com.farming.testsuite;

import com.farming.app.controller.ClientControllerUnitTest;
import com.farming.app.controller.FarmControllerUnitTest;
import com.farming.app.controller.FieldControllerUnitTest;
import com.farming.app.controller.OrganisationControllerUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({OrganisationControllerUnitTest.class, ClientControllerUnitTest.class,
                FarmControllerUnitTest.class, FieldControllerUnitTest.class})

public class ControllerTestSuite {

}