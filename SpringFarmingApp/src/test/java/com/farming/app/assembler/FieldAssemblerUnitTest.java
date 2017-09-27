package com.farming.app.assembler;


import com.farming.app.configuration.PersistentTestConfig;
import com.farming.app.model.Field;
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
public class FieldAssemblerUnitTest {

    Field field;

    @Before
    public void setUp() throws Exception{
         field = FieldAssembler.addFieldLinks(TestUtil.getField());
    }

    @Test
    public void Field_SelfLink_ShouldContainSelfLink() throws Exception{
        assertThat(field.getLink("self").getHref(), containsString("/farming/orgs/clients/farms/fields/100"));
    }

    @Test
    public void Field_OrganisationLink_ShouldContainOrganisationLink() throws Exception{
        assertThat(field.getLink("organisation").getHref(), containsString("/farming/orgs/1"));
    }

    @Test
    public void Field_ClientLink_ShouldContainClientLink() throws Exception{
        assertThat(field.getLink("client").getHref(), containsString("/farming/orgs/clients/1"));
    }

    @Test
    public void Field_FarmLink_ShouldContainFieldsLink() throws Exception{
        assertThat(field.getLink("farm").getHref(), containsString("/farming/orgs/clients/farms/1"));
    }
}