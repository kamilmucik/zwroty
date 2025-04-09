DROP TABLE IF EXISTS product_version_revision;
CREATE TABLE `product_version_revision` (
    `id` bigint NOT NULL,
    `product_version_id` bigint,
    `description` longtext,
    `img_path` varchar(4096) DEFAULT '',
    `is_main` bit(1) DEFAULT b'0',
    `hash_group` varchar(64) DEFAULT '',
    `last_update` varchar(255) DEFAULT NULL
) ENGINE=InnoDB;
ALTER TABLE `product_version_revision` CHANGE `id` `id` BIGINT NOT NULL AUTO_INCREMENT, add PRIMARY KEY (`id`);


DROP TABLE IF EXISTS product_version;
CREATE TABLE `product_version` (
    `id` bigint NOT NULL,
    `art_number` varchar(255) DEFAULT '',
    `title` varchar(255) DEFAULT '',
    `ean` varchar(4096) DEFAULT ''
) ENGINE=InnoDB;
ALTER TABLE `product_version` CHANGE `id` `id` BIGINT NOT NULL AUTO_INCREMENT, add PRIMARY KEY (`id`);

TRUNCATE TABLE product_version;
INSERT INTO product_version (id, art_number, ean, title) VALUES (1,'10203', ' 4000196934741 ', 't1');


TRUNCATE TABLE product_version_revision;
INSERT INTO product_version_revision (id, product_version_id, description, img_path, is_main, hash_group)
VALUES (1,1,'tył2', '10203_4000196934741/480852142.jpg', b'1','00000001');
INSERT INTO product_version_revision (id, product_version_id, description, img_path, is_main, hash_group)
VALUES (2,1,' opis 2', '10203_4000196934741/482174579.jpg', b'1','00000002');
INSERT INTO product_version_revision (id, product_version_id, description, img_path, is_main, hash_group)
VALUES (3,1, ' tył stary', '10203_4000196934741/480852142.jpg', b'0','00000001');
