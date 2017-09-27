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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FarmDaoImplTest extends EntityDaoImplTest {

    @Autowired
    FarmDao farmDao;

    @Before
    public void setUp() {
        executeSqlScript("classpath:farming.sql", false);
    }

    @After
    public  void tearDownClass() throws Exception {
        executeSqlScript("classpath:testdata.sql", false);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewFarm_BlankName_ShouldThrowDataIntegrityViolationException() {
       Client client = getClient();
       Farm farm = new Farm(1001, "", client);
        farmDao.add(farm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewFarm_NullName_ShouldThrowDataIntegrityViolationException() {
        Client client = getClient();
        Farm farm = new Farm(1001, null, client);
        farmDao.add(farm);
    }

    @Test(expected = DuplicateKeyException.class)
    public void add_NewFarm_DuplicateClientId_ShouldThrowDataIntegrityViolationException() {
        Client client = getClient();
        Farm farm = new Farm(1, "Farm1", client);
        farmDao.add(farm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewFarm_ZeroFarmId_ShouldThrowDataIntegrityViolationException() {
        Client client = getClient();
        Farm farm = new Farm(0, "Farm1", client);
        farmDao.add(farm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewFarm_NegativeFarmId_ShouldThrowDataIntegrityViolationException()  {
        Client client = getClient();
        Farm farm = new Farm(-1, "Farm1", client);
        farmDao.add(farm);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewFarm_ClientNotFound_ShouldThrowDataIntegrityViolationException()  {
        Client client = getClient();
        client.setClientId(1001);
        Farm farm = new Farm(1001, "Farm1", client);
        farmDao.add(farm);
    }

    @Test
    public void add_NewFarm_ShouldReturnOne()  {
        Client client = getClient();
        Farm farm = new Farm(1001, "Farm1", client);
        int count  = farmDao.add(farm);
        assertEquals(1, count);
    }

    @Test
    public void delete_FarmNotFound_ShouldReturnZero() {
        int count = farmDao.deleteById(1001);
        assertEquals(0, count);
    }

    @Test
    public void delete_FarmFound_ShouldReturnOne() {
        int count = farmDao.deleteById(1);
        assertEquals(1, count);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findById_FarmNotFound_ShouldThrowEmptyResultDataAccessException()  {
        Farm farm = farmDao.findById(1001);
    }

    @Test
    public void findById_FarmFound_ShouldReturnClient()  {

        Farm farm = farmDao.findById(1);
        assertNotNull(farm);
        assertEquals(1, farm.getFarmId());
        assertEquals("Farm1", farm.getFarmName());
        assertEquals(1, farm.getClient().getClientId());
        assertEquals(1, farm.getClient().getOrganisation().getOrgId());
    }

    @Test
    public void findField_FarmNotFound_ShouldReturnEmptyList(){
        List<Field> fields = farmDao.findField(10001);
        assertEquals(0, fields.size());
    }

    @Test
    public void findField_FarmFound_ShouldReturnFieldList(){
        List<Field> fields = farmDao.findField(1);
        assertNotNull(fields);
        assertEquals(2, fields.size());

        assertEquals(1, fields.get(0).getFieldId());
        assertEquals("Field1", fields.get(0).getFieldName());
        assertEquals(1, fields.get(0).getFarm().getFarmId());

        assertEquals(2, fields.get(1).getFieldId());
        assertEquals("Field2", fields.get(1).getFieldName());
        assertEquals(1, fields.get(1).getFarm().getFarmId());
    }

    
    private Organisation getOrganisation(){
        return  new Organisation(1, "Org1","Desc");
    }
    private Client getClient(){
        return new Client(1,"Test Client", getOrganisation());
    }
}