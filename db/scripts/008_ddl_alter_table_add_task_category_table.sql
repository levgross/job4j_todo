CREATE TABLE IF NOT EXISTS task_category (
   task_id int REFERENCES tasks(id),
   category_id int REFERENCES categories(id),
   PRIMARY KEY(task_id, category_id)
);