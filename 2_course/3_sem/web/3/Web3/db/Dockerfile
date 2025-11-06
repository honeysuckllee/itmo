# Используем официальный образ PostgreSQL
FROM postgres:15

# Устанавливаем переменные окружения
ENV POSTGRES_DB=webapp_db
ENV POSTGRES_USER=webapp_user
ENV POSTGRES_PASSWORD=webapp_password

# Открываем порт PostgreSQL
EXPOSE 5432

# Опционально: копируем SQL скрипты для инициализации
COPY init-scripts/ /docker-entrypoint-initdb.d/

# Устанавливаем правильные права на скрипты
RUN chmod +x /docker-entrypoint-initdb.d/*.sql 2>/dev/null || true