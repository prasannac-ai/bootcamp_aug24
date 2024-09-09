CREATE TABLE IF NOT EXISTS donors_rating (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    name VARCHAR(255),
    rating INTEGER
);