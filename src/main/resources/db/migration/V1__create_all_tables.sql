CREATE TABLE IF NOT EXISTS "User" ( 
    id SERIAL PRIMARY KEY,
    user_name VARCHAR (50) UNIQUE,
    user_pass VARCHAR (20) NOT NULL
 );

CREATE TABLE IF NOT EXISTS Tweet ( 
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES "User"(id) ON DELETE CASCADE,
    tweet TEXT NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Media( 
    id SERIAL PRIMARY KEY,
    media_type VARCHAR(225),
    media_name VARCHAR(225),
    media_url TEXT
);
-- N : M
CREATE TABLE IF NOT EXISTS Tweet_reaction (
    react BOOLEAN,
    comment TEXT,
    user_id INT REFERENCES "User"(id) ON DELETE CASCADE,  
    id INT REFERENCES Tweet(id) ON DELETE CASCADE,
    CONSTRAINT react_id PRIMARY KEY (user_id, id)
);

CREATE TABLE Tweet_media (
    tweet_id INT REFERENCES Tweet(id),
    media_id INT REFERENCES media(id),
    PRIMARY KEY (tweet_id, media_id)
);

