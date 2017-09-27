package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.OrganisationNotFoundException;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.service.OrganisationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.farming.app.assembler.ClientAssembler.addClientLinks;
import static com.farming.app.assembler.FarmAssembler.addFarmLinks;
import static com.farming.app.assembler.FieldAssembler.addFieldLinks;
import static com.farming.app.assembler.OrganisationAssembler.addOrganisationLinks;

/**
 * @author naveen.tyagi
 */

@RestController
@RequestMapping(value = "/farming/orgs", produces = "application/json",
                                         consumes = "application/json")
public class OrganisationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganisationController.class);

    @Autowired
    private OrganisationService organisationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@Valid @RequestBody(required = true) Organisation organisation) throws BadRequestException{

        LOGGER.debug("Adding a new organisation entry with information: {}", organisation);

        organisationService.add(organisation);
        LOGGER.debug("Added an Organisation with information: {}", organisation);

       return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("orgId") int orgId)
            throws OrganisationNotFoundException {

        LOGGER.debug("Deleting a Organisation entry with id: {}", orgId);

        organisationService.deleteById(orgId);
        LOGGER.debug("Deleted Organisation with information: {}");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Organisation>> findAll() throws ResourceNotFoundException {
        LOGGER.debug("Finding all Organisation.");

        List<Organisation> organisations = organisationService.findAll();
        for(Organisation organisation : organisations){
            addOrganisationLinks(organisation);
        }

        LOGGER.debug("Found {} Organisations.", organisations.size());

        return new ResponseEntity<>(organisations, HttpStatus.OK);
    }

    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    public ResponseEntity<Organisation> findById(@PathVariable("orgId") int orgId) throws ResourceNotFoundException {
        LOGGER.debug("Finding Organisation with id: {}", orgId);

        Organisation found = organisationService.findById(orgId);
        addOrganisationLinks(found);
        LOGGER.debug("Found Organisation entry with information: {}", found);

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @RequestMapping(value = "/{orgId}/clients", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> findClientsById(@PathVariable("orgId") int orgId)
            throws ResourceNotFoundException {

        LOGGER.debug("Found Clients of Organisation with OrgId: {}", orgId);

        List<Client> clients = organisationService.findClients(orgId);
        for(Client client : clients){
            addClientLinks(client);
        }
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/{orgId}/clients/farms/fields", method = RequestMethod.GET)
    public ResponseEntity<List<Field>> findFieldByOrg(@PathVariable("orgId")int orgId) throws  ResourceNotFoundException{

        LOGGER.debug("All the fields against Organisation: {}", orgId);

        List<Field> fields = organisationService.findField(orgId);
        for(Field field : fields){
            addFieldLinks(field);
        }

        return new ResponseEntity<>(fields, HttpStatus.OK);
    }

    @RequestMapping(value = "/{orgId}/clients/farms", method = RequestMethod.GET)
    public ResponseEntity<List<Farm>> findFarm(@PathVariable("orgId")int orgId) throws  ResourceNotFoundException{

        LOGGER.debug("All the fields against Organisation: {}", orgId);
        List<Farm> farms = organisationService.findFarm(orgId);
        for (Farm farm: farms){
            addFarmLinks(farm);
        }
        return new ResponseEntity<>(farms, HttpStatus.OK);
    }
}