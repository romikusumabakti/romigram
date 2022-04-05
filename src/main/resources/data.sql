INSERT
    IGNORE
INTO account(id, name, username, email, password)
VALUES (1, 'Romi Kusuma Bakti', 'romi', 'romikusumab@gmail.com',
        '$2a$10$2YopWZM1gv25/2KHAvng4OZtUIEMqNR2wwEdweoNgjklbbt5Evj6q'),
       (2, 'Yoga Hendrapratama', 'yhepra', 'yhepra@gmail.com',
        '$2a$10$2YopWZM1gv25/2KHAvng4OZtUIEMqNR2wwEdweoNgjklbbt5Evj6q');

TRUNCATE account_followers;

INSERT
    IGNORE
INTO account_followers(followed_id, followers_id)
VALUES (1, 2),
       (2, 1);

INSERT
    IGNORE
INTO post(id, author_id, caption)
VALUES (1, 1, 'Hello world!'),
       (2, 2, 'Hello world!');

TRUNCATE post_photos;

INSERT
    IGNORE
INTO post_photos(post_id, photos)
VALUES (1, '1-1-1.jpg'),
       (1, '1-1-2.jpg'),
       (1, '1-1-3.jpg');

TRUNCATE post_likes;

INSERT
    IGNORE
INTO post_likes(liked_posts_id, likes_id)
VALUES (1, 2);

INSERT
    IGNORE
INTO comment(id, target_id, author_id, content)
VALUES (1, 1, 2, 'Hi Romi!');

TRUNCATE comment_likes;

INSERT
    IGNORE
INTO comment_likes(comment_id, likes_id)
VALUES (1, 1);