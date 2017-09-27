
package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FarmNotFoundException;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.service.FarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.farming.app.assembler.FarmAssembler.addFarmLinks;
import static com.farming.app.assembler.FieldAssembler.addFieldLinks;

/**
 * @author naveen.tyagi
 */

@RestController
@RequestMapping(value = "/farming", produces = "application/json",
                                    consumes = "application/json")
public class FarmController{

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmController.class);

    @Autowired
    private FarmService service;


    @RequestMapping(value = "/orgs/clients/farms", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody(required = true) Farm farm)
            throws BadRequestException{

        LOGGER.debug("Adding a new Farm entry with information: {}", farm);
        service.add(farm);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/orgs/clients/farms", method = RequestMethod.GET)
    public ResponseEntity<List<Farm>> findAll() throws ResourceNotFoundException{
        LOGGER.debug("All Farm entry with information: {}");
        List<Farm> farms = service.findAll();
        for(Farm farm : farms){
            addFarmLinks(farm);
        }

        return new ResponseEntity<List<Farm>>(farms ,HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/farms/{farmId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("farmId") int farmId) throws FarmNotFoundException {
        LOGGER.debug("Deleting a Farm entry with id: {}", farmId);
        service.deleteById(farmId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/farms/{farmId}", method = RequestMethod.GET)
    public ResponseEntity<Farm> findById(@PathVariable("farmId") int farmId) throws ResourceNotFoundException {

        LOGGER.debug("Finding Farm with id: {}", farmId);
        Farm found = service.findById(farmId);
        LOGGER.debug("Found Farm entry with information: {}", found);
        addFarmLinks(found);
        return new ResponseEntity<Farm>(found, HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/farms/{farmId}/fields", method = RequestMethod.GET)
    public ResponseEntity<List<Field>> findField(@PathVariable("farmId") int farmId) throws ResourceNotFoundException{

        LOGGER.debug("Finding Fields for Farm with id: {}", farmId);
        List<Field> fields  = service.findField(farmId);
        for(Field field :fields){
            addFieldLinks(field);
        }
        return new ResponseEntity<List<Field>>(fields, HttpStatus.OK);
    }
}