package com.farming.app.exception;

import com.farming.app.controller.FarmController;
import com.farming.app.service.FarmService;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FarmNotFoundExceptionTest {

    private MockMvc mockMvc;

    @Mock
    FarmService farmService;

    @InjectMocks
    FarmController farmController;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        HandlerExceptionResolver handlerExceptionResolver = AdviceHandlerExceptionResolver
                .initGlobalExceptionHandlerResolvers();

        mockMvc = MockMvcBuilders.standaloneSetup(farmController)
               .setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Test
    public void deleteById_FarmNotFound_ShouldReturnNotFoundStatusCodeAndErrorMessage_FarmNotFound()
            throws Exception {

        when( farmService.deleteById(anyInt())).thenThrow(FarmNotFoundException.class);

        mockMvc.perform(delete("/farming/orgs/clients/farms/{id}", 101)

                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$errorCode", is(404)))
                .andExpect(jsonPath("$message", is("Farm Not Found")));
    }
}