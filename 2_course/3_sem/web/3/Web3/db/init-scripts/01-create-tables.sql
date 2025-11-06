CREATE TABLE IF NOT EXISTS point_results (
                                             id SERIAL PRIMARY KEY,
                                             x_value DOUBLE PRECISION NOT NULL,
                                             y_value DOUBLE PRECISION NOT NULL,
                                             r_value DOUBLE PRECISION NOT NULL,
                                             hit BOOLEAN NOT NULL,
                                             execution_time BIGINT NOT NULL,
                                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);