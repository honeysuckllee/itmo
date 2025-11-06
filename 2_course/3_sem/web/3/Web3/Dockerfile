# Используем официальный образ WildFly (Jakarta EE 10, JDK 17+)
FROM quay.io/wildfly/wildfly:37.0.1.Final-jdk17

# Копируем ваш WAR-файл в папку deployments WildFly
COPY build/libs/app.war /opt/jboss/wildfly/standalone/deployments/

# Открываем порты (HTTP, management)
EXPOSE 8080 8787

# Запуск WildFly в foreground-режиме
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug", "*:8787"]