package com.farming.app.util;

public class Constant {

    //Organisation Queries

    public static final String INSERT_ORGANISATION      = "INSERT INTO ORGANISATION(orgId, orgName, description)VALUES(?,?,?)";

    public static final String DELETE_ORGANISATION      = "DELETE from ORGANISATION WHERE orgId = ?";

    public static  final String FIND_ALL_ORGANISATION   =  "SELECT * FROM ORGANISATION";

    public static final String FIND_BYID_ORGANISATION   =  "SELECT O.*, C.* from ORGANISATION O "
                                                           +"LEFT JOIN CLIENT C on O.orgId = C.orgId "
                                                           +"WHERE O.orgId =? order by O.orgId";

    public static final String FIND_FIELD_ORGANISATION  =   "SELECT c.*, o.*, f.*, p.* from Field as f " +
                                                            "left join FARM as p on p.farmId = f.farmId " +
                                                            "left join Client c on c.clientId = p.clientId " +
                                                            "left join ORGANISATION as o on o.orgId = c.orgId " +
                                                            "WHERE o.orgId = ? ";

    public static final String FIND_FARM_ORGANISATION   =   "SELECT p.*,o.*,c.* from Farm as p "
                                                            +"left join Client c on c.clientId = p.clientId "
                                                            +"left join ORGANISATION as o on o.orgId = c.orgId "
                                                            +"WHERE o.orgId = ? ORDER By p.farmId";

    public static final String FIND_CLIENT_ORGANISATION =   "SELECT c.clientId, c.clientName, o.orgId, o.orgName,o.description " +
                                                            "FROM CLIENT as c left join ORGANISATION o on o.orgId = c.orgId WHERE " +
                                                            "o.orgId=?;";


    //Client Queries

    public static final String INSERT_CLIENT            =   "INSERT INTO CLIENT(clientId,orgId, clientName)VALUES(?,?,?)";

    public static final String DELETE_CLIENT            =   "DELETE from CLIENT WHERE clientId = ?";

    public static final String FIND_ALL_CLIENT          =   "SELECT c.*,o.* FROM CLIENT as c left join ORGANISATION o on o.orgId = c.orgId";

    public static final String FIND_BYID_CLIENT         =   "SELECT c.clientId, c.clientName, o.orgId, o.orgName,o.description "
                                                            + " FROM CLIENT as c left join ORGANISATION o on "
                                                            + "o.orgId = c.orgId WHERE clientId=?";

    public static final String FIND_FARM_CLIENT         =   "SELECT c.*, o.*, p.* from Farm as p "
                                                            + "left join Client c on p.clientId = c.clientId  "
                                                            + "left join ORGANISATION as o on o.orgId = c.orgId  "
                                                            + "WHERE c.clientId=?";

    public static final String FIND_FIELD_CLIENT        =   "SELECT c.*, o.*, f.*, p.* from Field as f "
                                                            + "left join FARM as p on p.farmId = f.farmId "
                                                            + "left join Client c on c.clientId = p.clientId "
                                                            + "left join ORGANISATION as o on o.orgId = c.orgId "
                                                            + "WHERE c.clientId=?";

    //Farm Queries

    public static final String INSERT_FARM      =   "INSERT INTO FARM(farmId,clientId, farmName)VALUES(?,?,?)";

    public static final String DELETE_FARM      =   "DELETE from FARM WHERE farmId = ?";

    public static final String FIND_ALL_FARM    =   "SELECT c.*, o.*, p.* from Farm as p " +
                                                    "LEFT JOIN Client c on c.clientId = p.clientId " +
                                                    "LEFT JOIN ORGANISATION as o on o.orgId = c.orgId ";

    public static final String FIND_BYID_FARM   =   "SELECT c.*, o.*, p.* from Farm as p " +
                                                    "LEFT JOIN Client c on c.clientId = p.clientId " +
                                                    "LEFT JOIN ORGANISATION as o on o.orgId = c.orgId " +
                                                    "WHERE p.farmId= ?";

    public static final String FIND_FIELD_FARM  =   "SELECT c.*, o.*, f.*, p.* from Field as f " +
                                                    "left join FARM as p on p.farmId = f.farmId " +
                                                    "left join Client c on c.clientId = p.clientId " +
                                                    "left join ORGANISATION as o on o.orgId = c.orgId " +
                                                    "WHERE p.farmId = ?";

    //Field Queries

    public static final String INSERT_FIELD     =  "INSERT INTO FIELD(fieldId, farmId, fieldName)VALUES(?,?,?)";

    public static final String DELETE_FIELD     = "DELETE from FIELD WHERE fieldId = ?";

    public static final String FIND_BYID_FIELD  =   "SELECT c.*, o.*, f.*, p.* from Field as f " +
                                                    "LEFT JOIN FARM as p on p.farmId = f.farmId " +
                                                    "LEFT JOIN Client c on c.clientId = p.clientId " +
                                                    "LEFT JOIN ORGANISATION as o on o.orgId = c.orgId " +
                                                    "WHERE f.fieldId=?";

    public static final String FIND_ALL_FIELD  =     "SELECT c.*, o.*, f.*, p.* from Field as f " +
                                                     "left join FARM as p on p.farmId = f.farmId " +
                                                     "left join Client c on c.clientId = p.clientId " +
                                                     "left join ORGANISATION as o on o.orgId = c.orgId ";
}
