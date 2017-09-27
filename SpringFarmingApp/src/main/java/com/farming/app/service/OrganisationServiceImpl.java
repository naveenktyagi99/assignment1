package com.farming.app.service;


import com.farming.app.dao.OrganisationDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.OrganisationNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author naveen.tyagi
 */
@Service("organisationService")
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationDao repository;

    public int add(Organisation added) throws BadRequestException{

        try {
            return repository.add(added);
        }
        catch (DuplicateKeyException ex){
            throw new BadRequestException(ex.getCause());
        }
        catch (DataAccessException ex){
            throw new BadRequestException(ex.getCause());
        }
    }

    public int deleteById(int id) throws OrganisationNotFoundException {

        int count = repository.deleteById(id);
        if(count<1){
            throw new OrganisationNotFoundException("Organisation Not Found");
        }
        return  count;
    }

    public List<Organisation> findAll() {
        return repository.findAll();
    }

    public Organisation findById(int id) throws OrganisationNotFoundException {

        Organisation found = repository.findById(id);
        if (found == null) {
            throw new OrganisationNotFoundException("No Organisation found with id: " + id);
        }
        return found;
    }

    public List<Client> findClients(int id) throws OrganisationNotFoundException {
        return  repository.findClient(id);
        }

    @Override
    public List<Field> findField(int orgId) throws  OrganisationNotFoundException{
        return repository.findField(orgId);
    }

    @Override
    public List<Farm> findFarm(int orgId) {
        return repository.findFarm(orgId);
    }
}