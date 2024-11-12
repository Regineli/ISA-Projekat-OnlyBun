-- Inserting users
INSERT INTO app_user (id, email, username, password, first_name, last_name, address, status) 
VALUES 
(1, 'sergej@nesto.com', 'sergej', '$2a$10$OPlU7rv98sfBJx5KdlsR4.Gv9Z7GT4fJCxALOd2AWs3rhfPOLPuEi', 'Sergej', 'Vla', '123 Main St', 'ACTIVE'),
(2, 'sergej1@nesto.com', 'sergej1', '$2a$10$K7E92h5wRShEnfLshmvCfuUv9DmmPqA2QkCzhS.uwDZuxvKt1FROy', 'Serge1j', 'Nina', '456 Oak Ave', 'ACTIVE'),
(3, 'sergej2@nesto.com', 'sergej2', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Sergej1', 'Jovana', '789 Pine Rd', 'ACTIVE');

INSERT INTO location (id, latitude, longitude) VALUES (1, 45.0, 19.0), (2, 44.8, 20.5), (3, 43.9, 18.4);

-- Inserting comments for bunnyPosts
INSERT INTO bunnyPost (id, details, user_id, photo, time, location_id) VALUES
(1, 'post 1 test', 1, 'photo1', CURRENT_TIMESTAMP, 1),
(2, 'post 2 test', 3, 'photo2', CURRENT_TIMESTAMP, 2),
(3, 'post 3 test', 2, 'photo3', CURRENT_TIMESTAMP, 3),
(4, 'post 4 test', 1, 'photo1', CURRENT_TIMESTAMP, 1),
(5, 'post 5 test', 1, 'photo1', CURRENT_TIMESTAMP, 1),
(6, 'post 6 test', 3, 'photo2', CURRENT_TIMESTAMP, 2),
(7, 'post 7 test', 2, 'photo3', CURRENT_TIMESTAMP, 3);
-- Inserting roles
INSERT INTO ROLE (id, name) VALUES 
(1, 'ADMIN'),
(2, 'USER'),
(3, 'REGISTERED');

-- Assigning roles to users (Fix: admin gets ADMIN role, user gets USER role)
INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1); -- user 1 gets ADMIN role
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2); -- user 2 gets USER role
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2); -- user 3 gets USER role


