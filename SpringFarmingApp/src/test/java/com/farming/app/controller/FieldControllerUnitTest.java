package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FieldNotFoundException;
import com.farming.app.model.Field;
import com.farming.app.service.FieldService;
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

public class FieldControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    FieldService fieldService;

    @InjectMocks
    FieldController fieldController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fieldController).build();
    }

    @Test
    public void add_NewField_BlankName_ShouldReturnBadRequestStatusCode() throws  Exception{

        String fieldJson = "{\n" +
                "\"fieldId\": 101,\n" + "\"fieldName\": \"\",\n" +
                "\"farm\":{\n" + "\t\"farmId\":1,\n" +
                "\t\"farmName\": \"farm1\"\n" + "}\n" + "}";

        when( fieldService.add(any(Field.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fieldJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);
        verify(fieldService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFieldId());
        assertEquals("", varArgs.getValue().getFieldName());

        verify(fieldService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void add_NewField_NullName_ShouldReturnBadRequestStatusCode() throws Exception{

        String fieldJson = "{\n" +
                "\"fieldId\": 101,\n" + "\"fieldName\": null,\n" +
                "\"farm\":{\n" + "\t\"farmId\":1,\n" +
                "\t\"farmName\": \"farm1\"\n" + "}\n" + " }";

        when( fieldService.add(any(Field.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fieldJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);
        verify(fieldService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFieldId());
        assertNull( varArgs.getValue().getFieldName());

        verify(fieldService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void add_NewField_DuplicateFieldId_ShouldReturnBadRequestStatusCode() throws Exception{

        String farmJson = "{\n" +
                "\"fieldId\": 1,\n" + "\"fieldName\": \"Field1\",\n" +
                " \"farm\":{\n" + "\t\"farmId\":1,\n" +
                "\t\"farmName\": \"farm1\"\n" + "}\n" + " }";

        when( fieldService.add(any(Field.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);
        verify(fieldService,times(1)).add(varArgs.capture());

        assertEquals(1, varArgs.getValue().getFieldId());
        assertEquals("Field1", varArgs.getValue().getFieldName());

        verify(fieldService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void add_NewField_ZeroFieldId_ShouldReturnBadRequestStatusCode() throws Exception {

        String farmJson = "{\n" +
                "\"fieldId\": 0,\n" + "\"fieldName\": \"Field1\",\n" +
                " \"farm\":{\n" + "\t\"farmId\":1,\n" +
                "\t\"farmName\": \"farm1\"\n" + "}\n" + " }";

        when( fieldService.add(any(Field.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);
        verify(fieldService,times(1)).add(varArgs.capture());

        assertEquals(0, varArgs.getValue().getFieldId());
        assertEquals("Field1", varArgs.getValue().getFieldName());

        verify(fieldService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void add_NewField_NegativeFieldId_ShouldReturnBadRequestStatusCode() throws Exception{

        String farmJson = "{\n" +
                "\"fieldId\": -1,\n" + "\"fieldName\": \"Field1\",\n" +
                " \"farm\":{\n" + "\t\"farmId\":1,\n" +
                "\t\"farmName\": \"farm1\"\n" + "}\n" + " }";

        when( fieldService.add(any(Field.class))).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/farming/orgs/clients/farms/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);
        verify(fieldService,times(1)).add(varArgs.capture());

        assertEquals(-1, varArgs.getValue().getFieldId());
        assertEquals("Field1", varArgs.getValue().getFieldName());

        verify(fieldService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void add_NewField_ShouldReturnOne()  throws Exception{

        String farmJson = "{\n" +
                "\"fieldId\": 101,\n" + "\"fieldName\": \"Field1\",\n" +
                " \"farm\":{\n" + "\t\"farmId\":1,\n" +
                "\t\"farmName\": \"farm1\"\n" + "}\n" + " }";

        when( fieldService.add(any(Field.class))).thenReturn(1);

        mockMvc.perform(post("/farming/orgs/clients/farms/fields")
                .contentType(MediaType.APPLICATION_JSON)
                .content(farmJson))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Field> varArgs = ArgumentCaptor.forClass(Field.class);
        verify(fieldService,times(1)).add(varArgs.capture());

        assertEquals(101, varArgs.getValue().getFieldId());
        assertEquals("Field1", varArgs.getValue().getFieldName());

        verify(fieldService,times(1)).add(varArgs.getValue());
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void deleteById_FieldFound_ShouldDeleteFieldAndReturnStatusCodeOk() throws Exception {

        when( fieldService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/farming/orgs/clients/farms/fields/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(fieldService,times(1)).deleteById(varArgs.capture());

        assertEquals(1, (int)varArgs.getValue());

        verify(fieldService,times(1)).deleteById(1);
        verifyNoMoreInteractions(fieldService);
    }

   @Test
    public void deleteById_FieldNotFound_ShouldReturnNotFoundStatusCode404() throws Exception {

        when( fieldService.deleteById(anyInt())).thenThrow(FieldNotFoundException.class);

        mockMvc.perform(delete("/farming/orgs/clients/farms/fields/{id}", 101)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(fieldService,times(1)).deleteById(varArgs.capture());

        assertEquals(101, (int)varArgs.getValue());

        verify(fieldService,times(1)).deleteById(101);
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void findById_FieldNotFound_ShouldReturnStatusCode404() throws Exception {

        when(fieldService.findById(anyInt())).thenThrow(FieldNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/farms/fields/{id}", 101)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(fieldService,times(1)).findById(varArgs.capture());

        assertEquals(101, (int)varArgs.getValue());

        verify(fieldService, times(1)).findById(101);
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void findById_FieldFound_ShouldReturnField() throws Exception {

        when(fieldService.findById(anyInt())).thenReturn(TestUtil.getField());

        mockMvc.perform(get("/farming/orgs/clients/farms/fields/{id}", 100)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

                .andExpect(jsonPath("$fieldId", is(100)))
                .andExpect(jsonPath("$fieldName", is("Test1 Farm")));

        ArgumentCaptor<Integer> varArgs = ArgumentCaptor.forClass(Integer.class);
        verify(fieldService,times(1)).findById(varArgs.capture());

        assertEquals(100, (int)varArgs.getValue());

        verify(fieldService, times(1)).findById(100);
        verifyNoMoreInteractions(fieldService);
    }

    @Test
    public void findAll_Field_ShouldReturnAllFields() throws Exception {

        when(fieldService.findAll()).thenReturn(TestUtil.getFields());

        mockMvc.perform(get("/farming/orgs/clients/farms/fields").accept("application/json")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())

                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].fieldId", is(100)))
                .andExpect(jsonPath("$[0].fieldName", is("Test1 Field1")))

                .andExpect(jsonPath("$[1].fieldId", is(101)))
                .andExpect(jsonPath("$[1].fieldName", is("Test1 Field2")))

                .andExpect(jsonPath("$[2].fieldId", is(102)))
                .andExpect(jsonPath("$[2].fieldName", is("Test1 Field3")));

        verify(fieldService, times(1)).findAll();
        verifyNoMoreInteractions(fieldService);
    }
}