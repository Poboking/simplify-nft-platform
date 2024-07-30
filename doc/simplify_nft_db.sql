CREATE TABLE `collection`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `token_id`    VARCHAR(255) NOT NULL,
    `member_name` VARCHAR(255) NOT NULL,
    `member_id`   BIGINT       NOT NULL,
    `name`        varchar(255) not null,
    `content`     VARCHAR(255) NOT NULL,
    `create_at`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `hide_flag`   INTEGER      NOT NULL DEFAULT 0,
    `delete_flag` INTEGER      NOT NULL DEFAULT 0,
    `delete_time` TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `member`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT,
    `member_name`   VARCHAR(255) NOT NULL,
    `block_address` VARCHAR(255) NOT NULL,
    `password`      VARCHAR(255) NOT NULL,
    `nick_name`     VARCHAR(255),
    `create_at`     TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    `delete_flag`   INTEGER      NOT NULL DEFAULT 0,
    `delete_time`   TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `apply_record`
(
    `id`                 BIGINT  NOT NULL AUTO_INCREMENT,
    `collection_id`      BIGINT  NOT NULL,
    `hold_collection_id` BIGINT  NOT NULL,
    `hold_member_id`     BIGINT  NOT NULL,
    `apply_member_id`    BIGINT  NOT NULL,
    `create_at`          TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    `status`             INTEGER,
    `delete_flag`        INTEGER NOT NULL DEFAULT 0,
    `delete_time`        TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `hold_collection`
(
    `id`                  BIGINT  NOT NULL AUTO_INCREMENT,
    `member_id`           BIGINT,
    `collection_id`       BIGINT,
    `apply_collection_id` BIGINT,
    `create_at`           TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    `status`              INTEGER NOT NULL DEFAULT 0,
    `hide_flag`           INTEGER NOT NULL DEFAULT 0,
    `delete_flag`         INTEGER NOT NULL DEFAULT 0,
    `delete_time`         TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `apply_collection`
(
    `id`                 BIGINT  NOT NULL AUTO_INCREMENT,
    `collection_id`      BIGINT  NOT NULL,
    `hold_collection_id` BIGINT  NOT NULL,
    `hold_member_id`     BIGINT  NOT NULL,
    `former_member_id`   BIGINT  NOT NULL,
    `type`               INTEGER,
    `create_at`          TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    `delete_flag`        INTEGER NOT NULL DEFAULT 0,
    `delete_time`        TIMESTAMP,
    PRIMARY KEY (`id`)
);


CREATE TABLE `manager`
(
    `id`            INTEGER      NOT NULL AUTO_INCREMENT,
    `block_address` VARCHAR(255) NOT NULL,
    `manager_name`  VARCHAR(255) NOT NULL,
    `password`      VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
