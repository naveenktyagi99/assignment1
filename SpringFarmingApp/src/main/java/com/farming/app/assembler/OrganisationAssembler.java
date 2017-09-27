package com.farming.app.assembler;

import com.farming.app.controller.OrganisationController;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Organisation;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class OrganisationAssembler {

    public static Organisation addOrganisationLinks(Organisation organisation) throws ResourceNotFoundException {

        Link selfLink = linkTo(methodOn(OrganisationController.class).findById(organisation
                .getOrgId())).withSelfRel();
        Link clientsLink = linkTo(methodOn(OrganisationController.class).findClientsById(organisation
                .getOrgId())).withRel("clients");
        Link farmsLink = linkTo(methodOn(OrganisationController.class).findFarm(organisation.getOrgId()))
                .withRel("farms");
        Link fieldsLink = linkTo(methodOn(OrganisationController.class).findFieldByOrg(organisation
                .getOrgId())).withRel("fields");

        organisation.add(selfLink);
        organisation.add(clientsLink);
        organisation.add(farmsLink);
        organisation.add(fieldsLink);

        return organisation;
    }
}
