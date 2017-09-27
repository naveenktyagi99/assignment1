package com.farming.app.service;


import com.farming.app.dao.EntityDaoImplTest;
import com.farming.app.dao.OrganisationDaoImpl;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.OrganisationNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


public class OrganisationServiceImplUnitTest extends EntityDaoImplTest {

    @InjectMocks
    private OrganisationServiceImpl organisationService;

    @Mock
    private OrganisationDaoImpl organisationDao;

    @Before
    public  void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewOrganisation_BlankName_ShouldThrowDataIntegrityViolationException()
        throws BadRequestException{

        Organisation organisation = new Organisation(1001, "","Desc");
        when(organisationDao.add(organisation)).thenThrow(DataIntegrityViolationException.class);
        organisationService.add(organisation);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewOrganisation_NullName_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException {
        Organisation organisation = new Organisation(1001, null,"Desc");
        when(organisationDao.add(organisation)).thenThrow(DataIntegrityViolationException.class);
        organisationService.add(organisation);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewOrganisation_ZeroOrganisationId_ShouldThrowDataIntegrityViolationException()
            throws BadRequestException{
        Organisation organisation = new Organisation(0, "Org1","Desc");
        when(organisationDao.add(organisation)).thenThrow(DataIntegrityViolationException.class);
        organisationService.add(organisation);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewOrganisation_NegativeOrganisationId_ShouldThrowDataIntegrityViolationException()
                    throws BadRequestException{
        Organisation organisation = new Organisation(-1, "Org1","Desc");
        when(organisationDao.add(organisation)).thenThrow(DataIntegrityViolationException.class);
        organisationService.add(organisation);
    }

    @Test(expected = BadRequestException.class)
    public void add_NewOrganisation_DuplicateOrganisationId_ShouldThrowDataIntegrityViolationException()
                throws  BadRequestException{
        Organisation organisation = new Organisation(1, "Org1","Desc");

        when(organisationDao.add(organisation)).thenThrow(DuplicateKeyException.class);
        organisationService.add(organisation);
    }

    @Test
    public void add_NewOrganisation_NullDescription_ShouldAddOrganisation()
        throws BadRequestException{

        Organisation organisation = new Organisation(1001, "Org1",null);

        when(organisationDao.add(organisation)).thenReturn(1);
        organisationService.add(organisation);
    }

    @Test
    public void add_NewOrganisation_BlankDescription_ShouldAddOrganisation() throws BadRequestException{

        Organisation organisation = new Organisation(1001, "Org1","");

        when(organisationDao.add(organisation)).thenReturn(1);
        organisationService.add(organisation);
    }

    @Test
    public void add_NewOrganisation_ShouldAddOrganisation() throws BadRequestException{

        Organisation organisation = new Organisation(1001, "Org1","Desc1");

        when(organisationDao.add(organisation)).thenReturn(1);
        organisationService.add(organisation);
    }

    @Test(expected = OrganisationNotFoundException.class)
    public void deleteById_OrganisationNotFound_ShouldThrow_OrganisationNotFoundException()
            throws OrganisationNotFoundException {

        when(organisationDao.deleteById(anyInt())).thenReturn(0);
        int count = organisationService.deleteById(1001);

    }

    @Test
    public void deleteById_OrganisationFound_ShouldReturnCount()
            throws OrganisationNotFoundException {

        when(organisationDao.deleteById(anyInt())).thenReturn(1);

        int count = organisationService.deleteById(1);
        assertEquals(1, count);
    }


    @Test
    public void findAll_ReturnEmptyList_ShouldReturnEmptyOrganisationList() {

        when(organisationDao.findAll()).thenReturn(new ArrayList<Organisation>());
        List<Organisation> organisations = organisationService.findAll();
        assertEquals(0, organisations.size());
    }

    @Test
    public void findAll_ShouldReturnOrganisationList() {

        List<Organisation> actual = TestUtil.getOrganisations();
        when(organisationDao.findAll()).thenReturn(actual);

        List<Organisation> organisations = organisationService.findAll();
        assertEquals(5, organisations.size());

        assertEquals(actual.get(0).getOrgId(),organisations.get(0).getOrgId() );
        assertEquals(actual.get(1).getOrgId(),organisations.get(1).getOrgId() );
        assertEquals(actual.get(2).getOrgId(),organisations.get(2).getOrgId() );
        assertEquals(actual.get(3).getOrgId(),organisations.get(3).getOrgId() );
        assertEquals(actual.get(4).getOrgId(),organisations.get(4).getOrgId() );

        assertEquals(actual.get(0).getOrgName(),organisations.get(0).getOrgName() );
        assertEquals(actual.get(1).getOrgName(),organisations.get(1).getOrgName() );
        assertEquals(actual.get(2).getOrgName(),organisations.get(2).getOrgName() );
        assertEquals(actual.get(3).getOrgName(),organisations.get(3).getOrgName() );
        assertEquals(actual.get(4).getOrgName(),organisations.get(4).getOrgName() );
    }



    @Test(expected = OrganisationNotFoundException.class)
    public void findById_OrganisationNotFound_ShouldRThrowOrganisationNotFoundException() throws Exception {

        when(organisationDao.findById(anyInt())).thenThrow(OrganisationNotFoundException.class);
        Organisation organisation = organisationService.findById(101);
    }


    @Test
    public void findById_OrganisationFound_ShouldReturnFoundOrganisation() throws Exception {

        Organisation actual = TestUtil.getOrganisation();
        when(organisationDao.findById(anyInt())).thenReturn(actual);

        Organisation organisation = organisationService.findById(1);
        assertNotNull(organisation);
        assertEquals(actual, organisation);

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(organisationDao,times(1)).findById(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());

    }

    @Test(expected = OrganisationNotFoundException.class)
    public void findClients_OrganisationNotFound_ShouldThrowOrganisationNotFound() throws Exception{
        when(organisationDao.findClient(anyInt())).thenThrow(OrganisationNotFoundException.class);
        List<Client> clinets = organisationService.findClients(101);
    }

    @Test
    public void findClients_OrganisationFound_ShouldReturnClientsList() throws Exception{

        Organisation actual = TestUtil.getOrgWithClient();

        when(organisationDao.findClient(anyInt())).thenReturn(actual.getClients());

        List<Client> clients = organisationService.findClients(1);

       assertEquals(actual.getClients().size(),clients.size());

       assertEquals(actual.getClients().get(0).getClientId(),clients.get(0).getClientId());
       assertEquals(actual.getClients().get(1).getClientId(),clients.get(1).getClientId());
       assertEquals(actual.getClients().get(2).getClientId(),clients.get(2).getClientId());
       assertEquals(actual.getClients().get(3).getClientId(),clients.get(3).getClientId());

        assertEquals(actual.getClients().get(0).getClientName(),clients.get(0).getClientName());
        assertEquals(actual.getClients().get(1).getClientName(),clients.get(1).getClientName());
        assertEquals(actual.getClients().get(2).getClientName(),clients.get(2).getClientName());
        assertEquals(actual.getClients().get(3).getClientName(),clients.get(3).getClientName());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(organisationDao,times(1)).findClient(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());

    }


    @Test
    public void findFarm_shouldReturnFarmList()throws Exception{

        List<Farm> actual = TestUtil.getFarms();
        when(organisationDao.findFarm(anyInt())).thenReturn(actual);

        List<Farm> farms = organisationService.findFarm(1);
        assertEquals(actual.size(), farms.size());

        assertEquals(actual.get(0).getFarmId(), farms.get(0).getFarmId());
        assertEquals(actual.get(1).getFarmId(), farms.get(1).getFarmId());
        assertEquals(actual.get(2).getFarmId(), farms.get(2).getFarmId());
        assertEquals(actual.get(3).getFarmId(), farms.get(3).getFarmId());

        assertEquals(actual.get(0).getFarmName(), farms.get(0).getFarmName());
        assertEquals(actual.get(1).getFarmName(), farms.get(1).getFarmName());
        assertEquals(actual.get(2).getFarmName(), farms.get(2).getFarmName());
        assertEquals(actual.get(3).getFarmName(), farms.get(3).getFarmName());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(organisationDao,times(1)).findFarm(varArgs.capture());
        assertEquals(1, (int)varArgs.getValue());

    }


    @Test
    public void findFarm_EmptyList_shouldReturnEmptyFarmList()throws Exception{

        when(organisationDao.findFarm(anyInt())).thenReturn(new ArrayList<Farm>());

        List<Farm> farms = organisationService.findFarm(100);
        assertEquals(0, farms.size());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(organisationDao,times(1)).findFarm(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());

    }


    @Test
    public void findField_OrganisationNotFound_shouldReturnZeroField()throws Exception{
        when(organisationDao.findField(anyInt())).thenReturn(new ArrayList<Field>());

        List<Field> fields = organisationService.findField(100);
        assertEquals(0, fields.size());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(organisationDao,times(1)).findField(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());
    }


    @Test
    public void findField_shouldReturnFieldList()throws Exception{

        List<Field> actual = TestUtil.getFields();

        when(organisationDao.findField(anyInt())).thenReturn(actual);

        List<Field> fields = organisationService.findField(100);
        assertEquals(actual.size(), fields.size());

        assertEquals(actual.get(0).getFieldId(), fields.get(0).getFieldId());
        assertEquals(actual.get(1).getFieldId(), fields.get(1).getFieldId());
        assertEquals(actual.get(2).getFieldId(), fields.get(2).getFieldId());
        assertEquals(actual.get(0).getFieldName(), fields.get(0).getFieldName());
        assertEquals(actual.get(1).getFieldName(), fields.get(1).getFieldName());
        assertEquals(actual.get(2).getFieldName(), fields.get(2).getFieldName());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);

        verify(organisationDao,times(1)).findField(varArgs.capture());
        assertEquals(100, (int)varArgs.getValue());
    }
}