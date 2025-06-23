CREATE TABLE post_comments (
    id BIGSERIAL PRIMARY KEY,
    ucomment_id BIGINT,
    post_id BIGINT,
    ucomment VARCHAR(255)
);

CREATE TABLE user_model (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    profile_path VARCHAR(255)
);

CREATE TABLE user_posts (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT,
    media_content VARCHAR(255),
    post_title VARCHAR(255),
    post_description VARCHAR(255),
    post_date TIMESTAMP
);
