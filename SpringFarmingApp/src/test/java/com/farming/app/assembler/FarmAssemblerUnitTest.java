package com.farming.app.assembler;


import com.farming.app.configuration.PersistentTestConfig;
import com.farming.app.model.Farm;
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
public class FarmAssemblerUnitTest {

    Farm farm;

    @Before
    public void setUp() throws Exception{
         farm = FarmAssembler.addFarmLinks(TestUtil.getFarm());
    }

    @Test
    public void Farm_SelfLink_ShouldContainSelfLink() throws Exception{
        assertThat(farm.getLink("self").getHref(), containsString("/farming/orgs/clients/farms/100"));
    }

    @Test
    public void Farm_OrganisationLink_ShouldContainOrganisationLink() throws Exception{
        assertThat(farm.getLink("organisation").getHref(), containsString("/farming/orgs/1"));
    }

    @Test
    public void Farm_ClientLink_ShouldContainClientLink() throws Exception{
        assertThat(farm.getLink("client").getHref(), containsString("/farming/orgs/clients/1"));
    }

    @Test
    public void Farm_FieldLink_ShouldContainClientLink() throws Exception{
        assertThat(farm.getLink("fields").getHref(), containsString("/farming/orgs/clients/farms/100/fields"));
    }

}