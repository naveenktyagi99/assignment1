package com.farming.app.exception;

import com.farming.app.controller.OrganisationController;
import com.farming.app.service.OrganisationService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrganisationNotFoundExceptionTest {

    private MockMvc mockMvc;

    @Mock
    OrganisationService organisationService;

    @InjectMocks
    OrganisationController organisationController;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        HandlerExceptionResolver handlerExceptionResolver = AdviceHandlerExceptionResolver
                .initGlobalExceptionHandlerResolvers();

        mockMvc = MockMvcBuilders.standaloneSetup(organisationController)
               .setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    @Test
    public void findFields_OrganisationNotFound_ShouldReturnEmptyFieldsList() throws  Exception{

        when(organisationService.findField(anyInt())).thenThrow(OrganisationNotFoundException.class);

        mockMvc.perform(get("/farming/orgs/{orgId}/clients/farms/fields", 111)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())

                .andExpect(jsonPath("$errorCode", is(404)))
                .andExpect(jsonPath("$message", is("Organisation Not Found")));
    }
}