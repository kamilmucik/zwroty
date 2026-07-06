@echo off
@REM GRADLE 9
@REM 1. Pobranie aktualnej ścieżki do aktualnego katalogu
set CURRENT_DIR=%cd%
echo Aktualny katalog: %CURRENT_DIR%

@REM 2. Uruchomienie akcji mvn clean
echo Uruchamianie mvn clean...
mvn clean && npm install && cd ./android/ && gradle clean && gradle assembleRelease && gradle bundleRelease


C:\ws\zwr_build\versionapp2>gradle --version

------------------------------------------------------------
Gradle 8.0.2
------------------------------------------------------------

Build time:   2023-03-03 16:41:37 UTC
Revision:     7d6581558e226a580d91d399f7dfb9e3095c2b1d

Kotlin:       1.8.10
Groovy:       3.0.13
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          11.0.28 (Oracle Corporation 11.0.28+12-LTS-279)
OS:           Windows 11 10.0 amd64

C:\ws\zwr_build\versionapp2>mvn -v
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: c:\ws\app\apache-maven-3.6.3\bin\..
Java version: 11.0.28, vendor: Oracle Corporation, runtime: c:\Program Files\Java\jdk-11
Default locale: pl_PL, platform encoding: Cp1250
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"

C:\ws\zwr_build\versionapp2>node -v
v26.4.0
