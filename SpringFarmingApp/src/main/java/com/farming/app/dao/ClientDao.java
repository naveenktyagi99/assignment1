package com.farming.app.dao;


import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;

import java.util.List;

public interface ClientDao {

     int add(Client client) ;

     int deleteById(int id) ;

     List<Client> findAll();

     Client findById(int id);

     List<Farm> findFarm(int clientId);

     List<Field> findField(int clientId);
}