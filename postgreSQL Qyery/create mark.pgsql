-- CREATE TABLE Mark (
--     id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
--     person_drug_id int REFERENCES Person_Drug(id) ON DELETE CASCADE,
--     taking_num int NOT NULL,
--     date DATE NOT NULL,
--     mark_num_per_day int NOT NULL,
--     mark varchar(9)
-- );

-- mark: да, нет, не помню
-- INSERT INTO mark(person_id, drug_id) 
--      VALUES (2, '2024-05-21');

select * from Mark;

-- DROP TABLE mark;
--DELETE FROM DrugMarks WHERE id=3;
UPDATE Mark SET mark = NULL WHERE id=1;
UPDATE Mark SET mark = NULL WHERE id=6;
-- UPDATE Mark SET mark = 'Не помню' WHERE id=3;
