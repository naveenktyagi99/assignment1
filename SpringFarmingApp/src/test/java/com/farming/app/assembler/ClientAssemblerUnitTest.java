package com.farming.app.assembler;


import com.farming.app.configuration.PersistentTestConfig;
import com.farming.app.model.Client;
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
public class ClientAssemblerUnitTest {

    Client client;

    @Before
    public void setUp() throws Exception{
         client = ClientAssembler.addClientLinks(TestUtil.getClient());
    }

    @Test
    public void Client_SelfLink_ShouldContainSelfLink() throws Exception{
        assertThat(client.getLink("self").getHref(), containsString("/farming/orgs/clients/100"));
    }

    @Test
    public void Client_OrganisationLink_ShouldContainOrganisationLink() throws Exception{
        assertThat(client.getLink("organisation").getHref(), containsString("/farming/orgs/1"));
    }

    @Test
    public void Client_FarmsLink_ShouldContainFarmsLink() throws Exception{
        assertThat(client.getLink("farms").getHref(), containsString("/farming/orgs/clients/100/farms"));
    }

    @Test
    public void Client_FieldsLink_ShouldContainFieldsLink() throws Exception{
        assertThat(client.getLink("fields").getHref(), containsString("/farming/orgs/clients/100/farms/fields"));
    }
}