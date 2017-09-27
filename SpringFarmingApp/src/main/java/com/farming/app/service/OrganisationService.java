package com.farming.app.service;


import com.farming.app.exception.BadRequestException;
import com.farming.app.exception.OrganisationNotFoundException;
import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;

import java.util.List;

public interface OrganisationService {

     int add(Organisation organisation) throws BadRequestException;

     int deleteById(int id) throws OrganisationNotFoundException;

     List<Organisation> findAll();

     Organisation findById(int id) throws OrganisationNotFoundException;

     List<Client> findClients(int id) throws OrganisationNotFoundException;

     List<Field> findField(int orgId) throws  OrganisationNotFoundException;

     List<Farm> findFarm(int orgId);

}
