CREATE TABLE IF NOT EXISTS weather_conditions(
    id BIGINT NOT NULL AUTO_INCREMENT,
    station_name VARCHAR(100),
    air_temperature FLOAT,
    wind_speed FLOAT,
    weather_phenomenon VARCHAR(100),
    observation_time DATETIME2,
    PRIMARY KEY (id)
);
