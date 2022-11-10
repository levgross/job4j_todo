CREATE TABLE IF NOT EXISTS task_category (
   id SERIAL PRIMARY KEY,
   task_id int REFERENCES tasks(id),
   category_id int REFERENCES categories(id)
);