package com.farming.app.assembler;

import com.farming.app.controller.ClientController;
import com.farming.app.controller.OrganisationController;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Client;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ClientAssembler {

    public static Client addClientLinks(Client client) throws ResourceNotFoundException {

        Link selfLink = linkTo(methodOn(ClientController.class).findById(client.getClientId())).withSelfRel();
        Link organisationLink = linkTo(methodOn(OrganisationController.class).findById(client
                .getOrganisation().getOrgId())).withRel("organisation");
        Link farmsLink = linkTo(methodOn(ClientController.class).findFarm(client.getClientId())).withRel("farms");
        Link fieldsLink = linkTo(methodOn(ClientController.class).findField(client.getClientId())).withRel("fields");

        client.add(selfLink);
        client.add(organisationLink);
        client.add(farmsLink);
        client.add(fieldsLink);

        return client;
    }
}
