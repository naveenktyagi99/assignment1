package com.farming.app.dao;


import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.util.Constant;
import com.farming.app.util.FarmResultSetExtractor;
import com.farming.app.util.FieldResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("farmDao")
public class FarmDaoImpl implements FarmDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Farm farm) {
            return  jdbcTemplate.update(Constant.INSERT_FARM, new Object[]{
                            farm.getFarmId(), farm.getClient().getClientId(), farm.getFarmName()});
    }

    public int deleteById(int id) {
        return jdbcTemplate.update(Constant.DELETE_FARM, new Object[]{id});
    }

    public Farm findById(int id){
            return jdbcTemplate.queryForObject(Constant.FIND_BYID_FARM, new Object[]{id}, new RowMapper<Farm>() {
                @Override
                public Farm mapRow(ResultSet resultSet, int i) throws SQLException {

                    int farmId = resultSet.getInt("farmId");
                    String farmName = resultSet.getString("farmName");

                    Organisation organisation = new Organisation(resultSet.getInt("orgId"),
                            resultSet.getString("orgName"), resultSet.getString("description"));
                    Client client = new Client(resultSet.getInt("clientId"),
                            resultSet.getString("clientName"),  organisation);

                    return new Farm(farmId, farmName, client);
                }
            });
    }

    public List<Farm> findAll() {
        return  jdbcTemplate.query(Constant.FIND_ALL_FARM, new Object[]{}, new FarmResultSetExtractor());
    }

    public List<Field> findField(int id) {
        return  jdbcTemplate.query(Constant.FIND_FIELD_FARM, new Object[]{id}, new FieldResultSetExtractor());
    }
}