package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.OrganisationNotFoundException;
import com.farming.app.model.Organisation;
import com.farming.app.service.OrganisationService;
import com.farming.app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class OrganisationControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    OrganisationService organisationService;

    @InjectMocks
    OrganisationController organisationController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(organisationController)
                .build();
    }

    @Test
    public void add_NewOrganisation_BlankName_ShouldReturnBadRequestStatusCode()
            throws Exception{

        Organisation organisation = new Organisation(101, "","Desc");

        when(organisationService.add(organisation)).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getOrgId());
        assertEquals("", varArgs.getValue().getOrgName());
        assertEquals("Desc", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void add_NewOrganisation_NullName_ShouldReturnBadRequestStatusCode()
            throws Exception {
        Organisation organisation = new Organisation(1001, null,"Desc");

        when(organisationService.add(organisation)).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(1001, varArgs.getValue().getOrgId());
        assertNull(varArgs.getValue().getOrgName());
        assertEquals("Desc", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void add_NewOrganisation_ZeroOrganisationId_ShouldReturnBadRequestStatusCode()
            throws Exception{
        Organisation organisation = new Organisation(0, "Org1","Desc");

        when(organisationService.add(organisation)).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(0, varArgs.getValue().getOrgId());
        assertEquals("Org1", varArgs.getValue().getOrgName());
        assertEquals("Desc", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void add_NewOrganisation_NegativeOrganisationId_ShouldReturnBadRequestStatusCode()
            throws Exception{
        Organisation organisation = new Organisation(-1, "Org1","Desc");

        when(organisationService.add(organisation)).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(-1, varArgs.getValue().getOrgId());
        assertEquals("Org1", varArgs.getValue().getOrgName());
        assertEquals("Desc", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void add_NewOrganisation_DuplicateOrganisationId_ShouldReturnBadRequestStatusCode()
            throws  Exception{
        Organisation organisation = new Organisation(1, "Org1","Desc");

        when(organisationService.add(organisation)).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(1, varArgs.getValue().getOrgId());
        assertEquals("Org1", varArgs.getValue().getOrgName());
        assertEquals("Desc", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }


    @Test
    public void add_NewOrganisation_NullDescription_ShouldAddOrganisation() throws  Exception {
        Organisation organisation = new Organisation(1001, "Org1",null);

        when(organisationService.add(organisation)).thenReturn(1);

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(1001, varArgs.getValue().getOrgId());
        assertEquals("Org1", varArgs.getValue().getOrgName());
        assertNull(varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void add_NewOrganisation_BlankDescription_ShouldAddOrganisation() throws Exception{
        Organisation organisation = new Organisation(1001, "Org1","");

        when(organisationService.add(organisation)).thenReturn(1);

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(1001, varArgs.getValue().getOrgId());
        assertEquals("Org1", varArgs.getValue().getOrgName());
        assertEquals("", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }


    @Test
    public void add_NewOrganisation_ShouldAddOrganisation() throws Exception {

        Organisation organisation = new Organisation(10, "Org1", "Desc");

        when(organisationService.add(organisation)).thenReturn(1);

        mockMvc.perform(post("/farming/orgs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(organisation)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Organisation> varArgs = ArgumentCaptor.forClass(Organisation.class);
        verify(organisationService,times(1)).add(varArgs.capture());

        assertEquals(10, varArgs.getValue().getOrgId());
        assertEquals("Org1", varArgs.getValue().getOrgName());
        assertEquals("Desc", varArgs.getValue().getDescription());

        verify(organisationService,times(1)).add(any(Organisation.class));
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void findAll_OrganisationFound_ShouldReturnFoundOrganisations() throws Exception {

        when(organisationService.findAll()).thenReturn(TestUtil.getOrganisations());

        mockMvc.perform(get("/farming/orgs").accept("application/json").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andDo(print())

                .andExpect(jsonPath("$[0].orgId", is(1)))
                .andExpect(jsonPath("$[0].orgName", is("Org Name1")))
                .andExpect(jsonPath("$[0].description", is("Description1")))

                .andExpect(jsonPath("$[1].orgId", is(2)))
                .andExpect(jsonPath("$[1].orgName", is("Org Name2")))
                .andExpect(jsonPath("$[1].description", is("Description2")))

                .andExpect(jsonPath("$[2].orgId", is(3)))
                .andExpect(jsonPath("$[2].orgName", is("Org Name3")))
                .andExpect(jsonPath("$[2].description", is("Description3")))

                .andExpect(jsonPath("$[3].orgId", is(4)))
                .andExpect(jsonPath("$[3].orgName", is("Org Name4")))
                .andExpect(jsonPath("$[3].description", is("Description4")))

                .andExpect(jsonPath("$[4].orgId", is(5)))
                .andExpect(jsonPath("$[4].orgName", is("Org Name5")))
                .andExpect(jsonPath("$[4].description", is("Description5")));

        verify(organisationService,times(1)).findAll();
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void findById_OrganisationFound_ShouldReturnFoundOrganisation() throws Exception {

        when(organisationService.findById(anyInt())).thenReturn(TestUtil.getOrganisation());
        mockMvc.perform(get("/farming/orgs/{id}", 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$orgId", is(100)))
                .andExpect(jsonPath("$orgName", is("Org100")))
                .andExpect(jsonPath("$description", is("Desc100")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findById(varArgs.capture());

        assertEquals(100, (int)varArgs.getValue());

        verify(organisationService,times(1)).findById(100);
        verifyNoMoreInteractions(organisationService);


    }

    @Test
    public void findById_OrganisationNotFound_ShouldReturnNotFoundStatusCode() throws Exception {

        when(organisationService.findById(anyInt())).thenThrow(OrganisationNotFoundException.class);
        mockMvc.perform(get("/farming/orgs/{id}", 11)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findById(varArgs.capture());

        assertEquals(11, (int)varArgs.getValue());

        verify(organisationService,times(1)).findById(11);
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void deleteById_OrganisationFound_ShouldDeleteOrganisationReturnStatusOk() throws Exception {

        when(organisationService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/farming/orgs/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).deleteById(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(organisationService,times(1)).deleteById(1);
        verifyNoMoreInteractions(organisationService);

    }

    @Test
    public void deleteById_OrganisationNotFound_ShouldReturnNotFound() throws Exception {

        when(organisationService.deleteById(anyInt())).thenThrow(OrganisationNotFoundException.class);

        mockMvc.perform(delete("/farming/orgs/{id}", 111)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).deleteById(varArgs.capture());

        assertEquals(111, (int)varArgs.getValue());

        verify(organisationService,times(1)).deleteById(111);
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void findClientsById_OrganisationNotFound_ShouldReturnStatusCodeNotFound() throws  Exception{

        when(organisationService.findClients(anyInt())).thenThrow(OrganisationNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/{id}/clients", 11)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findClients(varArgs.capture());

        assertEquals(11, (int)varArgs.getValue());

        verify(organisationService,times(1)).findClients(11);
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void findClientsById_OrganisationFound_ShouldReturnClientList() throws  Exception{

        when(organisationService.findClients(anyInt())).thenReturn(TestUtil.getClients());
        mockMvc.perform(get("/farming/orgs/{orgId}/clients", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andDo(print())

                .andExpect(jsonPath("$[0].clientId", is(1)))
                .andExpect(jsonPath("$[0].clientName", is("Client1")))

                .andExpect(jsonPath("$[1].clientId", is(2)))
                .andExpect(jsonPath("$[1].clientName", is("Client2")))

                .andExpect(jsonPath("$[2].clientId", is(3)))
                .andExpect(jsonPath("$[2].clientName", is("Client3")))

                .andExpect(jsonPath("$[3].clientId", is(4)))
                .andExpect(jsonPath("$[3].clientName", is("Client4")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findClients(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(organisationService,times(1)).findClients(1);
        verifyNoMoreInteractions(organisationService);

    }

    @Test
    public void findFields_OrganisationNotFound_ShouldReturnEmptyFieldsList() throws  Exception{

        when(organisationService.findField(anyInt())).thenThrow(OrganisationNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/{orgId}/clients/farms/fields", 111)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findField(varArgs.capture());

        assertEquals(111, (int)varArgs.getValue());

        verify(organisationService,times(1)).findField(111);
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void findFields_OrganisationFound_ShouldReturnFieldList() throws  Exception{

        when(organisationService.findField(anyInt())).thenReturn(TestUtil.getFields());

        mockMvc.perform(get("/farming/orgs/{orgId}/clients/farms/fields", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].fieldId", is(100)))
                .andExpect(jsonPath("$[0].fieldName", is("Test1 Field1")))

                .andExpect(jsonPath("$[1].fieldId", is(101)))
                .andExpect(jsonPath("$[1].fieldName", is("Test1 Field2")))

                .andExpect(jsonPath("$[2].fieldId", is(102)))
                .andExpect(jsonPath("$[2].fieldName", is("Test1 Field3")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findField(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(organisationService,times(1)).findField(1);
        verifyNoMoreInteractions(organisationService);
    }



    @Test
    public void findFarm_OrganisationNotFound_ShouldReturnEmptyFarmList() throws  Exception{


        when(organisationService.findFarm(anyInt())).thenThrow(OrganisationNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/{orgId}/clients/farms", 111)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findFarm(varArgs.capture());

        assertEquals(111, (int)varArgs.getValue());

        verify(organisationService,times(1)).findFarm(111);
        verifyNoMoreInteractions(organisationService);
    }

    @Test
    public void findFarm_OrganisationFound_ShouldReturnFarmList() throws  Exception{

        when(organisationService.findFarm(anyInt())).thenReturn(TestUtil.getFarms());

        mockMvc.perform(get("/farming/orgs/{orgId}/clients/farms", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].farmId", is(100)))
                .andExpect(jsonPath("$[0].farmName", is("Test1 Farm1")))

                .andExpect(jsonPath("$[1].farmId", is(101)))
                .andExpect(jsonPath("$[1].farmName", is("Test1 Farm2")))

                .andExpect(jsonPath("$[2].farmId", is(102)))
                .andExpect(jsonPath("$[2].farmName", is("Test1 Farm3")))

                .andExpect(jsonPath("$[3].farmId", is(103)))
                .andExpect(jsonPath("$[3].farmName", is("Test1 Farm4")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(organisationService,times(1)).findFarm(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(organisationService,times(1)).findFarm(1);
        verifyNoMoreInteractions(organisationService);
    }
}
