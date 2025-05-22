# Usa la imagen oficial de Tomcat con JDK 11
FROM tomcat:10-jdk17-temurin

# Copia tu aplicación WAR al directorio de despliegue de Tomcat
COPY IntegradoraGrupoA.war /usr/local/tomcat/webapps/ROOT.war



# Puerto que expone Tomcat
EXPOSE 8080

# Comando para iniciar Tomcat (ya viene en la imagen base)
CMD ["catalina.sh", "run"]