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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("organisationDao")
public class OrganisationDaoImpl implements OrganisationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Organisation organisation) {

        return jdbcTemplate.update(
                Constant.INSERT_ORGANISATION,
                organisation.getOrgId(), organisation.getOrgName(), organisation.getDescription());
    }

    public int deleteById(int orgId)  {
        return jdbcTemplate.update(Constant.DELETE_ORGANISATION, orgId);
    }

    public Organisation findById(int orgId) {

        return  jdbcTemplate.query(Constant.FIND_BYID_ORGANISATION, new Object[]{orgId}, new ResultSetExtractor<Organisation>() {

            @Override
            public Organisation extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Organisation organisation = null;
                while (resultSet.next()) {
                    if(organisation == null) {
                        organisation = new Organisation();
                        organisation.setOrgId(resultSet.getInt("orgId"));
                        organisation.setOrgName(resultSet.getString("orgName"));
                        organisation.setDescription(resultSet.getString("description"));
                    }
                    int clientId = resultSet.getInt("clientId");
                    if (clientId!=0) {
                        Organisation clientOrganisation  = new Organisation(organisation.getOrgId(),
                                organisation.getOrgName(),organisation.getDescription());
                        Client client = new Client(clientId,
                                resultSet.getString("clientName"), clientOrganisation);
                        organisation.getClients().add(client);
                    }
                }
                return  organisation;
            }
        });
    }

    public List<Organisation> findAll() {

        return jdbcTemplate.query(Constant.FIND_ALL_ORGANISATION,
                new BeanPropertyRowMapper<Organisation>(Organisation.class));
    }

    public List<Client> findClient(int orgId){

        return jdbcTemplate.query(Constant.FIND_CLIENT_ORGANISATION, new Object[]{orgId}, new ClientResultSetExtractor());
    }

    public List<Field> findField(int orgId) {
        return  jdbcTemplate.query(Constant.FIND_FIELD_ORGANISATION, new Object[]{orgId}, new FieldResultSetExtractor());
    }

    public List<Farm> findFarm(int orgId){
        return  jdbcTemplate.query(Constant.FIND_FARM_ORGANISATION, new Object[]{orgId}, new FarmResultSetExtractor()) ;
    }
}