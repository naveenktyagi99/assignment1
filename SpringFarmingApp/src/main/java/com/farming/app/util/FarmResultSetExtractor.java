package com.farming.app.util;

import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Organisation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FarmResultSetExtractor implements ResultSetExtractor<List<Farm>> {

    @Override
    public List<Farm> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Map<Integer, Farm> farms = new HashMap<>();

        while (resultSet.next()) {
            int farmId = resultSet.getInt("farmId");
            String farmdName = resultSet.getString("farmName");
            if(!farms.containsKey(farmId)) {

                Organisation organisation = new Organisation(resultSet.getInt("orgId"),
                        resultSet.getString("orgName"), resultSet.getString("description"));
                Client client = new Client(resultSet.getInt("clientId"),
                        resultSet.getString("clientName"), organisation);

                Farm farm = new Farm( resultSet.getInt("farmId"), resultSet.getString("farmName"), client);
                farms.put(farm.getFarmId(), farm);
            }
        }
        return  new ArrayList<>(farms.values());
    }
}