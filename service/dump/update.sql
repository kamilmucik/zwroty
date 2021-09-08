
-- 20210105: zwiekszenie rozmiaru pola
ALTER TABLE `shipment_product` CHANGE `ean` `ean` VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL;


-- 20210306: dodanie pola logowania poduktów
ALTER TABLE `shipment_product` ADD `scan_log` VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL;
ALTER TABLE `shipment_event` CHANGE `_description` `_description` VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL;


-- 20210322: dodanie wyjazdu
CREATE TABLE `release_article` (
  `id` bigint NOT NULL,
  `release_date` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `release_article_pallet` (
  `id` bigint NOT NULL,
  `release_article_id` bigint DEFAULT NULL,
  `release_code` varchar(50) DEFAULT NULL,
  `art_number` varchar(50) DEFAULT NULL,
  `counter` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




ALTER TABLE `release_article_pallet` ADD PRIMARY KEY (`id`), ADD KEY `FKmxd8tydhahlu5f4w8i7jik6j8` (`release_article_id`);
ALTER TABLE `release_article` MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE `release_article_pallet` MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE `release_article` ADD PRIMARY KEY (`id`);
ALTER TABLE `release_article_pallet` DROP FOREIGN KEY `FKi4i9jbay1a2jgpmwxskfi5r0d`;
ALTER TABLE `release_article_pallet` ADD constraint FKi4i9jbay1a2jgpmwxskfi5r0d foreign key (release_article_id) references release_article (id);

insert into release_article (id, release_date) values (1, '20210322');
insert into release_article (id, release_date) values (2, '20210323');
insert into release_article_pallet (id, release_article_id, release_code, art_number,counter) values (1, 1, '123', '321',234);
insert into release_article_pallet (id, release_article_id, release_code, art_number,counter) values (2, 1, '234', '432',234);
-- 003001517280117
-- http://localhost:8081/release/updateget/003001517200848

--20210419
ALTER TABLE `release_article_pallet` ADD `pallet_flag` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT "C";
ALTER TABLE `release_article_pallet` ADD `product_ean` VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT "";

--20210421
ALTER TABLE `release_article_pallet` ADD `pallet_counter` bigint DEFAULT 0;
ALTER TABLE `release_article_pallet` ADD `return_number` VARCHAR(10) NULL DEFAULT "";

--20210524
ALTER TABLE `shipment` ADD `temp_pallet` bigint DEFAULT 0;

--20210907
ALTER TABLE `shipment_product` ADD `scan_utilization` int(10) default '0';
