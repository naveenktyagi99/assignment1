package com.farming.app.dao;


import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.farming.app.util.Constant;
import com.farming.app.util.FieldResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("fieldDao")
public class FieldDaoImpl implements FieldDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(Field field){
        return  jdbcTemplate.update(Constant.INSERT_FIELD, new Object[]{
                        field.getFieldId(), field.getFarm().getFarmId(), field.getFieldName()});
    }

    public int deleteById(int id) {
       return  jdbcTemplate.update(Constant.DELETE_FIELD, new Object[]{id});
    }

    public Field findById(int fieldId){

            return jdbcTemplate.queryForObject(Constant.FIND_BYID_FIELD, new Object[]{fieldId},
                    new RowMapper<Field>() {

            @Override
            public Field mapRow(ResultSet resultSet, int i) throws SQLException {
                int fieldId = resultSet.getInt("fieldId");
                String fieldName = resultSet.getString("fieldName");
                Organisation organisation = new Organisation(resultSet.getInt("orgId"),
                        resultSet.getString("orgName"),resultSet.getString("description"));
                Client client = new Client(resultSet.getInt("clientId"),
                        resultSet.getString("clientName"), organisation);

                String farmName = resultSet.getString("farmName");
                int farmId = resultSet.getInt("farmId");

                Farm farm = new Farm(farmId, farmName, client);
                return new Field(fieldId, fieldName, farm);
                }
            });
    }

    public List<Field> findAll() {
        return  jdbcTemplate.query(Constant.FIND_ALL_FIELD, new Object[]{}, new FieldResultSetExtractor());
    }
}