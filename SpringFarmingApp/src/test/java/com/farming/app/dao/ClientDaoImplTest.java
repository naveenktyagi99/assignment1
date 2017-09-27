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
public class ClientDaoImplTest extends EntityDaoImplTest {

    @Autowired
    ClientDao clientDao;

    @Before
    public void setUp() {
        executeSqlScript("classpath:farming.sql", false);
    }

    @After
    public  void tearDownClass()  {
        executeSqlScript("classpath:testdata.sql", false);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewClient_BlankName_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = getOrganisation();
        Client client = new Client(1001, "", organisation);
        clientDao.add(client);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewClient_NullName_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = getOrganisation();
        Client client = new Client(1001, null, organisation);
        clientDao.add(client);
    }

    @Test(expected = DuplicateKeyException.class)
    public void add_NewClient_DuplicateClientId_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = getOrganisation();
        Client client = new Client(1, "Client1", organisation);
        clientDao.add(client);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewCLient_ZeroClientId_ShouldThrowDataIntegrityViolationException() {
        Organisation organisation = getOrganisation();
        Client client = new Client(0, "Client1", organisation);
        clientDao.add(client);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewClient_NegativeClientId_ShouldThrowDataIntegrityViolationException()  {
        Organisation organisation = getOrganisation();
        Client client = new Client(-1, "Client1", organisation);
        clientDao.add(client);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewClient_OrganisationNotFound_ShouldThrowDataIntegrityViolationException()  {
        Organisation organisation = new Organisation(1001, "Org1", "Desc1");
        Client client = new Client(1001, "Client1", organisation);
        clientDao.add(client);
    }

    @Test
    public void add_NewClient_ShouldReturnOne()  {
        Organisation organisation = new Organisation(1, "Org1", "Desc1");
        Client client = new Client(1001, "Client1", organisation);
        int count = clientDao.add(client);
        assertEquals(1, count);
    }

    @Test
    public void delete_ClientNotFound_ShouldReturnZero() {
        int count = clientDao.deleteById(1001);
        assertEquals(0, count);
    }

    @Test
    public void delete_ClientFound_ShouldReturnOne() {
        int count = clientDao.deleteById(1);
        assertEquals(1, count);
    }

    @Test
    public void findById_ClientNotFound_ShouldReturnNull()  {

        Client client = clientDao.findById(1001);
        assertNull(client);
    }

    @Test
    public void findById_ClientFound_ShouldReturnClient()  {

        Client client = clientDao.findById(1);
        assertNotNull(client);
        assertEquals(1, client.getClientId());
        assertEquals("Client1", client.getClientName());
        assertEquals(1, client.getOrganisation().getOrgId());
        assertEquals("Org1", client.getOrganisation().getOrgName());
    }

    @Test
    public void findFarm_ClientNotFound_ShouldReturnZero() throws Exception {
        List<Farm> farms = clientDao.findFarm(1001);
        assertEquals(0,farms.size());

    }

    @Test
    public void findFarm_ClientFoundWithZeroFarm_ShouldReturnZero() throws Exception {
        List<Farm> farms = clientDao.findFarm(7);
        assertEquals(0,farms.size());

    }

    @Test
    public void findFarm_ShouldReturnFarmList() throws Exception {
        List<Farm> farms = clientDao.findFarm(1);
        assertEquals(2,farms.size());

        assertEquals(1, farms.get(0).getFarmId());
        assertEquals("Farm1", farms.get(0).getFarmName());
        assertEquals(1, farms.get(0).getClient().getClientId());

        assertEquals(2, farms.get(1).getFarmId());
        assertEquals("Farm2", farms.get(1).getFarmName());
        assertEquals(1, farms.get(1).getClient().getClientId());
    }

    @Test
    public void findField_ClientNotFound_ShouldReturnZeroFieldInList() throws Exception {
        List<Field> fields = clientDao.findField(111);
        assertEquals(0,fields.size());
    }

    @Test
    public void findField_ClientFoundWithZeroField_ShouldReturnZeroFieldInList() throws Exception {
        List<Field> fields = clientDao.findField(111);
        assertEquals(0,fields.size());
    }

    @Test
    public void findField_ShouldReturnFieldList() throws Exception {
        List<Field> fields = clientDao.findField(1);
        assertEquals(4,fields.size());
    }

    private Organisation getOrganisation(){
        return  new Organisation(1, "Org1","Desc");
    }
}