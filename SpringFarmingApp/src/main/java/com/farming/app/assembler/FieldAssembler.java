package com.farming.app.assembler;

import com.farming.app.controller.ClientController;
import com.farming.app.controller.FarmController;
import com.farming.app.controller.FieldController;
import com.farming.app.controller.OrganisationController;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Field;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FieldAssembler {

    public static Field addFieldLinks(Field field)  throws ResourceNotFoundException {

        Link selfLink = linkTo(methodOn(FieldController.class).findById(field.getFieldId())).withSelfRel();
        Link farmLink = linkTo(methodOn(FarmController.class).findById(field.getFarm().getFarmId())).withRel("farm");
        Link clientLink = linkTo(methodOn(ClientController.class).findById(field
                .getFarm().getClient().getClientId())).withRel("client");
        Link organisationLink = linkTo(methodOn(OrganisationController.class).findById(field.getFarm()
                .getClient().getOrganisation().getOrgId())).withRel("organisation");

        field.add(selfLink);
        field.add(farmLink);
        field.add(clientLink);
        field.add(organisationLink);

        return field;
    }
}
