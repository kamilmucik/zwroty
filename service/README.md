Ikony: http://www.iconarchive.com/show/trainee-icons-by-emey87.html
http://www.iconarchive.com/show/lcd-boxes-icons-by-musett/Stamp-Clean-icon.html

mvn release:clean release:prepare release:perform  -DreleaseVersion=2.3.0 -DdevelopmentVersion=2.3.1-SNAPSHOT

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
DOCKER_DEFAULT_PLATFORM="linux/amd64"
```bash
mvn clean install -Pdocker
```
```bash
mvn -pl :return-parcel-docker-server install -PdockerBuild,docker-registry
```
```bash
docker-compose up --force-recreate --no-deps --remove-orphans --build parcel-server 
```
```bash
docker save -o  parcel-server.tar.gz registry.hub.docker.com/kamilmucik/return-parcel-server:2.3.0-SNAPSHOT
```
```bash
docker load --input  parcel-server.tar.gz 
```

cp return-parcel-docker/return-parcel-docker-server/target/dependencies/return-parcel-app-2.3.0-SNAPSHOT.war docker/docker/tomcat/ROOT.war
scp return-parcel-docker/return-parcel-docker-server/target/dependencies/return-parcel-app-2.3.0-SNAPSHOT.war ubuntu@162.19.227.81:/home/ubuntu/return-parcel/conf/tomcat-mock/target/ROOT.war
scp db-unit/src/main/resources/db/migration/V2.3.1__printer_update.sql ubuntu@e-strix.pl:/home/ubuntu/wp/releases/megapack/202501/
