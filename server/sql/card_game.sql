drop database if exists card_game;
create database card_game;
use card_game;

create table cards (
	card_id int primary key auto_increment,
    card_value varchar(10) not null,
    card_suit varchar(15) not null,
    in_deck boolean default (true)
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

	-- drop table if exists cards;
	--  create table cards (
	-- 	card_id int primary key auto_increment,
	--  card_value varchar(10) not null,
	--  card_suit varchar(15) not null
	-- ); 
	declare x int default 0;
	set x = 1;

	while x <= 6 do
		insert into cards (card_value, card_suit)
        value

        ("TWO","DIAMONDS"),("THREE","DIAMONDS"),("FOUR","DIAMONDS"),("FIVE","DIAMONDS"),("SIX","DIAMONDS"),("SEVEN","DIAMONDS"),("EIGHT","DIAMONDS"),("NINE","DIAMONDS"),
        ("TEN","DIAMONDS"),("JACK","DIAMONDS"),("QUEEN","DIAMONDS"),("KING","DIAMONDS"),("ACE","DIAMONDS"),
        
        ("TWO","CLUBS"),("THREE","CLUBS"),("FOUR","CLUBS"),("FIVE","CLUBS"),("SIX","CLUBS"),("SEVEN","CLUBS"),("EIGHT","CLUBS"),("NINE","CLUBS"),
        ("TEN","CLUBS"),("JACK","CLUBS"),("QUEEN","CLUBS"),("KING","CLUBS"),("ACE","CLUBS"),
        
        ("TWO","HEARTS"),("THREE","HEARTS"),("FOUR","HEARTS"),("FIVE","HEARTS"),("SIX","HEARTS"),("SEVEN","HEARTS"),("EIGHT","HEARTS"),("NINE","HEARTS"),
        ("TEN","HEARTS"),("JACK","HEARTS"),("QUEEN","HEARTS"),("KING","HEARTS"),("ACE","HEARTS"),
        
        ("TWO","SPADES"),("THREE","SPADES"),("FOUR","SPADES"),("FIVE","SPADES"),("SIX","SPADES"),("SEVEN","SPADES"),("EIGHT","SPADES"),("NINE","SPADES"),
        ("TEN","SPADES"),("JACK","SPADES"),("QUEEN","SPADES"),("KING","SPADES"),("ACE","SPADES");

        
        set x = x + 1;
	end while;
end //
delimiter ;

call deck();

/*
// Rank System Query
select
	username,
    currency_total
from app_user au
inner join user_stats st on au.user_id = st.user_id
order by currency_total desc; 
*/






