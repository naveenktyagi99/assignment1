package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FarmNotFoundException;
import com.farming.app.model.Farm;
import com.farming.app.service.FarmService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FarmControllerUnitTest {


    private MockMvc mockMvc;

    @Mock
    FarmService farmService;

    @InjectMocks
    FarmController farmController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(farmController).build();
    }

    @Test
    public void add_NewFarm_BlankName_ShouldReturnBadRequestStatusCode() throws  Exception{

        String farmJson = "{\n" + "\"farmId\": 101,\n" + "\"farmName\": \"\",\n" +
                "\"client\":{\n" + "\t\"clientId\":1,\n" + "\t\"clientName\": \"client1\"\n" +
                "}\n" + " }\n";

        when( farmService.add(any(Farm.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFarmId());
        assertEquals("", varArgs.getValue().getFarmName());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void add_NewFarm_NullName_ShouldReturnBadRequestStatusCode() throws Exception{

        String farmJson = "{\n" + "\"farmId\": 101,\n" +
                "\"farmName\": null,\n" + "\"client\":{\n" + "\t\"clientId\":1,\n" +
                "\t\"clientName\": \"client1\"\n" + "}\n" + " }";

        when( farmService.add(any(Farm.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFarmId());
        assertNull(varArgs.getValue().getFarmName());
        assertEquals(1, varArgs.getValue().getClient().getClientId());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }


    @Test
    public void add_NewFarm_DuplicateFarmId_ShouldReturnBadRequestStatusCode() throws Exception{

        String farmJson = "{\n" + "\"farmId\": 1,\n" +
                "\"farmName\": \"Farm1\",\n" + "\"client\":{\n" +
                "\t\"clientId\":1,\n" + "\t\"clientName\": \"client1\"\n" +
                "}\n" + " }\n";

        when( farmService.add(any(Farm.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(1, varArgs.getValue().getFarmId());
        assertEquals("Farm1", varArgs.getValue().getFarmName());
        assertEquals(1, varArgs.getValue().getClient().getClientId());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }


    @Test
    public void add_NewFarm_ZeroFarmId_ShouldReturnBadRequestStatusCode() throws Exception {

        String farmJson = "{\n" + "\"farmId\": 0,\n" +
                "\"farmName\": \"Farm1\",\n" + "\"client\":{\n" +
                "\t\"clientId\":1,\n" + "\t\"clientName\": \"client1\"\n" +
                "}\n" + " }\n";

        when( farmService.add(any(Farm.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(0, varArgs.getValue().getFarmId());
        assertEquals("Farm1", varArgs.getValue().getFarmName());
        assertEquals(1, varArgs.getValue().getClient().getClientId());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void add_NewFarm_NegativeFarmId_ShouldReturnbadRequestStatusCode() throws Exception{

        String farmJson = "{\n" + "\"farmId\": -1,\n" +
                "\"farmName\": \"Farm1\",\n" + "\"client\":{\n" +
                "\t\"clientId\":1,\n" + "\t\"clientName\": \"client1\"\n" +
                "}\n" + " }\n";

        when( farmService.add(any(Farm.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(-1, varArgs.getValue().getFarmId());
        assertEquals("Farm1", varArgs.getValue().getFarmName());
        assertEquals(1, varArgs.getValue().getClient().getClientId());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void add_NewFarm_ClientNotFound_ShouldReturnBadRequestStatusCode() throws Exception{

        String farmJson = "{\n" + "\"farmId\": 101,\n" +
                "\"farmName\": \"Farm1\",\n" + "\"client\":{\n" +
                "\t\"clientId\":1001,\n" + "\t\"clientName\": \"client1\"\n" +
                "}\n" + " }\n";

        when( farmService.add(any(Farm.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFarmId());
        assertEquals("Farm1", varArgs.getValue().getFarmName());
        assertEquals(1001, varArgs.getValue().getClient().getClientId());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void add_NewFarm_ShouldReturnOne()  throws Exception{

        String farmJson = "{\n" + "\"farmId\": 101,\n" +
                "\"farmName\": \"Farm1\",\n" + "\"client\":{\n" +
                "\t\"clientId\":1,\n" + "\t\"clientName\": \"client1\"\n" +
                "}\n" + " }\n";

        when( farmService.add(any(Farm.class))).thenReturn(1);

        mockMvc.perform(post("/farming/orgs/clients/farms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Farm> varArgs = ArgumentCaptor.forClass(Farm.class);
        verify(farmService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFarmId());
        assertEquals("Farm1", varArgs.getValue().getFarmName());
        assertEquals(1, varArgs.getValue().getClient().getClientId());

        verify(farmService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void deleteById_FarmFound_ShouldDeleteFarmAndReturnStatusCoeOk() throws Exception {

        when( farmService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/farming/orgs/clients/farms/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmService,times(1)).deleteById(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(farmService,times(1)).deleteById(1);
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void deleteById_FarmNotFound_ShouldReturnNotFoundStatusCode404() throws Exception {

        when( farmService.deleteById(anyInt())).thenThrow(FarmNotFoundException.class);

        mockMvc.perform(delete("/farming/orgs/clients/farms/{id}", 101)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmService,times(1)).deleteById(varArgs.capture());

        assertEquals(101, (int)varArgs.getValue());

        verify(farmService,times(1)).deleteById(101);
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void findById_FarmNotFound_ShouldReturnStatusCode404() throws Exception {

        when(farmService.findById(anyInt())).thenThrow(FarmNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/farms/{id}", 101)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmService,times(1)).findById(varArgs.capture());

        assertEquals(101, (int)varArgs.getValue());

        verify(farmService, times(1)).findById(101);
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void findById_FarmClientFound_ShouldContainsClient() throws Exception {

        when(farmService.findById(anyInt())).thenReturn(TestUtil.getFarm());

        mockMvc.perform(get("/farming/orgs/clients/farms/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

                .andExpect(jsonPath("$farmId", is(100)))
                .andExpect(jsonPath("$farmName", is("Test1 Farm")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmService,times(1)).findById(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(farmService, times(1)).findById(1);
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void findAll_FarmFound_ShouldReturnAllFarms() throws Exception {

        when(farmService.findAll()).thenReturn(TestUtil.getFarms());

        mockMvc.perform(get("/farming/orgs/clients/farms").accept("application/json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].farmId", is(100)))
                .andExpect(jsonPath("$[0].farmName", is("Test1 Farm1")))

                .andExpect(jsonPath("$[1].farmId", is(101)))
                .andExpect(jsonPath("$[1].farmName", is("Test1 Farm2")))

                .andExpect(jsonPath("$[2].farmId", is(102)))
                .andExpect(jsonPath("$[2].farmName", is("Test1 Farm3")));

        verify(farmService, times(1)).findAll();
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void findFields_FarmNotFound_ShouldReturnNotFoundStatusCode() throws Exception {

        when(farmService.findField(anyInt())).thenThrow(FarmNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/farms/{id}/fields", 111)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmService,times(1)).findField(varArgs.capture());

        assertEquals(111, (int)varArgs.getValue());

        verify(farmService, times(1)).findField(111);
        verifyNoMoreInteractions(farmService);
    }

    @Test
    public void findFields_FarmFound_ShouldReturnFieldList() throws Exception {

        when(farmService.findField(anyInt())).thenReturn(TestUtil.getFields());

        mockMvc.perform(get("/farming/orgs/clients/farms/{id}/fields", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(farmService,times(1)).findField(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(farmService, times(1)).findField(1);
        verifyNoMoreInteractions(farmService);
    }
}