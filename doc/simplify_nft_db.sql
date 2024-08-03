CREATE TABLE collection
(
    `id`          BIGINT                              NOT NULL AUTO_INCREMENT,
    `member_name` VARCHAR(255)                        NOT NULL,
    `member_id`   BIGINT                              NOT NULL,
    `name`        VARCHAR(255)                        NOT NULL,
    `content`     VARCHAR(255)                        NOT NULL,
    `create_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `delete_flag` INT       DEFAULT 0                 NOT NULL,
    `delete_time` TIMESTAMP,
    `token_id`    VARCHAR(255),
    `hide_flag`   INT       DEFAULT 0                 NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE apply_record
(
    id               BIGINT              NOT NULL AUTO_INCREMENT,
    collection_id    BIGINT              NOT NULL,
    from_member_id   BIGINT              NOT NULL,
    apply_member_id  BIGINT              NOT NULL,
    create_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status           INT,
    delete_flag      INT       DEFAULT 0 NOT NULL,
    delete_time      TIMESTAMP,
    transaction_hash VARCHAR(255),
    type             INT,
    PRIMARY KEY (id)
);



CREATE TABLE hold_collection
(
    `id`              BIGINT              NOT NULL AUTO_INCREMENT,
    `member_id`       BIGINT,
    `collection_id`   BIGINT,
    `apply_record_id` BIGINT,
    `create_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `status`          INT       DEFAULT 0 NOT NULL,
    `hide_flag`       INT       DEFAULT 0 NOT NULL,
    `delete_flag`     INT       DEFAULT 0 NOT NULL,
    `delete_time`     TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE member
(
    `id`            BIGINT              NOT NULL AUTO_INCREMENT,
    `member_name`   VARCHAR(255)        NOT NULL,
    `nick_name`     VARCHAR(255),
    `create_at`     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `delete_flag`   INT       DEFAULT 0 NOT NULL,
    `delete_time`   TIMESTAMP,
    `password`      VARCHAR(255)        NOT NULL,
    `block_address` VARCHAR(255),
    `public_key`    VARCHAR(255),
    `private_key`   VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE manager
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT,
    `manager_name`  VARCHAR(255) NOT NULL,
    `password`      VARCHAR(255) NOT NULL,
    `block_address` VARCHAR(255),
    PRIMARY KEY (id)
);
