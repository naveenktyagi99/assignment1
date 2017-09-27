package com.farming.testsuite;

import com.farming.app.assembler.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ClientAssemblerUnitTest.class, FarmAssemblerUnitTest.class,
                FieldAssemblerUnitTest.class, OrganisationAssemblerUnitTest.class})

public class AssemblerTestSuite {

}