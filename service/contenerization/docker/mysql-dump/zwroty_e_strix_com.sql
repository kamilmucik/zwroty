-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: mysql:3306
-- Czas generowania: 10 Maj 2022, 18:40
-- Wersja serwera: 8.0.28
-- Wersja PHP: 8.0.15

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
-- Struktura tabeli dla tabeli `category`
--

CREATE TABLE `category` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_group_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `category_group`
--

CREATE TABLE `category_group` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `collector`
--

CREATE TABLE `collector` (
  `id` bigint NOT NULL,
  `number` varchar(50) NOT NULL,
  `session_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `collector`
--

INSERT INTO `collector` (`id`, `number`, `session_id`) VALUES
(1, '1', 'c829d174-c9d8-48bc-9ba5-46bf587b92e3'),
(2, '0', '8d8f862d-0892-416d-ae49-b5d255255ae8');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `printer`
--

CREATE TABLE `printer` (
  `id` bigint NOT NULL,
  `active` bit(1) NOT NULL,
  `is_default` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `print_file`
--

CREATE TABLE `print_file` (
  `id` bigint NOT NULL,
  `active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `release_article`
--

CREATE TABLE `release_article` (
  `id` bigint NOT NULL,
  `release_date` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `release_article_pallet`
--

CREATE TABLE `release_article_pallet` (
  `id` bigint NOT NULL,
  `release_article_id` bigint DEFAULT NULL,
  `release_code` varchar(50) DEFAULT NULL,
  `art_number` varchar(50) DEFAULT NULL,
  `counter` bigint DEFAULT NULL,
  `pallet_counter` bigint DEFAULT NULL,
  `pallet_flag` varchar(10) DEFAULT NULL,
  `product_ean` varchar(4096) DEFAULT NULL,
  `return_number` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `setting`
--

CREATE TABLE `setting` (
  `id` bigint NOT NULL,
  `name` varchar(250) DEFAULT NULL,
  `value` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `setting`
--

INSERT INTO `setting` (`id`, `name`, `value`) VALUES
(1, 'path', 'C://temp/'),
(2, 'hour', '02:00'),
(3, 'doBackup', '1'),
(4, 'tempDirectory', 'C:/temp/');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shipment`
--

CREATE TABLE `shipment` (
  `id` bigint NOT NULL,
  `active` bit(1) NOT NULL,
  `_groups` int NOT NULL,
  `number` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `shipment`
--

INSERT INTO `shipment` (`id`, `active`, `_groups`, `number`) VALUES
(7, b'1', 1, '200210'),
(15, b'1', 0, '200269');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shipment_event`
--

CREATE TABLE `shipment_event` (
  `id` bigint NOT NULL,
  `collector_id` bigint DEFAULT NULL,
  `_description` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_update` varchar(255) DEFAULT NULL,
  `shipment_number` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `shipment_event`
--

INSERT INTO `shipment_event` (`id`, `collector_id`, `_description`, `last_update`, `shipment_number`, `user_name`) VALUES
(1, 1, 'Pobiera dane do kolektora.', '202006031603', '200002', 'Kolektor:1'),
(2, 1, 'Pobiera dane do kolektora.', '202006032018', '200002', 'Kolektor:1'),
(3, 1, 'Pobiera dane do kolektora.', '202006032023', '200002', 'Kolektor:1'),
(4, 1, 'Pobiera dane do kolektora.', '202006032155', '200002', 'Kolektor:1'),
(5, 1, 'Pobiera dane do kolektora.', '202006032156', '200002', 'Kolektor:1'),
(6, 1, 'Pobiera dane do kolektora.', '202006032158', '200002', 'Kolektor:1'),
(7, 1, 'Pobiera dane do kolektora.', '202006040020', '200002', 'Kolektor:1'),
(8, 1, 'Pobiera dane do kolektora.', '202006040021', '200195', 'Kolektor:1'),
(9, 1, 'Pobiera dane do kolektora.', '202006040023', '200002', 'Kolektor:1'),
(10, 1, 'Pobiera dane do kolektora.', '202006040025', '200002', 'Kolektor:1'),
(11, 1, 'Pobiera dane do kolektora.', '202006040033', '200002', 'Kolektor:1'),
(12, 1, 'Pobiera dane do kolektora.', '202006040034', '200002', 'Kolektor:1'),
(13, 1, 'Pobiera dane do kolektora.', '202006040036', '200002', 'Kolektor:1'),
(14, 1, 'Pobiera dane do kolektora.', '202006040037', '200002', 'Kolektor:1'),
(15, 1, 'Pobiera dane do kolektora.', '202006040038', '200002', 'Kolektor:1'),
(16, 1, 'Pobiera dane do kolektora.', '202006040042', '200002', 'Kolektor:1'),
(17, 1, 'Pobiera dane do kolektora.', '202006040043', '200002', 'Kolektor:1'),
(18, 1, 'Pobiera dane do kolektora.', '202006040057', '200002', 'Kolektor:1'),
(19, 1, 'Pobiera dane do kolektora.', '202006040059', '200002', 'Kolektor:1'),
(20, 1, 'Pobiera dane do kolektora.', '202006040102', '200002', 'Kolektor:1'),
(21, 1, 'Pobiera dane do kolektora.', '202006040105', '200002', 'Kolektor:1'),
(22, 1, 'Pobiera dane do kolektora.', '202006040108', '200002', 'Kolektor:1'),
(23, 1, 'Pobiera dane do kolektora.', '202006040111', '200002', 'Kolektor:1'),
(24, 1, 'Pobiera dane do kolektora.', '202006040118', '200002', 'Kolektor:1'),
(25, 1, 'Pobiera dane do kolektora.', '202006040121', '200002', 'Kolektor:1'),
(26, 1, 'Pobiera dane do kolektora.', '202006040121', '200002', 'Kolektor:1'),
(27, 1, 'Pobiera dane do kolektora.', '202006040126', '200002', 'Kolektor:1'),
(28, 1, 'Pobiera dane do kolektora.', '202006040131', '200195', 'Kolektor:1'),
(29, 1, 'Pobiera dane do kolektora.', '202006040131', '200208', 'Kolektor:1'),
(30, 1, 'Pobiera dane do kolektora.', '202006040133', '200003', 'Kolektor:1'),
(31, 1, 'Pobiera dane do kolektora.', '202006040138', '200002', 'Kolektor:1'),
(32, 1, 'Pobiera dane do kolektora.', '202006040140', '200002', 'Kolektor:1'),
(33, 1, 'Pobiera dane do kolektora.', '202006040142', '200002', 'Kolektor:1'),
(34, 1, 'Pobiera dane do kolektora.', '202006040146', '200002', 'Kolektor:1'),
(35, 1, 'Pobiera dane do kolektora.', '202006040152', '200002', 'Kolektor:1'),
(36, 1, 'Pobiera dane do kolektora.', '202006041130', '200210', 'Kolektor:1'),
(37, 1, 'Pobiera dane do kolektora.', '202006041131', '200210', 'Kolektor:1'),
(38, 1, 'Pobiera dane do kolektora.', '202006041132', '200210', 'Kolektor:1'),
(39, 1, 'Pobiera dane do kolektora.', '202006041133', '200003', 'Kolektor:1'),
(40, 1, 'Pobiera dane do kolektora.', '202006041136', '200210', 'Kolektor:1'),
(41, 1, 'Pobiera dane do kolektora.', '202006041136', '200210', 'Kolektor:1'),
(42, 1, 'Pobiera dane do kolektora.', '202006041140', '200210', 'Kolektor:1'),
(43, 1, 'Pobiera dane do kolektora.', '202009251310', '200269', 'Kolektor:1'),
(44, 1, 'Pobiera dane do kolektora.', '202009251310', '200269', 'Kolektor:1'),
(45, 0, 'Pobiera dane do kolektora.', '202009251310', '200269', 'Kolektor:0'),
(46, 1, 'Pobiera dane do kolektora.', '202009251312', '200269', 'Kolektor:1'),
(47, 1, 'Pobiera dane do kolektora.', '202009251315', '200210', 'Kolektor:1'),
(48, 1, 'Pobiera dane do kolektora.', '202009251317', '200210', 'Kolektor:1'),
(49, 1, 'Pobiera dane do kolektora.', '202009251319', '200210', 'Kolektor:1'),
(50, 1, 'Pobiera dane do kolektora.', '202009251321', '200210', 'Kolektor:1'),
(51, 1, 'Pobiera dane do kolektora.', '202009251322', '200210', 'Kolektor:1'),
(52, 1, 'Pobiera dane do kolektora.', '202009251325', '200210', 'Kolektor:1'),
(53, 1, 'Pobiera dane do kolektora.', '202009251327', '200210', 'Kolektor:1'),
(54, 1, 'Pobiera dane do kolektora.', '202009251329', '200210', 'Kolektor:1'),
(55, 1, 'Pobiera dane do kolektora.', '202009251331', '200210', 'Kolektor:1'),
(56, 1, 'Pobiera dane do kolektora.', '202009251333', '200210', 'Kolektor:1'),
(57, 1, 'Pobiera dane do kolektora.', '202009251337', '200210', 'Kolektor:1'),
(58, 1, 'Pobiera dane do kolektora.', '202009251345', '200210', 'Kolektor:1'),
(59, 1, 'Pobiera dane do kolektora.', '202009251345', '200210', 'Kolektor:1'),
(60, 1, 'Pobiera dane do kolektora.', '202009251346', '200210', 'Kolektor:1'),
(61, 1, 'Pobiera dane do kolektora.', '202009251347', '200269', 'Kolektor:1'),
(62, 1, 'Pobiera dane do kolektora.', '202009251348', '200269', 'Kolektor:1'),
(63, 1, 'Pobiera dane do kolektora.', '202009251358', '200269', 'Kolektor:1'),
(64, 1, 'Pobiera dane do kolektora.', '202009251358', '200269', 'Kolektor:1'),
(65, 1, 'Pobiera dane do kolektora.', '202009251402', '200269', 'Kolektor:1'),
(66, 1, 'Pobiera dane do kolektora.', '202009251402', '200269', 'Kolektor:1'),
(67, 1, 'Pobiera dane do kolektora.', '202009251402', '200269', 'Kolektor:1'),
(68, 1, 'Pobiera dane do kolektora.', '202009251403', '200269', 'Kolektor:1'),
(69, 1, 'Pobiera dane do kolektora.', '202009251403', '200269', 'Kolektor:1'),
(70, 1, 'Pobiera dane do kolektora.', '202009251405', '200269', 'Kolektor:1'),
(71, 1, 'Pobiera dane do kolektora.', '202009251405', '200269', 'Kolektor:1'),
(72, 1, 'Pobiera dane do kolektora.', '202009251405', '200269', 'Kolektor:1'),
(73, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(74, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(75, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(76, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(77, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(78, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(79, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(80, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(81, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(82, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(83, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(84, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(85, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(86, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(87, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(88, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(89, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(90, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(91, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(92, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(93, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(94, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(95, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(96, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(97, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(98, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(99, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(100, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(101, 1, 'Pobiera dane do kolektora.', '202009251408', '200269', 'Kolektor:1'),
(102, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(103, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(104, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(105, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(106, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(107, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(108, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(109, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(110, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(111, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(112, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(113, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(114, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(115, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(116, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(117, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(118, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(119, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(120, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(121, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(122, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(123, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(124, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(125, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(126, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(127, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(128, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(129, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(130, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(131, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(132, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(133, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(134, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(135, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(136, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(137, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(138, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(139, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(140, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(141, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(142, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(143, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(144, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(145, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(146, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(147, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(148, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(149, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(150, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(151, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(152, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(153, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(154, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(155, 1, 'Pobiera dane do kolektora.', '202009251409', '200269', 'Kolektor:1'),
(156, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(157, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(158, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(159, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(160, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(161, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(162, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(163, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(164, 1, 'Pobiera dane do kolektora.', '202009251410', '200269', 'Kolektor:1'),
(165, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(166, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(167, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(168, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(169, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(170, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(171, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(172, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(173, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(174, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(175, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(176, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(177, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(178, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(179, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(180, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(181, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(182, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(183, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(184, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(185, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(186, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(187, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(188, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(189, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(190, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(191, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(192, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(193, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(194, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(195, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(196, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(197, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(198, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(199, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(200, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(201, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(202, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(203, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(204, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(205, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(206, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(207, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(208, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(209, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(210, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(211, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(212, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(213, 1, 'Pobiera dane do kolektora.', '202009251411', '200269', 'Kolektor:1'),
(214, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(215, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(216, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(217, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(218, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(219, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(220, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(221, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(222, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(223, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(224, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(225, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(226, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(227, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(228, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(229, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(230, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(231, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(232, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(233, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(234, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(235, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(236, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(237, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(238, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(239, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(240, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(241, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(242, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(243, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(244, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(245, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(246, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(247, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(248, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(249, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(250, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(251, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(252, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(253, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(254, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(255, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(256, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(257, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(258, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(259, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(260, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(261, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(262, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(263, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(264, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(265, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(266, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(267, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(268, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(269, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(270, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(271, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(272, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(273, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(274, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(275, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(276, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(277, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(278, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(279, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(280, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(281, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(282, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(283, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(284, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(285, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(286, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(287, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(288, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(289, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(290, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(291, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(292, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(293, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(294, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(295, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(296, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(297, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(298, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(299, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(300, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(301, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(302, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(303, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(304, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(305, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(306, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(307, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(308, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(309, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(310, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(311, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(312, 1, 'Pobiera dane do kolektora.', '202009251412', '200269', 'Kolektor:1'),
(313, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(314, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(315, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(316, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(317, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(318, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(319, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(320, 1, 'Pobiera dane do kolektora.', '202009251417', '200269', 'Kolektor:1'),
(321, 1, 'Pobiera dane do kolektora.', '202009251418', '200269', 'Kolektor:1'),
(322, 1, 'Pobiera dane do kolektora.', '202009251418', '200269', 'Kolektor:1'),
(323, 1, 'Pobiera dane do kolektora.', '202009251418', '200269', 'Kolektor:1'),
(324, 1, 'Pobiera dane do kolektora.', '202009251418', '200269', 'Kolektor:1'),
(325, 1, 'Pobiera dane do kolektora.', '202009251418', '200269', 'Kolektor:1'),
(326, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(327, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(328, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(329, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(330, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(331, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(332, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(333, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(334, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(335, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(336, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(337, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(338, 1, 'Pobiera dane do kolektora.', '202009251440', '200269', 'Kolektor:1'),
(339, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(340, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(341, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(342, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(343, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(344, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(345, 1, 'Pobiera dane do kolektora.', '202009251548', '200269', 'Kolektor:1'),
(346, 1, 'Pobiera dane do kolektora.', '202009251549', '200269', 'Kolektor:1'),
(347, 1, 'Pobiera dane do kolektora.', '202009251549', '200269', 'Kolektor:1'),
(348, 1, 'Pobiera dane do kolektora.', '202009251549', '200269', 'Kolektor:1'),
(349, 1, 'Pobiera dane do kolektora.', '202009251549', '200269', 'Kolektor:1'),
(350, 1, 'Pobiera dane do kolektora.', '202009251549', '200269', 'Kolektor:1'),
(351, 1, 'Pobiera dane do kolektora.', '202009251549', '200269', 'Kolektor:1'),
(352, 1, 'Pobiera dane do kolektora.', '202009251814', '200269', 'Kolektor:1'),
(353, 1, 'Pobiera dane do kolektora.', '202009251814', '200269', 'Kolektor:1'),
(354, 1, 'Pobiera dane do kolektora.', '202009251814', '200269', 'Kolektor:1'),
(355, 1, 'Pobiera dane do kolektora.', '202009251814', '200269', 'Kolektor:1'),
(356, 1, 'Pobiera dane do kolektora.', '202009251814', '200269', 'Kolektor:1'),
(357, 1, 'Pobiera dane do kolektora.', '202009251815', '200269', 'Kolektor:1'),
(358, 1, 'Pobiera dane do kolektora.', '202009251815', '200269', 'Kolektor:1'),
(359, 1, 'Pobiera dane do kolektora.', '202009251815', '200269', 'Kolektor:1'),
(360, 1, 'Pobiera dane do kolektora.', '202009251815', '200269', 'Kolektor:1'),
(361, 1, 'Pobiera dane do kolektora.', '202009251830', '200269', 'Kolektor:1'),
(362, 1, 'Pobiera dane do kolektora.', '202009251830', '200269', 'Kolektor:1'),
(363, 1, 'Pobiera dane do kolektora.', '202009251830', '200269', 'Kolektor:1'),
(364, 1, 'Pobiera dane do kolektora.', '202009251830', '200269', 'Kolektor:1'),
(365, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(366, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(367, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(368, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(369, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(370, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(371, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(372, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(373, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(374, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(375, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(376, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(377, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(378, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(379, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(380, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(381, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(382, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(383, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(384, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(385, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(386, 1, 'Pobiera dane do kolektora.', '202009251831', '200269', 'Kolektor:1'),
(387, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(388, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(389, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(390, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(391, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(392, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(393, 1, 'Pobiera dane do kolektora.', '202009251846', '200269', 'Kolektor:1'),
(394, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(395, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(396, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(397, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(398, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(399, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(400, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(401, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(402, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(403, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(404, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(405, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(406, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(407, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(408, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(409, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(410, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(411, 1, 'Pobiera dane do kolektora.', '202009251847', '200269', 'Kolektor:1'),
(412, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(413, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(414, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(415, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(416, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(417, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(418, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(419, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(420, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(421, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(422, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(423, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(424, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(425, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(426, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(427, 1, 'Pobiera dane do kolektora.', '202009251906', '200269', 'Kolektor:1'),
(428, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(429, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(430, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(431, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(432, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(433, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(434, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(435, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(436, 1, 'Pobiera dane do kolektora.', '202009251907', '200269', 'Kolektor:1'),
(437, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(438, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(439, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(440, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(441, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(442, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(443, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(444, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(445, 1, 'Pobiera dane do kolektora.', '202009251921', '200269', 'Kolektor:1'),
(446, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(447, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(448, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(449, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(450, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(451, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(452, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(453, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(454, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(455, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(456, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(457, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(458, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(459, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(460, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(461, 1, 'Pobiera dane do kolektora.', '202009251922', '200269', 'Kolektor:1'),
(462, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(463, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(464, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(465, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(466, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(467, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(468, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(469, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(470, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(471, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(472, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(473, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(474, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(475, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(476, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(477, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(478, 1, 'Pobiera dane do kolektora.', '202009252008', '200269', 'Kolektor:1'),
(479, 1, 'Pobiera dane do kolektora.', '202009252022', '200269', 'Kolektor:1'),
(480, 1, 'Pobiera dane do kolektora.', '202009252022', '200269', 'Kolektor:1'),
(481, 1, 'Pobiera dane do kolektora.', '202009252022', '200269', 'Kolektor:1'),
(482, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(483, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(484, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(485, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(486, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(487, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(488, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(489, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(490, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(491, 1, 'Pobiera dane do kolektora.', '202009252023', '200269', 'Kolektor:1'),
(492, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(493, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(494, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(495, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(496, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(497, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(498, 1, 'Pobiera dane do kolektora.', '202009251956', '200269', 'Kolektor:1'),
(499, 1, 'Pobiera dane do kolektora.', '202009251957', '200269', 'Kolektor:1'),
(500, 1, 'Pobiera dane do kolektora.', '202009251957', '200269', 'Kolektor:1'),
(501, 1, 'Pobiera dane do kolektora.', '202009251957', '200269', 'Kolektor:1'),
(502, 1, 'Pobiera dane do kolektora.', '202009251957', '200269', 'Kolektor:1'),
(503, 1, 'Pobiera dane do kolektora.', '202009251957', '200269', 'Kolektor:1'),
(504, 1, 'Pobiera dane do kolektora.', '202009251957', '200269', 'Kolektor:1'),
(505, 0, 'Edytuje ilosci sztuk. Dobre (0), uszkodzone (0) : Lovely lakier Classic Nail Polish 346, 8ml.', '202009252011', '200269', 'admin@estrix.pl'),
(506, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(507, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(508, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(509, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(510, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(511, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(512, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(513, 1, 'Pobiera dane do kolektora.', '202009252028', '200269', 'Kolektor:1'),
(514, 1, 'Pobiera dane do kolektora.', '202009252029', '200269', 'Kolektor:1'),
(515, 1, 'Pobiera dane do kolektora.', '202009252029', '200269', 'Kolektor:1'),
(516, 1, 'Pobiera dane do kolektora.', '202009252029', '200269', 'Kolektor:1'),
(517, 1, 'Pobiera dane do kolektora.', '202009252029', '200269', 'Kolektor:1'),
(518, 1, 'Pobiera dane do kolektora.', '202009252029', '200269', 'Kolektor:1'),
(519, 1, 'Pobiera dane do kolektora.', '202009252134', '200269', 'Kolektor:1'),
(520, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(521, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(522, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(523, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(524, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(525, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(526, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(527, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(528, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(529, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(530, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(531, 1, 'Pobiera dane do kolektora.', '202009252135', '200269', 'Kolektor:1'),
(532, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(533, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(534, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(535, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(536, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(537, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(538, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(539, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(540, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(541, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(542, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(543, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(544, 1, 'Pobiera dane do kolektora.', '202009252148', '200269', 'Kolektor:1'),
(545, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(546, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(547, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(548, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(549, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(550, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(551, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(552, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(553, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(554, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(555, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(556, 1, 'Pobiera dane do kolektora.', '202009252224', '200269', 'Kolektor:1'),
(557, 1, 'Pobiera dane do kolektora.', '202009252225', '200269', 'Kolektor:1'),
(558, 1, 'Pobiera dane do kolektora.', '202009252245', '200269', 'Kolektor:1'),
(559, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(560, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(561, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(562, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(563, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(564, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(565, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(566, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(567, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(568, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(569, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(570, 1, 'Pobiera dane do kolektora.', '202009252246', '200269', 'Kolektor:1'),
(571, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(572, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(573, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(574, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(575, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(576, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(577, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(578, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(579, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(580, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(581, 1, 'Pobiera dane do kolektora.', '202009252259', '200269', 'Kolektor:1'),
(582, 1, 'Pobiera dane do kolektora.', '202009252300', '200269', 'Kolektor:1'),
(583, 1, 'Pobiera dane do kolektora.', '202009252300', '200269', 'Kolektor:1'),
(584, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(585, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(586, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(587, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(588, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(589, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(590, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(591, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(592, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(593, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0'),
(594, 0, 'Pobiera dane do kolektora.', '202009252314', '200269', 'Kolektor:0');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shipment_product`
--

CREATE TABLE `shipment_product` (
  `id` bigint NOT NULL,
  `art_number` bigint DEFAULT NULL,
  `art_return` varchar(50) DEFAULT NULL,
  `art_volume` double DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `counter` bigint DEFAULT NULL,
  `ean` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `last_update` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `scan_correct` int DEFAULT '0',
  `scan_error` int DEFAULT '0',
  `scan_label` int DEFAULT '0',
  `shipment_id` bigint DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `scan_log` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `scan_utilization` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `shipment_product`
--

INSERT INTO `shipment_product` (`id`, `art_number`, `art_return`, `art_volume`, `company`, `counter`, `ean`, `last_update`, `name`, `scan_correct`, `scan_error`, `scan_label`, `shipment_id`, `weight`, `scan_log`, `scan_utilization`) VALUES
(365, 124161, '200210', NULL, 'Dax Cosmetics', 23932, ' 0000000000000 ', '202006041119', 'Dax wulkaniczny peeling + maska do stóp 2x6ml', 0, 0, 0, 7, 0.0164, NULL, 0),
(366, 316418, '200210', NULL, 'BE BIO ACTIVE COSMETIQS MIND NETWORK Sp.zo.o.', 1268, ' 0000000000000 ', '202006041119', 'beBio zel p/p spirulina&chlorella 200 ml', 0, 0, 0, 7, 0.231, NULL, 0),
(367, 123401, '200210', NULL, 'Titania Fabrik GmbH', 2972, ' 0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl Puder do stóp 100g', 0, 0, 0, 7, 0.1581, NULL, 0),
(368, 331532, '200210', NULL, 'Eris', 535, ' 0000000000000 ', '202006041119', 'Lirene Eco Power maska łagodząca 32 g', 0, 0, 0, 7, 0.04, NULL, 0),
(369, 293134, '200210', NULL, 'Inter-Vion SA', 1737, ' 0000000000000 ', '202006041119', 'Glamour breloczek kot złoty puszek 1szt.', 0, 0, 0, 7, 0.0115, NULL, 0),
(370, 318478, '200210', NULL, 'Eris', 763, ' 0000000000000 ', '202006041119', 'Under Twenty maska na flizelinie nawilzająca 15 g', 0, 0, 0, 7, 0.023, NULL, 0),
(371, 331535, '200210', NULL, 'Eris', 572, ' 0000000000000 ', '202006041119', 'Lirene Organic przeciwzmarszczkowa 32 g', 0, 0, 0, 7, 0.04, NULL, 0),
(372, 318479, '200210', NULL, 'Eris', 1440, ' 0000000000000 ', '202006041119', 'Under Twenty maska na flizelinie oczyszczająca 15g', 0, 0, 0, 7, 0.023, NULL, 0),
(373, 172300, '200210', NULL, 'Reckitt Benckiser Healthcare sp. z o.o.', 5475, ' 0000000000000 ', '202006041119', 'Scholl krem zmiękczający twardą skórę stóp 60 ml', 0, 0, 0, 7, 0.0875, NULL, 0),
(374, 331530, '200210', NULL, 'Eris', 1624, ' 0000000000000 ', '202006041119', 'Lirene Eco Power maska normalizująca 32 g', 0, 0, 0, 7, 0.03, NULL, 0),
(375, 258323, '200210', NULL, 'Unilever Polska Sp. z o.o.', 1465, ' 0000000000000 ', '202006041119', 'Dove Baby szampon Rich Moisture 400ml', 0, 0, 0, 7, 0.45, NULL, 0),
(376, 312598, '200210', NULL, 'LOreal Polska', 725, ' 0000000000000 ', '202006041119', 'Garnier maska Fresh-Mix Vit C Shot 32 g', 0, 0, 0, 7, 0.04, NULL, 0),
(377, 226071, '200210', NULL, 'Colgate - Palmolive sp z.o.o.', 1294, ' 0000000000000 ', '202006041119', 'Colgate MWO Expert White Soft Mint SHINE 75ml', 0, 0, 0, 7, 0.1256, NULL, 0),
(378, 31253, '200210', NULL, 'BURNUS Polska Sp. z O.O.', 589, ' 0000000000000  0000000000000 ', '202006041119', 'Kamill krem d/rąk i paznokci intensiv 100ml', 0, 0, 0, 7, 0.1493, NULL, 0),
(379, 263710, '200210', NULL, 'Pharma C Food', 200, ' 0000000000000 ', '202006041119', 'Venus Nature Multipeeling d/stóp 3w1 250 ml', 0, 0, 0, 7, 0.312, NULL, 0),
(380, 287000, '200210', NULL, 'Pharma C Food', 5698, ' 0000000000000 ', '202006041119', 'Cztery P.R.peeling do skórek 15ml', 0, 0, 0, 7, 0.0276, NULL, 0),
(381, 327193, '200210', NULL, 'Dax Cosmetics', 4636, ' 0000000000000 ', '202006041119', 'Perfecta Extra Oils krem olejek d/rąk i pazn.195ml', 0, 0, 0, 7, 0.2265, NULL, 0),
(382, 313632, '200210', NULL, 'JOTA s.c,', 3084, ' 0000000000000 ', '202006041119', 'Dermaglin Anti-Acne Men maska do c.trądzikowej 20g', 0, 0, 0, 7, 0.0249, NULL, 0),
(383, 313633, '200210', NULL, 'JOTA s.c,', 3207, ' 0000000000000 ', '202006041119', 'Dermaglin Clear Men maska oczyszczająca 20 g', 0, 0, 0, 7, 0.0244, NULL, 0),
(384, 263714, '200210', NULL, 'Pharma C Food', 270, ' 0000000000000 ', '202006041119', 'Venus Nature Maska int reg d/stóp 75 ml + 2 skarp', 0, 0, 0, 7, 0.119, NULL, 0),
(385, 313634, '200210', NULL, 'JOTA s.c,', 3221, ' 0000000000000 ', '202006041119', 'Dermaglin Energy Men maska p-starzeniowa 20 g', 0, 0, 0, 7, 0.0245, NULL, 0),
(386, 260906, '200210', NULL, 'Dax Cosmetics Hadalabo', 2678, ' 0000000000000 ', '202006041119', 'HadaLabo White Maska nawilzajaca płat ', 0, 0, 0, 7, 0.0255, NULL, 0),
(387, 73263, '200210', NULL, 'RB (Hygiene Home) Poland Sp.z o.o.', 1206, ' 0000000000000  0000000000000 ', '202006041119', 'Air Wick elek. spacer po rajski plaży 19 ml zapas', 0, 0, 0, 7, 0.03, NULL, 0),
(388, 111151, '200210', NULL, 'Rudolf Lenhart GmbH & Co.KG', 3824, ' 0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Isana krem rumiankowy do rąk 100ml', 0, 0, 0, 7, 0.1137, NULL, 0),
(389, 226861, '200210', NULL, 'Oceanic S.A Dermokosmetyki', 623, ' 0000000000000 ', '202006041119', 'AA help cera atopowa krem-kompres SOS 40 ml', 0, 0, 0, 7, 0.0677, NULL, 0),
(390, 219699, '200210', NULL, 'Inter-Vion SA', 2081, ' 0000000000000 ', '202006041119', 'Killys utwardzacz do paznokci bio2 10ml', 0, 0, 0, 7, 0.0528, NULL, 0),
(391, 183859, '200210', NULL, 'Capeypharma Sp.zo.o. Społka komandytowa', 1647, ' 0000000000000 ', '202006041119', 'Babycap higiena uszka maluszka 15ml', 0, 0, 0, 7, 0.04, NULL, 0),
(392, 218161, '200210', NULL, 'Inter-Vion SA', 2412, ' 0000000000000  0000000000000 ', '202006041119', 'Killys ekspresowy wysuszacz lakieru 10 ml', 0, 0, 0, 7, 0.059, NULL, 0),
(393, 111157, '200210', NULL, 'Rudolf Lenhart GmbH & Co.KG', 3702, ' 0000000000000  0000000000000  0000000000000 ', '202006041119', 'Isana krem do rąk rumiankowy słoik 150ml', 0, 0, 0, 7, 0.183, NULL, 0),
(394, 183860, '200210', NULL, 'Capeypharma Sp.zo.o. Społka komandytowa', 529, ' 0000000000000 ', '202006041119', 'Babycap spray na ciemieniuchę 30ml', 0, 0, 0, 7, 0.042, NULL, 0),
(395, 247861, '200210', NULL, 'Swit Spółdzielnia', 1793, ' 0000000000000 ', '202006041119', 'Exclusive SPA skarpetki złuszczające 1szt.', 0, 0, 0, 7, 0.06, NULL, 0),
(396, 318270, '200210', NULL, 'Pharma C Food', 2648, ' 0000000000000 ', '202006041119', 'Venus Oleokrem do golenia 125ml', 0, 0, 0, 7, 0.1433, NULL, 0),
(397, 284985, '200210', NULL, 'Procter & Gamble ZEBY', 1160, ' 0000000000000 ', '202006041119', 'BAM Pasta d/zębów 3DW Therapy Sens 75 ml', 0, 0, 0, 7, 0.129, NULL, 0),
(398, 284986, '200210', NULL, 'Procter & Gamble ZEBY', 1767, ' 0000000000000 ', '202006041119', 'BAM Pasta d/zębów 3DW Therapy Enml 75 ml', 0, 0, 0, 7, 0.129, NULL, 0),
(399, 284996, '200210', NULL, 'Novamed Sp z o.o.', 23, ' 0000000000000 ', '202006041119', 'Vitammy RUBY podręczna szczoteczka soniczna ', 0, 0, 0, 7, 0.09, NULL, 0),
(400, 198976, '200210', NULL, 'Capeypharma Sp.zo.o. Społka komandytowa', 603, ' 0000000000000 ', '202006041119', 'Babycap spray woda morska do nosa 30 ml', 0, 0, 0, 7, 0.053, NULL, 0),
(401, 315970, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 2529, ' 0000000000000 ', '202006041119', 'Eveline odżywka proteinowa do paznokci 12ml', 0, 0, 0, 7, 0.059, NULL, 0),
(402, 217675, '200210', NULL, 'RB (Hygiene Home) Poland Sp.z o.o.', 2109, ' 0000000000000 ', '202006041119', 'Air Wick elektr. śwież lasów amazonii zapas 19ml', 0, 0, 0, 7, 0.0999, NULL, 0),
(403, 287053, '200210', NULL, 'NIVEA POLSKA SA', 3466, ' 0000000000000 ', '202006041119', 'Nivea Baby Micelarny zel do ciała 500ml', 0, 0, 0, 7, 0.57, NULL, 0),
(404, 271182, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 2137, ' 0000000000000 ', '202006041119', 'Eveline nail ther Nail Cement 12ml', 0, 0, 0, 7, 0.06, NULL, 0),
(405, 284495, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 2074, ' 0000000000000 ', '202006041119', 'Eveline Prof.odtłuszczacz do lakieru hybryd.150ml', 0, 0, 0, 7, 0.1566, NULL, 0),
(406, 285000, '200210', NULL, 'Novamed Sp z o.o.', 23, ' 0000000000000 ', '202006041119', 'Vitammy RUBY końc do szczoteczki sonicznej 4szt', 0, 0, 0, 7, 0.03, NULL, 0),
(407, 260941, '200210', NULL, 'Dax Cosmetics Hadalabo', 2770, ' 0000000000000 ', '202006041119', 'HadaLabo Red Maska p-zm w płacie 20 ml', 0, 0, 0, 7, 0.0292, NULL, 0),
(408, 261715, '200210', NULL, 'Novamed Sp z o.o.', 12, ' 0000000000000 ', '202006041119', 'Novamed Vitammy WAVE końc do irygatora 4szt', 0, 0, 0, 7, 0.026, NULL, 0),
(409, 261714, '200210', NULL, 'Novamed Sp z o.o.', 39, ' 0000000000000 ', '202006041119', 'Novamed Vitammy WAVE irygator', 0, 0, 0, 7, 0.454, NULL, 0),
(410, 32851, '200210', NULL, 'LOreal Polska', 41, ' 0000000000000 ', '202006041119', 'Garnier IPS krem suche strefy tuba 100ml', 0, 0, 0, 7, 0.1348, NULL, 0),
(411, 282199, '200210', NULL, 'Johnson&Johnson Poland Sp. z o.o.', 1590, ' 0000000000000 ', '202006041119', 'Penaten Ultra Sensitiv Płyn d/ciała i włosów 400ml', 0, 0, 0, 7, 0.4637, NULL, 0),
(412, 265297, '200210', NULL, 'Colgate - Palmolive sp z.o.o.', 411, ' 0000000000000 ', '202006041119', 'Lady Speed Stick Breath of f dezodorant w zelu 65g', 0, 0, 0, 7, 0.1066, NULL, 0),
(413, 282194, '200210', NULL, 'Oceanic S.A', 2288, ' 0000000000000 ', '202006041119', 'AA I love You Baby mleczko nawilz.do ciała 250ml', 0, 0, 0, 7, 0.297, NULL, 0),
(414, 17751, '200210', NULL, 'NIVEA POLSKA SA', 473, ' 0000000000000  0000000000000 ', '202006041119', 'Nivea Baby mydlo100g', 0, 0, 0, 7, 0.1071, NULL, 0),
(415, 176729, '200210', NULL, 'Henkel Polska S.A.', 1006, ' 0000000000000  0000000000000 ', '202006041119', 'FA deo spray Sport Invisible Power 150ml', 0, 0, 0, 7, 0.12, NULL, 0),
(416, 280671, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 9, ' 0000000000000 ', '202006041119', 'Eveline Revitalum odz.d/p reg.After Hybrid 12ml', 0, 0, 0, 7, 0.063, NULL, 0),
(417, 282200, '200210', NULL, 'Johnson&Johnson Poland Sp. z o.o.', 1864, ' 0000000000000 ', '202006041119', 'Penaten Ultra Sensitive mleczko nawilzające 400 ml', 0, 0, 0, 7, 0.4633, NULL, 0),
(418, 282201, '200210', NULL, 'Johnson&Johnson Poland Sp. z o.o.', 2686, ' 0000000000000 ', '202006041119', 'Penaten Ultra Sensitive Krem na odparzenia 75ml', 0, 0, 0, 7, 0.1565, NULL, 0),
(419, 287321, '200210', NULL, 'bci Bio Cosmetics International GmbH', 3, ' 0000000000000 ', '202006041119', 'Alterra Aloe Vera żel do ciała 50ml', 0, 0, 0, 7, 0.0703, NULL, 0),
(420, 282202, '200210', NULL, 'Johnson&Johnson Poland Sp. z o.o.', 1303, ' 0000000000000 ', '202006041119', 'Penaten Ultra Sensitive Krem do twarzy ciała 100ml', 0, 0, 0, 7, 0.14, NULL, 0),
(421, 154983, '200210', NULL, 'Bielenda Kosmetyki Naturalne', 1987, ' 0000000000000 ', '202006041119', 'Bielenda Happy end krem do piet i stop 125ml', 0, 0, 0, 7, 0.1475, NULL, 0),
(422, 283247, '200210', NULL, 'Inter-Vion SA', 1035, ' 0000000000000 ', '202006041119', 'Glamour breloczek dwa puszki 1szt.', 0, 0, 0, 7, 0.0129, NULL, 0),
(423, 314479, '200210', NULL, 'TORF CORPORATION SP.ZO.O.', 2331, ' 0000000000000 ', '202006041119', 'tołpa urban garden nocny krem-opatr do rąk 60ml', 0, 0, 0, 7, 0.071, NULL, 0),
(424, 283242, '200210', NULL, 'Inter-Vion SA', 1673, ' 0000000000000 ', '202006041119', 'Glamour breloczek czarny puszek 1szt.', 0, 0, 0, 7, 0.0096, NULL, 0),
(425, 127856, '200210', NULL, 'PHARCONA GmbH', 723, ' 0000000000000  0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Babydream Extrasensitive krem pielęgn.100ml', 0, 0, 0, 7, 0.114, NULL, 0),
(426, 283248, '200210', NULL, 'Inter-Vion SA', 1516, ' 0000000000000 ', '202006041119', 'Glamour breloczek kot czarny puszek 1szt.', 0, 0, 0, 7, 0.0114, NULL, 0),
(427, 283249, '200210', NULL, 'Inter-Vion SA', 1580, ' 0000000000000 ', '202006041119', 'Glamour breloczek kot szary puszek 1szt.', 0, 0, 0, 7, 0.0117, NULL, 0),
(428, 283250, '200210', NULL, 'Inter-Vion SA', 1452, ' 0000000000000 ', '202006041119', 'Glamour breloczek kot rózowy puszek 1szt.', 0, 0, 0, 7, 0.0115, NULL, 0),
(429, 318597, '200210', NULL, 'Pharma C Food', 2619, ' 0000000000000 ', '202006041119', 'Cztery p.r. krem do rąk nawilzający 150ml', 0, 0, 0, 7, 0.1736, NULL, 0),
(430, 318598, '200210', NULL, 'Pharma C Food', 1185, ' 0000000000000 ', '202006041119', 'Cztery p.r. krem do rąk ochronny 150ml', 0, 0, 0, 7, 0.171, NULL, 0),
(431, 229760, '200210', NULL, 'RB (Hygiene Home) Poland Sp.z o.o.', 1413, ' 0000000000000 ', '202006041119', 'Air Wick Pure Kwitnąca Wisnia 250 ml', 0, 0, 0, 7, 0.259, NULL, 0),
(432, 323459, '200210', NULL, 'Maxim', 1141, ' 0000000000000 ', '202006041119', 'Isana krem do rąk Aloe Vera&Jedwab 200ml', 0, 0, 0, 7, 0.2353, NULL, 0),
(433, 284556, '200210', NULL, 'Unilever Polska Sp. z o.o.', 1407, ' 0000000000000 ', '202006041119', 'Rexona deo spray Invisible Black&White 75 ml', 0, 0, 0, 7, 0.083, NULL, 0),
(434, 331404, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 364, ' 0000000000000 ', '202006041119', 'Eveline utwardzacz do paznokci Super Dri 5w1 12ml', 0, 0, 0, 7, 0.0593, NULL, 0),
(435, 331401, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 432, ' 0000000000000 ', '202006041119', 'Eveline odzywka Express Hard 12ml', 0, 0, 0, 7, 0.0593, NULL, 0),
(436, 284554, '200210', NULL, 'Unilever Polska Sp. z o.o.', 962, ' 0000000000000 ', '202006041119', 'Dove deo spray Invisible 75ml', 0, 0, 0, 7, 0.082, NULL, 0),
(437, 123534, '200210', NULL, 'Titania Fabrik GmbH', 292, ' 0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl Żel chłodzący do stóp 75ml', 0, 0, 0, 7, 0.096, NULL, 0),
(438, 327829, '200210', NULL, 'Colgate - Palmolive sp z.o.o.', 1312, ' 0000000000000 ', '202006041119', 'Colgate Max White Expert Complete pasta d/z 75ml', 0, 0, 0, 7, 0.14, NULL, 0),
(439, 285845, '200210', NULL, 'Royal Sanders (UK) Ltd', 1240, ' 0000000000000  0000000000000 ', '202006041119', 'Fruity Shower Zel p/prysznic kokos 250 ml', 0, 0, 0, 7, 0.2906, NULL, 0),
(440, 219792, '200210', NULL, 'Inter-Vion SA', 1917, ' 0000000000000 ', '202006041119', 'Killys witaminowa bomba bio2 10ml', 0, 0, 0, 7, 0.0524, NULL, 0),
(441, 123796, '200210', NULL, 'Titania Fabrik GmbH', 3291, ' 0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl sól do kąpieli stóp 450g', 0, 0, 0, 7, 0.528, NULL, 0),
(442, 200598, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 7144, ' 0000000000000 ', '202006041119', 'Eveline hand therapy maseczka par d/rąk rozgrz 7ml', 0, 0, 0, 7, 0.01, NULL, 0),
(443, 280209, '200210', NULL, 'Bielenda Kosmetyki Naturalne', 898, ' 0000000000000 ', '202006041119', 'BotanicSPA R.maseczka regenerująca Chia 50 ml', 0, 0, 0, 7, 0.215, NULL, 0),
(444, 285841, '200210', NULL, 'Royal Sanders (UK) Ltd', 117, ' 0000000000000  0000000000000 ', '202006041119', 'Fruity Shower Zel p/p truskawka&malina 250ml', 0, 0, 0, 7, 0.2935, NULL, 0),
(445, 327826, '200210', NULL, 'Colgate - Palmolive sp z.o.o.', 1006, ' 0000000000000 ', '202006041119', 'Colgate Max White Expert Anti-Stain pasta d/z 75ml', 0, 0, 0, 7, 0.14, NULL, 0),
(446, 235167, '200210', NULL, 'BURNUS Polska Sp. z O.O.', 2538, ' 0000000000000 ', '202006041119', 'Kamill krem do rąk i paznokci Urea 5% 75ml', 0, 0, 0, 7, 0.0893, NULL, 0),
(447, 61599, '200210', NULL, 'Geocos b.v.', 3031, ' 0000000000000  0000000000000  0000000000000  0000000000000  0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl Oslona na pięte para 1szt.', 0, 0, 0, 7, 0.0226, NULL, 0),
(448, 336549, '200210', NULL, 'Eris', 6706, ' 0000000000000 ', '202006041119', 'Lirene Maska Regeneracja łokci, 2 szt.', 0, 0, 0, 7, 0.028, NULL, 0),
(449, 327844, '200210', NULL, 'BE BIO ACTIVE COSMETIQS MIND NETWORK Sp.zo.o.', 3233, ' 0000000000000 ', '202006041119', 'beBio nat. krem do rąk bambus&trawa cytrynowa 75ml', 0, 0, 0, 7, 0.0862, NULL, 0),
(450, 191393, '200210', NULL, 'Zenner II Poland Sp.z o.o.', 3169, ' 0000000000000 ', '202006041119', 'EWA SCHMITT wycinacz do skórek ', 0, 0, 0, 7, 0.0166, NULL, 0),
(451, 336550, '200210', NULL, 'Eris', 6235, ' 0000000000000 ', '202006041119', 'Lirene Maska Regeneracja paznokci 7g', 0, 0, 0, 7, 0.014, NULL, 0),
(452, 336544, '200210', NULL, 'Eris', 8851, ' 0000000000000 ', '202006041119', 'Lirene Maska Wygładzony dekolt, 1 szt.', 0, 0, 0, 7, 0.026, NULL, 0),
(453, 191396, '200210', NULL, 'Zenner II Poland Sp.z o.o.', 3327, ' 0000000000000 ', '202006041119', 'EWA SCHMITT pilnik PEEL OFF wielowarstwowy', 0, 0, 0, 7, 0.02, NULL, 0),
(454, 279727, '200210', NULL, 'Efektima Monika Goca3, Marek Goca3', 2034, ' 0000000000000 ', '202006041119', 'Efektima Pedi Spa maska do stóp skarpeta 1 para ', 0, 0, 0, 7, 0.07, NULL, 0),
(455, 311727, '200210', NULL, 'Henkel Polska S.A.', 1364, ' 0000000000000 ', '202006041119', 'Fa Ipanema Nights spray 150 ml', 0, 0, 0, 7, 0.12, NULL, 0),
(456, 269236, '200210', NULL, 'Farmona Sp.zo.o.', 1750, ' 0000000000000 ', '202006041119', 'Farmona NIV sól odśw. d/k stóp ziołowa 600g', 0, 0, 0, 7, 0.66, NULL, 0),
(457, 219061, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 9, ' 0000000000000 ', '202006041119', 'Eveline nail therapy 6w1 odżywka rose 5ml', 0, 0, 0, 7, 0.0503, NULL, 0),
(458, 279730, '200210', NULL, 'Efektima Monika Goca3, Marek Goca3', 2030, ' 0000000000000 ', '202006041119', 'Efektima Pedi Spa maska złusz.d/stóp 1 para ', 0, 0, 0, 7, 0.08, NULL, 0),
(459, 329907, '200210', NULL, 'Bielenda Kosmetyki Naturalne', 566, ' 0000000000000 ', '202006041119', 'Biel.JapanLift maska p-zmarszczkowa 8 g', 0, 0, 0, 7, 0.0108, NULL, 0),
(460, 236475, '200210', NULL, 'LOreal Polska', 663, ' 0000000000000 ', '202006041119', 'Garnier Neo dry-mist Fresh Blossom 150ml', 0, 0, 0, 7, 0.129, NULL, 0),
(461, 321469, '200210', NULL, 'Orkla Cederroth Jordan', 882, ' 0000000000000 ', '202006041119', 'Jordan Caries Defence pasta 75ml', 0, 0, 0, 7, 0.12, NULL, 0),
(462, 164536, '200210', NULL, 'Merkury SA', 1349, ' 0000000000000  0000000000000 ', '202006041119', 'Oilatum Baby krem 150g', 0, 0, 0, 7, 0.19, NULL, 0),
(463, 321471, '200210', NULL, 'Orkla Cederroth Jordan', 622, ' 0000000000000 ', '202006041119', 'Jordan White Smile pasta 75ml', 0, 0, 0, 7, 0.12, NULL, 0),
(464, 139458, '200210', NULL, 'Geocos b.v.', 64, ' 0000000000000  0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl zelowe plasterki punktowe 6szt.', 0, 0, 0, 7, 0.023, NULL, 0),
(465, 292806, '200210', NULL, 'Zenner II Poland Sp.z o.o.', 2384, ' 0000000000000 ', '202006041119', 'Ewa Schmitt Gold nozyczki do skórek 1szt.', 0, 0, 0, 7, 0.0207, NULL, 0),
(466, 292800, '200210', NULL, 'Zenner II Poland Sp.z o.o.', 1637, ' 0000000000000 ', '202006041119', 'Ewa Schmitt Gun Black nozyczki 1szt.', 0, 0, 0, 7, 0.0246, NULL, 0),
(467, 169927, '200210', NULL, 'Procter & Gamble ZEBY', 2429, ' 0000000000000  0000000000000 ', '202006041119', 'BAM 3D White Cool Water pasta 100ml', 0, 0, 0, 7, 0.15, NULL, 0),
(468, 265665, '200210', NULL, 'MagnaPharm Poland Sp.zo.o.', 1545, ' 0000000000000 ', '202006041119', 'Compeed plastry na halluksy 5 szt', 0, 0, 0, 7, 0.0235, NULL, 0),
(469, 274881, '200210', NULL, 'Procter & Gamble ZEBY', 2315, ' 0000000000000  0000000000000  0000000000000 ', '202006041119', 'OralB PRO2500 szczoteczka elektryczna', 0, 0, 0, 7, 0.49, NULL, 0),
(470, 291779, '200210', NULL, 'Procter & Gamble ZEBY', 1233, ' 0000000000000 ', '202006041119', 'OralB ProHel Super Odświezenie pasta d/zębów 75 ml', 0, 0, 0, 7, 0.1188, NULL, 0),
(471, 292803, '200210', NULL, 'Zenner II Poland Sp.z o.o.', 2075, ' 0000000000000 ', '202006041119', 'Ewa Schmitt Gold nozyczki do paznokci 1szt.', 0, 0, 0, 7, 0.026, NULL, 0),
(472, 314572, '200210', NULL, 'AB Cosmetique Polska Sp. z o.o', 3309, ' 0000000000000 ', '202006041119', 'AB Franck Provost Barb Xpert-zest pilników 1szt', 0, 0, 0, 7, 0.031, NULL, 0),
(473, 71885, '200210', NULL, 'Pharma C Food', 1233, ' 0000000000000 ', '202006041119', 'Pharma CF No36 peeling złuszcz. do stóp 75+25 ml', 0, 0, 0, 7, 0.1196, NULL, 0),
(474, 71888, '200210', NULL, 'Pharma C Food', 63, ' 0000000000000  0000000000000 ', '202006041119', 'PharmaCF No36deo odśw z talkiem d/stóp 200 ml', 0, 0, 0, 7, 0.13, NULL, 0),
(475, 110295, '200210', NULL, 'Eris', 184, ' 0000000000000 ', '202006041119', 'Lirene STOP suchości nawilzający krem d/stóp 75 ml', 0, 0, 0, 7, 0.0878, NULL, 0),
(476, 323551, '200210', NULL, 'PHARCONA GmbH', 933, ' 0000000000000  0000000000000 ', '202006041119', 'Babydream MED zel pod prysznic i szampon 200ml', 0, 0, 0, 7, 0.262, NULL, 0),
(477, 30430, '200210', NULL, 'PHARCONA GmbH', 407, ' 0000000000000  0000000000000  0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Babydream olejek przeciw rozstępom 250ml', 0, 0, 0, 7, 0.2593, NULL, 0),
(478, 323552, '200210', NULL, 'Gewo GmbH', 1401, ' 0000000000000 ', '202006041119', 'Babydream MED balsam do ciała 200ml', 0, 0, 0, 7, 0.22, NULL, 0),
(479, 164580, '200210', NULL, 'Coty Eastern Europe zapach+drogeria sp. z o.o.', 2457, ' 0000000000000  0000000000000 ', '202006041119', 'SH advanced hard as nails nud 13,3ml', 0, 0, 0, 7, 0.062, NULL, 0),
(480, 317411, '200210', NULL, 'Farmona Sp.zo.o.', 1829, ' 0000000000000 ', '202006041119', 'Radical Nail Arch.rekonstruktor d/pazn. 12ml', 0, 0, 0, 7, 0.063, NULL, 0),
(481, 169963, '200210', NULL, 'NIVEA POLSKA SA', 640, ' 0000000000000  0000000000000 ', '202006041119', 'Nivea deo sztyft damski Stress Protect 40ml', 0, 0, 0, 7, 0.0814, NULL, 0),
(482, 286957, '200210', NULL, 'Novamed Sp z o.o.', 827, ' 0000000000000 ', '202006041119', 'Novamed Vitammy Platinum końcówki d/szczot 4szt', 0, 0, 0, 7, 0.0343, NULL, 0),
(483, 271598, '200210', NULL, 'Eveline Cosmetics Dystrybucja Spółka z o.o. S.K.A.', 2028, ' 0000000000000 ', '202006041119', 'Eveline Nail Ther terapia p/grzybicy paznokci 12ml', 0, 0, 0, 7, 0.0595, NULL, 0),
(484, 268264, '200210', NULL, 'AB Cosmetique Polska Sp. z o.o', 1230, ' 0000000000000 ', '202006041119', 'AB Franck Provost nozyczki do paznokci 1szt.', 0, 0, 0, 7, 0.0315, NULL, 0),
(485, 317416, '200210', NULL, 'Unilever Polska Sp. z o.o.', 1493, ' 0000000000000 ', '202006041119', 'Rexona deo roll-on White Flower 50 ml', 0, 0, 0, 7, 0.0825, NULL, 0),
(486, 286954, '200210', NULL, 'Novamed Sp z o.o.', 641, ' 0000000000000 ', '202006041119', 'Novamed Vitammy Platinum szcz.son.z ładowarką 1szt', 0, 0, 0, 7, 0.422, NULL, 0),
(487, 268267, '200210', NULL, 'AB Cosmetique Polska Sp. z o.o', 1348, ' 0000000000000 ', '202006041119', 'AB Franck Provost obcinak do paznokci do stóp1szt.', 0, 0, 0, 7, 0.0655, NULL, 0),
(488, 291316, '200210', NULL, 'LOreal Polska', 508, ' 0000000000000 ', '202006041119', 'Garnier maska pomarańcza 6 g', 0, 0, 0, 7, 0.0124, NULL, 0),
(489, 123376, '200210', NULL, 'Rossmann GmbH_ FYB', 3003, ' 0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl Wkladki Party Pads 1szt.', 0, 0, 0, 7, 0.0405, NULL, 0),
(490, 291318, '200210', NULL, 'LOreal Polska', 337, ' 0000000000000 ', '202006041119', 'Garnier Skin Active maska woda kokosowa 6 g', 0, 0, 0, 7, 0.0124, NULL, 0),
(491, 323568, '200210', NULL, 'Gewo GmbH', 1742, ' 0000000000000 ', '202006041119', 'Babydream MED intensywny krem piel. 75ml', 0, 0, 0, 7, 0.0916, NULL, 0),
(492, 336112, '200210', NULL, 'Colgate - Palmolive sp z.o.o.', 10986, ' 0000000000000 ', '202006041119', 'Colgate pasta dare to love 98ml, 1szt.', 0, 0, 0, 7, 0.15, NULL, 0),
(493, 139508, '200210', NULL, 'Szaidel Cosmetics Gmbh', 256, ' 0000000000000  0000000000000  0000000000000  0000000000000 ', '202006041119', 'Fusswohl Wellness masło do stóp 200ml', 0, 0, 0, 7, 0.2523, NULL, 0),
(494, 261620, '200210', NULL, 'Capeypharma Sp.zo.o. Społka komandytowa', 467, ' 0000000000000  0000000000000 ', '202006041119', 'Babycap ampułki soli fizjologicznej 10x5ml', 0, 0, 0, 7, 0.076, NULL, 0),
(495, 250875, '200210', NULL, 'LOreal Polska', 433, ' 0000000000000 ', '202006041119', 'Garnier maska w płachcie Aqua Bomb 32 g', 0, 0, 0, 7, 0.0478, NULL, 0),
(496, 250874, '200210', NULL, 'LOreal Polska', 3425, ' 0000000000000 ', '202006041119', 'L\'oreal maska rozświetlająca z glinką 50 ml', 0, 0, 0, 7, 0.265, NULL, 0),
(497, 124159, '200210', NULL, 'Dax Cosmetics', 19908, ' 0000000000000 ', '202006041119', 'Dax Perf. SPA szafir. peeling + maska do rąk 2x6ml', 0, 0, 0, 7, 0.0162, NULL, 0),
(2087, 285696, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 8435415006880 ', '202009251541', 'J.P.G. Le Male Eau Fraiche Superman edt 125 ml', 0, 0, 0, 15, 0.464, NULL, 0),
(2088, 274435, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3351500982219 ', '202009251541', 'Azzaro Homme A/SH 100ml', 0, 0, 0, 15, 0.334, NULL, 0),
(2089, 323596, '200269', NULL, 'NUVARIA GLOBAL, S.L.', 2, ' 8480029434710 ', '202009251541', 'Dicora Urban Fit CHICAGO edt 100ml', 0, 0, 0, 15, 0.432, NULL, 0),
(2090, 189456, '200269', NULL, 'Sirowa Premium IN-OUT', 4, ' 0737052684918 ', '202009251541', 'Hugo Boss Jour Pour Femme Balsam do ciała 200ml', 0, 0, 0, 15, 0.253, NULL, 0),
(2091, 189460, '200269', NULL, 'Sirowa Premium IN-OUT', 3, ' 0737052550176 ', '202009251541', 'Hugo Boss Nuit Pour Femme Żel pod prysznic 200ml', 0, 0, 0, 15, 0.261, NULL, 0),
(2092, 323602, '200269', NULL, 'NUVARIA GLOBAL, S.L.', 2, ' 8480029434635 ', '202009251541', 'Dicora Urban Fit SYDNEY edt 100ml', 0, 0, 0, 15, 0.432, NULL, 0),
(2093, 269340, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 1716, ' 8005610433240 ', '202009251541', 'M.F. podkład Healthy Skin Harmony 47, 30ml', 0, 0, 0, 15, 0.142, NULL, 0),
(2094, 269341, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 1627, ' 8005610433288 ', '202009251541', 'M.F. podkład Healthy Skin Harmony 50, 30ml', 0, 0, 0, 15, 0.1423, NULL, 0),
(2095, 269342, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 1838, ' 8005610433325 ', '202009251541', 'M.F. podkład Healthy Skin Harmony 55, 30ml', 0, 0, 0, 15, 0.142, NULL, 0),
(2096, 137241, '200269', NULL, 'Simex Trading AG IN-OUT', 24, ' 3595200113768  3595200501015 ', '202009251541', 'Lolita Lempicka edp 100ml', 0, 0, 0, 15, 0.309, NULL, 0),
(2097, 240664, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3351500980802 ', '202009251541', 'Azzaro Homme edt 50ml', 0, 0, 0, 15, 0.168, NULL, 0),
(2098, 269343, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 1818, ' 8005610433363 ', '202009251541', 'M.F. podkład Healthy Skin Harmony 60, 30ml', 0, 0, 0, 15, 0.142, NULL, 0),
(2099, 269339, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 1981, ' 8005610433165 ', '202009251541', 'M.F. podkład Healthy Skin Harmony 40, 30ml', 0, 0, 0, 15, 0.142, NULL, 0),
(2100, 293925, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3386460097871 ', '202009251541', 'Mont Blanc Legend Night zestaw 1 szt.', 0, 0, 0, 15, 0.651, NULL, 0),
(2101, 269345, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 1655, ' 8005610433448 ', '202009251541', 'M.F. podkład Healthy Skin Harmony 75, 30ml', 0, 0, 0, 15, 0.142, NULL, 0),
(2102, 317486, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 0737052780566 ', '202009251541', 'Hugo Boss Green Music edt 125 ml', 0, 0, 0, 15, 0.338, NULL, 0),
(2103, 193577, '200269', NULL, 'Star Global Trading IN-OUT', 4, ' 7640111611202 ', '202009251541', 'Mikki Beach Private Party him edt 100ml', 0, 0, 0, 15, 0.391, NULL, 0),
(2104, 293935, '200269', NULL, 'Simex Trading AG IN-OUT', 10, ' 3614222482635 ', '202009251541', 'Davidoff Horizon Extreme edp 75ml', 0, 0, 0, 15, 0.298, NULL, 0),
(2105, 256050, '200269', NULL, 'LOreal makijaz', 1313, ' 3600523409327 ', '202009251541', 'Loreal kredka do oczu SUPERLINER KOHL 111/ 1,2g', 0, 0, 0, 15, 0.0042, NULL, 0),
(2106, 189489, '200269', NULL, 'Simex Trading AG IN-OUT', 3, ' 0737052696072 ', '202009251541', 'Hugo Boss Orange Fresh Man edt 60 ml', 0, 0, 0, 15, 0.276, NULL, 0),
(2107, 256048, '200269', NULL, 'LOreal makijaz', 775, ' 3600523409280 ', '202009251541', 'Loreal kredka do oczu SUPERLINER KOHL 102/ 1,2g', 0, 0, 0, 15, 0.0041, NULL, 0),
(2108, 274492, '200269', NULL, 'Labori International b.v.', 2, ' 0719346176378 ', '202009251541', 'Juicy Couture Malibu Lala edt 75ml', 0, 0, 0, 15, 0.384, NULL, 0),
(2109, 281662, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1798, ' 5901761937664 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 299,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2110, 274495, '200269', NULL, 'Labori International b.v. IN-OUT', 3, ' 3349668524570 ', '202009251541', 'Paco Rabanne One Milion Oh my Gold Lady edt 80 ml', 0, 0, 0, 15, 0.337, NULL, 0),
(2111, 190524, '200269', NULL, 'Sirowa Poland Sp. z o.o.', 1, ' 5906190690227 ', '202009251541', 'Puma Animagical woman dns 75ml + deo 150ml', 0, 0, 0, 15, 0.425, NULL, 0),
(2112, 190525, '200269', NULL, 'Sirowa Poland Sp. z o.o.', 1, ' 5906190690258 ', '202009251541', 'Puma Jam woman dns 75ml + deo 150ml', 0, 0, 0, 15, 0.425, NULL, 0),
(2113, 275515, '200269', NULL, 'ADOS  P.P.H.U.', 656, ' 5906942550670 ', '202009251541', 'Ados lakier Long Lasting nr 67, 9 ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2114, 189506, '200269', NULL, 'Sirowa Premium IN-OUT', 8, ' 0737052347837 ', '202009251541', 'Hugo Boss Orange Man Woda po goleniu 100ml', 0, 0, 0, 15, 0.395, NULL, 0),
(2115, 188483, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 921, ' 3607349703363 ', '202009251541', 'M.S. cień pojedynczy Studio S 103, 3g', 0, 0, 0, 15, 0.0127, NULL, 0),
(2116, 281669, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1831, ' 5901761937985 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 305,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2117, 275526, '200269', NULL, 'ADOS  P.P.H.U.', 402, ' 5906942530436 ', '202009251541', 'Ados lakier Gel Lack nr 43, 8 ml', 0, 0, 0, 15, 0.0443, NULL, 0),
(2118, 193601, '200269', NULL, 'DAQSO International B.V.', 49, ' 0088300108978 ', '202009251541', 'CK One Unisex deo sticker 75ml', 0, 0, 0, 15, 0.142, NULL, 0),
(2119, 281664, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1387, ' 5901761937695 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 301,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2120, 275521, '200269', NULL, 'ADOS  P.P.H.U.', 575, ' 5906942550724 ', '202009251541', 'Ados lakier Long Lasting nr 72, 9 ml', 0, 0, 0, 15, 0.0472, NULL, 0),
(2121, 193604, '200269', NULL, 'Star Global Trading IN-OUT', 13, ' 3607342582002 ', '202009251541', 'CK One Unisex zestaw 1 szt.', 0, 0, 0, 15, 0.29, NULL, 0),
(2122, 281667, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1654, ' 5901761937978 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 304,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2123, 189515, '200269', NULL, 'Beauty Line Sp. z o.o.', 5, ' 0737052683287 ', '202009251541', 'Laura Biagiotti Essenza di Roma edt 100ml', 0, 0, 0, 15, 0.3465, NULL, 0),
(2124, 275533, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 3614270561665 ', '202009251541', 'YSL Mon Paris edp 30ml', 0, 0, 0, 15, 0.212, NULL, 0),
(2125, 326733, '200269', NULL, 'Simex Trading AG IN-OUT', 3, ' 8005610262000 ', '202009251541', 'Hugo Boss Iced edt 125ml', 0, 0, 0, 15, 0.335, NULL, 0),
(2126, 275535, '200269', NULL, 'Simex Trading AG IN-OUT', 9, ' 0730870196847 ', '202009251541', 'Hugo Boss The Scent for Her edp 50ml', 0, 0, 0, 15, 0.267, NULL, 0),
(2127, 323657, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 0737052780627 ', '202009251541', 'Hugo Boss Green Music edt 75 ml', 0, 0, 0, 15, 0.224, NULL, 0),
(2128, 275530, '200269', NULL, 'CRM Trading Ltd  IN-OUT', 2, ' 3414200064057 ', '202009251541', 'Joop! Go Homme edt 50ml', 0, 0, 0, 15, 0.194, NULL, 0),
(2129, 274507, '200269', NULL, 'DAQSO International B.V.', 3, ' 0088300108992 ', '202009251541', 'CK Be deo stick 75 g', 0, 0, 0, 15, 0.141, NULL, 0),
(2130, 188498, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 711, ' 3607349703370 ', '202009251541', 'M.S. cień pojedynczy Studio S 104, 3g', 0, 0, 0, 15, 0.0126, NULL, 0),
(2131, 182355, '200269', NULL, 'Beauty Gallery IN-OUT', 1, ' 3351500952724 ', '202009251541', 'Azzaro Now man edt 80ml', 0, 0, 0, 15, 0.309, NULL, 0),
(2132, 274517, '200269', NULL, 'DAQSO International B.V.', 7, ' 0088300605705 ', '202009251541', 'CK Eternity for Men deo stick 75 g', 0, 0, 0, 15, 0.141, NULL, 0),
(2133, 274519, '200269', NULL, 'DAQSO International B.V.', 7, ' 0088300606702 ', '202009251541', 'CK Obsession for Men deo stick 75 g', 0, 0, 0, 15, 0.145, NULL, 0),
(2134, 275543, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 0783320977893 ', '202009251541', 'BVLGARI Splendida Rose Rose edp 30ml', 0, 0, 0, 15, 0.218, NULL, 0),
(2135, 182359, '200269', NULL, 'Beauty Gallery IN-OUT', 3, ' 3351500953028 ', '202009251541', 'Azzaro Now woman edt 80ml', 0, 0, 0, 15, 0.309, NULL, 0),
(2136, 189527, '200269', NULL, 'JTG B.V.', 1, ' 8011530001131 ', '202009251541', 'Laura Biagiotti Laura edt 50 ml', 0, 0, 0, 15, 0.2021, NULL, 0),
(2137, 188506, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1321, ' 3607349703387 ', '202009251541', 'M.S. cień pojedynczy Studio S 105, 4g', 0, 0, 0, 15, 0.0123, NULL, 0),
(2138, 288870, '200269', NULL, 'Błysk Sp. z o.o', 473, ' 5902693162322 ', '202009251541', 'Provocater lakier hybrydowy 120, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2139, 288871, '200269', NULL, 'Błysk Sp. z o.o', 320, ' 5902693161974 ', '202009251541', 'Provocater lakier hybrydowy 110, 4ml', 0, 0, 0, 15, 0.0218, NULL, 0),
(2140, 227431, '200269', NULL, 'Beauty Gallery Sp. z o.o. Sp. komandytowa', 15, ' 0088300600502 ', '202009251541', 'CK Escape for Men edt 50ml', 0, 0, 0, 15, 0.18, NULL, 0),
(2141, 274528, '200269', NULL, 'Beauty Gallery IN-OUT', 10, ' 8005610261973 ', '202009251541', 'Hugo Boss Iced edt 75ml', 0, 0, 0, 15, 0.226, NULL, 0),
(2142, 274529, '200269', NULL, 'ZACOBI S.p.A.', 3, ' 3352818716008 ', '202009251541', 'Kenzo Amour edp 30ml', 0, 0, 0, 15, 0.2006, NULL, 0),
(2143, 288865, '200269', NULL, 'Błysk Sp. z o.o', 480, ' 5902693161936 ', '202009251541', 'Provocater lakier hybrydowy 106, 4ml', 0, 0, 0, 15, 0.0218, NULL, 0),
(2144, 328800, '200269', NULL, 'Simex Trading AG IN-OUT', 4, ' 3605521816511 ', '202009251541', 'Armani Si edp 30ml', 0, 0, 0, 15, 0.205, NULL, 0),
(2145, 288866, '200269', NULL, 'Błysk Sp. z o.o', 366, ' 5902693162308 ', '202009251541', 'Provocater lakier hybrydowy 116, 4ml', 0, 0, 0, 15, 0.0221, NULL, 0),
(2146, 288867, '200269', NULL, 'Błysk Sp. z o.o', 384, ' 5902693162315 ', '202009251541', 'Provocater lakier hybrydowy 117, 4ml', 0, 0, 0, 15, 0.0217, NULL, 0),
(2147, 188522, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 972, ' 3607349703417 ', '202009251541', 'M.S. cień pojedynczy Studio S 108, 3g', 0, 0, 0, 15, 0.0127, NULL, 0),
(2148, 189546, '200269', NULL, 'B&P Power s.r.o', 8, ' 0737052539058 ', '202009251541', 'Lacoste eau de Lacoste Femme edp 30ml', 0, 0, 0, 15, 0.181, NULL, 0),
(2149, 224363, '200269', NULL, 'Bell sp. z o.o.', 482, ' 5902082501640 ', '202009251541', 'Bell Hypo puder brąz.–roz. twarz i ciało 1 / 4,5g', 0, 0, 0, 15, 0.0413, NULL, 0),
(2150, 288876, '200269', NULL, 'Błysk Sp. z o.o', 129, ' 5902693162087 ', '202009251541', 'Provocater baza Top Shine 4 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2151, 288877, '200269', NULL, 'Błysk Sp. z o.o', 182, ' 5902693162094 ', '202009251541', 'Provocater baza Shape 4 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2152, 288878, '200269', NULL, 'Błysk Sp. z o.o', 155, ' 5902693162100 ', '202009251541', 'Provocater baza Primer 4 ml', 0, 0, 0, 15, 0.021, NULL, 0),
(2153, 288873, '200269', NULL, 'Błysk Sp. z o.o', 189, ' 5902693162056 ', '202009251541', 'Provocater baza vitamina E+Led/UV 4ml', 0, 0, 0, 15, 0.0212, NULL, 0),
(2154, 288875, '200269', NULL, 'Błysk Sp. z o.o', 155, ' 5902693162070 ', '202009251541', 'Provocater baza Base i Top 4 ml', 0, 0, 0, 15, 0.0214, NULL, 0),
(2155, 189552, '200269', NULL, 'Simex Trading AG IN-OUT', 4, ' 0737052949161 ', '202009251541', 'Lacoste pour femme edp vapo 30ml', 0, 0, 0, 15, 0.15, NULL, 0),
(2156, 19572, '200269', NULL, 'Coty Eastern Europe zapach Prestige sp. z o.o.', 6, ' 4082800179700  4082800188603  8005610326603 ', '202009251541', 'B. B. Woman dns 75ml', 0, 0, 0, 15, 0.199, NULL, 0),
(2157, 274545, '200269', NULL, 'Simex Trading AG IN-OUT', 9, ' 3147754035357 ', '202009251541', 'Lancome Hypnose Homme edt 50ml', 0, 0, 0, 15, 0.338, NULL, 0),
(2158, 189562, '200269', NULL, 'Sirowa Premium IN-OUT', 7, ' 0737052517704 ', '202009251541', 'Lacoste L.12.12 Rouge edt 30ml', 0, 0, 0, 15, 0.137, NULL, 0),
(2159, 312446, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 512, ' 0074170454062 ', '202009251541', 'SH lakier Insta Dri 343, 9,2ml', 0, 0, 0, 15, 0.052, NULL, 0),
(2160, 189567, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 0737052139890 ', '202009251541', 'Hugo Boss Red Man Energise edt 125 ml', 0, 0, 0, 15, 0.36, NULL, 0),
(2161, 249987, '200269', NULL, 'Bell sp. z o.o.', 212, ' 5902082515647 ', '202009251541', 'Bell Hypo pomadka w płynie matowa  03, 1szt.', 0, 0, 0, 15, 0.0236, NULL, 0),
(2162, 274564, '200269', NULL, 'Beauty Gallery IN-OUT', 17, ' 8411061786345 ', '202009251541', 'C.Herrera CH Prive man edt 50 ml', 0, 0, 0, 15, 0.26, NULL, 0),
(2163, 249985, '200269', NULL, 'Bell sp. z o.o.', 115, ' 5902082515630 ', '202009251541', 'Bell Hypo pomadka w płynie matowa  02, 1szt.', 0, 0, 0, 15, 0.0232, NULL, 0),
(2164, 193665, '200269', NULL, 'Star Global Trading IN-OUT', 3, ' 8032529119941 ', '202009251541', 'Emanuel Ungaro Apparition Pink edt 30ml', 0, 0, 0, 15, 0.145, NULL, 0),
(2165, 249984, '200269', NULL, 'Bell sp. z o.o.', 226, ' 5902082515623 ', '202009251541', 'Bell Hypo pomadka w płynie matowa  01, 1szt.', 0, 0, 0, 15, 0.0232, NULL, 0),
(2166, 274567, '200269', NULL, 'Beauty Gallery IN-OUT', 1, ' 3607347603382 ', '202009251541', 'Davidoff The Game Intense man edt 60 ml', 0, 0, 0, 15, 0.284, NULL, 0),
(2167, 249990, '200269', NULL, 'Bell sp. z o.o.', 334, ' 5902082515654 ', '202009251541', 'Bell Hypo pomadka w płynie matowa  04, 1szt.', 0, 0, 0, 15, 0.0233, NULL, 0),
(2168, 274562, '200269', NULL, 'Beauty Gallery IN-OUT', 5, ' 8411061665039 ', '202009251541', 'C.Herrera CH man edt 50 ml', 0, 0, 0, 15, 0.284, NULL, 0),
(2169, 274572, '200269', NULL, 'Beauty Gallery IN-OUT', 6, ' 3274871952356 ', '202009251541', 'Kenzo Amour My Love woman edt 50 ml', 0, 0, 0, 15, 0.235, NULL, 0),
(2170, 289932, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 3925, ' 3614225688102 ', '202009251541', 'Bourjois szminka Lip Duo Sculpt 03, 0,5 g', 0, 0, 0, 15, 0.0112, NULL, 0),
(2171, 289933, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 4107, ' 3614225688119 ', '202009251541', 'Bourjois szminka Lip Duo Sculpt 04, 0,5 g', 0, 0, 0, 15, 0.0112, NULL, 0),
(2172, 249993, '200269', NULL, 'Bell sp. z o.o.', 269, ' 5902082515678 ', '202009251541', 'Bell Hypo pomadka w płynie matowa  06, 1szt.', 0, 0, 0, 15, 0.0234, NULL, 0),
(2173, 289934, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 3106, ' 3614225688126 ', '202009251541', 'Bourjois szminka Lip Duo Sculpt 05, 0,5 g', 0, 0, 0, 15, 0.0112, NULL, 0),
(2174, 249992, '200269', NULL, 'Bell sp. z o.o.', 307, ' 5902082515661 ', '202009251541', 'Bell Hypo pomadka w płynie matowa  05, 1szt.', 0, 0, 0, 15, 0.0218, NULL, 0),
(2175, 274575, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3274870000997  3352819617106 ', '202009251541', 'Kenzo Madly woman edp 50 ml', 0, 0, 0, 15, 0.221, NULL, 0),
(2176, 289929, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2393, ' 3614225688089 ', '202009251541', 'Bourjois szminka Lip Duo Sculpt 01, 0,5 g', 0, 0, 0, 15, 0.0111, NULL, 0),
(2177, 274570, '200269', NULL, 'Beauty Gallery IN-OUT', 2, ' 3386460062725 ', '202009251541', 'Lanvin Eclat D\'Arpege man edt 50 ml', 0, 0, 0, 15, 0.317, NULL, 0),
(2178, 289931, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 3353, ' 3614225688096 ', '202009251541', 'Bourjois szminka Lip Duo Sculpt 02, 0,5 g', 0, 0, 0, 15, 0.0111, NULL, 0),
(2179, 289936, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 3513, ' 3614225688133 ', '202009251541', 'Bourjois szminka Lip Duo Sculpt 06, 0,5 g', 0, 0, 0, 15, 0.0113, NULL, 0),
(2180, 189589, '200269', NULL, 'Beauty Line Sp. z o.o.', 6, ' 3349668515745  3349668530564 ', '202009251541', 'Paco Rabanne Invictus deozdorant spray 150ml', 0, 0, 0, 15, 0.182, NULL, 0),
(2181, 193699, '200269', NULL, 'Star Global Trading IN-OUT', 2, ' 0094922191014 ', '202009251541', 'Ed Hardy Born Wild woman edp 100ml', 0, 0, 0, 15, 0.342, NULL, 0),
(2182, 189612, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3349668508587 ', '202009251541', 'Paco Rabanne One Milion Lady edp 80 ml', 0, 0, 0, 15, 0.301, NULL, 0),
(2183, 274612, '200269', NULL, 'Simex Trading AG IN-OUT', 13, ' 3423474883950 ', '202009251541', 'Issey L\'eau D\'Issey pour Homme Nuit edp125ml', 0, 0, 0, 15, 0.465, NULL, 0),
(2184, 281783, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 200, ' 5901761966152 ', '202009251541', 'EVEL. podkład Art. Scenic nr 21,40 ml', 0, 0, 0, 15, 0.0538, NULL, 0),
(2185, 293043, '200269', NULL, 'Eris makijaż', 1257, ' 5902860347071 ', '202009251541', 'SinSkin cień Bouncy 21, 2,5g - limited', 0, 0, 0, 15, 0.0294, NULL, 0),
(2186, 293052, '200269', NULL, 'Eris makijaż', 1176, ' 5902860347088 ', '202009251541', 'SinSkin cień Bouncy 22, 2,5g - limited', 0, 0, 0, 15, 0.0298, NULL, 0),
(2187, 293053, '200269', NULL, 'Eris makijaż', 804, ' 5902860347095 ', '202009251541', 'SinSkin cień Bouncy 23, 2,5g - limited', 0, 0, 0, 15, 0.0294, NULL, 0),
(2188, 269502, '200269', NULL, 'Simex Trading AG IN-OUT', 9, ' 0783320913068 ', '202009251541', 'BVLGARI Aqua pour Homme Atlantique edt 30ml', 0, 0, 0, 15, 0.17, NULL, 0),
(2189, 293054, '200269', NULL, 'Eris makijaż', 992, ' 5902860347101 ', '202009251541', 'SinSkin cień Bouncy 24, 2,5g - limited', 0, 0, 0, 15, 0.0292, NULL, 0),
(2190, 249023, '200269', NULL, 'Beauty Gallery IN-OUT', 8, ' 8034097951060 ', '202009251541', 'S. Ferragamo Attimo man edt 60 ml', 0, 0, 0, 15, 0.314, NULL, 0),
(2191, 281786, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 168, ' 5901761966169 ', '202009251541', 'EVEL. podkład Art. Scenic nr 22,40 ml', 0, 0, 0, 15, 0.0527, NULL, 0),
(2192, 281798, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 169, ' 5901761966145 ', '202009251541', 'EVEL. podkład Art. Scenic nr 20,40 ml', 0, 0, 0, 15, 0.052, NULL, 0),
(2193, 284871, '200269', NULL, 'LOreal makijaz', 1447, ' 3600523533503 ', '202009251541', 'LOreal  rozświetlacz sztyft Infallible 503, 34ml', 0, 0, 0, 15, 0.034, NULL, 0),
(2194, 189638, '200269', NULL, 'Beauty Line Sp. z o.o.', 1, ' 8011530911058 ', '202009251541', 'Blumarine Innamorata edp 30ml', 0, 0, 0, 15, 0.2205, NULL, 0),
(2195, 281793, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 252, ' 5901761966176 ', '202009251541', 'EVEL. podkład Art. Scenic nr 23,40 ml', 0, 0, 0, 15, 0.0526, NULL, 0),
(2196, 284865, '200269', NULL, 'LOreal makijaz', 1506, ' 3600523533497 ', '202009251541', 'LOreal  rozświetlacz sztyft Infallible 502, 34ml', 0, 0, 0, 15, 0.034, NULL, 0),
(2197, 249028, '200269', NULL, 'Beauty Gallery IN-OUT', 5, ' 3423470485882 ', '202009251541', 'Issey Miyake L\'eau Bleue D\'issey e Fraiche edt75ml', 0, 0, 0, 15, 0.302, NULL, 0),
(2198, 293069, '200269', NULL, 'Eris makijaż', 755, ' 5902860345855 ', '202009251541', 'SinSkin rozświetlacz spiekany 044, 8g - limited', 0, 0, 0, 15, 0.082, NULL, 0),
(2199, 293070, '200269', NULL, 'Eris makijaż', 86, ' 5902860345862 ', '202009251541', 'SinSkin rozświetlacz spiekany 045, 8g - limited', 0, 0, 0, 15, 0.0814, NULL, 0),
(2200, 2253, '200269', NULL, 'IFD  Fragrance Distribution Ltd - IN-OUT', 3, ' 3614220276670  3414206000158 ', '202009251541', 'Joop Le Bain edp spray 40ml.', 0, 0, 0, 15, 0.257, NULL, 0),
(2201, 248013, '200269', NULL, 'Wibo Sp. z o.o.', 917, ' 5901801617419 ', '202009251541', 'Lovely paleta do brwi Dark Eyebrow Creator, 1szt.', 0, 0, 0, 15, 0.0797, NULL, 0),
(2202, 189650, '200269', NULL, 'Beauty Line Sp. z o.o.', 8, ' 8011530850012 ', '202009251541', 'Trussardi My Name edp 50ml', 0, 0, 0, 15, 0.23, NULL, 0),
(2203, 189651, '200269', NULL, 'Simex Trading AG IN-OUT', 12, ' 8411061711804 ', '202009251541', 'C.Herrera 212 Vip edp 50 ml', 0, 0, 0, 15, 0.33, NULL, 0),
(2204, 189654, '200269', NULL, 'Beauty Line Sp. z o.o.', 7, ' 8011530820008 ', '202009251541', 'Trussardi Donna edp 30ml', 0, 0, 0, 15, 0.1645, NULL, 0),
(2205, 293072, '200269', NULL, 'Eris makijaż', 288, ' 5902860344889 ', '202009251541', 'SinSkin puder prasowany matujący T01, 9g - limited', 0, 0, 0, 15, 0.078, NULL, 0),
(2206, 248022, '200269', NULL, 'Wibo Sp. z o.o.', 1425, ' 5901801616702 ', '202009251541', 'Wibo róż Ecstasy Blusher nr 2, 4,5 g', 0, 0, 0, 15, 0.046, NULL, 0),
(2207, 294098, '200269', NULL, 'LOreal makijaz', 1877, ' 5021044024345 ', '202009251541', 'May.szminka Color Sensational Hydra Extreme535, 5g', 0, 0, 0, 15, 0.0158, NULL, 0),
(2208, 189653, '200269', NULL, 'Simex Trading AG IN-OUT', 4, ' 8411061711767 ', '202009251541', 'C.Herrera 212 Vip edp 80 ml', 0, 0, 0, 15, 0.39, NULL, 0),
(2209, 189656, '200269', NULL, 'Beauty Line Sp. z o.o.', 4, ' 8011530820015 ', '202009251541', 'Trussardi Donna edp 50ml', 0, 0, 0, 15, 0.3, NULL, 0),
(2210, 189657, '200269', NULL, 'Beauty Line Sp. z o.o.', 18, ' 8011530820022 ', '202009251541', 'Trussardi Donna edp 100ml', 0, 0, 0, 15, 0.453, NULL, 0),
(2211, 189662, '200269', NULL, 'Beauty Line Sp. z o.o.', 11, ' 8011530851040 ', '202009251541', 'Trussardi My Name dezodorant spray 100ml', 0, 0, 0, 15, 0.2515, NULL, 0),
(2212, 189666, '200269', NULL, 'Beauty Line Sp. z o.o.', 8, ' 8011530840006 ', '202009251541', 'Trussardi Donna Delicate Rose edt 30ml', 0, 0, 0, 15, 0.1655, NULL, 0),
(2213, 332005, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2026, ' 5903416001706 ', '202009251541', 'EVEL lakier żelowy 30, 8 ml', 0, 0, 0, 15, 0.0427, NULL, 0),
(2214, 189667, '200269', NULL, 'Beauty Line Sp. z o.o.', 1, ' 8011530840013 ', '202009251541', 'Trussardi Donna Delicate Rose edt 50ml', 0, 0, 0, 15, 0.3005, NULL, 0),
(2215, 332007, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1565, ' 5903416001720 ', '202009251541', 'EVEL lakier żelowy 32, 8 ml', 0, 0, 0, 15, 0.0426, NULL, 0),
(2216, 332006, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1897, ' 5903416001713 ', '202009251541', 'EVEL lakier żelowy 31, 8 ml', 0, 0, 0, 15, 0.0424, NULL, 0),
(2217, 189675, '200269', NULL, 'Beauty Line Sp. z o.o.', 13, ' 8011530830038 ', '202009251541', 'Trussardi My Land woda po goleniu 100ml', 0, 0, 0, 15, 0.397, NULL, 0),
(2218, 271593, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 4703, ' 3614224298982 ', '202009251541', 'Bourjois kredka do ust Miraculous 0,3 g', 0, 0, 0, 15, 0.0049, NULL, 0),
(2219, 332008, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2519, ' 5903416001737 ', '202009251541', 'EVEL lakier żelowy 33, 8 ml', 0, 0, 0, 15, 0.046, NULL, 0),
(2220, 211186, '200269', NULL, 'Labori International b.v. IN-OUT', 2, ' 3607342455610 ', '202009251541', 'Vera Wang Lovestruck Floral Rush edp 100ml', 0, 0, 0, 15, 0.54, NULL, 0),
(2221, 189680, '200269', NULL, 'Beauty Line Sp. z o.o.', 3, ' 8011530811037 ', '202009251541', 'Trussardi Uomo dezodorant w sprayu 100ml', 0, 0, 0, 15, 0.13, NULL, 0),
(2222, 254199, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 675, ' 5901761936117 ', '202009251541', 'EVEL. cienie Quattro Eye Shadow 09/ 5,2g', 0, 0, 0, 15, 0.046, NULL, 0),
(2223, 254203, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1297, ' 5901761936131 ', '202009251541', 'EVEL. cienie Quattro Eye Shadow 11/ 5,2g', 0, 0, 0, 15, 0.0423, NULL, 0),
(2224, 254201, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 664, ' 5901761936124 ', '202009251541', 'EVEL. cienie Quattro Eye Shadow 10/ 5,2g', 0, 0, 0, 15, 0.0427, NULL, 0),
(2225, 271609, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 3300, ' 3614224103002 ', '202009251541', 'Bourjois szminka Velvet Lipstick 11, 2,4 g', 0, 0, 0, 15, 0.0236, NULL, 0),
(2226, 254205, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1109, ' 5901761936148 ', '202009251541', 'EVEL. cienie Quattro Eye Shadow 12/ 5,2g', 0, 0, 0, 15, 0.0458, NULL, 0),
(2227, 271610, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 3400, ' 3614224103019 ', '202009251541', 'Bourjois szminka Velvet Lipstick 12, 2,4 g', 0, 0, 0, 15, 0.0235, NULL, 0),
(2228, 333060, '200269', NULL, 'Baltic Company Sp. z o.o', 38, ' 8011607322626 ', '202009251541', 'Pupa rozświetlacz w kremie Glow Obsession 002, 9g', 0, 0, 0, 15, 0.07, NULL, 0),
(2229, 333057, '200269', NULL, 'Baltic Company Sp. z o.o', 99, ' 8011607322558 ', '202009251541', 'Pupa rozświetlacz w płynie Glow Obsession 001 30ml', 0, 0, 0, 15, 0.052, NULL, 0),
(2230, 333059, '200269', NULL, 'Baltic Company Sp. z o.o', 17, ' 8011607322619 ', '202009251541', 'Pupa rozświetlacz w kremie Glow Obsession 001, 9g', 0, 0, 0, 15, 0.07, NULL, 0),
(2231, 333058, '200269', NULL, 'Baltic Company Sp. z o.o', 133, ' 8011607322565 ', '202009251541', 'Pupa rozświetlacz w płynie Glow Obsession 002 30ml', 0, 0, 0, 15, 0.052, NULL, 0),
(2232, 250125, '200269', NULL, 'SAR Trading  B.V. IN-OUT', 1, ' 0737052377865 ', '202009251541', 'Escada Absolutely Me edp 30 ml', 0, 0, 0, 15, 0.15, NULL, 0),
(2233, 267530, '200269', NULL, 'Jota .2', 622, ' 5901350455586 ', '202009251541', 'Delia lakier Coral - Hybrid Gel 08, 11ml', 0, 0, 0, 15, 0.0558, NULL, 0),
(2234, 237851, '200269', NULL, 'ADOS  P.P.H.U.', 461, ' 5906942210185 ', '202009251541', 'Ados lakier Premium Line nr 18, 12ml', 0, 0, 0, 15, 0.072, NULL, 0),
(2235, 189723, '200269', NULL, 'Beauty Gallery IN-OUT', 2, ' 3351500963041 ', '202009251541', 'Azzaro Duo men edt 30ml', 0, 0, 0, 15, 0.16, NULL, 0),
(2236, 194849, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2361, ' 3052503260310 ', '202009251541', 'Bourjois pomadka Rouge Velvet nr 03 6,7ml', 0, 0, 0, 15, 0.0283, NULL, 0),
(2237, 189738, '200269', NULL, 'Beauty Gallery IN-OUT', 3, ' 3595200116646 ', '202009251541', 'Lolita Lempicka L\'eau en Blanc edp 50ml', 0, 0, 0, 15, 0.195, NULL, 0),
(2238, 189736, '200269', NULL, 'Beauty Gallery IN-OUT', 8, ' 3595200116653 ', '202009251541', 'Lolita Lempicka L\'eau en Blanc edp 30ml', 0, 0, 0, 15, 0.1455, NULL, 0),
(2239, 189742, '200269', NULL, 'Beauty Gallery IN-OUT', 2, ' 3351500956524 ', '202009251541', 'Azzaro Twin man edt 80ml', 0, 0, 0, 15, 0.309, NULL, 0),
(2240, 237879, '200269', NULL, 'ADOS  P.P.H.U.', 630, ' 5906942550175 ', '202009251541', 'Ados lakier Long Lasting nr 17, 9ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2241, 237883, '200269', NULL, 'ADOS  P.P.H.U.', 500, ' 5906942550212 ', '202009251541', 'Ados lakier Long Lasting nr 21, 9ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2242, 192825, '200269', NULL, 'Aventus B.V.', 6, ' 0088300162512 ', '202009251541', 'CK Euphoria edp 100ml', 0, 0, 0, 15, 0.3695, NULL, 0),
(2243, 168254, '200269', NULL, 'Beauty Gallery IN-OUT', 8, ' 3607345849416 ', '202009251541', 'Joop! Splash for men limited edition 115ml', 0, 0, 0, 15, 0.418, NULL, 0);
INSERT INTO `shipment_product` (`id`, `art_number`, `art_return`, `art_volume`, `company`, `counter`, `ean`, `last_update`, `name`, `scan_correct`, `scan_error`, `scan_label`, `shipment_id`, `weight`, `scan_log`, `scan_utilization`) VALUES
(2244, 168255, '200269', NULL, 'CRM Trading Ltd  IN-OUT', 5, ' 3607345771526 ', '202009251541', 'Joop! Splash edt40ml, zel pod pr75ml', 0, 0, 0, 15, 0.332, NULL, 0),
(2245, 233799, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2374, ' 3614222074533 ', '202009251541', 'Rimm. eyeliner Colour Precise 001, 1ml', 0, 0, 0, 15, 0.007, NULL, 0),
(2246, 280910, '200269', NULL, 'ADOS  P.P.H.U.', 452, ' 5906942210406 ', '202009251541', 'Ados lakier Premium Line nr 40, 12ml', 0, 0, 0, 15, 0.07, NULL, 0),
(2247, 237900, '200269', NULL, 'ADOS  P.P.H.U.', 519, ' 5906942550373 ', '202009251541', 'Ados lakier Long Lasting nr 37, 9ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2248, 319828, '200269', NULL, 'Jota .2', 618, ' 5901350479773 ', '202009251541', 'Delia lakier do paznokci S-size 208, 5 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2249, 280917, '200269', NULL, 'ADOS  P.P.H.U.', 311, ' 5906942550786 ', '202009251541', 'Ados lakier Long Lasting nr 78, 9ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2250, 281942, '200269', NULL, 'LOreal makijaz', 1356, ' 3600531484590 ', '202009251541', 'May. paletka cieni Temptation, 80g', 0, 0, 0, 15, 0.0726, NULL, 0),
(2251, 189777, '200269', NULL, 'Beauty Gallery IN-OUT', 3, ' 3274870222351 ', '202009251541', 'Givenchy \"Pi\" Neo man edt 50ml', 0, 0, 0, 15, 0.262, NULL, 0),
(2252, 280913, '200269', NULL, 'ADOS  P.P.H.U.', 359, ' 5906942210413 ', '202009251541', 'Ados lakier Premium Line nr 41, 12ml', 0, 0, 0, 15, 0.0713, NULL, 0),
(2253, 296273, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1826, ' 5901761981070 ', '202009251541', 'EVEL. lakier żelowy 02, 8ml', 0, 0, 0, 15, 0.0427, NULL, 0),
(2254, 319825, '200269', NULL, 'Jota .2', 560, ' 5901350479742 ', '202009251541', 'Delia lakier do paznokci S-size 205, 5 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2255, 280914, '200269', NULL, 'ADOS  P.P.H.U.', 144, ' 5906942550762 ', '202009251541', 'Ados lakier Long Lasting nr 76, 9ml', 0, 0, 0, 15, 0.0476, NULL, 0),
(2256, 319826, '200269', NULL, 'Jota .2', 729, ' 5901350479759 ', '202009251541', 'Delia lakier do paznokci S-size 206, 5 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2257, 296275, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 947, ' 5901761981087 ', '202009251541', 'EVEL. lakier żelowy 03, 8ml', 0, 0, 0, 15, 0.043, NULL, 0),
(2258, 245083, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 0737052802800 ', '202009251541', 'Hugo Boss Ma Vie edp 75ml', 0, 0, 0, 15, 0.252, NULL, 0),
(2259, 296284, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1165, ' 5901761981117 ', '202009251541', 'EVEL. lakier żelowy 06, 8ml', 0, 0, 0, 15, 0.0422, NULL, 0),
(2260, 189785, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 0088300606504 ', '202009251541', 'CK Obsession men edt 75ml', 0, 0, 0, 15, 0.21, NULL, 0),
(2261, 296287, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 457, ' 5901761981131 ', '202009251541', 'EVEL. lakier żelowy 08, 8ml', 0, 0, 0, 15, 0.0423, NULL, 0),
(2262, 319839, '200269', NULL, 'Jota .2', 716, ' 5901350479889 ', '202009251541', 'Delia lakier do paznokci S-size 219, 5 ml', 0, 0, 0, 15, 0.024, NULL, 0),
(2263, 320863, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 866, ' 5901761985016 ', '202009251541', 'EVEL. lakier Glow Go 01, 8ml', 0, 0, 0, 15, 0.0426, NULL, 0),
(2264, 140638, '200269', NULL, 'Beauty Gallery Sp. z o.o. Sp. komandytowa', 1, ' 3414202011752 ', '202009251541', 'Davidoff Cool Water Woman edt 100ml', 0, 0, 0, 15, 0.257, NULL, 0),
(2265, 280921, '200269', NULL, 'ADOS  P.P.H.U.', 445, ' 5906942550809 ', '202009251541', 'Ados lakier Long Lasting nr 80, 9ml', 0, 0, 0, 15, 0.046, NULL, 0),
(2266, 296281, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1078, ' 5901761981094 ', '202009251541', 'EVEL. lakier żelowy 04, 8ml', 0, 0, 0, 15, 0.0423, NULL, 0),
(2267, 319833, '200269', NULL, 'Jota .2', 737, ' 5901350479827 ', '202009251541', 'Delia lakier do paznokci S-size 213, 5 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2268, 296282, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 663, ' 5901761981100 ', '202009251541', 'EVEL. lakier żelowy 05, 8ml', 0, 0, 0, 15, 0.0424, NULL, 0),
(2269, 280932, '200269', NULL, 'ADOS  P.P.H.U.', 278, ' 5906942550816 ', '202009251541', 'Ados lakier Long Lasting nr 81, 9ml', 0, 0, 0, 15, 0.0473, NULL, 0),
(2270, 280933, '200269', NULL, 'ADOS  P.P.H.U.', 264, ' 5906942550823 ', '202009251541', 'Ados lakier Long Lasting nr 82, 9ml', 0, 0, 0, 15, 0.0473, NULL, 0),
(2271, 296293, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 490, ' 5901761981162 ', '202009251541', 'EVEL. lakier żelowy 11, 8ml', 0, 0, 0, 15, 0.0426, NULL, 0),
(2272, 239969, '200269', NULL, 'Satoki Partners Oy IN-OUT', 3, ' 5060152406292 ', '202009251541', 'Cristiano Ronaldo Legacy deo stick 75g', 0, 0, 0, 15, 0.167, NULL, 0),
(2273, 271718, '200269', NULL, 'Oceanic S.A', 2060, ' 5900116030340 ', '202009251541', 'AA W.O.C eyeliner Wow Grow 04, 6 ml', 0, 0, 0, 15, 0.0136, NULL, 0),
(2274, 296294, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 430, ' 5901761981179 ', '202009251541', 'EVEL. lakier żelowy 12, 8ml', 0, 0, 0, 15, 0.0423, NULL, 0),
(2275, 319846, '200269', NULL, 'Jota .2', 559, ' 5901350479957 ', '202009251541', 'Delia lakier do paznokci S-size 226, 5 ml', 0, 0, 0, 15, 0.0226, NULL, 0),
(2276, 319840, '200269', NULL, 'Jota .2', 701, ' 5901350479896 ', '202009251541', 'Delia lakier do paznokci S-size 220, 5 ml', 0, 0, 0, 15, 0.0213, NULL, 0),
(2277, 320864, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2134, ' 5901761985023 ', '202009251541', 'EVEL. lakier Glow Go 02, 8ml', 0, 0, 0, 15, 0.0426, NULL, 0),
(2278, 320865, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1857, ' 5901761985030 ', '202009251541', 'EVEL. lakier Glow Go 03, 8ml', 0, 0, 0, 15, 0.0426, NULL, 0),
(2279, 296290, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 966, ' 5901761981148 ', '202009251541', 'EVEL. lakier żelowy 09, 8ml', 0, 0, 0, 15, 0.0421, NULL, 0),
(2280, 320866, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1805, ' 5901761985047 ', '202009251541', 'EVEL. lakier Glow Go 04, 8ml', 0, 0, 0, 15, 0.0423, NULL, 0),
(2281, 165221, '200269', NULL, 'Star Global Trading IN-OUT', 2, ' 3351500970087 ', '202009251541', 'Azzaro Decibel edt 25ml', 0, 0, 0, 15, 0.11, NULL, 0),
(2282, 296291, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 438, ' 5901761981155 ', '202009251541', 'EVEL. lakier żelowy 10, 8ml', 0, 0, 0, 15, 0.0428, NULL, 0),
(2283, 280941, '200269', NULL, 'ADOS  P.P.H.U.', 347, ' 5906942550847 ', '202009251541', 'Ados lakier Long Lasting nr 84, 9ml', 0, 0, 0, 15, 0.0478, NULL, 0),
(2284, 319849, '200269', NULL, 'Jota .2', 615, ' 5901350479988 ', '202009251541', 'Delia lakier do paznokci S-size 229, 5 ml', 0, 0, 0, 15, 0.0217, NULL, 0),
(2285, 319861, '200269', NULL, 'Jota .2', 643, ' 5901350480106 ', '202009251541', 'Delia lakier do paznokci S-size 241, 5 ml', 0, 0, 0, 15, 0.0226, NULL, 0),
(2286, 250225, '200269', NULL, 'Beauty Gallery IN-OUT', 4, ' 8034097956270 ', '202009251541', 'S. Ferragamo Incanto Amity edt 50 ml', 0, 0, 0, 15, 0.211, NULL, 0),
(2287, 319862, '200269', NULL, 'Jota .2', 566, ' 5901350480113 ', '202009251541', 'Delia lakier do paznokci S-size 242, 5 ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2288, 177524, '200269', NULL, 'Wibo Sp. z o.o.', 648, ' 5901571045054 ', '202009260659', 'Lovely lakier Classic Nail Polish 346, 8ml', 2, 0, 0, 15, 0.0446, NULL, 0),
(2289, 280947, '200269', NULL, 'ADOS  P.P.H.U.', 324, ' 5906942530498 ', '202009251541', 'Ados lakier Gel Lack nr 49, 8ml', 0, 0, 0, 15, 0.044, NULL, 0),
(2290, 319859, '200269', NULL, 'Jota .2', 596, ' 5901350480083 ', '202009251541', 'Delia lakier do paznokci S-size 239, 5 ml', 0, 0, 0, 15, 0.0226, NULL, 0),
(2291, 293247, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2040, ' 3614226472175 ', '202009251541', 'Rimm. szminka w płynie Stay Satin 130, 5,5 ml', 0, 0, 0, 15, 0.0198, NULL, 0),
(2292, 189822, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3274870001000 ', '202009251541', 'Kenzo Madly woman edp 80ml', 0, 0, 0, 15, 0.287, NULL, 0),
(2293, 261502, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1879, ' 5901761936889 ', '202009251541', 'EVEL. cienie Quattro Eye Shadow 13/ 5,2g', 0, 0, 0, 15, 0.0423, NULL, 0),
(2294, 237959, '200269', NULL, 'ADOS  P.P.H.U.', 665, ' 5906942550458 ', '202009251541', 'Ados lakier Long Lasting nr 45, 9ml', 0, 0, 0, 15, 0.0478, NULL, 0),
(2295, 293248, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2712, ' 3614226472212 ', '202009251541', 'Rimm. szminka w płynie Stay Satin 430, 5,5 ml', 0, 0, 0, 15, 0.02, NULL, 0),
(2296, 293249, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2415, ' 3614226472236 ', '202009251541', 'Rimm. szminka w płynie Stay Satin 600, 5,5 ml', 0, 0, 0, 15, 0.02, NULL, 0),
(2297, 313730, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1172, ' 5901761984675 ', '202009251541', 'EVEL. lakier żelowy 14, 8ml', 0, 0, 0, 15, 0.0422, NULL, 0),
(2298, 313731, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1219, ' 5901761984682 ', '202009251541', 'EVEL. lakier żelowy 15, 8ml', 0, 0, 0, 15, 0.0428, NULL, 0),
(2299, 312724, '200269', NULL, 'Pricecheck Toiletries Ltd', 3, ' 3614224342982 ', '202009251541', 'Marc Jacobs Daisy edp 100ml', 0, 0, 0, 15, 0.3627, NULL, 0),
(2300, 313748, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 2028, ' 3614227960091 ', '202009251541', 'M.F. paleta rózy Miracle Cheek Duo 20, 1 szt.', 0, 0, 0, 15, 0.0537, NULL, 0),
(2301, 313749, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 2946, ' 3614227128613 ', '202009251541', 'M.F. paleta Miracle Contour Duo, 1 szt.', 0, 0, 0, 15, 0.0527, NULL, 0),
(2302, 245143, '200269', NULL, 'Beauty Gallery IN-OUT', 10, ' 0022548315378 ', '202009251541', 'DKNY Be Delicious Dreamsicle edp 50ml', 0, 0, 0, 15, 0.27, NULL, 0),
(2303, 264592, '200269', NULL, 'Beauty Gallery IN-OUT', 8, ' 3274871943453 ', '202009251541', 'Givenchy Electric Rose Woman edt 75 ml', 0, 0, 0, 15, 0.28, NULL, 0),
(2304, 293264, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 5376, ' 3614226326195 ', '202009251541', 'Rimm. tusz Scan Eyes Wow 12 ml', 0, 0, 0, 15, 0.0264, NULL, 0),
(2305, 282014, '200269', NULL, 'Coty Eastern Europe Max Factor sp. z o.o.', 4835, ' 0000096166826 ', '202009251541', 'M.F. mascara DarkMagic czarny 10 ml', 0, 0, 0, 15, 0.029, NULL, 0),
(2306, 264600, '200269', NULL, 'Beauty Gallery IN-OUT', 13, ' 3607342630987 ', '202009251541', 'Cerruti 1881 Aqua Forte Homme edt 125ml', 0, 0, 0, 15, 0.384, NULL, 0),
(2307, 313769, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1246, ' 5901761984699 ', '202009251541', 'EVEL. lakier żelowy 16, 8ml', 0, 0, 0, 15, 0.0421, NULL, 0),
(2308, 313780, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1028, ' 5901761984705 ', '202009251541', 'EVEL. lakier żelowy 17, 8ml', 0, 0, 0, 15, 0.042, NULL, 0),
(2309, 313789, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1710, ' 5901761984712 ', '202009251541', 'EVEL. lakier żelowy 18, 8ml', 0, 0, 0, 15, 0.0423, NULL, 0),
(2310, 313791, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 692, ' 5901761984743 ', '202009251541', 'EVEL. lakier żelowy 20, 8ml', 0, 0, 0, 15, 0.042, NULL, 0),
(2311, 313792, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1992, ' 5901761984736 ', '202009251541', 'EVEL. lakier żelowy 21 8ml', 0, 0, 0, 15, 0.0424, NULL, 0),
(2312, 313793, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 868, ' 5901761984750 ', '202009251541', 'EVEL. lakier żelowy 22, 8ml', 0, 0, 0, 15, 0.0426, NULL, 0),
(2313, 313794, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1497, ' 5901761984767 ', '202009251541', 'EVEL. lakier żelowy 23, 8ml', 0, 0, 0, 15, 0.042, NULL, 0),
(2314, 313795, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 358, ' 5901761984774 ', '202009251541', 'EVEL. lakier żelowy 24, 8ml', 0, 0, 0, 15, 0.0427, NULL, 0),
(2315, 313804, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2113, ' 5901761984811 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 317,5ml', 0, 0, 0, 15, 0.0351, NULL, 0),
(2316, 223690, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 3274872268029 ', '202009251541', 'Kenzo Flower zestaw dla kobiet 1 szt.', 0, 0, 0, 15, 0.651, NULL, 0),
(2317, 313805, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1788, ' 5901761987331 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 318,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2318, 313806, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1990, ' 5901761987348 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 319,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2319, 295369, '200269', NULL, 'Simex Trading AG IN-OUT', 166, ' 3614271566560 ', '202009251541', 'YSL Opium black Floral Shock edp 30 ml', 0, 0, 0, 15, 0.14, NULL, 0),
(2320, 188876, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1077, ' 3607349703424 ', '202009251541', 'M.S. cień pojedynczy Studio S 109, 3g', 0, 0, 0, 15, 0.0125, NULL, 0),
(2321, 239059, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 481, ' 0074170422986 ', '202009251541', 'SH lakier Miracle Gel 200, 14,7 ml', 0, 0, 0, 15, 0.0727, NULL, 0),
(2322, 274898, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 8005610258430 ', '202009251541', 'Hugo Boss Bottled Intense edp 50ml', 0, 0, 0, 15, 0.204, NULL, 0),
(2323, 274899, '200269', NULL, 'JTG B.V.', 7, ' 0737052766775 ', '202009251541', 'Hugo Boss Bottled Unlimited edt 100ml', 0, 0, 0, 15, 0.322, NULL, 0),
(2324, 279005, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 4850, ' 3614224218591 ', '202009251541', 'Rimm. rozświetlacz Insta Strobbing 002', 0, 0, 0, 15, 0.0335, NULL, 0),
(2325, 279003, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 5117, ' 3614224218584 ', '202009251541', 'Rimm. rozświetlacz Insta Strobbing 001', 0, 0, 0, 15, 0.0338, NULL, 0),
(2326, 282094, '200269', NULL, 'Oceanic S.A', 1676, ' 5900116041544 ', '202009251541', 'AA W.O.C lakier Nail Lacquer 24, 11 ml', 0, 0, 0, 15, 0.0575, NULL, 0),
(2327, 232947, '200269', NULL, 'Labori International b.v. IN-OUT', 1, ' 0719346158657 ', '202009251541', 'Juicy Couture La La edp 100ml', 0, 0, 0, 15, 0.536, NULL, 0),
(2328, 314868, '200269', NULL, 'Jota .2', 538, ' 5901350478196 ', '202009251541', 'Delia lakier Coral - Hybrid Gel 47, 11ml', 0, 0, 0, 15, 0.0551, NULL, 0),
(2329, 314869, '200269', NULL, 'Jota .2', 503, ' 5901350478202 ', '202009251541', 'Delia lakier Coral - Hybrid Gel 48, 11ml', 0, 0, 0, 15, 0.0553, NULL, 0),
(2330, 266736, '200269', NULL, 'Oceanic S.A', 669, ' 5900116029429 ', '202205032216', 'AA W.O.C błyszczyk Jelly 3, 8 ml', 23, 64, 33, 15, NULL, '15+1+16+18+15+1+1+1+2+1+1', 0),
(2331, 314867, '200269', NULL, 'Jota .2', 561, ' 5901350478189 ', '202009251541', 'Delia lakier Coral - Hybrid Gel 46, 11ml', 0, 0, 0, 15, 0.056, NULL, 0),
(2332, 281088, '200269', NULL, 'Labori International b.v.', 6, ' 8159400202001  0827669027038 ', '202009251541', 'Ford Mustang edt 100ml', 0, 0, 0, 15, 0.3645, NULL, 0),
(2333, 281089, '200269', NULL, 'Labori International b.v.', 1, ' 0827669027052  8159400202049 ', '202009251541', 'Ford Mustang Sport edt 100ml', 0, 0, 0, 15, 0.365, NULL, 0),
(2334, 281090, '200269', NULL, 'Labori International b.v.', 1, ' 0827669030908  8159400202087 ', '202009251541', 'Ford Mustang Performance edt 100ml', 0, 0, 0, 15, 0.381, NULL, 0),
(2335, 266764, '200269', NULL, 'Oceanic S.A', 136, ' 5900116030012 ', '202009251541', 'AA W.O.C cień do powiek Mono 52, 1,8 g', 0, 0, 0, 15, 0.019, NULL, 0),
(2336, 266765, '200269', NULL, 'Oceanic S.A', 62, ' 5900116030067 ', '202009251541', 'AA W.O.C cień do powiek Mono 53,  1,8 g', 0, 0, 0, 15, 0.0192, NULL, 0),
(2337, 266766, '200269', NULL, 'Oceanic S.A', 79, ' 5900116030043 ', '202009251541', 'AA W.O.C cień do powiek Mono 54, 1,8 g', 0, 0, 0, 15, 0.0192, NULL, 0),
(2338, 266767, '200269', NULL, 'Oceanic S.A', 80, ' 5900116030029 ', '202009251541', 'AA W.O.C cień do powiek Mono 55, 1,8 g', 0, 0, 0, 15, 0.0193, NULL, 0),
(2339, 266762, '200269', NULL, 'Oceanic S.A', 193, ' 5900116029993 ', '202009251541', 'AA W.O.C cień do powiek Mono 50,  1,8 g', 0, 0, 0, 15, 0.019, NULL, 0),
(2340, 266763, '200269', NULL, 'Oceanic S.A', 300, ' 5900116030005 ', '202009251541', 'AA W.O.C cień do powiek Mono 51, 1,8 g', 0, 0, 0, 15, 0.0193, NULL, 0),
(2341, 266772, '200269', NULL, 'Oceanic S.A', 102, ' 5900116030050 ', '202009251541', 'AA W.O.C cień do powiek Mono 58, 1,8 g', 0, 0, 0, 15, 0.0191, NULL, 0),
(2342, 266773, '200269', NULL, 'Oceanic S.A', 43, ' 5900116030098 ', '202009251541', 'AA W.O.C cień do powiek Mono 59,  1,8 g', 0, 0, 0, 15, 0.0188, NULL, 0),
(2343, 282134, '200269', NULL, 'Oceanic S.A', 1813, ' 5900116041575 ', '202009251541', 'AA W.O.C lakier Nail Lacquer 27, 11 ml', 0, 0, 0, 15, 0.0575, NULL, 0),
(2344, 266775, '200269', NULL, 'Oceanic S.A', 225, ' 5900116030036 ', '202009251541', 'AA W.O.C cień do powiek Mono 60,  1,8 g', 0, 0, 0, 15, 0.0191, NULL, 0),
(2345, 266768, '200269', NULL, 'Oceanic S.A', 86, ' 5900116030074 ', '202009251541', 'AA W.O.C cień do powiek Mono 56, 1,8 g', 0, 0, 0, 15, 0.0193, NULL, 0),
(2346, 266770, '200269', NULL, 'Oceanic S.A', 120, ' 5900116030104 ', '202009251541', 'AA W.O.C cień do powiek Mono 57, 1,8 g', 0, 0, 0, 15, 0.0193, NULL, 0),
(2347, 313885, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2497, ' 5901761987386 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 323,5ml', 0, 0, 0, 15, 0.0353, NULL, 0),
(2348, 313887, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2362, ' 5901761987393 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 324,5ml', 0, 0, 0, 15, 0.0346, NULL, 0),
(2349, 203295, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1663, ' 3052503300719 ', '202009251541', 'Bourjois Konturówka C. Levre 07, 1,14g', 0, 0, 0, 15, 0.0037, NULL, 0),
(2350, 266776, '200269', NULL, 'Oceanic S.A', 128, ' 5900116030081 ', '202009251541', 'AA W.O.C cień do powiek Mono 61, 1,8 g', 0, 0, 0, 15, 0.019, NULL, 0),
(2351, 292376, '200269', NULL, 'ADOS  P.P.H.U.', 539, ' 5906942210437 ', '202009251541', 'Ados lakier Premium Line nr 43, 12ml', 0, 0, 0, 15, 0.0723, NULL, 0),
(2352, 203294, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1184, ' 3052503300610 ', '202009251541', 'Bourjois Konturówka C. Levre 06, 1,14g', 0, 0, 0, 15, 0.0038, NULL, 0),
(2353, 313881, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2397, ' 5901761987355 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 320,5ml', 0, 0, 0, 15, 0.0353, NULL, 0),
(2354, 203293, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1632, ' 3052503300511 ', '202009251541', 'Bourjois Konturówka C. Levre 05, 1,14g', 0, 0, 0, 15, 0.004, NULL, 0),
(2355, 313882, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2149, ' 5901761987362 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 321,5ml', 0, 0, 0, 15, 0.0351, NULL, 0),
(2356, 292379, '200269', NULL, 'ADOS  P.P.H.U.', 507, ' 5906942550854 ', '202009251541', 'Ados lakier Long Lasting nr 85, 9 ml', 0, 0, 0, 15, 0.0475, NULL, 0),
(2357, 313892, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2005, ' 5901761987423 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 327,5ml', 0, 0, 0, 15, 0.0356, NULL, 0),
(2358, 203297, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1619, ' 3052503301013 ', '202009251541', 'Bourjois Konturówka C. Levre 10, 1,14g', 0, 0, 0, 15, 0.004, NULL, 0),
(2359, 292390, '200269', NULL, 'ADOS  P.P.H.U.', 658, ' 5906942530542 ', '202009251541', 'Ados lakier Gel Lack nr 54, 8ml', 0, 0, 0, 15, 0.0439, NULL, 0),
(2360, 203296, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1347, ' 3052503300818 ', '202009251541', 'Bourjois Konturówka C. Levre 08, 1,14g', 0, 0, 0, 15, 0.0039, NULL, 0),
(2361, 292391, '200269', NULL, 'ADOS  P.P.H.U.', 527, ' 5906942530559 ', '202009251541', 'Ados lakier Gel Lack nr 55, 8ml', 0, 0, 0, 15, 0.0438, NULL, 0),
(2362, 313889, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2701, ' 5901761987409 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 325,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2363, 313890, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1988, ' 5901761987416 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 326,5ml', 0, 0, 0, 15, 0.0349, NULL, 0),
(2364, 292387, '200269', NULL, 'ADOS  P.P.H.U.', 499, ' 5906942550939 ', '202009251541', 'Ados lakier Long Lasting nr 93, 9 ml', 0, 0, 0, 15, 0.0475, NULL, 0),
(2365, 203304, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1625, ' 3052503300214 ', '202009251541', 'Bourjois Konturówka C. Levre 02, 1,14g', 0, 0, 0, 15, 0.0038, NULL, 0),
(2366, 311860, '200269', NULL, 'Błysk Sp. z o.o', 382, ' 5902693161950 ', '202009251541', 'Provocater lakier hybrydowy 108, 4ml', 0, 0, 0, 15, 0.0213, NULL, 0),
(2367, 259639, '200269', NULL, 'TRADE LAB Sp z o.o. SP.K BOHO MANU', 8, ' 3760220171153 ', '202009251541', 'Boho róż do policzków Bois de Rose 01, 4,5 g', 0, 0, 0, 15, 0.015, NULL, 0),
(2368, 272946, '200269', NULL, 'JTG B.V.', 4, ' 8011530001148 ', '202009251541', 'Laura Biagiotti Laura edt 75 ml', 0, 0, 0, 15, 0.27, NULL, 0),
(2369, 311859, '200269', NULL, 'Błysk Sp. z o.o', 380, ' 5902693161943 ', '202009251541', 'Provocater lakier hybrydowy 107, 4ml', 0, 0, 0, 15, 0.019, NULL, 0),
(2370, 212536, '200269', NULL, 'Labori International b.v. IN-OUT', 1, ' 0688575179415 ', '202009251541', 'Vera Wang Princess edt 100ml', 0, 0, 0, 15, 0.364, NULL, 0),
(2371, 266815, '200269', NULL, 'Oceanic S.A', 1081, ' 5900116029276 ', '202009251541', 'AA W.O.C kredka All Day 2, 0,33 g', 0, 0, 0, 15, 0.0049, NULL, 0),
(2372, 259650, '200269', NULL, 'TRADE LAB Sp z o.o. SP.K BOHO MANU', 13, ' 3760220172204 ', '202009251541', 'Boho róż do policzków Lie de Vin 06, 4,5 g', 0, 0, 0, 15, 0.0167, NULL, 0),
(2373, 166465, '200269', NULL, 'Simex Trading AG IN-OUT', 8, ' 0737052211541 ', '202009251541', 'Dunhill Black 30ml', 0, 0, 0, 15, 0.228, NULL, 0),
(2374, 244288, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 411, ' 0074170438147 ', '202009251541', 'SH lakier Miracle Gel 445, 14,7 ml', 0, 0, 0, 15, 0.073, NULL, 0),
(2375, 266823, '200269', NULL, 'Oceanic S.A', 259, ' 5900116029986 ', '202009251541', 'AA W.O.C modelator 3 Step Face Designer 16,5 g', 0, 0, 0, 15, 0.1012, NULL, 0),
(2376, 137798, '200269', NULL, 'Simex Trading AG IN-OUT', 7, ' 3386460013628 ', '202009251541', 'Burberry The Beat Man edt 50ml', 0, 0, 0, 15, 0.216, NULL, 0),
(2377, 266817, '200269', NULL, 'Oceanic S.A', 1364, ' 5900116029283 ', '202009251541', 'AA W.O.C kredka All Day 3, 0,33 g', 0, 0, 0, 15, 0.005, NULL, 0),
(2378, 296522, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 3274872323483 ', '202009251541', 'Kenzo World edp 50ml', 0, 0, 0, 15, 0.304, NULL, 0),
(2379, 236115, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 3274872276550 ', '202009251541', 'Givenchy Very Irres.l\'ean en Rose edt 30ml', 0, 0, 0, 15, 0.146, NULL, 0),
(2380, 259671, '200269', NULL, 'TRADE LAB Sp z o.o. SP.K BOHO MANU', 20, ' 3760220171139 ', '202009251541', 'Boho podkład w kompakcie Beige Hale 04, 4,5 g', 0, 0, 0, 15, 0.0155, NULL, 0),
(2381, 266832, '200269', NULL, 'Oceanic S.A', 717, ' 5900116030388 ', '202009251541', 'AA W.O.C olejek do ust Chic 2, 6 ml', 0, 0, 0, 15, 0.0136, NULL, 0),
(2382, 259670, '200269', NULL, 'TRADE LAB Sp z o.o. SP.K BOHO MANU', 12, ' 3760220171122 ', '202009251541', 'Boho podkład w kompakcie Beige Dore 03, 4,5 g', 0, 0, 0, 15, 0.0156, NULL, 0),
(2383, 266833, '200269', NULL, 'Oceanic S.A', 689, ' 5900116030395 ', '202009251541', 'AA W.O.C olejek do ust Chic 3, 6 ml', 0, 0, 0, 15, 0.0133, NULL, 0),
(2384, 236123, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 0783320452109 ', '202009251541', 'BVLGARI Omnia Indian Garnet edt 40ml', 0, 0, 0, 15, 0.173, NULL, 0),
(2385, 266847, '200269', NULL, 'Oceanic S.A', 866, ' 5900116030586 ', '202009251541', 'AA W.O.C podkład Matt Balance 205, 30 ml', 0, 0, 0, 15, 0.042, NULL, 0),
(2386, 236127, '200269', NULL, 'Simex Trading AG IN-OUT', 4, ' 3137370309376 ', '202009251541', 'Nina Ricci Mademoiselle Ricci edp 80ml', 0, 0, 0, 15, 0.334, NULL, 0),
(2387, 266852, '200269', NULL, 'Oceanic S.A', 770, ' 5900116030616 ', '202009251541', 'AA W.O.C podkład Matt Balance 213, 30 ml', 0, 0, 0, 15, 0.0413, NULL, 0),
(2388, 194144, '200269', NULL, 'Schimar Sp. z o.o. Manufaktura', 5, ' 3147754035364 ', '202009251541', 'Lancome Hypnose Homme edt 75ml', 0, 0, 0, 15, 0.473, NULL, 0),
(2389, 200295, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 644, ' 5907609399113 ', '202009251541', 'EVEL. fluid CC Medium BEŻ 52, 30 ml', 0, 0, 0, 15, 0.0433, NULL, 0),
(2390, 280161, '200269', NULL, 'Simex Trading AG IN-OUT', 10, ' 8411061853160 ', '202009251541', 'C.Herrera 212 Men edt 100 ml', 0, 0, 0, 15, 0.4, NULL, 0),
(2391, 62058, '200269', NULL, 'Beauty Gallery IN-OUT', 6, ' 0022548133545 ', '202009251541', 'DKNY Red Delicious Man edt 50ml', 0, 0, 0, 15, 0.272, NULL, 0),
(2392, 200311, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 779, ' 5907609399045 ', '202009251541', 'EVEL. fluid CC BEŻ, 53, 30 ml', 0, 0, 0, 15, 0.0416, NULL, 0),
(2393, 327280, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 3274872366220 ', '202009251541', 'Kenzo L\'eau Par Kenzo Pour Femme dt100ml, BL75ml', 0, 0, 0, 15, 0.478, NULL, 0),
(2394, 246389, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1305, ' 3614222960485 ', '202009251541', 'M.S. szminka Wonder Smooth 102, 1g', 0, 0, 0, 15, 0.023, NULL, 0),
(2395, 266876, '200269', NULL, 'Oceanic S.A', 1266, ' 5900116030562 ', '202009251541', 'AA W.O.C podkład Perfect Beaty 207, 35 ml', 0, 0, 0, 15, 0.1342, NULL, 0),
(2396, 270973, '200269', NULL, 'Baltic Company Sp. z o.o', 147, ' 8011607281688 ', '202009251541', 'Pupa róż do policzków Like a Doll Luminys 100/1,8g', 0, 0, 0, 15, 0.04, NULL, 0),
(2397, 270974, '200269', NULL, 'Baltic Company Sp. z o.o', 119, ' 8011607281701 ', '202009251541', 'Pupa róż do policzków Like a Doll Luminys 102/1,8g', 0, 0, 0, 15, 0.0385, NULL, 0),
(2398, 331391, '200269', NULL, 'Simex Trading AG IN-OUT', 3, ' 3346470135161 ', '202009251541', 'Guerlain Aqua Alleg. Rosa Rossa edt vapo 75 ml', 0, 0, 0, 15, 0.24, NULL, 0),
(2399, 266879, '200269', NULL, 'Oceanic S.A', 1031, ' 5900116029634 ', '202009251541', 'AA W.O.C pomadka Creme 40, 3,8 g', 0, 0, 0, 15, 0.0183, NULL, 0),
(2400, 266873, '200269', NULL, 'Oceanic S.A', 1333, ' 5900116030531 ', '202009251541', 'AA W.O.C podkład Perfect Beaty 203, 35 ml', 0, 0, 0, 15, 0.1333, NULL, 0),
(2401, 266875, '200269', NULL, 'Oceanic S.A', 1270, ' 5900116030555 ', '202009251541', 'AA W.O.C podkład Perfect Beaty 206, 35 ml', 0, 0, 0, 15, 0.1266, NULL, 0),
(2402, 266884, '200269', NULL, 'Oceanic S.A', 233, ' 5900116029597 ', '202009251541', 'AA W.O.C pomadka Creme 45, 3,8 g', 0, 0, 0, 15, 0.0185, NULL, 0),
(2403, 266885, '200269', NULL, 'Oceanic S.A', 906, ' 5900116029665 ', '202009251541', 'AA W.O.C pomadka Creme 46, 3,8 g', 0, 0, 0, 15, 0.018, NULL, 0),
(2404, 266886, '200269', NULL, 'Oceanic S.A', 932, ' 5900116029641 ', '202009251541', 'AA W.O.C pomadka Creme 47, 3,8 g', 0, 0, 0, 15, 0.0183, NULL, 0),
(2405, 266887, '200269', NULL, 'Oceanic S.A', 1076, ' 5900116029627 ', '202009251541', 'AA W.O.C pomadka Creme 48, 3,8 g', 0, 0, 0, 15, 0.0182, NULL, 0),
(2406, 266880, '200269', NULL, 'Oceanic S.A', 1039, ' 5900116029603 ', '202009251541', 'AA W.O.C pomadka Creme 41, 3,8 g', 0, 0, 0, 15, 0.0183, NULL, 0),
(2407, 270976, '200269', NULL, 'Baltic Company Sp. z o.o', 127, ' 8011607281725 ', '202009251541', 'Pupa róż do policzków Like a Doll Luminys 200/1,8g', 0, 0, 0, 15, 0.0417, NULL, 0),
(2408, 266881, '200269', NULL, 'Oceanic S.A', 1136, ' 5900116029672 ', '202009251541', 'AA W.O.C pomadka Creme 42, 3,8 g', 0, 0, 0, 15, 0.0187, NULL, 0),
(2409, 270977, '200269', NULL, 'Baltic Company Sp. z o.o', 79, ' 8011607281763 ', '202009251541', 'Pupa róż do policzków Like a Doll Luminys 300/1,8g', 0, 0, 0, 15, 0.04, NULL, 0),
(2410, 266882, '200269', NULL, 'Oceanic S.A', 1012, ' 5900116029610 ', '202009251541', 'AA W.O.C pomadka Creme 43, 3,8 g', 0, 0, 0, 15, 0.0187, NULL, 0),
(2411, 266883, '200269', NULL, 'Oceanic S.A', 1299, ' 5900116029658 ', '202009251541', 'AA W.O.C pomadka Creme 44, 3,8 g', 0, 0, 0, 15, 0.0184, NULL, 0),
(2412, 270979, '200269', NULL, 'Baltic Company Sp. z o.o', 131, ' 8011607281787 ', '202009251541', 'Pupa róż do policzków Like a Doll Luminys 302/1,8g', 0, 0, 0, 15, 0.0427, NULL, 0),
(2413, 318092, '200269', NULL, 'Wibo Sp. z o.o.', 939, ' 5901801646143 ', '202009251541', 'Wibo rozświetlacz WIBOmood 1, 4,6 g', 0, 0, 0, 15, 0.0176, NULL, 0),
(2414, 318095, '200269', NULL, 'Wibo Sp. z o.o.', 807, ' 5901801646150 ', '202009251541', 'Wibo rozświetlacz WIBOmood 2, 4,6 g', 0, 0, 0, 15, 0.0167, NULL, 0),
(2415, 266888, '200269', NULL, 'Oceanic S.A', 1150, ' 5900116029580 ', '202009251541', 'AA W.O.C pomadka Creme 49, 3,8 g', 0, 0, 0, 15, 0.0185, NULL, 0),
(2416, 266890, '200269', NULL, 'Oceanic S.A', 692, ' 5900116029535 ', '202009251541', 'AA W.O.C pomadka Matt 31, 3,8 g', 0, 0, 0, 15, 0.0188, NULL, 0),
(2417, 250515, '200269', NULL, 'Beauty Gallery IN-OUT', 2, ' 3423473947059 ', '202009251541', 'Issey Miyake Pleats Please edt 30ml', 0, 0, 0, 15, 0.22, NULL, 0),
(2418, 250512, '200269', NULL, 'Beauty Gallery IN-OUT', 6, ' 3423470485196 ', '202009251541', 'Issey Miyake L\'eau Bleue pour homme edt125ml', 0, 0, 0, 15, 0.391, NULL, 0),
(2419, 235166, '200269', NULL, 'Simex Trading AG IN-OUT', 4, ' 0737052513744 ', '202009251541', 'Hugo Boss Orange Charity Men edt 40 ml', 0, 0, 0, 15, 0.216, NULL, 0),
(2420, 193190, '200269', NULL, 'CRM Trading Ltd  IN-OUT', 1, ' 0088300178278  0088300178285 ', '202009251541', 'CK Euphoria Men edt 100ml', 0, 0, 0, 15, 0.43, NULL, 0),
(2421, 250532, '200269', NULL, 'Wibo Sp. z o.o.', 457, ' 5901801617358 ', '202009251541', 'Lovely paleta rozświetlaczy  Nobodys Perfect, 10g', 0, 0, 0, 15, 0.0575, NULL, 0),
(2422, 246441, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1249, ' 3614222960522 ', '202009251541', 'M.S. szminka Wonder Smooth 203, 1g', 0, 0, 0, 15, 0.0233, NULL, 0),
(2423, 266923, '200269', NULL, 'Oceanic S.A', 215, ' 5900116029870 ', '202009251541', 'AA W.O.C rozświetlacz Precious 100, 8,5 g', 0, 0, 0, 15, 0.0538, NULL, 0),
(2424, 266928, '200269', NULL, 'Oceanic S.A', 118, ' 5900116029894 ', '202009251541', 'AA W.O.C rozświetlacz Skin Tone 110, 8 g', 0, 0, 0, 15, 0.0531, NULL, 0),
(2425, 266930, '200269', NULL, 'Oceanic S.A', 121, ' 5900116029900 ', '202009251541', 'AA W.O.C rozświetlacz Skin Tone 111, 8 g', 0, 0, 0, 15, 0.0525, NULL, 0),
(2426, 329394, '200269', NULL, 'Orbico Beauty Sp.zo.o.', 3, ' 8411114057194 ', '202009251541', 'Pepe Jeans for Him edt 100ml', 0, 0, 0, 15, 0.41, NULL, 0),
(2427, 193210, '200269', NULL, 'Kadaco Adam Kacprzyk IN-OUT', 3, ' 3607342402386 ', '202009251541', 'Davidoff Cool Water Sensual Essence Woman edp100ml', 0, 0, 0, 15, 0.25, NULL, 0),
(2428, 246458, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1376, ' 3614222960546 ', '202009251541', 'M.S. szminka Wonder Smooth 301, 1g', 0, 0, 0, 15, 0.0233, NULL, 0),
(2429, 200382, '200269', NULL, 'Beauty Gallery IN-OUT', 3, ' 3607349654306 ', '202009251541', 'CK Down Town zestaw dla kobiet 1 szt.', 0, 0, 0, 15, 0.728, NULL, 0),
(2430, 218830, '200269', NULL, 'LOreal makijaz', 1078, ' 3600531224462 ', '202009251541', 'May.szminka Color Sensational Mat Magnet 950, 4,4g', 0, 0, 0, 15, 0.0223, NULL, 0),
(2431, 330461, '200269', NULL, 'Wibo Sp. z o.o.', 494, ' 5901801647812 ', '202009251541', 'Wibo eyeliner glitterwy Luna Katosu 3 g', 0, 0, 0, 15, 0.0243, NULL, 0),
(2432, 330460, '200269', NULL, 'Wibo Sp. z o.o.', 53, ' 5901801647829 ', '202009251541', 'Wibo eyeliner Triton Katosu 0,8 g', 0, 0, 0, 15, 0.0115, NULL, 0),
(2433, 330463, '200269', NULL, 'Wibo Sp. z o.o.', 1058, ' 5901801647881 ', '202009251541', 'Wibo pomadka w płynie Omega Katosu 2, 2,3 g', 0, 0, 0, 15, 0.024, NULL, 0),
(2434, 330458, '200269', NULL, 'Wibo Sp. z o.o.', 3280, ' 5901801647843 ', '202009251541', 'Wibo Comet liner do brwi 02, 2,7 g', 0, 0, 0, 15, 0.024, NULL, 0),
(2435, 330468, '200269', NULL, 'Wibo Sp. z o.o.', 3826, ' 5901801647867 ', '202009251541', 'Wibo paleta Stellar Katosu 12 g', 0, 0, 0, 15, 0.1226, NULL, 0),
(2436, 316134, '200269', NULL, 'Eris makijaż', 1246, ' 5902860347118 ', '202009251541', 'SinSkin cień Bouncy 025  1 szt - limited2', 0, 0, 0, 15, 0.0296, NULL, 0),
(2437, 316135, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 3386460097994 ', '202009251541', 'Mont Blanc Emblem Absol.edt 100ml,edt7,5ml, zel100', 0, 0, 0, 15, 1.043, NULL, 0),
(2438, 330470, '200269', NULL, 'Wibo Sp. z o.o.', 2427, ' 5901801650324 ', '202009251541', 'Wibo cień Duo Stellar Katosu 2, 3  g', 0, 0, 0, 15, 0.0136, NULL, 0),
(2439, 331494, '200269', NULL, 'Labori International b.v.', 2, ' 0085715325679 ', '202009251541', 'Guess 1981 Man edt100ml, BS 226ml ,zel 200ml', 0, 0, 0, 15, 1.01, NULL, 0),
(2440, 330464, '200269', NULL, 'Wibo Sp. z o.o.', 1880, ' 5901801647898 ', '202009251541', 'Wibo pomadka w płynie Omega Katosu 3, 2,3 g', 0, 0, 0, 15, 0.024, NULL, 0),
(2441, 330467, '200269', NULL, 'Wibo Sp. z o.o.', 100, ' 5901801650454 ', '202009251541', 'Wibo rozświetlacz Galaxy Katosu 9 g', 0, 0, 0, 15, 0.062, NULL, 0),
(2442, 330466, '200269', NULL, 'Wibo Sp. z o.o.', 762, ' 5901801647850 ', '202009251541', 'Wibo kredka nudowa Alpha Katosu 0,8 g', 0, 0, 0, 15, 0.006, NULL, 0),
(2443, 316140, '200269', NULL, 'Eris makijaż', 1111, ' 5902860347125 ', '202009251541', 'SinSkin cień Bouncy 026,  1szt. limited2', 0, 0, 0, 15, 0.027, NULL, 0),
(2444, 330477, '200269', NULL, 'Wibo Sp. z o.o.', 393, ' 5901801652052 ', '202009251541', 'Wibo cienie w płynie Katosu 1, 4 ml', 0, 0, 0, 15, 0.0233, NULL, 0),
(2445, 236264, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 3274872266643 ', '202009251541', 'Givenchy Very Irres.l\'ean en Rose edt 50ml', 0, 0, 0, 15, 0.213, NULL, 0),
(2446, 236271, '200269', NULL, 'Simex Trading AG IN-OUT', 7, ' 0783320911033 ', '202009251541', 'BVLGARI Aqva Amara edt 50ml', 0, 0, 0, 15, 0.219, NULL, 0),
(2447, 330475, '200269', NULL, 'Wibo Sp. z o.o.', 647, ' 5901801650300 ', '202009251541', 'Wibo rozświetlacz Stellar Katosu 3, 3  g', 0, 0, 0, 15, 0.013, NULL, 0),
(2448, 330484, '200269', NULL, 'Wibo Sp. z o.o.', 1143, ' 5901801651536 ', '202009251541', 'Wibo lakier Trendy Nails 4, 8,5 ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2449, 330481, '200269', NULL, 'Wibo Sp. z o.o.', 1011, ' 5901801651505 ', '202009251541', 'Wibo lakier Trendy Nails 1, 8,5 ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2450, 330483, '200269', NULL, 'Wibo Sp. z o.o.', 1135, ' 5901801651529 ', '202009251541', 'Wibo lakier Trendy Nails 3, 8,5 ml', 0, 0, 0, 15, 0.047, NULL, 0),
(2451, 217844, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1233, ' 3614220077741 ', '202009251541', 'Rimm. błyszczyk Oh My Gloss 500/ 6,5 ml', 0, 0, 0, 15, 0.0231, NULL, 0),
(2452, 330482, '200269', NULL, 'Wibo Sp. z o.o.', 1062, ' 5901801651512 ', '202009251541', 'Wibo lakier Trendy Nails 2, 8,5 ml', 0, 0, 0, 15, 0.048, NULL, 0),
(2453, 330495, '200269', NULL, 'Wibo Sp. z o.o.', 442, ' 5901801649359 ', '202009251541', 'Lovely paleta cieni  Surprise Me Ocean Lagoon 6 g', 0, 0, 0, 15, 0.0433, NULL, 0),
(2454, 217854, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1624, ' 3614221316450 ', '202009251541', 'M.S. cień MONO STUDIO MATTE 125 /2,5g', 0, 0, 0, 15, 0.0123, NULL, 0),
(2455, 316155, '200269', NULL, 'Wibo Sp. z o.o.', 1929, ' 5901801645535 ', '202009251541', 'Wibo paleta cieni Mychoice 2 Viva a Viva, 17 g', 0, 0, 0, 15, 0.128, NULL, 0),
(2456, 316160, '200269', NULL, 'Wibo Sp. z o.o.', 2237, ' 5901801645573 ', '202009251541', 'Wibo cienie Mychoice Viva a Viva/4 Mistery, 4,4 g', 0, 0, 0, 15, 0.017, NULL, 0),
(2457, 236293, '200269', NULL, 'Simex Trading AG IN-OUT', 8, ' 0737052806235 ', '202009251541', 'Hugo Boss Bottled Collector Men edt 50 ml', 0, 0, 0, 15, 0.204, NULL, 0),
(2458, 330505, '200269', NULL, 'Wibo Sp. z o.o.', 55, ' 5901801649397 ', '202009251541', 'Lovely eyeliner Glitter 1, 1 szt.', 0, 0, 0, 15, 0.0107, NULL, 0),
(2459, 189196, '200269', NULL, 'Beauty Gallery IN-OUT', 2, ' 3607342341968 ', '202009251541', 'CK Sheer Beauty zestaw 1 szt.', 0, 0, 0, 15, 0.921, NULL, 0),
(2460, 330507, '200269', NULL, 'Wibo Sp. z o.o.', 25, ' 5901801649403 ', '202009251541', 'Lovely eyeliner Glitter 2, 1 szt.', 0, 0, 0, 15, 0.01, NULL, 0),
(2461, 267024, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 0737052513805 ', '202009251541', 'Hugo Boss Orange Charity Men edt 60 ml', 0, 0, 0, 15, 0.288, NULL, 0),
(2462, 330521, '200269', NULL, 'Wibo Sp. z o.o.', 2169, ' 5901801651611 ', '202009251541', 'Lovely lakier Matte or Shine Lovely 2x3 ml', 0, 0, 0, 15, 0.044, NULL, 0),
(2463, 168737, '200269', NULL, 'Star Global Trading IN-OUT', 3, ' 0783320831263 ', '202009251541', 'BVLGARI Soir homme edt 30ml', 0, 0, 0, 15, 0.1687, NULL, 0),
(2464, 168741, '200269', NULL, 'Star Global Trading IN-OUT', 6, ' 3607342278769 ', '202009251541', 'Davidoff Champion Energy man as splash 90ml', 0, 0, 0, 15, 0.253, NULL, 0),
(2465, 236331, '200269', NULL, 'Labori International b.v.', 1, ' 0737052565064 ', '202009251541', 'Escada Especially delicate notes edt 50 ml', 0, 0, 0, 15, 0.311, NULL, 0),
(2466, 168747, '200269', NULL, 'Star Global Trading IN-OUT', 1, ' 0719346128193 ', '202009251541', 'Juicy Couture Couture edp 30ml', 0, 0, 0, 15, 0.215, NULL, 0),
(2467, 330549, '200269', NULL, 'Wibo Sp. z o.o.', 1007, ' 5901801651253 ', '202009251541', 'Lovely lakier Seasonal  Trend Edition 3, 8 ml', 0, 0, 0, 15, 0.04, NULL, 0),
(2468, 330548, '200269', NULL, 'Wibo Sp. z o.o.', 657, ' 5901801651246 ', '202009251541', 'Lovely lakier Seasonal  Trend Edition 2, 8 ml', 0, 0, 0, 15, 0.0446, NULL, 0),
(2469, 330551, '200269', NULL, 'Wibo Sp. z o.o.', 904, ' 5901801651277 ', '202009251541', 'Lovely lakier Seasonal  Trend Edition 5, 8 ml', 0, 0, 0, 15, 0.0446, NULL, 0),
(2470, 330550, '200269', NULL, 'Wibo Sp. z o.o.', 1703, ' 5901801651260 ', '202009251541', 'Lovely lakier Seasonal  Trend Edition 4, 8 ml', 0, 0, 0, 15, 0.0448, NULL, 0),
(2471, 257847, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 476, ' 0074170443523 ', '202009251541', 'SH lakier Color Therapy 130, 14,7ml', 0, 0, 0, 15, 0.074, NULL, 0),
(2472, 330547, '200269', NULL, 'Wibo Sp. z o.o.', 1145, ' 5901801651239 ', '202009251541', 'Lovely lakier Seasonal  Trend Edition 1, 8 ml', 0, 0, 0, 15, 0.04, NULL, 0),
(2473, 274237, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 0737052513881 ', '202009251541', 'Hugo Boss Orange Woman Charity edt 30 ml', 0, 0, 0, 15, 0.18, NULL, 0),
(2474, 168767, '200269', NULL, 'Star Global Trading IN-OUT', 1, ' 0031655729336 ', '202009251541', 'Sarah Jessica Parker Lovely Endless edt 75 ml', 0, 0, 0, 15, 0.29, NULL, 0),
(2475, 257854, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 460, ' 0074170443554 ', '202009251541', 'SH lakier Color Therapy 160, 14,7ml', 0, 0, 0, 15, 0.074, NULL, 0),
(2476, 168768, '200269', NULL, 'Star Global Trading IN-OUT', 2, ' 0031655729312 ', '202009251541', 'Sarah Jessica Parker Lovely Twilight edt 75 ml', 0, 0, 0, 15, 0.288, NULL, 0),
(2477, 228174, '200269', NULL, 'Labori International b.v. IN-OUT', 4, ' 3331436101038 ', '202009251541', 'S.Dali Eau de Ruby Lips edt 100ml', 0, 0, 0, 15, 0.38, NULL, 0),
(2478, 228172, '200269', NULL, 'Labori International b.v. IN-OUT', 2, ' 3331438921504 ', '202009251541', 'S.Dali Sea and Sun edt 100ml', 0, 0, 0, 15, 0.385, NULL, 0),
(2479, 288598, '200269', NULL, 'Błysk Sp. z o.o', 219, ' 5902693161394 ', '202009251541', 'Provocater lakier hybrydowy 01, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2480, 195412, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1878, ' 3607349610326 ', '202009251541', 'Rimm. szminka Moisture Ren 380, 4 g', 0, 0, 0, 15, 0.0201, NULL, 0),
(2481, 265042, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 1653, ' 5901761960037 ', '202009251541', 'EVEL. cienie Quattro Eye Shadow 14/ 5,2g', 0, 0, 0, 15, 0.0453, NULL, 0),
(2482, 275283, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 444, ' 0074170443691 ', '202009251541', 'SH lakier Color Therapy 300, 14,7ml', 0, 0, 0, 15, 0.0745, NULL, 0),
(2483, 317279, '200269', NULL, 'Labori International b.v. IN-OUT', 2, ' 7350084490444 ', '202009251541', 'Zlatan Ibrahimovic Myth Wood edt 100ml', 0, 0, 0, 15, 0.16, NULL, 0),
(2484, 260958, '200269', NULL, 'Bell sp. z o.o.', 429, ' 5902082518402 ', '202009251541', 'Bell Hypo primer All in 1 nr 1, 10g', 0, 0, 0, 15, 0.065, NULL, 0),
(2485, 288614, '200269', NULL, 'Błysk Sp. z o.o', 283, ' 5902693161400 ', '202009251541', 'Provocater lakier hybrydowy 04, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2486, 288615, '200269', NULL, 'Błysk Sp. z o.o', 470, ' 5902693161417 ', '202009251541', 'Provocater lakier hybrydowy 05, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2487, 275296, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 2414, ' 3614223263493 ', '202009251541', 'Bourjois eyeliner Black Shine, 1szt.', 0, 0, 0, 15, 0.0124, NULL, 0),
(2488, 257893, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 504, ' 0074170443639 ', '202009251541', 'SH lakier Color Therapy 240, 14,7ml', 0, 0, 0, 15, 0.074, NULL, 0),
(2489, 288616, '200269', NULL, 'Błysk Sp. z o.o', 374, ' 5902693161424 ', '202009251541', 'Provocater lakier hybrydowy 06, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2490, 236401, '200269', NULL, 'COM RMG in out', 4, ' 3454090003019 ', '202009251541', 'Paris Elysees La Petit Fleur Blanche edt 100ml', 0, 0, 0, 15, 0.345, NULL, 0),
(2491, 257905, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 492, ' 0074170443684 ', '202009251541', 'SH lakier Color Therapy 290, 14,7ml', 0, 0, 0, 15, 0.0746, NULL, 0),
(2492, 288631, '200269', NULL, 'Błysk Sp. z o.o', 488, ' 5902693161462 ', '202009251541', 'Provocater lakier hybrydowy 24, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2493, 317296, '200269', NULL, 'CRM Trading Ltd  IN-OUT', 2, ' 3614222554134 ', '202009251541', 'Marc Jacobs Daisy Dream Kiss edt 50ml', 0, 0, 0, 15, 0.332, NULL, 0),
(2494, 191360, '200269', NULL, 'Beauty Gallery IN-OUT', 3, ' 0022548137215 ', '202009251541', 'DKNY Red Delicious Man edt 30ml', 0, 0, 0, 15, 0.163, NULL, 0),
(2495, 61315, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 3352818208305  3274872289772  3352818208152 ', '202009251541', 'Kenzo Jungle Homme edt 100ml', 0, 0, 0, 15, 0.391, NULL, 0),
(2496, 271239, '200269', NULL, 'Coty Eastern Europe zapach Prestige sp. z o.o.', 5, ' 8005610371016 ', '202009251541', 'B. B. Mans Best edt 30 ml', 0, 0, 0, 15, 0.1173, NULL, 0),
(2497, 316289, '200269', NULL, 'Eris makijaż', 132, ' 5902860345756 ', '202009251541', 'SinSkin paletka rozświetlaczy 01, 1 szt. limited2', 0, 0, 0, 15, 0.14, NULL, 0),
(2498, 316290, '200269', NULL, 'Eris makijaż', 222, ' 5902860345763 ', '202009251541', 'SinSkin paletka rozświetlaczy 02, 1 szt. limited2', 0, 0, 0, 15, 0.1387, NULL, 0),
(2499, 316291, '200269', NULL, 'Eris makijaż', 527, ' 5902860348009 ', '202009251541', 'SinSkin pędzel wachlarzykowaty 11, 1 szt. limited2', 0, 0, 0, 15, 0.0185, NULL, 0),
(2500, 288652, '200269', NULL, 'Błysk Sp. z o.o', 238, ' 5902693161479 ', '202009251541', 'Provocater lakier hybrydowy 27, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2501, 181134, '200269', NULL, 'Star Global Trading IN-OUT', 1, ' 0719346135825 ', '202009251541', 'Juicy Peace and Love edp 30ml', 0, 0, 0, 15, 0.152, NULL, 0),
(2502, 288660, '200269', NULL, 'Błysk Sp. z o.o', 290, ' 5902693161486 ', '202009251541', 'Provocater lakier hybrydowy 28, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2503, 181136, '200269', NULL, 'Labori International b.v.', 1, ' 0098691047718 ', '202009251541', 'Juicy Counture Viva la Juicy edp 100ml', 0, 0, 0, 15, 0.538, NULL, 0),
(2504, 259985, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 1595, ' 0000030121669 ', '202009251541', 'Rimm. lakier Super Gel 082, 12 ml', 0, 0, 0, 15, 0.0495, NULL, 0),
(2505, 219034, '200269', NULL, 'Coty Eastern Europe makijaz sp.z o.o.', 421, ' 0074170423297 ', '202009251541', 'SH lakier Miracle Gel 510, 14,7 ml', 0, 0, 0, 15, 0.072, NULL, 0),
(2506, 267165, '200269', NULL, 'Simex Trading AG IN-OUT', 1, ' 8011530015206 ', '202009251541', 'Trussardi Uomo the Red edt 50ml', 0, 0, 0, 15, 0.3135, NULL, 0),
(2507, 276382, '200269', NULL, 'Simex Trading AG IN-OUT', 5, ' 3274872271210 ', '202009251541', 'Kenzo Homme Night edt 100ml', 0, 0, 0, 15, 0.3575, NULL, 0),
(2508, 279448, '200269', NULL, 'Oceanic S.A', 1541, ' 5900116031286 ', '202009251541', 'AA W.O.C lakier Nail Lacquer 2, 11ml', 0, 0, 0, 15, 0.058, NULL, 0),
(2509, 285592, '200269', NULL, 'SAR Trading  B.V. IN-OUT', 1, ' 3346470100640 ', '202009251541', 'Guerlain Insolence edt 30ml', 0, 0, 0, 15, 0.18, NULL, 0),
(2510, 276377, '200269', NULL, 'Wibo Sp. z o.o.', 440, ' 5901801631590 ', '202009251541', 'Wibo błyszczyk High Gloss 3, 4,9 g', 0, 0, 0, 15, 0.031, NULL, 0),
(2511, 285593, '200269', NULL, 'SAR Trading  B.V. IN-OUT', 1, ' 3346470113756 ', '202009251541', 'Guerlain Idylle edt 35ml', 0, 0, 0, 15, 0.154, NULL, 0),
(2512, 288665, '200269', NULL, 'Błysk Sp. z o.o', 264, ' 5902693161493 ', '202009251541', 'Provocater lakier hybrydowy 32, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2513, 288677, '200269', NULL, 'Błysk Sp. z o.o', 474, ' 5902693161547 ', '202009251541', 'Provocater lakier hybrydowy 40, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2514, 250784, '200269', NULL, 'IFD  Fragrance Distribution Ltd - IN-OUT', 2, ' 5045252648988 ', '202009251541', 'Burberry Touch for Men edt 100ml', 0, 0, 0, 15, 0.416, NULL, 0),
(2515, 280487, '200269', NULL, 'Simex Trading AG IN-OUT', 13, ' 5045456747654 ', '202009251541', 'Burberry Mr Burberry men edt 50ml', 0, 0, 0, 15, 0.332, NULL, 0),
(2516, 288675, '200269', NULL, 'Błysk Sp. z o.o', 442, ' 5902693161530 ', '202009251541', 'Provocater lakier hybrydowy 39, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2517, 191401, '200269', NULL, 'Star Global Trading IN-OUT', 8, ' 3423470485189 ', '202009251541', 'Issey Miyake L\'eau Blue pour homme edt 75ml', 0, 0, 0, 15, 0.303, NULL, 0),
(2518, 288681, '200269', NULL, 'Błysk Sp. z o.o', 430, ' 5902693162254 ', '202009251541', 'Provocater lakier hybrydowy 45, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2519, 250796, '200269', NULL, 'IFD  Fragrance Distribution Ltd - IN-OUT', 3, ' 5045294100406  3386463710203 ', '202009251541', 'Burberry Touch Femme edp 100ml', 0, 0, 0, 15, 0.413, NULL, 0),
(2520, 250800, '200269', NULL, 'IFD  Fragrance Distribution Ltd - IN-OUT', 23, ' 5045252667576  3386463402818 ', '202009251541', 'Burberry Weekend men edt 100ml', 0, 0, 0, 15, 0.293, NULL, 0),
(2521, 288689, '200269', NULL, 'Błysk Sp. z o.o', 379, ' 5902693161561 ', '202009251541', 'Provocater lakier hybrydowy 48, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2522, 279475, '200269', NULL, 'Oceanic S.A', 1095, ' 5900116030807 ', '202009251541', 'AA W.O.C lakier Nail Lacquer 20, 11ml', 0, 0, 0, 15, 0.058, NULL, 0),
(2523, 288691, '200269', NULL, 'Błysk Sp. z o.o', 429, ' 5902693161585 ', '202009251541', 'Provocater lakier hybrydowy 50, 4ml', 0, 0, 0, 15, 0.0217, NULL, 0),
(2524, 288696, '200269', NULL, 'Oceanic S.A', 851, ' 5900116045030 ', '202009251541', 'AA W.O.C pomadka Creme 87, 3,8 g', 0, 0, 0, 15, 0.0189, NULL, 0),
(2525, 288697, '200269', NULL, 'Oceanic S.A', 1094, ' 5900116045047 ', '202009251541', 'AA W.O.C pomadka Creme 88, 3,8 g', 0, 0, 0, 15, 0.0188, NULL, 0),
(2526, 288698, '200269', NULL, 'Oceanic S.A', 933, ' 5900116045054 ', '202009251541', 'AA W.O.C pomadka Creme 89, 3,8 g', 0, 0, 0, 15, 0.018, NULL, 0),
(2527, 288699, '200269', NULL, 'Oceanic S.A', 824, ' 5900116045061 ', '202009251541', 'AA W.O.C pomadka Creme 90, 3,8 g', 0, 0, 0, 15, 0.0184, NULL, 0),
(2528, 276421, '200269', NULL, 'Oceanic S.A', 1196, ' 5900116031040 ', '202009251541', 'AA W.O.C błyszczyk Tint 101, 6ml', 0, 0, 0, 15, 0.0145, NULL, 0),
(2529, 276422, '200269', NULL, 'Oceanic S.A', 1171, ' 5900116031057 ', '202009251541', 'AA W.O.C błyszczyk Tint 102, 6ml', 0, 0, 0, 15, 0.016, NULL, 0),
(2530, 250816, '200269', NULL, 'IFD  Fragrance Distribution Ltd - IN-OUT', 8, ' 3607347580898 ', '202009251541', 'Davidoff Coolwater night dive edt 75ml', 0, 0, 0, 15, 0.222, NULL, 0),
(2531, 288711, '200269', NULL, 'Błysk Sp. z o.o', 503, ' 5902693162261 ', '202009251541', 'Provocater lakier hybrydowy 58, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2532, 250822, '200269', NULL, 'IFD  Fragrance Distribution Ltd - IN-OUT', 20, ' 3607347580775 ', '202009251541', 'Davidoff Coolwater night dive edt 125ml', 0, 0, 0, 15, 0.337, NULL, 0),
(2533, 327628, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2828, ' 5901761997576 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 331,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2534, 327629, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2061, ' 5901761997583 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 332,5ml', 0, 0, 0, 15, 0.0347, NULL, 0),
(2535, 288719, '200269', NULL, 'Błysk Sp. z o.o', 408, ' 5902693161677 ', '202009251541', 'Provocater lakier hybrydowy 69, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2536, 327624, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2559, ' 5901761997545 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 328,5ml', 0, 0, 0, 15, 0.034, NULL, 0),
(2537, 327626, '200269', NULL, 'Eveline Cosmetics Dyst. Sp. z o.o makijaż', 2518, ' 5901761997552 ', '202009251541', 'EVEL. lakier hybrydowy Hybrid Professional 329,5ml', 0, 0, 0, 15, 0.035, NULL, 0),
(2538, 288715, '200269', NULL, 'Błysk Sp. z o.o', 198, ' 5902693161653 ', '202009251541', 'Provocater lakier hybrydowy 64, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2539, 288725, '200269', NULL, 'Błysk Sp. z o.o', 433, ' 5902693161868 ', '202009251541', 'Provocater lakier hybrydowy 91, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2540, 288721, '200269', NULL, 'Błysk Sp. z o.o', 479, ' 5902693161691 ', '202009251541', 'Provocater lakier hybrydowy 77, 4ml', 0, 0, 0, 15, 0.022, NULL, 0);
INSERT INTO `shipment_product` (`id`, `art_number`, `art_return`, `art_volume`, `company`, `counter`, `ean`, `last_update`, `name`, `scan_correct`, `scan_error`, `scan_label`, `shipment_id`, `weight`, `scan_log`, `scan_utilization`) VALUES
(2541, 288734, '200269', NULL, 'Błysk Sp. z o.o', 428, ' 5902693161899 ', '202009251541', 'Provocater lakier hybrydowy 101, 4ml', 0, 0, 0, 15, 0.0216, NULL, 0),
(2542, 239581, '200269', NULL, 'LOreal makijaz', 1728, ' 3600523165391 ', '202009251541', 'Loreal kredka BROW ARTIST MAKER 02 LIGHT, 1,5 g', 0, 0, 0, 15, 0.0113, NULL, 0),
(2543, 288730, '200269', NULL, 'Błysk Sp. z o.o', 451, ' 5902693161882 ', '202009251541', 'Provocater lakier hybrydowy 100, 4ml', 0, 0, 0, 15, 0.022, NULL, 0),
(2544, 168930, '200269', NULL, 'Schimar Sp. z o.o. Manufaktura', 42, ' 3351500952694 ', '202009251541', 'Azzaro Now man edt 30ml', 0, 0, 0, 15, 0.153, NULL, 0),
(2545, 239585, '200269', NULL, 'LOreal makijaz', 1918, ' 3600523165414 ', '202009251541', 'Loreal kredka BROW ARTIST MAKER 04 DARK, 1,5 g', 0, 0, 0, 15, 0.0111, NULL, 0),
(2546, 288737, '200269', NULL, 'Błysk Sp. z o.o', 341, ' 5902693161905 ', '202009251541', 'Provocater lakier hybrydowy 102, 4ml', 0, 0, 0, 15, 0.0215, NULL, 0),
(2547, 294901, '200269', NULL, 'Wibo Sp. z o.o.', 1377, ' 5901801640066 ', '202009251541', 'Wibo lakier hybrydowy Special Effect 3, 5 ml', 0, 0, 0, 15, 0.0318, NULL, 0),
(2548, 145392, '200269', NULL, 'Beauty Gallery IN-OUT', 1, ' 8032529119774 ', '202009251541', 'S. Ferragamo Attimo edp 30 ml', 0, 0, 0, 15, 0.195, NULL, 0),
(2549, 294897, '200269', NULL, 'Wibo Sp. z o.o.', 1325, ' 5901801640059 ', '202009251541', 'Wibo lakier hybrydowy Special Effect 2, 5 ml', 0, 0, 0, 15, 0.0313, NULL, 0),
(2550, 189434, '200269', NULL, 'Simex Trading AG IN-OUT', 2, ' 0737052684390 ', '202009251541', 'Hugo Boss Jour Pour Femme edp 30ml', 0, 0, 0, 15, 0.159, NULL, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `shipment_product_shop`
--

CREATE TABLE `shipment_product_shop` (
  `id` bigint NOT NULL,
  `art_number` bigint DEFAULT NULL,
  `art_return` varchar(50) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `recognition_counter` bigint DEFAULT NULL,
  `recognition_number` bigint DEFAULT NULL,
  `scan_correct` int DEFAULT '0',
  `scan_error` int DEFAULT '0',
  `scan_label` int DEFAULT '0',
  `ship_number` bigint DEFAULT NULL,
  `shop_number` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `store`
--

CREATE TABLE `store` (
  `id` int NOT NULL,
  `min_volume` decimal(10,0) NOT NULL,
  `max_volume` decimal(10,0) NOT NULL,
  `place` varchar(50) NOT NULL,
  `groups` int NOT NULL DEFAULT '1',
  `description` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `store`
--

INSERT INTO `store` (`id`, `min_volume`, `max_volume`, `place`, `groups`, `description`) VALUES
(1, '6', '9', 'Aleja 2', 1, 'Aleja 2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `email` varchar(50) NOT NULL,
  `is_enabled` bit(1) DEFAULT b'0',
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `is_locked` bit(1) DEFAULT b'0',
  `password` varchar(80) DEFAULT NULL,
  `role_name` varchar(20) DEFAULT NULL,
  `is_subscribed` bit(1) DEFAULT b'0',
  `verificationKey` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `email`, `is_enabled`, `first_name`, `last_name`, `is_locked`, `password`, `role_name`, `is_subscribed`, `verificationKey`) VALUES
(1, 'k.mucik@estrix.pl', b'1', 'user', 'github', b'0', '36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 'ROLE_USER', b'0', NULL),
(2, 'admin@estrix.pl', b'1', 'admin', 'github', b'0', '36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 'ROLE_ADMIN', b'0', NULL),
(3, 'superuser@estrix.pl', b'1', 'superuser', 'github', b'0', '36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 'ROLE_SUPERUSER', b'0', NULL),
(4, 'operator@estrix.pl', b'1', 'operator', 'github', b'0', '36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 'ROLE_SCANER_OPERATOR', b'0', NULL);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmxd8tydhahlu5f4w8i7jik6j7` (`category_group_id`);

--
-- Indeksy dla tabeli `category_group`
--
ALTER TABLE `category_group`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `collector`
--
ALTER TABLE `collector`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKln0yq5axuyimrntn5pel05uiy` (`number`);

--
-- Indeksy dla tabeli `printer`
--
ALTER TABLE `printer`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `print_file`
--
ALTER TABLE `print_file`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `release_article_pallet`
--
ALTER TABLE `release_article_pallet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmxd8tydhahlu5f4w8i7jik6j8` (`release_article_id`);

--
-- Indeksy dla tabeli `setting`
--
ALTER TABLE `setting`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `shipment`
--
ALTER TABLE `shipment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr6v12o33phn8pcwlmr76n4h3s` (`number`);

--
-- Indeksy dla tabeli `shipment_event`
--
ALTER TABLE `shipment_event`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `shipment_product`
--
ALTER TABLE `shipment_product`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `shipment_product_shop`
--
ALTER TABLE `shipment_product_shop`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uidx_user_email` (`email`),
  ADD UNIQUE KEY `uidx_verification_key` (`verificationKey`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `category_group`
--
ALTER TABLE `category_group`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `collector`
--
ALTER TABLE `collector`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `printer`
--
ALTER TABLE `printer`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `print_file`
--
ALTER TABLE `print_file`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `setting`
--
ALTER TABLE `setting`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `shipment`
--
ALTER TABLE `shipment`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT dla tabeli `shipment_event`
--
ALTER TABLE `shipment_event`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=595;

--
-- AUTO_INCREMENT dla tabeli `shipment_product`
--
ALTER TABLE `shipment_product`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2551;

--
-- AUTO_INCREMENT dla tabeli `shipment_product_shop`
--
ALTER TABLE `shipment_product_shop`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `store`
--
ALTER TABLE `store`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `FKmxd8tydhahlu5f4w8i7jik6j7` FOREIGN KEY (`category_group_id`) REFERENCES `category_group` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
