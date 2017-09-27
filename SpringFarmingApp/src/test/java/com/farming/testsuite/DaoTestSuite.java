package com.farming.testsuite;

import com.farming.app.dao.ClientDaoImplTest;
import com.farming.app.dao.FarmDaoImplTest;
import com.farming.app.dao.FieldDaoImplTest;
import com.farming.app.dao.OrganisationDaoImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrganisationDaoImplTest.class, ClientDaoImplTest.class,
                FarmDaoImplTest.class, FieldDaoImplTest.class})

public class DaoTestSuite {

}