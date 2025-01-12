
ALTER TABLE `printer` ADD `last_update` varchar(255) DEFAULT NULL;
ALTER TABLE `print_file` ADD `printer` varchar(255) DEFAULT NULL;
