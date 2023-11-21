from confluent_kafka import Producer
import csv
import json
import sys 
import time
import datetime

lane_file = 'belgrade_lanes.csv'
emission_file = 'emissions.csv'
lanes_topic = 'belgrade-lanes'
emission_topic = 'belgrade-vehicle-emission'

def receipt(err, msg):
    if err is not None:
        print('Error: {}', format(err))
    else:
        message = 'Produces message on topic {}:{}'.format(msg.topic(), msg.value().decode('utf-8'))
        print(message)

if __name__ == '__main__':
    if len(sys.argv) != 2:
        sys.exit(1)

    csv_file = sys.argv[1]
    producer = Producer({'bootstrap.servers': 'kafka:9092'})
    print('Kafka Producer has been initiated')

    if csv_file == lane_file:
        with open(lane_file) as vehicle_file:
            lane_data = csv.DictReader(vehicle_file, delimiter=';')
            for row in lane_data:
                vehicle_info = {}
                vehicle_info['interval_begin'] = float(row['interval_begin'])
                vehicle_info['interval_end'] = float(row['interval_end']) if row['interval_end'] != "" else 0.0
                vehicle_info['interval_id'] = row['interval_id']
                vehicle_info['edge_id'] = row['edge_id']
                vehicle_info['lane_arrived'] = int(row['lane_arrived']) if row['lane_arrived'] != "" else 0
                vehicle_info['lane_density'] = float(row['lane_density']) if row['lane_density'] != "" else 0.0
                vehicle_info['lane_departed'] = int(row['lane_departed']) if row['lane_departed'] != "" else 0
                vehicle_info['lane_entered'] = int(row['lane_entered']) if row['lane_entered'] != "" else 0
                vehicle_info['lane_id'] = row['lane_id']
                vehicle_info['lane_left'] = int(row['lane_left']) if row['lane_left'] != "" else 0
                vehicle_info['lane_occupancy'] = float(row['lane_occupancy']) if row['lane_occupancy'] != "" else 0.0
                vehicle_info['lane_overlapTraveltime'] = float(row['lane_overlapTraveltime']) if row['lane_overlapTraveltime'] != "" else 0.0
                vehicle_info['lane_sampledSeconds'] = float(row['lane_sampledSeconds']) if row['lane_sampledSeconds'] != "" else 0.0
                vehicle_info['lane_speed'] = float(row['lane_speed']) if row['lane_speed'] != "" else 0.0
                vehicle_info['lane_speedRelative'] = float(row['lane_speedRelative']) if row['lane_speedRelative'] != "" else 0.0
                vehicle_info['lane_timeLoss'] = float(row['lane_timeLoss']) if row['lane_timeLoss'] != "" else 0.0
                vehicle_info['lane_traveltime'] = float(row['lane_traveltime']) if row['lane_traveltime'] != "" else 0.0
                vehicle_info['lane_waitingTime'] = float(row['lane_waitingTime']) if row['lane_waitingTime'] != "" else 0.0

                producer.produce(lanes_topic, key = 'belgrade', value = json.dumps(vehicle_info), callback = receipt)
                producer.flush()
                time.sleep(0.1)
    else:
        with open(emission_file) as emission_file:
            data = csv.DictReader(emission_file, delimiter=';')
            for row in data:
                emission_info = {}
                emission_info['timestep_time'] = float(row['timestep_time'])
                emission_info['vehicle_CO'] = float(row['vehicle_CO']) if row['vehicle_CO'] != "" else 0.0
                emission_info['vehicle_CO2'] = float(row['vehicle_CO2']) if row['vehicle_CO2'] != "" else 0.0
                emission_info['vehicle_HC'] = float(row['vehicle_HC']) if row['vehicle_HC'] != "" else 0.0
                emission_info['vehicle_NOx'] = float(row['vehicle_NOx']) if row['vehicle_NOx'] != "" else 0.0
                emission_info['vehicle_PMx'] = float(row['vehicle_PMx']) if row['vehicle_PMx'] != "" else 0.0
                emission_info['vehicle_angle'] = float(row['vehicle_angle']) if row['vehicle_angle'] != "" else 0.0
                emission_info['vehicle_electricity'] = float(row['vehicle_electricity']) if row['vehicle_electricity'] != "" else 0.0
                emission_info['vehicle_eclass'] = row['vehicle_eclass']
                emission_info['vehicle_id'] = row['vehicle_id']
                emission_info['vehicle_lane'] = row['vehicle_lane']
                emission_info['vehicle_fuel'] = float(row['vehicle_fuel']) if row['vehicle_fuel'] != "" else 0.0
                emission_info['vehicle_noise'] = float(row['vehicle_noise']) if row['vehicle_noise'] != "" else 0.0
                emission_info['vehicle_pos'] = float(row['vehicle_pos']) if row['vehicle_pos'] != "" else 0.0
                emission_info['vehicle_route'] = row['vehicle_route']
                emission_info['vehicle_speed'] = float(row['vehicle_speed']) if row['vehicle_speed'] != "" else 0.0
                emission_info['vehicle_type'] = row['vehicle_type']
                emission_info['vehicle_waiting'] = float(row['vehicle_waiting']) if row['vehicle_waiting'] != "" else 0.0
                emission_info['vehicle_x'] = float(row['vehicle_x']) if row['vehicle_x'] != "" else 0.0
                emission_info['vehicle_y'] = float(row['vehicle_y']) if row['vehicle_y'] != "" else 0.0

                producer.produce(emission_topic, key = 'belgrade', value = json.dumps(emission_info), callback = receipt)
                producer.flush()
                time.sleep(0.1)
    print('Kafka message producer done')









