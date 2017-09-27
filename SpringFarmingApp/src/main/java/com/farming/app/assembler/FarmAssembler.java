package com.farming.app.assembler;

import com.farming.app.controller.ClientController;
import com.farming.app.controller.FarmController;
import com.farming.app.controller.OrganisationController;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Farm;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FarmAssembler {

    public static Farm addFarmLinks(Farm farm) throws ResourceNotFoundException {

        Link selfLink = linkTo(methodOn(FarmController.class).findById(farm.getFarmId())).withSelfRel();
        Link fieldLink = linkTo(methodOn(FarmController.class).findField(farm.getFarmId())).withRel("fields");
        Link clientLink = linkTo(methodOn(ClientController.class).findById(farm.getClient()
                .getClientId())).withRel("client");
        Link organisationLink = linkTo(methodOn(OrganisationController.class).findById(farm
                .getClient().getOrganisation().getOrgId())).withRel("organisation");

        farm.add(selfLink);
        farm.add(organisationLink);
        farm.add(clientLink);
        farm.add(fieldLink);

        return farm;
    }
}
