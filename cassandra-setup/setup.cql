CREATE KEYSPACE IF NOT EXISTS bigdata
  WITH REPLICATION = {
   'class' : 'SimpleStrategy',
   'replication_factor' : 1
  };

DROP TABLE IF EXISTS bigdata.lane_emissions;

CREATE TABLE bigdata.lane_emissions (
  date TIMESTAMP PRIMARY KEY,
  laneId text,
  laneCO float,
  laneCO2 float,          
  laneHC float,
  laneNOx float,
  lanePMx float
);

DROP TABLE IF EXISTS bigdata.vehicle_summary;
CREATE TABLE bigdata.vehicle_summary (
  date TIMESTAMP PRIMARY KEY,
  vehicleId text,
  vehicleCO float,
  vehicleCO2 float,
  vehicleHC float,
  vehicleNOx float,
  vehiclePMx float
);

DROP TABLE IF EXISTS bigdata.lane_vehicle_count;
CREATE TABLE bigdata.lane_vehicle_count (
  date TIMESTAMP PRIMARY KEY,
  laneId text,
  vehicleCount int
);
