CREATE TABLE IF NOT EXISTS base_fees(
    id BIGINT NOT NULL AUTO_INCREMENT,
    city  VARCHAR(100),
    vehicle_type  VARCHAR(100),
    base_fee FLOAT,
    status VARCHAR(100),
    created_date DATETIME2,
    PRIMARY KEY (id)
);
