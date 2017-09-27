package com.farming.app.dao;


import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class OrganisationDaoImplTest extends EntityDaoImplTest {


    @Autowired
    OrganisationDao organisationDao;

    @Before
    public void setUp() {
        executeSqlScript("classpath:farming.sql", false);
    }

    @After
    public  void tearDownClass() throws Exception {
        executeSqlScript("classpath:testdata.sql", false);
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewOrganisation_BlankName_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = new Organisation(1001, "","Desc");
        organisationDao.add(organisation);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewOrganisation_NullName_ShouldThrowDataIntegrityViolationException() throws Exception {
        Organisation organisation = new Organisation(1001, null,"Desc");
        organisationDao.add(organisation);
    }

    @Test(expected = DuplicateKeyException.class)
    public void add_NewOrganisation_DuplicateOrganisationId_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = new Organisation(1, "Org1","Desc");
        organisationDao.add(organisation);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewOrganisation_ZeroOrganisationId_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = new Organisation(0, "Org1","Desc");
        organisationDao.add(organisation);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewOrganisation_NegativeOrganisationId_ShouldThrowDataIntegrityViolationException()  {
        Organisation organisation = new Organisation(-1, "Org1","Desc");
        organisationDao.add(organisation);
    }


    @Test
    public void add_NewOrganisation_NullDescription_ShouldAddOrganisation()  {
        Organisation organisation = new Organisation(1001, "Org1",null);
        organisationDao.add(organisation);
    }

    @Test
    public void add_NewOrganisation_BlankDescription_ShouldAddOrganisation() {
        Organisation organisation = new Organisation(1001, "Org1","");
        organisationDao.add(organisation);
    }

    @Test
    public void add_NewOrganisation_ShouldAddOrganisation() {
        Organisation organisation = new Organisation(1001, "Org1","Desc1");
        organisationDao.add(organisation);
    }

    @Test
    public void delete_OrganisationNotFound_ShouldReturnZero() {
        int count = organisationDao.deleteById(1001);
        assertEquals(0, count);
    }

    @Test
    public void delete_OrganisationFound_ShouldReturnOne() {
        int count = organisationDao.deleteById(1);
       assertEquals(1, count);
    }

    @Test
    public void findById_OrganisationNotFound_ShouldReturnNull() throws Exception {

        Organisation organisation = organisationDao.findById(1001);
        assertNull(organisation);
    }

    @Test
    public void findById_OrganisationFound_ShouldReturnOrganisation() throws Exception {

        Organisation organisation = organisationDao.findById(1);

        assertNotNull(organisation);
        assertEquals(1, organisation.getOrgId());
        assertEquals("Org1", organisation.getOrgName());

        List<Client> clients = organisation.getClients();
        assertEquals(2, clients.size());

        assertEquals(1, clients.get(0).getClientId());
        assertEquals("Client1", clients.get(0).getClientName());

        assertEquals(2, clients.get(1).getClientId());
        assertEquals("Client2", clients.get(1).getClientName());
    }

    @Test
    public void findOrganisationClients_OrganisationNotFound_ShouldReturnNull()throws Exception{
        Organisation organisation = organisationDao.findById(60);
        assertNull(organisation);
    }

    @Test
    public void findOrganisationWithNoClients_ShouldReturnClientListWithZeroElement()throws Exception{
        Organisation organisation = organisationDao.findById(5);
        assertEquals(0, organisation.getClients().size());
    }

    @Test
    public void findAll_ShouldReturnOrganisationList() {

        List<Organisation> organisations = organisationDao.findAll();

        assertEquals(organisations.size(), 6);

        assertEquals(1, organisations.get(0).getOrgId());
        assertEquals("Org1", organisations.get(0).getOrgName());

        assertEquals(2, organisations.get(1).getOrgId());
        assertEquals("Org2", organisations.get(1).getOrgName());

        assertEquals(3, organisations.get(2).getOrgId());
        assertEquals("Org3", organisations.get(2).getOrgName());

        assertEquals(4, organisations.get(3).getOrgId());
        assertEquals("Org4", organisations.get(3).getOrgName());

        assertEquals(5, organisations.get(4).getOrgId());
        assertEquals("Org5", organisations.get(4).getOrgName());

        assertEquals(6, organisations.get(5).getOrgId());
        assertEquals("Org6", organisations.get(5).getOrgName());
    }

    @Test
    public void findFarm_OrganisationNotFound_shouldReturnZeroFarm()throws Exception{
        List<Farm> farms = organisationDao.findFarm(111);
        assertEquals(0, farms.size());
    }

    @Test
    public void findFarm_shouldReturnFarmList()throws Exception{
        List<Farm> farms = organisationDao.findFarm(1);
        assertEquals(4, farms.size());

        assertEquals(1, farms.get(0).getFarmId());
        assertEquals("Farm1", farms.get(0).getFarmName());
        assertEquals(1, farms.get(0).getClient().getOrganisation().getOrgId());

        assertEquals(2, farms.get(1).getFarmId());
        assertEquals("Farm2", farms.get(1).getFarmName());
        assertEquals(1, farms.get(1).getClient().getOrganisation().getOrgId());

        assertEquals(3, farms.get(2).getFarmId());
        assertEquals("Farm3", farms.get(2).getFarmName());
        assertEquals(1, farms.get(2).getClient().getOrganisation().getOrgId());

        assertEquals(4, farms.get(3).getFarmId());
        assertEquals("Farm4", farms.get(3).getFarmName());
        assertEquals(1, farms.get(3).getClient().getOrganisation().getOrgId());
    }

    @Test
    public void findField_shouldReturnFieldList() {

        List<Field> fields = organisationDao.findField(1);
        assertEquals(6, fields.size());

        assertEquals(1, fields.get(0).getFieldId());
        assertEquals("Field1", fields.get(0).getFieldName());
        assertEquals(1, fields.get(0).getFarm().getClient().getOrganisation().getOrgId());

        assertEquals(2, fields.get(1).getFieldId());
        assertEquals("Field2", fields.get(1).getFieldName());
        assertEquals(1, fields.get(1).getFarm().getClient().getOrganisation().getOrgId());

        assertEquals(3, fields.get(2).getFieldId());
        assertEquals("Field3", fields.get(2).getFieldName());
        assertEquals(1, fields.get(2).getFarm().getClient().getOrganisation().getOrgId());

        assertEquals(4, fields.get(3).getFieldId());
        assertEquals("Field4", fields.get(3).getFieldName());
        assertEquals(1, fields.get(3).getFarm().getClient().getOrganisation().getOrgId());

        assertEquals(5, fields.get(4).getFieldId());
        assertEquals("Field5", fields.get(4).getFieldName());
        assertEquals(1, fields.get(4).getFarm().getClient().getOrganisation().getOrgId());

        assertEquals(6, fields.get(5).getFieldId());
        assertEquals("Field6", fields.get(5).getFieldName());
        assertEquals(1, fields.get(5).getFarm().getClient().getOrganisation().getOrgId());
    }

    @Test
    public void findField_OrganisationNotFound_shouldReturnZeroField()throws Exception{
        List<Field> fields = organisationDao.findField(111);
        assertEquals(0, fields.size());
    }
}