ALTER TABLE `product_image_version` ADD `_path` varchar(256) DEFAULT NULL;

ALTER TABLE `product_image_version_revision` ADD `img_front_desc` longtext;
ALTER TABLE `product_image_version_revision` ADD `img_back_desc` longtext;
ALTER TABLE `product_image_version_revision` ADD `img_left_desc` longtext;
ALTER TABLE `product_image_version_revision` ADD `img_right_desc` longtext;
ALTER TABLE `product_image_version_revision` ADD `img_top_desc` longtext;
ALTER TABLE `product_image_version_revision` ADD `img_bottom_desc` longtext;

