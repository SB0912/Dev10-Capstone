package learn.blackjack.data;

import learn.blackjack.data.mappers.CardMapper;
import learn.blackjack.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CardDatabaseRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Card> getAllCards () {

        String sql = """
                select *
                 from cards
                 where in_deck = true;
                """;

        List<Card> allCards = jdbcTemplate.query(
                sql,
                new CardMapper());

        if( allCards.size() > 0 ){
            return allCards;
        }
        return null;
    }



//    public boolean deleteCardById (Card card) {
//        return jdbcTemplate.update("delete from cards where card_id = ?;", card.getCardId()) > 0;
//    }

    public boolean updateCard (Card card){
        return jdbcTemplate.update("update cards set in_deck = false where card_id = ?", card.getCardId()) > 0;
    }

    public void reshuffleCards () {
       jdbcTemplate.update("""
                update cards set in_deck = true;
                """);
    }
}
