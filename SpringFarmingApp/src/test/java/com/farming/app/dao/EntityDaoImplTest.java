package com.farming.app.dao;

import com.farming.app.configuration.PersistentTestConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;


@ContextConfiguration(classes = { PersistentTestConfig.class})
public abstract class EntityDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

}