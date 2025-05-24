# Integradora-GrupoA

### ***comandos ramas***

git checkout -b security-backup  
git push origin security-backup  
git checkout -b FASE-1 <br>
git push origin FASE-1 <br>

### *DINÁMICA DE RAMAS*
1. Commit en la rama de tu usuario
2. Muevete a la rama con la que quieras hacer merge
3. git merge nombre_rama
4. git push origin rama (FASE_1)
5. Crear nuevo pull request
6. Consultar codeRabbit
7. Aceptar el merge desde el pull request

### *DOCKER COMPOSE* # hace falta descargar mi imagen de apache  laroca33/apacheintegradora:entrega
services:
  apacheintegradora:
    image: laroca33/apacheintegradora:entrega
    ports:
      - "80:80"
#    networks:
#      frontend:
#        ipv4_address: 172.20.0.10
#  tomcatintegradora:
#    image: laroca33/integradoraprueba:integradoratest1 //NO FUNCIONA AUN, LO INTENTARÉ  DENTRO DE POCO
#    build:
#      context: .
#    ports:
#      - "8080:8080"
#    depends_on:
#      - apacheIntegradora
#      - mysqlIntegradoraPrueba
#    networks:
#      frontend:
#        ipv4_address: 172.20.0.20
  mysqlIntegradoraPrueba:
    image: mysql:5.7
    container_name: mysqlIntegradora225
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: jpa
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
#    networks:
#      frontend:
#        ipv4_address: 172.20.0.30
    command: --default-authentication-plugin=mysql_native_password
networks:
  frontend:
    driver: bridge
    ipam:
      config:
        - subnet: 172.20.0.0/24
volumes:
  mysql_data:
