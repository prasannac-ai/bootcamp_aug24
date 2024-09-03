CREATE TABLE IF NOT EXISTS food_request (
    id UUID PRIMARY KEY,
    collector_id UUID NOT NULL,
    food_category VARCHAR(20),
    food_type VARCHAR(20),
    quantity INTEGER,
    request_time TIMESTAMP NOT NULL,
    fulfillment_time TIMESTAMP,
    -- Indicates the latest time by which the requested food is needed    location VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING' NOT NULL,
    CONSTRAINT food_category_check CHECK (
        food_category IS NULL
        OR food_category IN (
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
        food_type IS NULL
        OR food_type IN (
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
        status IN ('PENDING', 'MATCHED', 'FULFILLED', 'CANCELLED')
    )
);