CREATE TABLE IF NOT EXISTS gift_certificates
(
    id               LONG PRIMARY KEY NOT NULL UNIQUE AUTO_INCREMENT,
    name             VARCHAR(45)      NOT NULL,
    description      VARCHAR(45)      NOT NULL,
    price            DECIMAL(3, 2)    NOT NULL,
    duration         INT              NOT NULL,
    create_date      TIMESTAMP        NOT NULL,
    last_update_date TIMESTAMP        NOT NULL
);


CREATE TABLE IF NOT EXISTS tag
(
    tag_id   LONG PRIMARY KEY NOT NULL UNIQUE AUTO_INCREMENT,
    tag_name VARCHAR(45)      NOT NULL
);

CREATE TABLE IF NOT EXISTS gift_tags
(
    id      LONG PRIMARY KEY NOT NULL UNIQUE AUTO_INCREMENT,
    gift_id INT              NOT NULL,
    tag_id  INT              NOT NULL
);