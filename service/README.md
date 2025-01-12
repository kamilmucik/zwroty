Ikony: http://www.iconarchive.com/show/trainee-icons-by-emey87.html
http://www.iconarchive.com/show/lcd-boxes-icons-by-musett/Stamp-Clean-icon.html

mvn release:clean release:prepare release:perform  -DreleaseVersion=2.2.0 -DdevelopmentVersion=2.2.1-SNAPSHOT

scp /Users/kamilmuc/ws/test/20230425/zwroty/service/return-parcel-app/target/return-parcel-app.war ubuntu@51.83.132.174:/var/www/e-strix.pl/public_html/megapack/ROOT_2.2.2.war
scp /Users/kamilmuc/ws/test/20230425/zwroty/service/return-parcel-app/target/ROOT.war ubuntu@51.83.132.174:/var/www/e-strix.pl/public_html/megapack/ROOT_2.2.2-RC.war
scp /Users/kamilmuc/ws/test/20230425/zwroty/service/return-parcel-app/target/ROOT.war ubuntu@162.19.227.81:/home/ubuntu/docker/run/nginx/tomcat/target/ROOT.war

mvn versions:set -DnewVersion=2.2.2 -DprocessAllModules -DgenerateBackupPoms=false


Procedura instalacji:
1. Wyczyścic katalog tmp z plików tymczasowych
2. Wyczyścić bazę danych z plików które są nieaktywne active=false




JDK 1.8
Tomcat 9

Build
```bash
mvn clean install -Pdocker
```


Budowa dla megapack
```bash
mvn clean install -Pmegapack
```

Docker
```bash
mvn clean install -Pdocker
```
```bash
mvn -pl :return-parcel-docker-server install -PdockerBuild,docker-registry
```
```bash
docker-compose up --force-recreate --no-deps --remove-orphans --build parcel-server 
```

