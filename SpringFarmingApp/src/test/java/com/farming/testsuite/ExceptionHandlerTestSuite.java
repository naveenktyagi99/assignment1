package com.farming.testsuite;

import com.farming.app.exception.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ExceptionHandlerBadRequestTest.class, ExceptionHandlerResourceNotFounndTest.class,
        OrganisationNotFoundExceptionTest.class, ClientNotFoundExceptionTest.class,
        FarmNotFoundExceptionTest.class, FieldNotFoundExceptionTest.class})

public class ExceptionHandlerTestSuite {

}