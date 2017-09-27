package com.farming.app.service;


import com.farming.app.dao.FieldDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FieldNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;


public class FieldServiceImplUnitTest {


    @InjectMocks
    FieldServiceImpl fieldService;

    @Mock
    FieldDao fieldDao;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewField_BlankName_ShouldThrowDataIntegrityViolationException() throws BadRequestException {

        Farm farm = getFarm();
        Field field = new Field(1001, "", farm);

        when(fieldDao.add(any(Field.class))).thenThrow(DataIntegrityViolationException.class);
        int count = fieldService.add(field);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewField_NullName_ShouldThrowDataIntegrityViolationException() throws BadRequestException  {

        Farm farm = getFarm();
        Field field = new Field(1001, null, farm);

        when(fieldDao.add(any(Field.class))).thenThrow(DataIntegrityViolationException.class);
        int count = fieldService.add(field);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewField_DuplicateClientId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {

        Farm farm = getFarm();
        Field field = new Field(1, "Field1", farm);

        when(fieldDao.add(any(Field.class))).thenThrow(DuplicateKeyException.class);
        int count = fieldService.add(field);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewField_ZeroFieldId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {

        Farm farm = getFarm();
        Field field = new Field(0, "Field1", farm);

        when(fieldDao.add(any(Field.class))).thenThrow(DataIntegrityViolationException.class);
        int count = fieldService.add(field);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewField_NegativeFieldId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException{
        Farm farm = getFarm();
        Field field = new Field(-1, "", farm);

        when(fieldDao.add(any(Field.class))).thenThrow(DataIntegrityViolationException.class);
        int count = fieldService.add(field);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewField_FarmNotFound_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Farm farm = getFarm();
        farm.setFarmId(1001);

        when(fieldDao.add(any(Field.class))).thenThrow(DataIntegrityViolationException.class);
        Field field = new Field(1001, "", farm);
        int count = fieldService.add(field);
    }

    @Test
    public void add_NewField_ShouldReturnOne()
            throws BadRequestException {
        Farm farm = getFarm();
        Field field = new Field(1001, "Farm1001", farm);

        when(fieldDao.add(any(Field.class))).thenReturn(1);
        int count = fieldDao.add(field);

        assertEquals(1, count);
    }

    @Test(expected = FieldNotFoundException.class)
    public void deleteById_FieldNotFound_ShouldReturnZero() throws FieldNotFoundException{

        when(fieldDao.add(any(Field.class))).thenReturn(0);

        int count = fieldService.deleteById(1001);
        assertEquals(0, count);
    }


    @Test
    public void deleteById_FieldFound_ShouldDeleteFieldAndReturnOne() throws FieldNotFoundException{

        when(fieldDao.deleteById(anyInt())).thenReturn(1);

        int count = fieldService.deleteById(1);
        assertEquals(1, count);
    }


    @Test(expected = FieldNotFoundException.class)
    public void findById_FieldNotFound_ShouldReturnNull() throws FieldNotFoundException {
        when(fieldDao.findById(anyInt())).thenThrow(EmptyResultDataAccessException.class);
        Field field = fieldService.findById(101);
    }

    @Test
    public void findById_FieldFound_ShouldReturnFoundField() throws FieldNotFoundException {

        Field actual = TestUtil.getField();

        when(fieldDao.findById(anyInt())).thenReturn(actual);
        Field field = fieldService.findById(1);

        assertNotNull(field);

        assertEquals(actual.getFieldName(), field.getFieldName());
        assertEquals(actual.getFieldId(), field.getFieldId());
        assertEquals(actual.getFarm(), field.getFarm());
    }

    @Test
    public void findAll_ShouldReturnClientFiledList() {

        List<Field> actual = TestUtil.getFields();

        when(fieldDao.findAll()).thenReturn(actual);

       List<Field> fields =  fieldService.findAll();

       assertEquals(actual.size(), fields.size());

        assertEquals(actual.get(0).getFieldId(), fields.get(0).getFieldId());
        assertEquals(actual.get(0).getFieldName(), fields.get(0).getFieldName());
        assertEquals(actual.get(0).getFarm(), fields.get(0).getFarm());

        assertEquals(actual.get(1).getFieldId(), fields.get(1).getFieldId());
        assertEquals(actual.get(1).getFieldName(), fields.get(1).getFieldName());
        assertEquals(actual.get(1).getFarm(), fields.get(1).getFarm());

        assertEquals(actual.get(2).getFieldId(), fields.get(2).getFieldId());
        assertEquals(actual.get(2).getFieldName(), fields.get(2).getFieldName());
        assertEquals(actual.get(2).getFarm(), fields.get(2).getFarm());
    }

    private Farm getFarm(){ return new Farm(1,"Farm1001", getClient()); }

    private Client getClient(){ return new Client(1,"Test Client", getOrganisation());
    }

    private Organisation getOrganisation(){
        return  new Organisation(1, "Org1","Desc");
    }


   /* @Test
    public void add_NewField_ShouldAddField() throws Exception {

        Field field = TestUtil.getField();
        when(fieldDao.add(any(Field.class))).thenReturn(1);
        fieldDao.add(field);

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);

        verify(fieldDao,times(1)).add(varArgs.capture());
        Assert.assertEquals(field, varArgs.getValue());

    }

    @Test(expected = FarmNotFoundException.class)
    public void add_NewFarm_FarmNotFound_ShouldThrowFarmNotFoundException() throws Exception {


        when(fieldDao.findById(anyInt())).thenThrow(FarmNotFoundException.class);
        fieldDao.findById(1);
    }

    @Test
    public void findById_FiledFound_ShouldReturnFoundField() throws Exception {
        when(fieldDao.findById(anyInt())).thenReturn(TestUtil.getField());

        Field field = fieldDao.findById(1);
        Assert.assertNotNull(field);
        Assert.assertEquals(TestUtil.getField(), field);

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(fieldDao,times(1)).findById(varArgs.capture());
        Assert.assertEquals(1, (int)varArgs.getValue());
    }

    @Test(expected = FieldNotFoundException.class)
    public void findById_FiledNotFound_ShouldRThrowFiledNotFoundException() throws Exception {
        when(fieldDao.findById(anyInt())).thenThrow(FieldNotFoundException.class);
        Field field = fieldDao.findById(1);
    }

    @Test
    public void findFiledFarm_ShouldContainsFarm() throws Exception{
        Field field = TestUtil.getField();
        Farm farm = TestUtil.getFarm();
        field.setFarm(farm);

        when(fieldDao.findById(anyInt())).thenReturn(field);

        Field returned = fieldDao.findById(1);
        Assert.assertNotNull(returned);
        Assert.assertEquals(returned.getFarm(), TestUtil.getFarm());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(fieldDao,times(1)).findById(varArgs.capture());
        Assert.assertEquals(1, (int)varArgs.getValue());
    }

    */

    /*@Test
    public void findByFarmId_FarmFound_ShouldReturnFieldList(){

        when(fieldDao.findByFarmId(anyInt())).thenReturn(TestUtil.getFields());

        List<Field> fields= fieldService.findByFarmId(1);
        Assert.assertEquals(3, fields.size());
        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(fieldDao,times(1)).findByFarmId(varArgs.capture());
        Assert.assertEquals(1, (int)varArgs.getValue());
    }

    @Test
    public void findByFarmId_FarmNotFound_ShouldReturnZeroFieldInList(){

        when(fieldDao.findByFarmId(anyInt())).thenReturn(new ArrayList<>());

        List<Field> fields= fieldService.findByFarmId(1);
        Assert.assertEquals(0, fields.size());
        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(fieldDao,times(1)).findByFarmId(varArgs.capture());
        Assert.assertEquals(1, (int)varArgs.getValue());
    }*/
}