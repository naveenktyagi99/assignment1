
package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.ClientNotFoundException;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.farming.app.assembler.ClientAssembler.addClientLinks;
import static com.farming.app.assembler.FarmAssembler.addFarmLinks;
import static com.farming.app.assembler.FieldAssembler.addFieldLinks;

/**
 * @author naveen.tyagi
 */

@RestController
@RequestMapping(value = "/farming", produces = "application/json",
                                    consumes = "application/json")
public class ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/orgs/clients", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody(required = true) Client client)
            throws BadRequestException{
        LOGGER.debug("Adding a new Client entry with information: {}", client);
        clientService.add(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/orgs/clients", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> findAll() throws ResourceNotFoundException{
        LOGGER.debug("All Clients");
        List<Client> clients = clientService.findAll();
        for(Client client : clients){
            addClientLinks(client);
        }

        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/{clientId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("clientId") int clientId) throws ClientNotFoundException {

        LOGGER.debug("Deleting a Client entry with id: {}", clientId);
        clientService.deleteById(clientId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<Client> findById(@PathVariable("clientId") int clientId) throws ResourceNotFoundException {
        LOGGER.debug("Finding Client with id: {}", clientId);

        Client found = clientService.findById(clientId);
        addClientLinks(found);
        LOGGER.debug("Found Client entry with information: {}", found);

        return new ResponseEntity<Client>(found, HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/{clientId}/farms", method = RequestMethod.GET)
    public ResponseEntity<List<Farm>> findFarm(@PathVariable("clientId") int clientId)
            throws ResourceNotFoundException{

        LOGGER.debug("Finding Farms for  Client entry with Id : {}", clientId);
        List<Farm> farms = clientService.findFarm(clientId);
        for (Farm farm : farms){
            addFarmLinks(farm);
        }

        return new ResponseEntity<List<Farm>>(farms, HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/{clientId}/farms/fields",  method = RequestMethod.GET)
    public ResponseEntity<List<Field>> findField(@PathVariable("clientId") int clientId)throws ResourceNotFoundException{

        LOGGER.debug("Finding Fields for  Client  with Id : {}", clientId);
        List<Field> fields = clientService.findField(clientId);
        for(Field field : fields){
            addFieldLinks(field);
        }
        return  new ResponseEntity<List<Field>>(fields, HttpStatus.OK);
    }
}