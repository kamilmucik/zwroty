Ikony: http://www.iconarchive.com/show/trainee-icons-by-emey87.html
http://www.iconarchive.com/show/lcd-boxes-icons-by-musett/Stamp-Clean-icon.html

mvn release:clean release:prepare release:perform  -DreleaseVersion=2.2.0 -DdevelopmentVersion=2.2.1-SNAPSHOT

scp /Users/kamilmuc/ws/test/20230425/zwroty/service/return-parcel-app/target/return-parcel-app.war ubuntu@51.83.132.174:/var/www/e-strix.pl/public_html/megapack/ROOT_2.2.1-RC.war
scp /Users/kamilmuc/ws/test/20230425/zwroty/service/return-parcel-app/target/ROOT.war ubuntu@51.83.132.174:/var/www/e-strix.pl/public_html/megapack/ROOT_2.2.1-RC.war

mvn versions:set -DnewVersion=2.2.1 -DprocessAllModules -DgenerateBackupPoms=false
