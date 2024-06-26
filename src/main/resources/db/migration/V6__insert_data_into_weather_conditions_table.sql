INSERT INTO weather_conditions
(station_name, air_temperature, wind_speed, weather_phenomenon, observation_time)
VALUES ('TALLINN', 30, 25, 'RAIN', now()),
       ('TALLINN', 0, 5, 'RAIN', now()-1000),
       ('TALLINN', 0, 5, 'SUNNY', now()-2000),
       ('TALLINN', -10, 15, 'SNOW', now()-3000),
       ('TALLINN', -10, 5, 'HAIL', now()-4000),
       ('TALLINN', -25, 15, 'SNOW', now()-5000);

INSERT INTO base_fees
(city, vehicle_type, base_fee, status, created_date)
VALUES ('PARNU', 'CAR',3.90, 'CURRENT', now()),
       ('PARNU', 'SCOOTER', 3.40, 'CURRENT', now()),
       ('PARNU', 'BIKE', 2.90, 'CURRENT', now());
INSERT INTO base_fees
(city, vehicle_type, base_fee, status, created_date)
VALUES ('TARTU', 'CAR',3.95, 'CURRENT', now()),
       ('TARTU', 'SCOOTER', 3.45, 'CURRENT', now()),
       ('TARTU', 'BIKE', 2.95, 'CURRENT', now());