CREATE TABLE app.mails (
    id BIGSERIAL,
    email VARCHAR(256) NOT NULL,
    title VARCHAR(256) NOT NULL,
    body TEXT NOT NULL,
    create_at TIMESTAMP NOT NULL,
    status VARCHAR(16) NOT NULL,
    status_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id)

);
