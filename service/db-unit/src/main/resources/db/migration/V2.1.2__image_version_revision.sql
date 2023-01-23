-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: mysql:3306
-- Czas generowania: 26 Lis 2022, 10:57
-- Wersja serwera: 8.0.30
-- Wersja PHP: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `zwroty_e_strix_com`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product_image_version_revision`
--

-- DROP TABLE IF EXISTS `product_image_version_revision`;
CREATE TABLE `product_image_version_revision` (
  `id` bigint NOT NULL,
  `reason` varchar(4096) DEFAULT NULL,
  `version_date` varchar(255) DEFAULT NULL,
  `product_image_version_id` bigint DEFAULT NULL,
  `img_back` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `img_front` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `last_version_date` varchar(255) DEFAULT NULL,
  `img_Bottom` longtext,
  `img_left` longtext,
  `img_right` longtext,
  `img_top` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `product_image_version_revision`
--
ALTER TABLE `product_image_version_revision`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdd0kwyuv0jewcku3kaqu4ff6u` (`product_image_version_id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `product_image_version_revision`
--
ALTER TABLE `product_image_version_revision`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `product_image_version_revision`
--
ALTER TABLE `product_image_version_revision`
  ADD CONSTRAINT `FKdd0kwyuv0jewcku3kaqu4ff6u` FOREIGN KEY (`product_image_version_id`) REFERENCES `product_image_version` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
