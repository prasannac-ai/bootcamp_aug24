-- Table to store user information
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY, -- Unique identifier for the user
    name VARCHAR(255) NOT NULL, -- Name of the user, improve it include first and last name
    email VARCHAR(255) NOT NULL UNIQUE, -- Email of the user, must be unique
    password VARCHAR(255) NOT NULL, -- Password for user authentication
    role VARCHAR(50) NOT NULL, -- Role of the user (Admin, Donor, Collector, Volunteer)
    mobile VARCHAR(20) NOT NULL UNIQUE, -- Mobile number of the user, must be unique
    address VARCHAR(255), -- Address of the user
    latitude DOUBLE PRECISION, -- Latitude for geolocation, Actually no need to two columns, it can be simplified further
    longitude DOUBLE PRECISION -- Longitude for geolocation
);

CREATE TABLE IF NOT EXISTS token_blacklist (
    id UUID DEFAULT gen_random_uuid () PRIMARY KEY,
    token VARCHAR(1024) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);