package com.farming.app.service;


import com.farming.app.dao.FarmDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.ClientNotFoundException;
import com.farming.app.exception.FarmNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class FarmServiceImplUnitTest {

    @InjectMocks
    FarmServiceImpl farmService;

    @Mock
    FarmDao farmDao;

    @Mock
    ClientService clientService;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = BadRequestException.class)
    public void add_NewFarm_BlankName_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException{
        Client client = getClient();
        Farm farm = new Farm(1001, "", client);
        when(farmDao.add(any(Farm.class))).thenThrow(DataIntegrityViolationException.class);

        farmService.add(farm);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewFarm_NullName_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Client client = getClient();
        Farm farm = new Farm(1001, null, client);
        when(farmDao.add(any(Farm.class))).thenThrow(DataIntegrityViolationException.class);
        farmService.add(farm);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewFarm_DuplicateClientId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Client client = getClient();
        Farm farm = new Farm(1, "Farm1", client);
        when(farmDao.add(any(Farm.class))).thenThrow(DataIntegrityViolationException.class);
        farmService.add(farm);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewFarm_ZeroFarmId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Client client = getClient();
        Farm farm = new Farm(0, "Farm1", client);
        when(farmDao.add(any(Farm.class))).thenThrow(DataIntegrityViolationException.class);
        farmService.add(farm);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewFarm_NegativeFarmId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Client client = getClient();
        Farm farm = new Farm(-1, "Farm1", client);
        when(farmDao.add(any(Farm.class))).thenThrow(DataIntegrityViolationException.class);
        farmService.add(farm);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewFarm_ClientNotFound_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Client client = getClient();
        client.setClientId(1001);
        Farm farm = new Farm(1001, "Farm1", client);

        when(farmDao.add(any(Farm.class))).thenThrow(DataIntegrityViolationException.class);
        farmService.add(farm);
    }

    @Test
    public void add_NewFarm_ShouldAddFarm()  throws BadRequestException {

        Farm farm = TestUtil.getFarm();
        when(farmDao.add(any(Farm.class))).thenReturn(1);

        int count = farmService.add(farm);
        assertEquals(1, count);
        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);

        verify(farmDao,times(1)).add(varArgs.capture());
        assertEquals(farm, varArgs.getValue());
    }

    @Test(expected = ClientNotFoundException.class)
    public void add_NewFarm_ClientNotFound_ShouldThrowClientNotFound()
            throws BadRequestException {

        Farm farm = TestUtil.getFarm();
        when(farmDao.add(any(Farm.class))).thenThrow(ClientNotFoundException.class);

        farmService.add(farm);
    }

    @Test(expected = FarmNotFoundException.class)
    public void findById_FarmtNotFound_ShouldRThrowFarmNotFoundException()
            throws FarmNotFoundException {
        when(farmDao.findById(anyInt())).thenThrow(EmptyResultDataAccessException.class);
        Farm farm = farmService.findById(1001);
    }

    @Test
    public void findById_FarmFound_ShouldReturnFoundFarm() throws FarmNotFoundException {

        when(farmDao.findById(anyInt())).thenReturn(TestUtil.getFarm());

        Farm farm = farmDao.findById(1);
        Assert.assertNotNull(farm);
        assertEquals(TestUtil.getFarm(), farm);

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(farmDao,times(1)).findById(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());
    }

    @Test(expected = FarmNotFoundException.class)
    public void deleteById_FarmNotFound_ShouldReturnFarmNotFoundException()
            throws FarmNotFoundException {
        when(farmDao.deleteById(anyInt())).thenReturn(0);
        farmService.deleteById(100);
    }

    @Test
    public void deleteById_FarmFound_ShouldDeleteFarm()
            throws FarmNotFoundException {

        when(farmDao.deleteById(anyInt())).thenReturn(1);
        int count = farmService.deleteById(100);

        assertEquals(1,count);

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmDao,times(1)).deleteById(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());
    }

    @Test
    public void findAll_EmptyList_ShouldReturnEmptyFarmList() {
        when(farmDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, farmService.findAll().size());
    }

    @Test
    public void findAll_ShouldReturnFarmList() {
        List<Farm> actual = TestUtil.getFarms();
        when(farmDao.findAll()).thenReturn(actual);

        List<Farm> farms = farmService.findAll();
        assertEquals(4, farms.size());

        assertEquals(actual.get(0).getFarmId(), farms.get(0).getFarmId());
        assertEquals(actual.get(0).getFarmName(), farms.get(0).getFarmName());
        assertEquals(actual.get(0).getClient(), farms.get(0).getClient());

        assertEquals(actual.get(1).getFarmId(), farms.get(1).getFarmId());
        assertEquals(actual.get(1).getFarmName(), farms.get(1).getFarmName());
        assertEquals(actual.get(1).getClient(), farms.get(1).getClient());

        assertEquals(actual.get(2).getFarmId(), farms.get(2).getFarmId());
        assertEquals(actual.get(2).getFarmName(), farms.get(2).getFarmName());
        assertEquals(actual.get(2).getClient(), farms.get(2).getClient());

        assertEquals(actual.get(3).getFarmId(), farms.get(3).getFarmId());
        assertEquals(actual.get(3).getFarmName(), farms.get(3).getFarmName());
        assertEquals(actual.get(3).getClient(), farms.get(3).getClient());
    }

    @Test
    public void findField_FarmNotFound_ShouldReturnEmptyList(){

        when(farmDao.findField(anyInt())).thenReturn(new ArrayList<>());
        List<Field> fields = farmService.findField(10001);
        assertEquals(0, fields.size());
    }

    @Test
    public void findField_FarmFound_ShouldReturnFieldList(){

        List<Field> actual = TestUtil.getFields();
        when(farmService.findField(anyInt())).thenReturn(actual);

        List<Field> fields = farmService.findField(1);

        assertNotNull(fields);
        assertEquals(3, fields.size());

        assertEquals(actual.get(0).getFieldId(), fields.get(0).getFieldId());
        assertEquals(actual.get(0).getFieldName(), fields.get(0).getFieldName());
        assertEquals(actual.get(0).getFarm().getFarmId(), fields.get(0).getFarm().getFarmId());

        assertEquals(actual.get(1).getFieldId(), fields.get(1).getFieldId());
        assertEquals(actual.get(1).getFieldName(), fields.get(1).getFieldName());
        assertEquals(actual.get(1).getFarm().getFarmId(), fields.get(1).getFarm().getFarmId());

        assertEquals(actual.get(2).getFieldId(), fields.get(2).getFieldId());
        assertEquals(actual.get(2).getFieldName(), fields.get(2).getFieldName());
        assertEquals(actual.get(2).getFarm().getFarmId(), fields.get(2).getFarm().getFarmId());
    }


    private Organisation getOrganisation(){
        return  new Organisation(1, "Org1","Desc");
    }
    private Client getClient(){
        return new Client(1,"Test Client", getOrganisation());
    }
}