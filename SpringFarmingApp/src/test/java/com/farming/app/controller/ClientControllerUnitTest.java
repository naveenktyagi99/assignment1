package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.ClientNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.service.ClientService;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerUnitTest {


    private MockMvc mockMvc;

    @Mock
    ClientService clientService;

    @InjectMocks
     ClientController clientController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void add_NewClient_BlankName_ShouldReturnBadRequestStatusCode()
            throws Exception{

        String clientJson = "{\n" + " \t \"clientId\": 101,\n" + "\"clientName\": \"\",\n" +
                "\"organisation\":{\n" + "\t\"orgId\":1,\n" + "\t\"orgName\": \"org1\"\n" +
                "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getClientId());
        assertEquals("", varArgs.getValue().getClientName());
        assertEquals(1, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);
    }

    @Test
    public void add_NewClient_NullName_ShouldReturnBadRequestStatusCode() throws  Exception{

        String clientJson = "{\n" + "\"clientId\": 101,\n" + "\"clientName\": null,\n" +
                " \"organisation\":{\n" + "\t\"orgId\":1,\n" + "\t\"orgName\": \"org1\"\n" +
                "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getClientId());
        assertNull(varArgs.getValue().getClientName());
        assertEquals(1, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);
    }

    @Test
    public void add_NewClient_DuplicateClientId_ShouldReturnBadRequestStatusCode() throws Exception{

        String clientJson = "{\n" +
                "\"clientId\": 101,\n" + "\"clientName\": \"Client1\",\n" +
                " \"organisation\":{\n" + "\t\"orgId\":1,\n" +
                "\t\"orgName\": \"org1\"\n" + "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getClientId());
        assertEquals("Client1", varArgs.getValue().getClientName());
        assertEquals(1, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);

    }

    @Test
    public void add_NewClient_ZeroClientId_ShouldReturnBadRequestStatusCode() throws Exception{

        String clientJson = "{\n" +
                "\"clientId\": 0,\n" + "\"clientName\": \"Client1\",\n" +
                " \"organisation\":{\n" + "\t\"orgId\":1,\n" +
                "\t\"orgName\": \"org1\"\n" + "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(0, varArgs.getValue().getClientId());
        assertEquals("Client1", varArgs.getValue().getClientName());
        assertEquals(1, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);

    }


    @Test
    public void add_NewClient_NegativeClientId_ShouldReturnBadRequestStatusCode() throws Exception  {

        String clientJson = "{\n" +
                "\"clientId\": -1,\n" + "\"clientName\": \"Client1\",\n" +
                " \"organisation\":{\n" + "\t\"orgId\":1,\n" +
                "\t\"orgName\": \"org1\"\n" + "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(-1, varArgs.getValue().getClientId());
        assertEquals("Client1", varArgs.getValue().getClientName());
        assertEquals(1, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);

    }


    @Test
    public void add_NewClient_OrganisationNotFound_ShouldReturnBadRequestStatusCode() throws Exception {

        String clientJson = "{\n" +
                "\"clientId\": 100,\n" + "\"clientName\": \"Client1\",\n" +
                " \"organisation\":{\n" + "\t\"orgId\":101,\n" +
                "\t\"orgName\": \"org1\"\n" + "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getClientId());
        assertEquals("Client1", varArgs.getValue().getClientName());
        assertEquals(101, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);
    }

    @Test
    public void add_NewClient_ShouldReturnOne() throws Exception {
        String clientJson = "{\n" +
                "\"clientId\": 100,\n" + "\"clientName\": \"Client1\",\n" +
                " \"organisation\":{\n" + "\t\"orgId\":1,\n" +
                "\t\"orgName\": \"org1\"\n" + "}\n" + " }";

        when(clientService.add(any(Client.class))).thenReturn(1);

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Client> varArgs = ArgumentCaptor.forClass(Client.class);
        verify(clientService,times(1)).add(varArgs.capture());

        assertEquals(100, varArgs.getValue().getClientId());
        assertEquals("Client1", varArgs.getValue().getClientName());
        assertEquals(1, varArgs.getValue().getOrganisation().getOrgId());

        verify(clientService,times(1)).add(any(Client.class));
        verifyNoMoreInteractions(clientService);
    }

    @Test
    public void delete_ClientNotFound_ShouldReturnNotFoundStatusCode() throws Exception{

        when(clientService.deleteById(anyInt())).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(delete("/farming/orgs/clients/{id}", 111)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).deleteById(varArgs.capture());

        assertEquals(111, (int)varArgs.getValue());

        verify(clientService,times(1)).deleteById(111);
        verifyNoMoreInteractions(clientService);
    }


    @Test
    public void delete_ClientFound_ShouldReturnStatusOk() throws Exception {
        when(clientService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/farming/orgs/clients/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).deleteById(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(clientService,times(1)).deleteById(1);
        verifyNoMoreInteractions(clientService);

    }

    @Test
    public void findById_ClientNotFound_ShouldReturnStatusCodeNotFound() throws Exception {

        when(clientService.findById(anyInt())).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/{id}", 11)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).findById(varArgs.capture());

        assertEquals(11, (int)varArgs.getValue());

        verify(clientService,times(1)).findById(11);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    public void findById_ClientFound_ShouldReturnClient() throws Exception{

        when(clientService.findById(anyInt())).thenReturn(TestUtil.getClient());

        mockMvc.perform(get("/farming/orgs/clients/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$clientId", is(100)))
                .andExpect(jsonPath("$clientName", is("Test1 Client")));


        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).findById(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(clientService,times(1)).findById(1);
        verifyNoMoreInteractions(clientService);
    }


    @Test
    public void findFarm_ClientNotFound_ShouldReturnStatusCodeNotFound() throws Exception {

        when(clientService.findFarm(anyInt())).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/{id}/farms", 1001)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).findFarm(varArgs.capture());

        assertEquals(1001, (int)varArgs.getValue());

        verify(clientService,times(1)).findFarm(1001);
        verifyNoMoreInteractions(clientService);

    }

    @Test
    public void findFarm_ClientFoundWithZeroFarm_ShouldReturnZero() throws Exception {

        when(clientService.findFarm(anyInt())).thenReturn(TestUtil.getFarms());

        mockMvc.perform(get("/farming/orgs/clients/{id}/farms", 1001)

                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
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
        verify(clientService,times(1)).findFarm(varArgs.capture());

        assertEquals(1001, (int)varArgs.getValue());

        verify(clientService,times(1)).findFarm(1001);
        verifyNoMoreInteractions(clientService);
    }


    @Test
    public void findField_ClientNotFound_ShouldReturnNotNotFoundStatusCode() throws Exception {

        when(clientService.findField(anyInt())).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/{id}/farms/fields", 1001)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).findField(varArgs.capture());

        assertEquals(1001, (int)varArgs.getValue());

        verify(clientService,times(1)).findField(1001);
        verifyNoMoreInteractions(clientService);
    }

    @Test
    public void findField_ClientFound_ShouldReturnFieldList() throws Exception {

        when(clientService.findField(anyInt())).thenReturn(TestUtil.getFields());

        mockMvc.perform(get("/farming/orgs/clients/{id}/farms/fields", 1001)

                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].fieldId", is(100)))
                .andExpect(jsonPath("$[0].fieldName", is("Test1 Field1")))

                .andExpect(jsonPath("$[1].fieldId", is(101)))
                .andExpect(jsonPath("$[1].fieldName", is("Test1 Field2")))

                .andExpect(jsonPath("$[2].fieldId", is(102)))
                .andExpect(jsonPath("$[2].fieldName", is("Test1 Field3")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(clientService,times(1)).findField(varArgs.capture());

        assertEquals(1001, (int)varArgs.getValue());

        verify(clientService,times(1)).findField(1001);
        verifyNoMoreInteractions(clientService);
    }
}