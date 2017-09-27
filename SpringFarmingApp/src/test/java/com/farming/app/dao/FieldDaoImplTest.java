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
public class FieldDaoImplTest extends EntityDaoImplTest {

    @Autowired
    FieldDao fieldDao;

    @Before
    public void setUp() {
        executeSqlScript("classpath:farming.sql", false);
    }

    @After
    public  void tearDownClass() throws Exception {
        executeSqlScript("classpath:testdata.sql", false);
    }


    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewField_BlankName_ShouldThrowDataIntegrityViolationException() {
        Farm farm = getFarm();
        Field field = new Field(1001, "", farm);
        fieldDao.add(field);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewField_NullName_ShouldThrowDataIntegrityViolationException() {
        Farm farm = getFarm();
        Field field = new Field(1001, null, farm);
        fieldDao.add(field);
    }

    @Test(expected = DuplicateKeyException.class)
    public void add_NewField_DuplicateClientId_ShouldThrowDataIntegrityViolationException() {
        Farm farm = getFarm();
        Field field = new Field(1, "Field1", farm);
        fieldDao.add(field);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewField_ZeroFieldId_ShouldThrowDataIntegrityViolationException() {
        Farm farm = getFarm();
        Field field = new Field(0, "", farm);
        fieldDao.add(field);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewField_NegativeFieldId_ShouldThrowDataIntegrityViolationException()  {
        Farm farm = getFarm();
        Field field = new Field(-1, "", farm);
        fieldDao.add(field);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void add_NewField_FarmNotFound_ShouldThrowDataIntegrityViolationException()  {
        Farm farm = getFarm();
        farm.setFarmId(1001);
        Field field = new Field(1001, "", farm);
        fieldDao.add(field);
    }

    @Test
    public void add_NewField_ShouldReturnOne()  {
        Farm farm = getFarm();
        Field field = new Field(1001, "Farm1001", farm);
        int count = fieldDao.add(field);

        assertEquals(1, count);
    }

    @Test
    public void deleteById_FieldFound_ShouldDeleteFieldAndReturnOne() {
        int count = fieldDao.deleteById(1);
        assertEquals(1, count);
    }
    @Test
    public void deleteById_FieldNotFound_ShouldReturnZero() {
        int count = fieldDao.deleteById(1001);
        assertEquals(0, count);
    }

    @Test
    public void findById_FieldFound_ShouldReturnFoundField() throws Exception {

        Field field = fieldDao.findById(1);

        assertNotNull(field);
        assertEquals("Field1", field.getFieldName());
        assertEquals(1, field.getFieldId());
        assertEquals(1, field.getFarm().getFarmId());
        assertEquals("Farm1", field.getFarm().getFarmName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findById_FieldNotFound_ShouldReturnNull() throws Exception {
        Field field = fieldDao.findById(101);
    }

    @Test
    public void findAll_ShouldReturnFieldist() {
        List<Field> fields = fieldDao.findAll();
        assertEquals(fields.size(), 8);

        assertEquals(1,fields.get(0).getFieldId());
        assertEquals("Field1", fields.get(0).getFieldName());
        assertEquals(1, fields.get(0).getFarm().getFarmId());

        assertEquals(2,fields.get(1).getFieldId());
        assertEquals("Field2", fields.get(1).getFieldName());
        assertEquals(1, fields.get(1).getFarm().getFarmId());

        assertEquals(3,fields.get(2).getFieldId());
        assertEquals("Field3", fields.get(2).getFieldName());
        assertEquals(2, fields.get(2).getFarm().getFarmId());

        assertEquals(4,fields.get(3).getFieldId());
        assertEquals("Field4", fields.get(3).getFieldName());
        assertEquals(2, fields.get(3).getFarm().getFarmId());

        assertEquals(5,fields.get(4).getFieldId());
        assertEquals("Field5", fields.get(4).getFieldName());
        assertEquals(3, fields.get(4).getFarm().getFarmId());

        assertEquals(6,fields.get(5).getFieldId());
        assertEquals("Field6", fields.get(5).getFieldName());
        assertEquals(3, fields.get(5).getFarm().getFarmId());

        assertEquals(7,fields.get(6).getFieldId());
        assertEquals("Field7", fields.get(6).getFieldName());
        assertEquals(5, fields.get(6).getFarm().getFarmId());

        assertEquals(8,fields.get(7).getFieldId());
        assertEquals("Field8", fields.get(7).getFieldName());
        assertEquals(5, fields.get(7).getFarm().getFarmId());
    }

    private Farm getFarm(){ return new Farm(1,"Farm1001", getClient()); }

    private Client getClient(){ return new Client(1,"Test Client", getOrganisation());
    }

    private Organisation getOrganisation(){
        return  new Organisation(1, "Org1","Desc");
    }
}