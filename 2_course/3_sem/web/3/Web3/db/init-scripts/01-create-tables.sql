-- Скрипт инициализации базы данных

-- В PostgreSQL пользователь postgres создается автоматически при установке
-- Нам нужно только убедиться, что у него правильный пароль и права доступа

-- Устанавливаем пароль для пользователя postgres, если он существует
DO
$$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_user
      WHERE usename = 'postgres'
   ) THEN
      ALTER USER postgres WITH PASSWORD 'postgres';
   END IF;
END
$$;

-- Предоставляем права на базу данных web3 пользователю postgres
GRANT ALL PRIVILEGES ON DATABASE web3 TO postgres;

-- Предоставляем права на схему public
GRANT ALL PRIVILEGES ON SCHEMA public TO postgres;

-- Создаем таблицу результатов проверки
CREATE TABLE IF NOT EXISTS point_results (
                                             id SERIAL PRIMARY KEY,
                                             x_value DOUBLE PRECISION NOT NULL,
                                             y_value DOUBLE PRECISION NOT NULL,
                                             r_value DOUBLE PRECISION NOT NULL,
                                             hit BOOLEAN NOT NULL,
                                             execution_time BIGINT NOT NULL,
                                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);