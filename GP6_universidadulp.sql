-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-10-2025 a las 01:05:12
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gp6_universidadulp`
--
CREATE DATABASE IF NOT EXISTS `gp6_universidadulp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gp6_universidadulp`;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `idAlumno` int(11) NOT NULL,
  `dni` int(11) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estructura de tabla para la tabla `inscripcion`
--

CREATE TABLE `inscripcion` (
  `idInscripto` int(11) NOT NULL,
  `nota` double NOT NULL,
  `idAlumno` int(11) NOT NULL,
  `idMateria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE `materia` (
  `idMateria` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `año` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`idAlumno`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD PRIMARY KEY (`idInscripto`),
  ADD KEY `idAlumno` (`idAlumno`),
  ADD KEY `idMateria` (`idMateria`);

--
-- Indices de la tabla `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`idMateria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumno`
--
ALTER TABLE `alumno`
  MODIFY `idAlumno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  MODIFY `idInscripto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=229;

--
-- AUTO_INCREMENT de la tabla `materia`
--
ALTER TABLE `materia`
  MODIFY `idMateria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD CONSTRAINT `inscripcion_ibfk_1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`idAlumno`),
  ADD CONSTRAINT `inscripcion_ibfk_2` FOREIGN KEY (`idMateria`) REFERENCES `materia` (`idMateria`);
  
--
-- INSERTS CREADOS PARA QUE YA CARGUE DATOS A LA BD
--
INSERT INTO `alumno` (`dni`, `apellido`, `nombre`, `fechaNacimiento`, `estado`) VALUES
(30551131, 'GIMENEZ', 'DIEGO', '1983-12-13', 1),
(44309664, 'MECIAS', 'GERMAN', '2002-07-23', 1),
(45802941, 'MIGLIOZZI', 'TOMAS', '2004-08-05', 1),
(46260667, 'URBANI', 'JOSE', '2005-02-10', 1);

INSERT INTO `materia` (`idMateria`, `nombre`, `año`, `estado`) VALUES
(1, 'Programación 1', 1, 1),
(2, 'Matemática I', 1, 1),
(3, 'Ingles I', 1, 1),
(4, 'Laboratorio de Programación I', 1, 1),
(5, 'Administración de Base de datos', 1, 1),
(6, 'Programación Web I', 1, 1),
(7, 'Estructura de Datos y Algoritmos', 2, 1),
(8, 'Programación Web II', 2, 1),
(9, 'Ingeniería de Software', 2, 1),
(10, 'Matemática II', 2, 1),
(11, 'Laboratorio de Programación II', 2, 1),
(12, 'Metodología de Desarrollo', 2, 1),
(13, 'Redes', 2, 1),
(14, 'Programación para Dispositivos Móviles', 3, 1),
(15, 'Ingles II', 3, 1),
(16, 'Seguridad Informática', 3, 1),
(17, 'Optativa', 3, 1),
(18, 'Práctica Profesional', 3, 1);

-- Diego Gimenez
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 8.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 30551131), 1; -- Programación 1
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 7.5, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 30551131), 2; -- Matemática I
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 9.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 30551131), 4; -- Laboratorio de Programación I
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 7.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 30551131), 6; -- Programación Web I

-- German Mecias
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 6.5, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 44309664), 1; -- Programación 1
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 9.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 44309664), 3; -- Ingles I
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 8.5, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 44309664), 5; -- Administración de Base de datos

-- Tomas Migliozzi
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 9.5, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 45802941), 2; -- Matemática I
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 8.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 45802941), 4; -- Laboratorio de Programación I
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 7.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 45802941), 5; -- Administración de Base de datos
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 9.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 45802941), 6; -- Programación Web I

-- Jose Urbani
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 6.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 46260667), 1; -- Programación 1
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 7.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 46260667), 3; -- Ingles I
INSERT INTO `inscripcion` (`nota`, `idAlumno`, `idMateria`)
SELECT 8.0, (SELECT `idAlumno` FROM `alumno` WHERE `dni` = 46260667), 5; -- Administración de Base de datos
  
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
