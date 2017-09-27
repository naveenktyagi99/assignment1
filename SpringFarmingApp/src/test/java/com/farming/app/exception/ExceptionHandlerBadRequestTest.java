package com.farming.app.exception;

import com.farming.app.controller.ClientController;
import com.farming.app.model.Client;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExceptionHandlerBadRequestTest {

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
    public void add_NewClient_BlankName_ShouldReturnBadRequestStatusCodeAndBadRequestMessage()
            throws Exception{

        String clientJson = "{\n" + " \t \"clientId\": 101,\n" + "\"clientName\": \"\",\n" +
                "\"organisation\":{\n" + "\t\"orgId\":1,\n" + "\t\"orgName\": \"org1\"\n" +
                "}\n" + " }";

        when(clientService.add(any(Client.class))).thenThrow(new BadRequestException("Bad Request"));

        mockMvc.perform(post("/farming/orgs/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientJson))
                .andDo(print())
                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$errorCode", is(400)))
                .andExpect(jsonPath("$message", is("Bad Request")));
    }
}