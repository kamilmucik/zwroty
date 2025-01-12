CREATE SCHEMA IF NOT EXISTS printer_schema;
SET SCHEMA printer_schema;


CREATE TABLE IF NOT EXISTS printer_setting
(
    ID IDENTITY NOT NULL PRIMARY KEY,
    setting_name  VARCHAR(255) NOT NULL,
    setting_description  VARCHAR(255) NOT NULL,
    setting_value VARCHAR(255) NOT NULL,
    PRIMARY KEY ( ID )
)
