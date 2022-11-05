ALTER TABLE tasks ADD COLUMN IF NOT EXISTS user_id INT;
ALTER TABLE tasks 
    ADD CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES todo_user (id);