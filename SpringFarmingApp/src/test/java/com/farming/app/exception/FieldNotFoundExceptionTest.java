package com.farming.app.exception;

import com.farming.app.controller.FieldController;
import com.farming.app.service.FieldService;
import com.farming.app.util.AdviceHandlerExceptionResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FieldNotFoundExceptionTest {

    private MockMvc mockMvc;

    @Mock
    FieldService fieldService;

    @InjectMocks
    FieldController fieldController;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        HandlerExceptionResolver handlerExceptionResolver = AdviceHandlerExceptionResolver
                .initGlobalExceptionHandlerResolvers();

        mockMvc = MockMvcBuilders.standaloneSetup(fieldController)
               .setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Test
    public void deleteById_FieldNotFound_ShouldReturnNotFoundStatusCode404() throws Exception {

        when( fieldService.deleteById(anyInt())).thenThrow(FieldNotFoundException.class);

        mockMvc.perform(delete("/farming/orgs/clients/farms/fields/{id}", 101)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$errorCode",is(404)))
                .andExpect(jsonPath("$message",is("Field Not Found")));
    }
}