
-- Inserting users
INSERT INTO app_user (id, email, username, password, first_name, last_name, address, status) 
VALUES 
(1, 'sergej@nesto.com', 'sergej', '$2a$10$OPlU7rv98sfBJx5KdlsR4.Gv9Z7GT4fJCxALOd2AWs3rhfPOLPuEi', 'Sergej', 'Vla', '123 Main St', 'ACTIVE'),
--(1, 'sergej@nesto.com', 'sergej', 'password123', 'Sergej', 'Vla', '123 Main St', 'ACTIVE'),
(2, 'sergej1@nesto.com', 'sergej1', '$2a$10$K7E92h5wRShEnfLshmvCfuUv9DmmPqA2QkCzhS.uwDZuxvKt1FROy', 'Serge1j', 'Nina', '456 Oak Ave', 'ACTIVE'),
(3, 'sergej2@nesto.com', 'sergej2', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Sergej1', 'Jovana', '789 Pine Rd', 'ACTIVE'),
(4, 'marksmith4@example.com', 'marksmith4', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG21', 'Mark', 'Smith', '101 Maple St', 'ACTIVE'),
(5, 'emilyjones5@example.com', 'emilyjones5', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Emily', 'Jones', '202 Birch St', 'ACTIVE'),
(6, 'michaelbrown6@example.com', 'michaelbrown6', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Michael', 'Brown', '303 Cedar St', 'ACTIVE'),
(7, 'susanwhite7@example.com', 'susanwhite7', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Susan', 'White', '404 Elm St', 'ACTIVE'),
(8, 'davidclark8@example.com', 'davidclark8', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'David', 'Clark', '505 Pine St', 'ACTIVE'),
(9, 'lauraallen9@example.com', 'lauraallen9', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Laura', 'Allen', '606 Spruce St', 'ACTIVE'),
(10, 'robertking10@example.com', 'robertking10', '$2a$10$5PQeHsmvqDgsGk1kPrRVM1t4ntqHlbPh9D2tbSt6TThUDeNhMJcG2', 'Robert', 'King', '707 Redwood St', 'ACTIVE');

INSERT INTO location (id, latitude, longitude) VALUES (1, 45.0, 19.0), (2, 44.8, 20.5), (3, 43.9, 18.4);
SELECT setval('location_id_seq', (SELECT MAX(id) FROM location) + 1);

-- Inserting comments for bunnyPosts
INSERT INTO bunnyPost (id, details, user_id, photo, time, location_id, deleted, likes_count) VALUES
(1, 'post 1 test', 1, 'src/main/webapp/images/photo_8.jpg', CURRENT_TIMESTAMP, 1, false, 0),
(2, 'post 2 test', 3, 'photo2', CURRENT_TIMESTAMP, 2, false, 0),
(3, 'post 3 test', 2, 'photo3', '2023-06-20 11:45:00', 3, false, 0),
(4, 'post 4 test', 1, 'photo1', CURRENT_TIMESTAMP, 1, false, 0),
(5, 'post 5 test', 1, 'photo1', '2024-06-16 11:45:00', 1, false, 0),
(6, 'post 6 test', 3, 'photo2', '2023-06-16 11:45:00', 2, false, 0),
(7, 'post 7 test', 2, 'photo3', '2023-06-17 11:45:00', 3, false, 0),
(8, 'post 8 test', 3, 'photo2', CURRENT_TIMESTAMP, 2, false, 0),
(9, 'post 9 test', 2, 'photo3', '2023-06-20 11:45:00', 3, false, 0),
(10, 'post 10 test', 1, 'photo1', CURRENT_TIMESTAMP, 1, false, 0),
(11, 'post 11 test', 1, 'photo1', CURRENT_TIMESTAMP, 1, false, 0),
(12, 'post 12 test', 3, 'photo2', CURRENT_TIMESTAMP, 2, false, 0),
(13, 'post 13 test', 2, 'photo3', '2023-06-17 11:45:00', 3, false, 0);

SELECT setval('bunnypost_id_seq', (SELECT MAX(id) FROM bunnypost) + 1);

-- Inserting roles
INSERT INTO ROLE (id, name) VALUES 
(1, 'ADMIN'),
(2, 'USER'),
(3, 'REGISTERED');

-- Assigning roles to users (Fix: admin gets ADMIN role, user gets USER role)
INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1); -- user 1 gets ADMIN role
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2); -- user 2 gets USER role
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 2); -- user 3 gets USER role

INSERT INTO USER_FOLLOWERS (follower, following) VALUES (1, 3);
INSERT INTO USER_FOLLOWERS (follower, following) VALUES (1, 2);
INSERT INTO USER_FOLLOWERS (follower, following) VALUES (2, 1);



-- Komentari
INSERT INTO Comment (details, bunny_post_id, user_id) 
VALUES 
    ('Great post! Really enjoyed it.', 1, 1),
    ('Interesting insights, thanks for sharing.', 1, 2),
    ('Nice photo!', 2, 3),
    ('Can you share more details?', 2, 1),
    ('Looking forward to more posts like this!', 3, 2),
    ('This was very helpful, thanks!', 3, 3),
    ('Awesome!', 1, 3),
    ('Loved it!', 3, 1);

INSERT INTO bunny_post_comments (bunny_post_id, comment_id) 
VALUES 
    (1, 1),  -- post 1 comment 1
    (1, 2),  -- post 1 comment 2
    (2, 3),  -- post 2 comment 3
    (3, 4),  -- post 3 comment 1
    (3, 5),  -- post 3 comment 2
    (3, 6);  -- post 3 comment 3

-- Lajkovi
INSERT INTO post_likes (bunny_post_id, user_id) 
VALUES 
    (1, 1),  -- Korisnik 1 lajkovao post 1
    (1, 2),  -- Korisnik 2 lajkovao post 1
    (2, 3),  -- Korisnik 3 lajkovao post 2
    (3, 1),  -- Korisnik 1 lajkovao post 3
    (3, 2),  -- Korisnik 2 lajkovao post 3
    (3, 3);  -- Korisnik 3 lajkovao post 3


INSERT INTO user_like_post (id, user_id, post_id, date_time) 
VALUES
    (1, 1, 1, '2024-12-18 13:45:30'),  -- Korisnik 1 lajkovao post 1
    (2, 1, 2, '2024-12-19 09:10:20'),  -- Korisnik 1 lajkovao post 2
    (3, 1, 3, '2024-12-22 07:22:45'),  -- Korisnik 2 lajkovao post 3
    (4, 3, 1, '2024-12-21 14:05:10'),  -- Korisnik 3 lajkovao post 1
    (5, 3, 2, '2024-12-20 17:30:55'),  -- Korisnik 3 lajkovao post 2
    (6, 3, 3, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (7, 1, 12, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (8, 3, 11, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (9, 3, 10, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (10, 3, 8, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (11, 2, 12, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (12, 2, 11, '2024-12-23 12:01:33'),  -- Korisnik 3 lajkovao post 3
    (13, 2, 10, '2024-12-23 12:01:33');  -- Korisnik 3 lajkovao post 3
