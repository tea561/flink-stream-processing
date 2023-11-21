CREATE KEYSPACE IF NOT EXISTS bigdata
  WITH REPLICATION = {
   'class' : 'SimpleStrategy',
   'replication_factor' : 1
  };

DROP TABLE IF EXISTS bigdata.lane_aggregation;

CREATE TABLE bigdata.lane_aggregation (
  date TIMESTAMP PRIMARY KEY,
  laneId text,
  laneCO double,
  laneCO2 double,          
  laneHC double,
  laneNOx double,
  lanePMx double,
  avgSpeed double,
  avgWaitingTime double
);

DROP TABLE IF EXISTS bigdata.lane_metrics;
CREATE TABLE bigdata.lane_metrics (
  laneId text PRIMARY KEY,
  laneLength double,
  avgTrafficVolume double,
  avgNumOfVehicles double
);

DROP TABLE IF EXISTS bigdata.vehicle_summary;
CREATE TABLE bigdata.vehicle_summary (
  date TIMESTAMP PRIMARY KEY,
  vehicleId text,
  vehicleCO double,
  vehicleCO2 double,
  vehicleHC double,
  vehicleNOx double,
  vehiclePMx double
);

DROP TABLE IF EXISTS bigdata.lane_vehicle_count;
CREATE TABLE bigdata.lane_vehicle_count (
  date TIMESTAMP PRIMARY KEY,
  laneId text,
  vehicleCount int
);

DROP TABLE IF EXISTS bigdata.speed_CO2_correlation;
CREATE TABLE bigdata.speed_CO2_correlation (
  date TIMESTAMP PRIMARY KEY,
  coefficient double
);

DROP TABLE IF EXISTS bigdata.suburb_emission;
CREATE TABLE bigdata.suburb_emission (
  name text,
  date TIMESTAMP PRIMARY KEY,
  suburbCO double,
  suburbCO2 double,
  suburbHC double,
  suburbNOx double,
  suburbPMx double
);

DROP TABLE IF EXISTS bigdata.traffic_jam;
CREATE TABLE bigdata.traffic_jam (
  laneId text,
  date TIMESTAMP PRIMARY KEY,
  avgTrafficVolume double,
  avgSpeed double,
  avgWaitingTime double
);