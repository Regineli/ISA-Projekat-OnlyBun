-- Korisnici
INSERT INTO app_user (id, email, username, password, first_name, last_name, address, status) 
VALUES 
    (1, 'sergej@nesto.com', 'sergej', 'password123', 'Sergej', 'Vla', '123 Main St', 'ACTIVE'),
    (2, 'sergej1@nesto.com', 'sergej1', 'password456', 'Serge1j', 'Nina', '456 Oak Ave', 'ACTIVE'),
    (3, 'sergej2@nesto.com', 'sergej2', 'password789', 'Sergej1', 'Jovana', '789 Pine Rd', 'ACTIVE');

-- Objave
INSERT INTO bunnypost (id, details, user_id, photo, deleted, likes_count) 
VALUES 
    (1, 'post 1 test', 1, 'photo1', false, 2), 
    (2, 'post 2 test', 3, 'photo2', false, 1), 
    (3, 'post 3 test', 2, 'photo3', false, 3), 
    (4, 'post 4 test', 1, 'photo4', false, 0),
    (5, 'deleted post test', 1, 'photo5', true, 0);  -- Ova objava je obrisana

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

-- Lajkovi
INSERT INTO post_likes (bunny_post_id, user_id) 
VALUES 
    (1, 1),  -- Korisnik 1 lajkovao post 1
    (1, 2),  -- Korisnik 2 lajkovao post 1
    (2, 3),  -- Korisnik 3 lajkovao post 2
    (3, 1),  -- Korisnik 1 lajkovao post 3
    (3, 2),  -- Korisnik 2 lajkovao post 3
    (3, 3);  -- Korisnik 3 lajkovao post 3