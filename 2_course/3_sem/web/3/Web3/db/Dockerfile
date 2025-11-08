# Используем официальный образ PostgreSQL
FROM postgres:15

# Устанавливаем переменные окружения
ENV POSTGRES_DB=web3
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

# Открываем порт PostgreSQL
EXPOSE 5432

# Опционально: копируем SQL скрипты для инициализации
#COPY init-scripts/ /docker-entrypoint-initdb.d/

# Устанавливаем правильные права на скрипты
#RUN chmod +x /docker-entrypoint-initdb.d/*.sql 2>/dev/null || true