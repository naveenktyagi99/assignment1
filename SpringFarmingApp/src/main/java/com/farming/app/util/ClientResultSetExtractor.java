package com.farming.app.util;

import com.farming.app.model.Client;
import com.farming.app.model.Organisation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientResultSetExtractor implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Map<Integer, Client> clients = new HashMap<>();
        while (resultSet.next()) {
            int clientId = resultSet.getInt("clientId");
            String clientName = resultSet.getString("clientName");
            if(!clients.containsKey(clientId)) {
                Organisation organisation = new Organisation(resultSet.getInt("orgId"),
                        resultSet.getString("orgName"), resultSet.getString("description"));
                Client client = new Client(resultSet.getInt("clientId"),
                        resultSet.getString("clientName"), organisation);
                clients.put(client.getClientId(),client);
            }
        }
        return  new ArrayList<>(clients.values());
    }
}
