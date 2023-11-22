export DB_HOST=150.163.25.44
export DB_PORT=5436
export DB_USERNAME=postgres
export DB_PASSWORD=Qmd@1998

mvn -f ./service-gateway/pom.xml clean package
mvn -f ./service-discovery/pom.xml clean package
mvn -f ./focos-service/pom.xml clean package

docker-compose up --build