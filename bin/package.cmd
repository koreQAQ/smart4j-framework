cd ..
cd smart4j-framework-dependencies
call mvn clean package install -Dmaven.test.skip=true

cd ..
cd smart4j-framework-utils
call mvn clean package install -Dmaven.test.skip=true

cd ..
cd smart4j-framework-bean
call mvn clean package install -Dmaven.test.skip=true

cd ..
cd smart4j-framework-webmvc
call mvn clean package install -Dmaven.test.skip=true

cd ..
cd ./bin