ALTER TABLE user
    ADD reset_password_answer VARCHAR(255) NULL;

ALTER TABLE user
    ADD reset_password_question VARCHAR(255) NULL;