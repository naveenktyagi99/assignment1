package com.farming.app.util;

import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldResultSetExtractor implements ResultSetExtractor<List<Field>> {

    @Override
    public List<Field> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Map<Integer, Field> fields = new HashMap<>();

        while (resultSet.next()) {
            int filedId = resultSet.getInt("fieldId");
            String filedName = resultSet.getString("fieldName");
            if(!fields.containsKey(filedId)) {

                Organisation organisation = new Organisation(resultSet.getInt("orgId"),
                        resultSet.getString("orgName"), resultSet.getString("description"));
                Client client = new Client(resultSet.getInt("clientId"),
                        resultSet.getString("clientName"), organisation);

                Farm farm = new Farm( resultSet.getInt("farmId"), resultSet.getString("farmName"), client);
                Field field = new Field(filedId, filedName, farm);
                fields.put(field.getFieldId(), field);
            }
        }
        return  new ArrayList<>(fields.values());
    }
}