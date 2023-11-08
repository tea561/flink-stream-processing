from confluent_kafka import Producer
import csv
import json
import sys 
import time

csv_file = 'belgrade_full_output.csv'
vehicle_topic = 'belgrade-vehicle'
edge_topic = 'belgrade-edge'

def receipt(err, msg):
    if err is not None:
        print('Error: {}', format(err))
    else:
        message = 'Produces message on topic {}:{}'.format(msg.topic(), msg.value().decode('utf-8'))
        print(message)

if __name__ == '__main__':
    producer = Producer({'bootstrap.servers': 'localhost:29092'})
    print('Kafka Producer has been initiated')

    with open(csv_file) as file:
        data = csv.DictReader(file, delimiter=';')
        for row in data:
            if row['vehicle_CO'] != "":
                vehicle_info = {}
                vehicle_info['data_timestep'] = float(row['data_timestep'])
                vehicle_info['vehicle_CO'] = float(row['vehicle_CO']) if row['vehicle_CO'] != "" else 0.0
                vehicle_info['vehicle_CO2'] = float(row['vehicle_CO2']) if row['vehicle_CO2'] != "" else 0.0
                vehicle_info['vehicle_HC'] = float(row['vehicle_HC']) if row['vehicle_HC'] != "" else 0.0
                vehicle_info['vehicle_NOx'] = float(row['vehicle_NOx']) if row['vehicle_NOx'] != "" else 0.0
                vehicle_info['vehicle_PMx'] = float(row['vehicle_PMx']) if row['vehicle_PMx'] != "" else 0.0
                vehicle_info['vehicle_angle'] = float(row['vehicle_angle']) if row['vehicle_angle'] != "" else 0.0
                vehicle_info['vehicle_eclass'] = row['vehicle_eclass']
                vehicle_info['vehicle_electricity'] = float(row['vehicle_electricity']) if row['vehicle_electricity'] != "" else 0.0
                vehicle_info['vehicle_fuel'] = float(row['vehicle_fuel']) if row['vehicle_fuel'] != "" else 0.0
                vehicle_info['vehicle_id'] = row['vehicle_id']
                vehicle_info['vehicle_lane'] = row['vehicle_lane']
                vehicle_info['vehicle_noise'] = float(row['vehicle_noise']) if row['vehicle_noise'] != "" else 0.0
                vehicle_info['vehicle_pos'] = float(row['vehicle_pos']) if row['vehicle_pos'] != "" else 0.0 
                vehicle_info['vehicle_route'] = row['vehicle_route'] 
                vehicle_info['vehicle_speed'] = float(row['vehicle_speed']) if row['vehicle_speed'] != "" else 0.0
                vehicle_info['vehicle_type'] = row['vehicle_type']  
                vehicle_info['vehicle_waiting'] = row['vehicle_waiting'] 
                vehicle_info['vehicle_x'] = float(row['vehicle_x']) if row['vehicle_x'] != "" else 0.0
                vehicle_info['vehicle_y'] = float(row['vehicle_y']) if row['vehicle_y'] != "" else 0.0

                producer.produce(vehicle_topic, key = 'belgrade', value = json.dumps(vehicle_info), callback = receipt)
                producer.flush()
                time.sleep(0.1)
            elif row['edge_id'] != "":
                edge_info = {}
                edge_info['data_timestep'] = float(row['data_timestep'])
                edge_info['edge_id'] = row['edge_id']
                edge_info['edge_traveltime'] = float(row['edge_traveltime']) if row['edge_traveltime'] != "" else 0.0
                edge_info['lane_CO'] = float(row['lane_CO']) if row['lane_CO'] != "" else 0.0
                edge_info['lane_CO2'] = float(row['lane_CO2']) if row['lane_CO2'] != "" else 0.0
                edge_info['lane_HC'] = float(row['lane_HC']) if row['lane_HC'] != "" else 0.0
                edge_info['lane_NOx'] = float(row['lane_NOx']) if row['lane_NOx'] != "" else 0.0
                edge_info['lane_PMx'] = float(row['lane_PMx']) if row['lane_PMx'] != "" else 0.0
                edge_info['lane_electricity'] = float(row['lane_electricity']) if row['lane_electricity'] != "" else 0.0
                edge_info['lane_fuel'] = float(row['lane_fuel']) if row['lane_fuel'] != "" else 0.0
                edge_info['lane_id'] = row['lane_id']
                edge_info['lane_maxspeed'] = float(row['lane_maxspeed']) if row['lane_maxspeed'] != "" else 0.0
                edge_info['lane_meanspeed'] = float(row['lane_meanspeed']) if row['lane_meanspeed'] != "" else 0.0
                edge_info['lane_noise'] = float(row['lane_noise']) if row['lane_noise'] != "" else 0.0
                edge_info['lane_occupancy'] = float(row['lane_occupancy']) if row['lane_occupancy'] != "" else 0.0
                edge_info['lane_vehicle_count'] = int(row['lane_vehicle_count']) if row['lane_vehicle_count'] != "" else 0

                producer.produce(edge_topic, key = 'belgrade', value = json.dumps(edge_info), callback = receipt)
                producer.flush()
                time.sleep(0.1)
    print('Kafka message producer done')









