package com.farming.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ControllerTestSuite.class, ServiceTestSuite.class,
        DaoTestSuite.class, ExceptionHandlerTestSuite.class, AssemblerTestSuite.class})

public class AppTestSuite {

}