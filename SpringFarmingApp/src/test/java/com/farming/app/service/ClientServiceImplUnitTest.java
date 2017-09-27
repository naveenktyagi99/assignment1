package com.farming.app.service;


import com.farming.app.dao.ClientDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.ClientNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


public class ClientServiceImplUnitTest {


    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    ClientDao clientDao;

    @Mock
    OrganisationService organisationService;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test(expected = BadRequestException.class)
    public void add_NewClient_BlankName_ShouldThrowBadRequestException() throws BadRequestException{

        Organisation organisation = getOrganisation();
        Client client = new Client(1001, "", organisation);

        when(clientDao.add(client)).thenThrow(DataIntegrityViolationException.class);
        clientService.add(client);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewClient_NullName_ShouldThrowBadRequestException() throws BadRequestException{

        Organisation organisation = getOrganisation();
        Client client = new Client(1001, null, organisation);

        when(clientDao.add(client)).thenThrow(DataIntegrityViolationException.class);
        clientService.add(client);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewClient_DuplicateClientId_ShouldThrowBadRequestException() throws BadRequestException {

        Organisation organisation = getOrganisation();
        Client client = new Client(1, "Client1", organisation);

        when(clientDao.add(client)).thenThrow(DataIntegrityViolationException.class);
        clientService.add(client);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewClient_ZeroClientId_ShouldThrowBadRequestException() throws BadRequestException {

        Organisation organisation = getOrganisation();
        Client client = new Client(0, "Client1", organisation);

        when(clientDao.add(client)).thenThrow(DataIntegrityViolationException.class);
        clientService.add(client);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewClient_NegativeClientId_ShouldThrowBadRequestException() throws BadRequestException {

        Organisation organisation = getOrganisation();
        Client client = new Client(-1, "Client1", organisation);

        when(clientDao.add(client)).thenThrow(DataIntegrityViolationException.class);
        clientService.add(client);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewClient_OrganisationNotFound_ShouldThrowBadRequestException()  throws BadRequestException {

        Organisation organisation = new Organisation(1001, "Org1", "Desc1");
        Client client = new Client(1001, "Client1", organisation);

        when(clientDao.add(client)).thenThrow(DataIntegrityViolationException.class);
        clientService.add(client);
    }

    @Test
    public void add_NewClient_ShouldAddClientAndReturnOne() throws BadRequestException {

        Client client = TestUtil.getClient();
        client.setOrganisation(TestUtil.getOrganisation());

        when(clientDao.add(any(Client.class))).thenReturn(1);
        int count = clientService.add(client);

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);

        verify(clientDao,times(1)).add(varArgs.capture());
        assertEquals(client, varArgs.getValue());
    }

    @Test(expected = ClientNotFoundException.class)
    public void deleteById_ClientNotFound_ShouldReturnClientNotFoundException() throws Exception {

        when(clientDao.deleteById(anyInt())).thenReturn(0);
        clientService.deleteById(1001);
    }

    @Test
    public void deleteById_ClientFound_ShouldDeleteClientAndReturnIt() throws Exception {

        when(clientDao.deleteById(anyInt())).thenReturn(1);

        int count = clientService.deleteById(100);
        assertEquals(1, count);

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(clientDao,times(1)).deleteById(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());
    }

    @Test(expected = ClientNotFoundException.class)
    public void findById_ClientNotFound_ShouldRThrowClientNotFoundException() throws Exception {
        when(clientDao.findById(anyInt())).thenReturn(null);
        Client client = clientService.findById(1001);
    }


    @Test
    public void findById_ClientFound_ShouldReturnFoundClient() throws Exception {

        when(clientDao.findById(anyInt())).thenReturn(TestUtil.getClient());

        Client client = clientService.findById(100);

        Assert.assertNotNull(client);
        assertEquals(client,TestUtil.getClient());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(clientDao,times(1)).findById(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());

    }

    @Test
    public void findAll_ShouldReturnClientList() {

        when(clientDao.findAll()).thenReturn(TestUtil.getClients());

        List<Client> clients = clientService.findAll();
        assertEquals(4,clients.size());

        assertEquals(1, clients.get(0).getClientId());
        assertEquals("Client1",clients.get(0).getClientName());

        assertEquals(2, clients.get(1).getClientId());
        assertEquals("Client2",clients.get(1).getClientName());
        assertEquals(3, clients.get(2).getClientId());
        assertEquals("Client3",clients.get(2).getClientName());
    }

    @Test
    public void findAll_EmptyList_ShouldReturnEmptyClientList() {
        when(clientDao.findAll()).thenReturn(new ArrayList<Client>());
        assertEquals(0, clientService.findAll().size());
    }

    @Test
    public void findClientOrganisation_ShouldContainsOrganisation() throws Exception{

        when(clientDao.findById(anyInt())).thenReturn(TestUtil.getClient());

        Client client = clientService.findById(100);
        Assert.assertNotNull(client);
        assertEquals(client,TestUtil.getClient());

        assertEquals(1, client.getOrganisation().getOrgId());
        assertEquals("Test1 Org", client.getOrganisation().getOrgName());
        assertEquals("Desc", client.getOrganisation().getDescription());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientDao,times(1)).findById(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());
    }

    @Test
    public void findFarm_ShouldReturnFarmList() throws Exception {

        List<Farm> actual = TestUtil.getFarms();
        when(clientDao.findFarm(anyInt())).thenReturn(actual);

        List<Farm> farms = clientService.findFarm(1);
        assertEquals(4, farms.size());

        assertEquals(actual.get(0).getFarmId(), farms.get(0).getFarmId());
        assertEquals(actual.get(1).getFarmId(), farms.get(1).getFarmId());
        assertEquals(actual.get(2).getFarmId(), farms.get(2).getFarmId());

        assertEquals(actual.get(0).getFarmName(), farms.get(0).getFarmName());
        assertEquals(actual.get(1).getFarmName(), farms.get(1).getFarmName());
        assertEquals(actual.get(2).getFarmName(), farms.get(2).getFarmName());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientDao,times(1)).findFarm(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());
    }

    @Test
    public void findFarm_EmptyList_ShouldReturnEmptyFarmList() throws Exception {

        when(clientDao.findFarm(anyInt())).thenReturn(new ArrayList<Farm>());

        List<Farm> farms = clientService.findFarm(1);
        assertEquals(0, farms.size());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientDao,times(1)).findFarm(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());
    }

    @Test(expected = ClientNotFoundException.class)
    public void findFarm_ClientNotFount_ShouldReturnZeroFarmInList() throws Exception {

        when(clientDao.findFarm(anyInt())).thenThrow(ClientNotFoundException.class);
        List<Farm> farms = clientService.findFarm(1);
    }

    @Test
    public void findField_EmptyList_ShouldReturnEmptyFieldList() throws Exception {
        when(clientDao.findField(anyInt())).thenReturn(new ArrayList<Field>());

        List<Field> fields = clientService.findField(1);
        assertEquals(0, fields.size());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientDao,times(1)).findField(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());
    }

    @Test
    public void findField_ShouldReturnFieldList() throws Exception {

        List<Field> actual = TestUtil.getFields();

        when(clientDao.findField(anyInt())).thenReturn(actual);

        List<Field> fields = clientService.findField(100);
        assertEquals(3, fields.size());

        assertEquals(100, fields.get(0).getFieldId());
        assertEquals(101, fields.get(1).getFieldId());
        assertEquals(102, fields.get(2).getFieldId());

        assertEquals("Test1 Field1", fields.get(0).getFieldName());
        assertEquals("Test1 Field2", fields.get(1).getFieldName());
        assertEquals("Test1 Field3", fields.get(2).getFieldName());

        assertEquals(actual.get(0).getFarm().getFarmId(),fields.get(0).getFarm().getFarmId());
        assertEquals(actual.get(1).getFarm().getFarmId(),fields.get(1).getFarm().getFarmId());
        assertEquals(actual.get(2).getFarm().getFarmId(),fields.get(2).getFarm().getFarmId());

        assertEquals(actual.get(0).getFarm().getFarmName(),fields.get(0).getFarm().getFarmName());
        assertEquals(actual.get(1).getFarm().getFarmName(),fields.get(1).getFarm().getFarmName());
        assertEquals(actual.get(2).getFarm().getFarmName(),fields.get(2).getFarm().getFarmName());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientDao,times(1)).findField(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());
    }

    @Test(expected = ClientNotFoundException.class)
    public void findField_ClientNotFound_ShouldReturnZeroFieldInList() throws Exception {
        when(clientDao.findField(anyInt())).thenThrow(ClientNotFoundException.class);
        List<Field> fields = clientService.findField(100);
    }

    private Organisation getOrganisation(){
        return  new Organisation(1, "Org1","Desc");
    }
}