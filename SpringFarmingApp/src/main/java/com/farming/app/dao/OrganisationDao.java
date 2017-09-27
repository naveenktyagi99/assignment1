package com.farming.app.dao;


import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("organisationRepository")
public interface OrganisationDao {

     int add(Organisation organisation) ;

     int deleteById(int id);

     List<Organisation> findAll();

     Organisation findById(int id);

     List<Field> findField(int orgId);

     List<Client> findClient(int orgId);

     List<Farm> findFarm(int orgId);
}