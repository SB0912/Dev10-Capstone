-- use card_game_test;
-- SET SQL_SAFE_UPDATES = 0;
-- delete from cards;
-- SET SQL_SAFE_UPDATES = 1;

insert into cards (card_value, card_suit, url)
        value
        
        ("THREE","DIAMONDS", "diamonds_3.svg"),
        ("THREE","CLUBS", "clubs_3.svg"),
        ("THREE","HEARTS", "hearts_3.svg"),
        ("THREE","SPADES", "spades_3.svg");
        
insert into cards (card_value, card_suit, url)
value

		("TWO","DIAMONDS", "diamonds_2.svg"),("THREE","DIAMONDS", "diamonds_3.svg"),("FOUR","DIAMONDS", "diamonds_4.svg"),("FIVE","DIAMONDS", "diamonds_5.svg"),("SIX","DIAMONDS", "diamonds_6.svg"),
        ("SEVEN","DIAMONDS", "diamonds_7.svg"),("EIGHT","DIAMONDS", "diamonds_8.svg"),("NINE","DIAMONDS", "diamonds_9.svg"),
        ("TEN","DIAMONDS", "diamonds_10.svg"),("JACK","DIAMONDS", "diamonds_jack.svg"),("QUEEN","DIAMONDS", "diamonds_queen.svg"),("KING","DIAMONDS", "diamonds_king.svg"),("ACE","DIAMONDS", "diamonds_ace.svg");

insert into cards (card_value, card_suit, url)
value

        ("TEN","DIAMONDS", "diamonds_10.svg"),("JACK","DIAMONDS", "diamonds_jack.svg"),
        ("QUEEN","DIAMONDS", "diamonds_queen.svg"),("KING","DIAMONDS", "diamonds_king.svg"),
        ("ACE","DIAMONDS", "diamonds_ace.svg");
