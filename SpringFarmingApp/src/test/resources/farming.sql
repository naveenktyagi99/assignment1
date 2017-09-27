CREATE TABLE ORGANISATION (
  orgId INT AUTO_INCREMENT PRIMARY KEY CHECK(orgId > 0),
  orgName varchar(20) NOT NULL CHECK(orgName > ''),
  description varchar(50)
);

Insert into ORGANISATION (orgId, orgName,  description) values(1, 'Org1','Desc1');
Insert into ORGANISATION (orgId, orgName,  description) values(2, 'Org2','Desc2');
Insert into ORGANISATION (orgId, orgName,  description) values(3, 'Org3','Desc3');
Insert into ORGANISATION (orgId, orgName,  description) values(4, 'Org4','Desc4');
Insert into ORGANISATION (orgId, orgName,  description) values(5, 'Org5','Desc5');
Insert into ORGANISATION (orgId, orgName,  description) values(6, 'Org6','Desc6');


CREATE TABLE CLIENT (
  clientId INT AUTO_INCREMENT PRIMARY KEY CHECK(clientId > 0),
  clientName varchar(20) NOT NULL CHECK(clientName > ''),
  orgId INT
);
ALTER TABLE PUBLIC.CLIENT ADD FOREIGN KEY ( orgId ) REFERENCES PUBLIC.ORGANISATION( orgId ) ON DELETE CASCADE ;

Insert into CLIENT (clientId, clientName,  orgId) values(1, 'Client1',1);
Insert into CLIENT (clientId, clientName,  orgId) values(2, 'Client2',1);
Insert into CLIENT (clientId, clientName,  orgId) values(3, 'Client3',2);
Insert into CLIENT (clientId, clientName,  orgId) values(4, 'Client4',3);
Insert into CLIENT (clientId, clientName,  orgId) values(5, 'Client5',2);
Insert into CLIENT (clientId, clientName,  orgId) values(6, 'Client6',3);
Insert into CLIENT (clientId, clientName,  orgId) values(7, 'Client7',2);


CREATE TABLE FARM (
  farmId INT AUTO_INCREMENT PRIMARY KEY CHECK(farmId > 0),
  farmName varchar(20) NOT NULL CHECK(farmName > ''),
  clientId INT
);

ALTER TABLE PUBLIC.FARM ADD FOREIGN KEY (clientId) REFERENCES PUBLIC.CLIENT( clientId ) ON DELETE CASCADE ;

Insert into FARM (farmId, farmName,  clientId) values(1, 'Farm1',1);
Insert into FARM (farmId, farmName,  clientId) values(2, 'Farm2',1);
Insert into FARM (farmId, farmName,  clientId) values(3, 'Farm3',2);
Insert into FARM (farmId, farmName,  clientId) values(4, 'Farm4',2);
Insert into FARM (farmId, farmName,  clientId) values(5, 'Farm5',3);
Insert into FARM (farmId, farmName,  clientId) values(6, 'Farm6',3);
Insert into FARM (farmId, farmName,  clientId) values(7, 'Farm7',5);
Insert into FARM (farmId, farmName,  clientId) values(8, 'Farm8',5);


CREATE TABLE FIELD (
  fieldId INT AUTO_INCREMENT PRIMARY KEY CHECK(fieldId > 0),
  fieldName varchar(20) NOT NULL CHECK(fieldName > ''),
  farmId INT
);

ALTER TABLE PUBLIC.FIELD ADD FOREIGN KEY (farmId) REFERENCES PUBLIC.FARM(farmId) ON DELETE CASCADE ;

Insert into FIELD (fieldId, fieldName,  farmId) values(1, 'Field1',1);
Insert into FIELD (fieldId, fieldName,  farmId) values(2, 'Field2',1);
Insert into FIELD (fieldId, fieldName,  farmId) values(3, 'Field3',2);
Insert into FIELD (fieldId, fieldName,  farmId) values(4, 'Field4',2);
Insert into FIELD (fieldId, fieldName,  farmId) values(5, 'Field5',3);
Insert into FIELD (fieldId, fieldName,  farmId) values(6, 'Field6',3);
Insert into FIELD (fieldId, fieldName,  farmId) values(7, 'Field7',5);
Insert into FIELD (fieldId, fieldName,  farmId) values(8, 'Field8',5);