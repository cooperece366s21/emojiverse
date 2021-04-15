/* using local mysql uri*/
/* username : root */
/* password : new_password */

create table if not exists message_list(
    message_id int primary key not null,
    message_name varchar(50)
);

create table if not exists emoji_store(
    emoji_id int primary key not null,
    emoji varchar(10),
    emoji_price int
);


create table if not exists users(
    username varchar(50) primary key not null,
    public_name varchar(50),
    hashed_psw varchar(50),
    profile_img varchar(100), /* using aws s3 urls loaded thru boto*/
    emoji_coins int
);

create table if not exists emojis(
    username varchar(50),
    emoji_id int,
    foreign key(username) references users(username),
    foreign key(emoji_id) references emoji_store(emoji_id),
    primary key(username,emoji_id)

);

create table if not exists user_messages(
    user_message_id int,
    message_id int,
    username varchar(50),
    foreign key(username) references users(username),
    foreign key(message_id) references message_list(message_id),
    message varchar(1000)
);

create table if not exists friends(
    friend_username varchar(50) primary key not null,
    friend_public_name varchar(50),
    username varchar(50),
    foreign key(username) references users(username),
    friend_profile_img varchar(100)
);

create table if not exists blocked(
    blocked_username varchar(50) primary key not null,
    blocked_public_name varchar(50),
    blocked_profile_img varchar(100),
    username varchar(50),
    foreign key(username) references users(username)
)
