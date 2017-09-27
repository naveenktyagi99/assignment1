
package com.farming.app.controller;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.FieldNotFoundException;
import com.farming.app.exception.ResourceNotFoundException;
import com.farming.app.model.Field;
import com.farming.app.service.FieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.farming.app.assembler.FieldAssembler.addFieldLinks;


/**
 * @author naveen.tyagi
 */

@RestController
@RequestMapping(value = "/farming", produces = "application/json",
                                    consumes = "application/json")
public class FieldController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldController.class);

    @Autowired
    private FieldService fieldService;

    @RequestMapping(value = "/orgs/clients/farms/fields", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody(required = true) Field field)
            throws BadRequestException {
        LOGGER.debug("Adding a new Field entry with information: {}", field);

        fieldService.add(field);
        LOGGER.debug("Added an Field with information: {}", field);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/orgs/clients/farms/fields", method = RequestMethod.GET)
    public ResponseEntity<List<Field>> findAll() throws  Exception{
        LOGGER.debug("All Fields");
        List<Field> fields = fieldService.findAll();
        for(Field field : fields){
            addFieldLinks(field);
        }
        return new ResponseEntity<List<Field>>(fields, HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/farms/fields/{fieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable("fieldId") int fieldId) throws FieldNotFoundException {

        LOGGER.debug("Deleting a Field entry with id: {}", fieldId);
        fieldService.deleteById(fieldId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/orgs/clients/farms/fields/{fieldId}", method = RequestMethod.GET)
    public ResponseEntity<Field> findById(@PathVariable("fieldId") int fieldId)
            throws ResourceNotFoundException {

        LOGGER.debug("Finding Field with id: {}", fieldId);

        Field found = fieldService.findById(fieldId);
        LOGGER.debug("Found Farm entry with information: {}", found);
        addFieldLinks(found);
        return new ResponseEntity<Field>(found, HttpStatus.OK);
    }
}