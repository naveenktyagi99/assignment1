package com.farming.app.assembler;


import com.farming.app.configuration.PersistentTestConfig;
import com.farming.app.model.Organisation;
import com.farming.app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { PersistentTestConfig.class})
public class OrganisationAssemblerUnitTest {

     Organisation organisation;

    @Before
    public void setUp() throws Exception{
        organisation = OrganisationAssembler.addOrganisationLinks(TestUtil.getOrganisation());

    }

    @Test
    public void Organisation_SelfLink_ShouldContainSelfLink() throws Exception{
        assertThat(organisation.getLink("self").getHref(), containsString("/farming/orgs/100"));
    }

    @Test
    public void Organisation_ClientsLink_ShouldContainOrganisationLink() throws Exception{
        assertThat(organisation.getLink("clients").getHref(), containsString("/farming/orgs/100/clients"));
    }

    @Test
    public void Organisation_FarmsLink_ShouldContainFarmsLink() throws Exception{
        assertThat(organisation.getLink("farms").getHref(), containsString("/farming/orgs/100/clients/farms"));
    }

    @Test
    public void Organisation_FieldsLink_ShouldContainFieldsLink() throws Exception{
        assertThat(organisation.getLink("farms").getHref(), containsString("/farming/orgs/100/clients/farms"));
    }
}