package com.farming.app.service;


import com.farming.app.dao.ClientDao;
import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.ClientNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientService")
public class ClientServiceImpl implements ClientService{


    @Autowired
    ClientDao clientDao;

    @Autowired
    OrganisationService organisationService;

    public int add(Client added) throws BadRequestException{
        try {
            return clientDao.add(added);
        }
        catch (DuplicateKeyException ex){
            throw new BadRequestException(ex.getCause());
        }
        catch (DataIntegrityViolationException ex){
            throw new BadRequestException(ex.getCause());
        }
    }

    public int deleteById(int id) throws ClientNotFoundException {
        int count = clientDao.deleteById(id);
        if(count<1){
            throw new ClientNotFoundException("Client Not Found");
        }
        return count;
    }

    public List<Client> findAll() {
        return clientDao.findAll();
    }

    public Client findById(int id) throws ClientNotFoundException {
        Client found = clientDao.findById(id);
        if (found == null) {
            throw new ClientNotFoundException("No Client found with id: " + id);
        }
        return found;
    }

    @Override
    public List<Farm> findFarm(int clientId)throws ClientNotFoundException {
        return clientDao.findFarm(clientId);
    }

    @Override
    public List<Field> findField(int clientId)throws ClientNotFoundException {
        return clientDao.findField(clientId);
    }
}