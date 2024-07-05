drop database if exists card_game_test;
create database card_game_test;
use card_game_test;

create table cards (
	card_id int primary key auto_increment,
    card_value varchar(10) not null,
    card_suit varchar(15) not null,
    in_deck boolean default (true),
    url varchar(50)
);

create table currency_type (
	currency_id int primary key auto_increment,
    currency_name varchar(30) not null,
    currency_value double not null
);

create table app_user (
	user_id int primary key auto_increment,
	first_name varchar(50) not null,
    last_name varchar(50) not null,
    username varchar(100) not null unique,
	email varchar(200) not null unique,
    passhash varchar(2048) not null,
    enabled bit not null default(1),
    role_id int not null default(1)
    
);

create table app_role (
	role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table user_stats (
    user_id int not null,
    total_wins int default(0),
    total_losses int default(0),
	currency_total double not null default(0),
    currency_id int not null default(1),
    constraint foreign key (currency_id) references currency_type(currency_id),
    constraint foreign key (user_id) references app_user(user_id)
);


insert into currency_type (currency_id, currency_name, currency_value) values (1, 'USD', 1);
insert into currency_type (currency_id, currency_name, currency_value) values (2, 'Euro', 1.06);

insert into app_role (role_id, name) values (1, "User");
insert into app_role (role_id, name) values (2, "Guest");
insert into app_role (role_id, name) values (3, "Admin");


delimiter //
create procedure deck()
begin

	declare x int default 0;
	set x = 1;

	while x <= 6 do
		insert into cards (card_value, card_suit, url)
        value
        
        ("TWO","DIAMONDS", "diamonds_2.svg"),("THREE","DIAMONDS", "diamonds_3.svg"),("FOUR","DIAMONDS", "diamonds_4.svg"),("FIVE","DIAMONDS", "diamonds_5.svg"),("SIX","DIAMONDS", "diamonds_6.svg"),
        ("SEVEN","DIAMONDS", "diamonds_7.svg"),("EIGHT","DIAMONDS", "diamonds_8.svg"),("NINE","DIAMONDS", "diamonds_9.svg"),
        ("TEN","DIAMONDS", "diamonds_10.svg"),("JACK","DIAMONDS", "diamonds_jack.svg"),("QUEEN","DIAMONDS", "diamonds_queen.svg"),("KING","DIAMONDS", "diamonds_king.svg"),("ACE","DIAMONDS", "diamonds_ace.svg"),
        
        ("TWO","CLUBS", "clubs_2.svg"),("THREE","CLUBS", "clubs_3.svg"),("FOUR","CLUBS", "clubs_4.svg"),("FIVE","CLUBS", "clubs_5.svg"),("SIX","CLUBS", "clubs_6.svg"),("SEVEN","CLUBS", "clubs_7.svg"),
        ("EIGHT","CLUBS", "clubs_8.svg"),("NINE","CLUBS", "clubs_9.svg"),
        ("TEN","CLUBS", "clubs_10.svg"),("JACK","CLUBS", "clubs_jack.svg"),("QUEEN","CLUBS", "clubs_queen.svg"),("KING","CLUBS", "clubs_king.svg"),("ACE","CLUBS", "clubs_ace.svg"),
        
        ("TWO","HEARTS", "hearts_2.svg"),("THREE","HEARTS", "hearts_3.svg"),("FOUR","HEARTS", "hearts_4.svg"),("FIVE","HEARTS", "hearts_5.svg"),("SIX","HEARTS", "hearts_6.svg"),("SEVEN","HEARTS", "hearts_7.svg"),
        ("EIGHT","HEARTS", "hearts_8.svg"),("NINE","HEARTS", "hearts_9.svg"),
        ("TEN","HEARTS", "hearts_10.svg"),("JACK","HEARTS", "hearts_jack.svg"),("QUEEN","HEARTS", "hearts_queen.svg"),("KING","HEARTS", "hearts_king.svg"),("ACE","HEARTS", "hearts_ace.svg"),
        
        ("TWO","SPADES", "spades_2.svg"),("THREE","SPADES", "spades_3.svg"),("FOUR","SPADES", "spades_4.svg"),("FIVE","SPADES", "spades_5.svg"),("SIX","SPADES", "spades_6.svg"),("SEVEN","SPADES", "spades_7.svg"),
        ("EIGHT","SPADES", "spades_8.svg"),("NINE","SPADES", "spades_9.svg"),
        ("TEN","SPADES", "spades_10.svg"),("JACK","SPADES", "spades_jack.svg"),("QUEEN","SPADES", "spades_queen.svg"),("KING","SPADES", "spades_king.svg"),("ACE","SPADES", "spades_ace.svg");
        
        set x = x + 1;
	end while;
end //

create procedure set_known_good_state()
begin

	SET SQL_SAFE_UPDATES = 0;

	delete from app_user;
	alter table app_user auto_increment = 1;
    delete from user_stats;
    delete from cards;
    alter table cards auto_increment = 1;
    
    SET SQL_SAFE_UPDATES = 1;
    -- barthan0 has password "bad-password"
    -- aboswell6 has pass : "top-secret-password"
    -- ewace7 has pass : "1234"
    -- guestUser hopefully doesn't need a password
    insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (2, 'Bernete', 'Arthan', 'barthan0', 'barthan0@chronoengine.com', '$2a$12$udDF/wYAOJNMW6e/yAt2xu98PGo5fKd1UBFi2w2zybtmfoldhoXSW', 1,1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (3, 'Adaline', 'Boswell', 'aboswell6', 'aboswell6@dmoz.org', '$2a$12$I/cLnltMl7buHiJAHD7kbOMZDey9bsebXj/YeVsju4WDhuh.suQdy', 1,2);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (4, 'Eleen', 'Wace', 'ewace7', 'ewace7@apache.org', '$2a$12$mXJpHsNSfzueDlhESjphOeAJnsUcnKfLtIckva4kG0acCceYxncOq', 1,3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (5, 'Arvin', 'Craw', 'acraw4', 'acraw4@hao123.com', 'hIfc1EzapjEX', 0, 2);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (6, 'Mohandis', 'Scading', 'mscading5', 'mscading5@huffingtonpost.com', 'sJ1OaLiJ', 0, 2);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (7, 'Herve', 'Eakly', 'heakly6', 'heakly6@japanpost.jp', 'UbhptNrLMo', 1, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (8, 'Gabrila', 'Minerdo', 'gminerdo7', 'gminerdo7@wp.com', 'g9Wav7RvA', 0, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (9, 'Bunnie', 'Husband', 'bhusband8', 'bhusband8@nyu.edu', 'HDTnHE', 0, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (10, 'Angelia', 'Conn', 'aconn9', 'aconn9@illinois.edu', 'yPqbCgP4Pqx', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (11, 'Abigale', 'Cornillot', 'acornillota', 'acornillota@ucoz.ru', 'DSzqISgPrU', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (12, 'Findley', 'Braganca', 'fbragancab', 'fbragancab@ucla.edu', 'NIwOjOWD', 1, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (13, 'Jemimah', 'Authers', 'jauthersc', 'jauthersc@businessweek.com', '0cIS7ax3Utfo', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (14, 'Tory', 'Keher', 'tkeherd', 'tkeherd@xrea.com', 'Tk5hmOC', 1, 2);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (15, 'Janette', 'Laban', 'jlabane', 'jlabane@auda.org.au', 'h5uc1A1v', 1, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (16, 'Kit', 'Coupar', 'kcouparf', 'kcouparf@mit.edu', '0HpNkG', 0, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (17, 'Gardener', 'Mockes', 'gmockesg', 'gmockesg@bluehost.com', '4ri0NTEcFmSO', 1, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (18, 'Carilyn', 'Stickels', 'cstickelsh', 'cstickelsh@google.co.jp', 'OFVgI8YcT', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (19, 'Brittne', 'Simchenko', 'bsimchenkoi', 'bsimchenkoi@latimes.com', 'Ge4RHkQwQX', 1, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (20, 'Lindie', 'Randell', 'lrandellj', 'lrandellj@usatoday.com', '88pzhWxx9E5o', 1, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (21, 'Hendrik', 'Izakovitz', 'hizakovitzk', 'hizakovitzk@yelp.com', 'nqrDWJAz', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (22, 'Myrna', 'Lantaff', 'mlantaffl', 'mlantaffl@parallels.com', 'zqDmYAuCZyXW', 0, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (23, 'Nanci', 'Leadbetter', 'nleadbetterm', 'nleadbetterm@addthis.com', '5TbLQcjd', 1, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (24, 'Collie', 'O''Loughlin', 'coloughlinn', 'coloughlinn@addtoany.com', 'UhPzcUFdP2', 1, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (25, 'Delmer', 'Blacklidge', 'dblacklidgeo', 'dblacklidgeo@odnoklassniki.ru', 'QyP5Ch', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (26, 'Georgiana', 'Stebbing', 'gstebbingp', 'gstebbingp@sourceforge.net', 'e5QG7P', 0, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (27, 'Cammy', 'Gemnett', 'cgemnettq', 'cgemnettq@gov.uk', '03UdaK7', 1, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (28, 'Avram', 'Shale', 'ashaler', 'ashaler@forbes.com', '1V9QIWz', 0, 3);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (29, 'Julita', 'Eneas', 'jeneass', 'jeneass@hp.com', 'gEgG1x81a7', 0, 1);
	insert into app_user (user_id, first_name, last_name, username, email, passhash, enabled, role_id) values (30, 'Wolf', 'Wheble', 'wwheblet', 'wwheblet@wired.com', 'hEDJCjME', 0, 3);
        
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (2, 150, 60, 20, 2);
    insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (3, 111, 87, 36, 1);
    insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (4, 9, 90, 61, 2);
    insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (5, 1, 24, 71.16, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (6, 20, 5, 15.31, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (7, 29, 17, 27.55, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (8, 11, 14, 49.07, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (9, 29, 23, 64.45, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (10, 2, 25, 72.22, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (11, 7, 27, 53.16, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (12, 14, 12, 28.49, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (13, 27, 26, 15.72, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (14, 9, 8, 50.54, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (15, 11, 15, 76.36, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (16, 12, 11, 30.94, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (17, 10, 7, 85.19, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (18, 11, 17, 96.32, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (19, 16, 18, 43.74, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (20, 28, 19, 60.1, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (21, 29, 11, 14.43, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (22, 13, 4, 47.53, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (23, 21, 4, 85.34, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (24, 27, 20, 51.35, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (25, 30, 25, 6.0, 2);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (26, 2, 25, 40.39, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (27, 6, 28, 25.07, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (28, 13, 18, 57.1, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (29, 15, 8, 96.69, 1);
	insert into user_stats (user_id, total_wins, total_losses, currency_total, currency_id) values (30, 21, 8, 41.55, 2);
		
    call deck();
    
end//

delimiter ;

call set_known_good_state(); 

/*
// Rank System Query
select
	username,
    currency_total
from app_user au
inner join user_stats st on au.user_id = st.user_id
order by currency_total desc; 
*/



