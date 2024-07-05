package learn.blackjack.data.mappers;

import learn.blackjack.model.Card;
import learn.blackjack.model.CardSuit;
import learn.blackjack.model.CardEnum;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class CardMapper implements RowMapper<Card> {

    @Override
    public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
        Card result = new Card();

        result.setCardEnum(CardEnum.valueOf(rs.getString("card_value")));
        result.setCardSuit(CardSuit.valueOf(rs.getString("card_suit")));
        result.setCardId(rs.getInt("card_id"));
        result.setInDeck(rs.getBoolean("in_deck"));
        result.setValue(result.getCardEnum().getValue());

        result.setUrl(rs.getString("url"));

        result.setFileDisplay(result.getCardEnum().getFileDisplay());


        return result;
    }
}
