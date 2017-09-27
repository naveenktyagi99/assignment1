package com.farming.app.util;

import com.farming.app.model.Client;
import com.farming.app.model.Farm;
import com.farming.app.model.Field;
import com.farming.app.model.Organisation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author naveen.tyagi
 */
public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static final String CHARACTER = "a";

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }


    public static Organisation getOrganisation(){
        return new Organisation(100,"Org100","Desc100");
    }

    public static List<Organisation> getOrganisations(){

        List<Organisation> organisations = new ArrayList<Organisation>();

        organisations.add(new Organisation(1,"Org Name1","Description1"));
        organisations.add(new Organisation(2,"Org Name2","Description2"));
        organisations.add(new Organisation(3,"Org Name3","Description3"));
        organisations.add(new Organisation(4,"Org Name4","Description4"));
        organisations.add(new Organisation(5,"Org Name5","Description5"));

        return organisations;
    }

    //CLient

    public static Client getClient(){
        Organisation organisation = new Organisation(1, "Test1 Org", "Desc");
        return new Client(100, "Test1 Client", organisation);
    }

    public static List<Client> getClients(){

        List<Client> clients = new ArrayList<Client>();
        Organisation organisation = getOrganisation();
        Client client1 = new Client(1,"Client1", organisation);
        Client client2 = new Client(2,"Client2", organisation);
        Client client3 = new Client(3,"Client3",organisation);
        Client client4 = new Client(4,"Client4", organisation);

        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);

        return clients;
    }


    public static Farm getFarm(){
        Client client = getClient();
        client.setClientId(1);
        return new Farm(100, "Test1 Farm", client);
    }

    public static Field getField(){
        Farm farm = getFarm();
        farm.setFarmId(1);
        return new Field(100, "Test1 Farm", farm);
    }


    public static  Organisation getOrgWithClient(){
        Organisation organisation = new Organisation(1,"Test1","Desc1");
        organisation.setClients(getClients());
        return organisation;
    }

    public static List<Farm> getFarms() {
        List<Farm> farms = new ArrayList<Farm>();
        Client client = getClient();
        client.setClientId(1);
        Farm farm1 = new Farm(100, "Test1 Farm1", client);
        Farm farm2 = new Farm(101, "Test1 Farm2", client);
        Farm farm3 = new Farm(102, "Test1 Farm3", client);
        Farm farm4 = new Farm(103, "Test1 Farm4", client);

        farms.add(farm1);
        farms.add(farm2);
        farms.add(farm3);
        farms.add(farm4);


        return farms;
    }

    public static List<Field> getFields(){

        List<Field> fields = new ArrayList<Field>();
        Farm farm = getFarm();
        farm.setFarmId(1);
        fields.add(new Field(100, "Test1 Field1", farm));
        fields.add(new Field(101, "Test1 Field2", farm));
        fields.add(new Field(102, "Test1 Field3", farm));

        return fields;
    }
}
