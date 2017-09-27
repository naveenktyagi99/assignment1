package com.farming.app.dao;


import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.util.ClientResultSetExtractor;
import com.farming.app.util.Constant;
import com.farming.app.util.FarmResultSetExtractor;
import com.farming.app.util.FieldResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("clientDao")
public class ClientDaoImpl implements  ClientDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int  add(Client client){
        return jdbcTemplate.update(Constant.INSERT_CLIENT, client.getClientId(),
                client.getOrganisation().getOrgId(), client.getClientName());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update(Constant.DELETE_CLIENT, new Object[]{id});

    }

    public List<Client> findAll() {
        return  jdbcTemplate.query(Constant.FIND_ALL_CLIENT, new Object[]{}, new ClientResultSetExtractor());
    }

    public Client findById(int clientId){
           return jdbcTemplate.query(Constant.FIND_BYID_CLIENT, new Object[]{clientId},
                   new ResultSetExtractor<Client>() {

               Client client = null;
               @Override
               public Client extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                   while (resultSet.next()) {
                       int clientId = resultSet.getInt("clientId");
                       String clientName = resultSet.getString("clientName");
                       String orgName = resultSet.getString("orgName");
                       int orgId = resultSet.getInt("orgId");
                       String orgDescription = resultSet.getString("description");
                       Organisation organisation = new Organisation(orgId, orgName, orgDescription);
                       client = new Client(clientId, clientName, organisation);
                   }
                   return client;
               }
           });
    }

    @Override
    public List<Farm> findFarm(int clientId) {
        return jdbcTemplate.query(Constant.FIND_FARM_CLIENT, new Object[]{clientId}, new FarmResultSetExtractor());
    }

    @Override
    public List<Field> findField(int clientId) {
        return jdbcTemplate.query(Constant.FIND_FIELD_CLIENT, new Object[]{clientId}, new FieldResultSetExtractor());
    }
}
