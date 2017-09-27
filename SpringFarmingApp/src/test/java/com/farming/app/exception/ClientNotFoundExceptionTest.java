package com.farming.app.exception;

import com.farming.app.controller.ClientController;
import com.farming.app.service.ClientService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientNotFoundExceptionTest {

    private MockMvc mockMvc;

    @Mock
    ClientService clientService;

    @InjectMocks
    ClientController clientController;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        HandlerExceptionResolver handlerExceptionResolver = AdviceHandlerExceptionResolver
                .initGlobalExceptionHandlerResolvers();

        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
               .setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Test
    public void findFields_ClientNotFound_ShouldReturnNotFoundStatusCode_AndErrorMessage_ClientNotFound() throws  Exception{

        when(clientService.findField(anyInt())).thenThrow(ClientNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/clients/{id}/farms/fields", 1001)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$errorCode", is(404)))
                .andExpect(jsonPath("$message", is("Client Not Found")));
    }
}
