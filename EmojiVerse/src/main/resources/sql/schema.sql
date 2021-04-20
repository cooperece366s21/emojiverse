/* using local mysql uri*/
/* username : root */
/* password : new_password */

/*Note: auto_increment automatically increments ids with unique numbers without having to insert using an insert statement*/

create table if not exists chat_list(
    chat_id int primary key not null auto_increment,
    chat_name varchar(50),
    owner varchar(50)
);

create table if not exists emoji_store(
    emoji_id int primary key not null auto_increment,
    emoji varchar(10),
    emoji_price int,
    category varchar(50)
);


create table if not exists users(
    user_id int primary key not null auto_increment,
    username varchar(50) not null unique,
    email varchar(50) not null unique,
    public_name varchar(50),
    hashed_psw varchar(50) not null,
    profile_img varchar(100), /* using aws s3 urls loaded thru boto*/
    permission_level int,
    emoji_coins int
);

create table if not exists chat_participants(
    chat_id int,
    user_id int,
    requester_username varchar(50),
    foreign key(user_id) references users(user_id),
    foreign key(chat_id) references chat_list(chat_id),
    primary key(user_id,chat_id)
);

create table if not exists emojis(
    user_id int,
    emoji_id int,
    foreign key(user_id) references users(user_id),
    foreign key(emoji_id) references emoji_store(emoji_id),
    primary key(user_id,emoji_id)

);

create table if not exists user_messages(
    user_message_id int primary key not null auto_increment,
    chat_id int,
    user_id int,
    datetime varchar(100),
    foreign key(user_id) references users(user_id),
    foreign key(chat_id) references chat_list(chat_id),
    message varchar(1000)
);

create table if not exists friends(
    friends_id int primary key not null auto_increment,
    friend_username varchar(50) not null unique,
    friend_public_name varchar(50),
    user_id int,
    foreign key(user_id) references users(user_id),
    friend_profile_img varchar(100)
);

create table if not exists blocked(
    blocked_id int primary key not null auto_increment,
    blocked_username varchar(50) not null unique,
    blocked_public_name varchar(50),
    blocked_profile_img varchar(100),
    user_id int,
    foreign key(user_id) references users(user_id)
)
