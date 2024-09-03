CREATE TABLE IF NOT EXISTS food_announcement (
    id UUID PRIMARY KEY,
    donor_id UUID NOT NULL,
    food_category VARCHAR(20) NOT NULL,
    food_type VARCHAR(20) NOT NULL,
    quantity INTEGER NOT NULL,
    availability_time TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'AVAILABLE' NOT NULL,
    CONSTRAINT food_category_check CHECK (
        food_category IN (
            'COOKED',
            'VEGETABLES',
            'PROCESSED',
            'DAIRY',
            'BEVERAGES',
            'BAKERY',
            'ESSENTIALS'
        )
    ),
    CONSTRAINT food_type_check CHECK (
        food_type IN (
            'COOKED',
            'VEGETABLES',
            'PROCESSED',
            'DAIRY',
            'BEVERAGES',
            'BAKERY',
            'RICE',
            'GRAIN',
            'DAAL'
        )
    ),
    CONSTRAINT status_check CHECK (
        status IN ('AVAILABLE', 'MATCHED', 'FULFILLED', 'EXPIRED')
    )
);
-- CREATE INDEX idx_food_category ON food_announcement(food_category);
-- CREATE INDEX idx_food_type ON food_announcement(food_type);
-- CREATE INDEX idx_location ON food_announcement(location);
-- CREATE INDEX idx_availability_time ON food_announcement(availability_time);