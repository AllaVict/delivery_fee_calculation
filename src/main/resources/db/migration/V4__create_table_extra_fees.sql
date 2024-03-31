CREATE TABLE IF NOT EXISTS extra_fees(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name  VARCHAR(100),
    vehicle_type  VARCHAR(100),
    extra_fee FLOAT,
    lower_limit FLOAT,
    upper_limit FLOAT,
    weather_phenomenon  VARCHAR(100),
    is_forbidden BOOLEAN,
    status VARCHAR(100),
    created_date DATETIME2,
    PRIMARY KEY (id)
 );


