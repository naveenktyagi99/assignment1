package com.farming.app.service;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.ClientNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;

import java.util.List;

public interface ClientService {

     int add(Client client) throws BadRequestException;

     int deleteById(int id) throws ClientNotFoundException;

     List<Client> findAll();

     Client findById(int id) throws ClientNotFoundException;

     List<Farm> findFarm(int clientId) throws ClientNotFoundException;

     List<Field> findField(int clientId) throws ClientNotFoundException;
}