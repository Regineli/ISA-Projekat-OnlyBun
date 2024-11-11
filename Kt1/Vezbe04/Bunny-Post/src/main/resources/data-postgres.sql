INSERT INTO app_user (id, email, username, password, first_name, last_name, address, status) 
VALUES 
(1, 'sergej@nesto.com', 'sergej', 'password123', 'Sergej', 'Vla', '123 Main St', 'ACTIVE'),
(2, 'sergej1@nesto.com', 'sergej1', 'password456', 'Serge1j', 'Nina', '456 Oak Ave', 'ACTIVE'),
(3, 'sergej2@nesto.com', 'sergej2', 'password789', 'Sergej1', 'Jovana', '789 Pine Rd', 'ACTIVE');


insert into bunnyPost (id, details, user_id, photo, time) values (1, 'post 1 test', 1, 'photo1', CURRENT_TIMESTAMP);
insert into bunnyPost (id, details, user_id, photo, time) values (2, 'post 2 test', 3, 'photo2', CURRENT_TIMESTAMP);
insert into bunnyPost (id, details, user_id, photo, time) values (3, 'post 3 test', 2, 'photo3', CURRENT_TIMESTAMP);
insert into bunnyPost (id, details, user_id, photo, time) values (4, 'post 4 test', 1, 'photo1', CURRENT_TIMESTAMP);
insert into bunnyPost (id, details, user_id, photo, time) values (5, 'post 5 test', 1, 'photo1', CURRENT_TIMESTAMP);
insert into bunnyPost (id, details, user_id, photo, time) values (6, 'post 6 test', 3, 'photo2', CURRENT_TIMESTAMP);
insert into bunnyPost (id, details, user_id, photo, time) values (7, 'post 7 test', 2, 'photo3', CURRENT_TIMESTAMP);


-- Inserting comments for bunnyPosts
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (1, 'Great post! Really enjoyed it.', 1, 1);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (2, 'Interesting insights, thanks for sharing.', 1, 2);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (3, 'Nice photo!', 2, 3);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (4, 'Can you share more details?', 2, 1);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (5, 'Looking forward to more posts like this!', 3, 2);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (6, 'This was very helpful, thanks!', 3, 3);

INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (7, 'Great post! Really enjoyed it.', 3, 1);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (8, 'Interesting insights, thanks for sharing.', 3, 2);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (9, 'Nice photo!', 4, 3);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (10, 'Can you share more details?', 6, 1);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (11, 'Looking forward to more posts like this!', 5, 2);
INSERT INTO Comment (id, details, bunny_post_id, user_id) VALUES (12, 'This was very helpful, thanks!', 7, 3);

INSERT INTO ROLE (id, name) VALUES 
(1, 'ADMIN'),
(2, 'USER'),
(3, 'REGISTERED');


INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1); -- user-u dodeljujemo rolu USER
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1); -- admin-u dodeljujemo rolu USER
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2); -- user-u dodeljujemo rolu ADMIN

