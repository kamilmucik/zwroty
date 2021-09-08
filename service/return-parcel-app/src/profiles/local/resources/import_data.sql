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
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `store`
--
ALTER TABLE `store`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;
COMMIT;

ALTER TABLE store CHANGE id id INT(20) NOT NULL AUTO_INCREMENT;
INSERT INTO `store` (`id`, `min_volume`, `max_volume`, `place`, `groups`, `description`) VALUES (NULL, '6', '9', 'Aleja 2', '1', 'Aleja 2');
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (1, 'opis 1', 5, 0, 'Aleja 1', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (2, '', 15, 5, 'Aleja 2', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (3, '', 50, 15, 'Aleja 3', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (4, '', 70, 50, 'Aleja 4', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (5, '', 100, 70, 'Aleja 5', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (6, '', 150, 100, 'Aleja 6a', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (7, '', 250, 150, 'Aleja 6b', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (8, '', 500, 250, 'Aleja 7', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (9, '', 1000, 500, 'Aleja 8', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (10, '', 2000, 1000, 'Aleja 9', 1);
--INSERT INTO store (id, description, max_volume, min_volume, place, groups) VALUES (11, '', 5000, 2000, 'Plac', 1);

ALTER TABLE store AUTO_INCREMENT = 12;

insert into users(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('1','user@estrix.pl', 'user', 'github', '36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_USER');
insert into users(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('2','admin@estrix.pl', 'admin', 'github','36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_ADMIN');
insert into users(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('3','superuser@estrix.pl', 'superuser', 'github','36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_SUPERUSER');
insert into users(id, email, first_name, last_name, password, is_enabled, is_locked, role_name) values('4','operator@estrix.pl', 'operator', 'github','36767690feffd782e729ae821dff3355dda8ad40896263c007ad5a372cac7edc6da6cefca1bcdb11', 1, 0, 'ROLE_SCANER_OPERATOR');

ALTER TABLE users AUTO_INCREMENT = 5;

INSERT INTO setting (id, name, value) VALUES (1, 'path', 'C://temp/');
INSERT INTO setting (id, name, value) VALUES (2, 'hour', '02:00');
INSERT INTO setting (id, name, value) VALUES (3, 'doBackup', '1');

ALTER TABLE setting AUTO_INCREMENT = 4;
