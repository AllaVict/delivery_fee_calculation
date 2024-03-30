INSERT INTO extra_fees
(name, vehicle_type, lower_limit, upper_limit, extra_fee, is_forbidden, status)
VALUES ('air temperature', 'SCOOTER',-10, null, 1.00, FALSE, 'CURRENT'),
       ('air temperature', 'BIKE',-10, null, 1.00, FALSE, 'CURRENT'),
       ('air temperature', 'SCOOTER', -10, 0, 0.50, FALSE, 'CURRENT'),
       ('air temperature', 'BIKE', -10, 0, 0.50, FALSE, 'CURRENT'),
       ('wind speed', 'BIKE',10, 20, 0.50, FALSE, 'CURRENT'),
       ('wind speed', 'BIKE',null, 20, null, TRUE, 'CURRENT');

INSERT INTO extra_fees
(name, vehicle_type, weather_phenomenon, extra_fee, is_forbidden, lower_limit, upper_limit, status)
VALUES ('weather phenomenon', 'SCOOTER','SNOW', 1.00, FALSE, null, null, 'CURRENT'),
       ('weather phenomenon', 'SCOOTER','SLEET', 1.00, FALSE, null, null, 'CURRENT'),
       ('weather phenomenon', 'BIKE','SNOW', 1.00, FALSE, null, null, 'CURRENT'),
       ('weather phenomenon', 'BIKE','SLEET', 1.00, FALSE, null, null, 'CURRENT'),
       ('weather phenomenon', 'SCOOTER','RAIN', 0.50, FALSE, null, null, 'CURRENT'),
       ('weather phenomenon', 'BIKE','RAIN', 0.50, FALSE, null, null, 'CURRENT'),

       ('weather phenomenon', 'SCOOTER','GLAZE', null, TRUE, null, null, 'CURRENT'),
       ('weather phenomenon', 'SCOOTER','HAIL', null, TRUE, null, null, 'CURRENT'),
       ('weather phenomenon', 'SCOOTER','THUNDER', null, TRUE, null, null, 'CURRENT'),
       ('weather phenomenon', 'BIKE','GLAZE', null, TRUE, null, null, 'CURRENT'),
       ('weather phenomenon', 'BIKE','HAIL', null, TRUE, null, null, 'CURRENT'),
       ('weather phenomenon', 'BIKE','THUNDER', null, TRUE, null, null, 'CURRENT');
