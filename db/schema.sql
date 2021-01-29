CREATE TABLE type (
    id SERIAL PRIMARY KEY,
    name VARCHAR (2000) NOT NULL
);

CREATE TABLE accident (
    id SERIAL PRIMARY KEY,
    name VARCHAR (2000) NOT NULL,
    text VARCHAR (2000) NOT NULL,
    address VARCHAR (2000) NOT NULL,
    type_id INT NOT NULL,
    FOREIGN KEY (type_id) REFERENCES type (id)
);

CREATE TABLE rule (
    id SERIAL PRIMARY KEY,
    name VARCHAR (2000) NOT NULL
);

CREATE TABLE accident_rule (
    accident_id int NOT NULL,
    rule_id int NOT NULL,
    CONSTRAINT accident_rule_pkey PRIMARY KEY (accident_id, rule_id),
    FOREIGN KEY (accident_id) REFERENCES accident (id),
    FOREIGN KEY (rule_id) REFERENCES rule (id)
);

INSERT INTO type VALUES (1, 'Две машины');
INSERT INTO type VALUES (2, 'Машина и человек');
INSERT INTO type VALUES (3, 'Машина и велосипед');

INSERT INTO rule VALUES (1, 'Статья 1');
INSERT INTO rule VALUES (2, 'Статья 2');
INSERT INTO rule VALUES (3, 'Статья 3');
INSERT INTO rule VALUES (4, 'Статья 4');
INSERT INTO rule VALUES (5, 'Статья 5');