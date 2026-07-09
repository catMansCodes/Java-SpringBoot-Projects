CREATE TABLE weather (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    city        VARCHAR(100) NOT NULL,
    details     VARCHAR(255) NOT NULL,
    temperature DOUBLE       NOT NULL,
    humidity    INT          NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_weather_city UNIQUE (city)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
