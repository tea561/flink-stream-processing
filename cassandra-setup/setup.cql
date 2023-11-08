CREATE KEYSPACE IF NOT EXISTS bigdata
  WITH REPLICATION = {
   'class' : 'SimpleStrategy',
   'replication_factor' : 1
  };

DROP TABLE IF EXISTS bigdata.edgeemission;

CREATE TABLE bigdata.edgeemission (
  dataTimeStep float,
  edgeId text,
  laneCO float,
  laneCO2 float,          
  laneHC float,
  laneNOx float,
  lanePMx float,
  PRIMARY KEY(dataTimeStep, edgeId)
);

CREATE TABLE bigdata.edges (
  dataTimeStep float,
  edgeId text,
  edgeTravelTime float,
  laneCO float,
  laneCO2 float,          
  laneHC float,
  laneNOx float,
  lanePMx float,
  laneElectricity float,
  laneId text,
  laneMaxSpeed float,
  laneMeanSpeed float,
  laneNoise float,
  laneOccupancy float,
  laneVehicleCount int,
  PRIMARY KEY(dataTimeStep, laneId)
);