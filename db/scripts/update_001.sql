CREATE TABLE IF NOT EXISTS tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP NOT NULL,
   done BOOLEAN
);