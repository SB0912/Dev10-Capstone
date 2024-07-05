package learn.blackjack.domain;

import learn.blackjack.data.CardDatabaseRepo;
import learn.blackjack.model.Card;
import learn.blackjack.model.CardListDealer;
import learn.blackjack.model.CardListUser;
import learn.blackjack.model.OutcomeUserDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CardService {
    @Autowired
    CardDatabaseRepo repo;


    public OutcomeUserDealer makeStart(int numberOfPlayers, String theBetString) {
        OutcomeUserDealer result = new OutcomeUserDealer();
        BigDecimal theBet = new BigDecimal(theBetString);

        List<Card> allCards = new ArrayList<>();

//      I tweaked the card drawing method to no longer give the chance at a split.

        List<Card> returnedCards;
        Set<String> allDisplays;
        int numberOfCardsNeeded = 2 + (numberOfPlayers*2);

        do{
            allCards = repo.getAllCards();
            if (allCards.size()<50){
                repo.reshuffleCards();
            }
            Set<Integer> allSelectedIndices = new HashSet<>();
            allDisplays = new HashSet<>();
            returnedCards = new ArrayList<>();
            for (int i = 0; i<numberOfCardsNeeded; i++) {
                allCards = repo.getAllCards();
                int index = allCards.size() - 1;

                int selectedIndex = (int) (Math.random() * index);

                while (!allSelectedIndices.add(selectedIndex)){
                selectedIndex = (int) (Math.random() * index);
                }
                Card selectedCard = allCards.get(selectedIndex);
                allDisplays.add(selectedCard.getFileDisplay());

                if (repo.updateCard(selectedCard)) {
                    returnedCards.add(selectedCard);
                //return result;
                } else {
                    result.addMessage("Something went wrong.");
                }
            }
        }
        while(allDisplays.size()!=numberOfCardsNeeded);

        result = initializeHands(returnedCards, numberOfPlayers, theBet);
        analyzeUserOptions(result);

        result = checkForDealerBlackjack(result);
//        check that the list is populated with booleans
        result.setCardListUsers(result.getCardListUsers());

        return result;
    }

    private OutcomeUserDealer checkForDealerBlackjack (OutcomeUserDealer outcomeUserDealer) {
        if (getUserTotal(outcomeUserDealer.getCardListDealer().getCardList())==21 ||
                getUserTotal(outcomeUserDealer.getCardListUsers().get(0).getCardList())==21){

            return handleStay(outcomeUserDealer);
        }
        else{
            return outcomeUserDealer;
        }
    }


    private List<Card> addACard (List<Card> cards) {
        List<Card> allCards = repo.getAllCards();
        int index = allCards.size() - 1;
        int selectedIndex = (int) (Math.random() * index);
        Card selectedCard = allCards.get(selectedIndex);
        cards.add(selectedCard);
        return cards;
    }

    private OutcomeUserDealer initializeHands (List<Card> listOfCards, int numberOfPlayers, BigDecimal theBet){
        OutcomeUserDealer result = new OutcomeUserDealer();
        CardListDealer dealerCards = new CardListDealer();

        List<Card> dealerList = new ArrayList<>();
        dealerList.add(listOfCards.get(0));
        dealerList.add(listOfCards.get(1));

        dealerCards.setCardList(dealerList);
        result.setCardListDealer(dealerCards);

        for (int i = 0; i<numberOfPlayers; i++){
             result.addCardListUser(new CardListUser(i,
                     new ArrayList<Card>(Arrays.asList(
                             listOfCards.get(i*2+2),listOfCards.get(i*2+3))), theBet));

        }
//  check that there are actually a list of card users, each with two cards and an index
        return result;
    }


    private OutcomeUserDealer analyzeUserOptions (OutcomeUserDealer outcomeUserDealer) {
        for (CardListUser cardListUser: outcomeUserDealer.getCardListUsers()) {
            int total = getUserTotal(cardListUser.getCardList());
            if (cardListUser.getCardList().size() == 2) {
                if (total == 21) {
                    handleUserBlackJackOption(cardListUser);
                } else if (cardListUser.getCardList().get(0).getCardEnum().getFileDisplay()
                        .equals(cardListUser.getCardList().get(1).getCardEnum().getFileDisplay())) {
                    handleSplitOption(cardListUser);
                } else {
                    handleUserDoubleDownOption(cardListUser);
                }

            } else {
                if (total < 21) {
                    handleUserHitOption(cardListUser);
                } else if (total > 21) {
                    handleBustOption(cardListUser);
                    return handleStay(outcomeUserDealer);
                } else {
                    handleStayOption(cardListUser);
                }
            }
            cardListUser.setTotal(total);

        }
        return outcomeUserDealer;
    }


    private void handleSplitOption(CardListUser cardListUser) {
        cardListUser.setSplit(true);
        cardListUser.setHit(true);
        cardListUser.setStay(true);
        cardListUser.setDoubleDown(true);
    }
    private void handleUserBlackJackOption(CardListUser cardListUser){
        cardListUser.setSplit(false);
        cardListUser.setHit(false);
        cardListUser.setStay(true);
        cardListUser.setDoubleDown(false);
    }
    private void handleUserDoubleDownOption(CardListUser cardListUser) {
        cardListUser.setSplit(false);
        cardListUser.setHit(true);
        cardListUser.setStay(true);
        cardListUser.setDoubleDown(true);
    }
    private void handleUserHitOption(CardListUser cardListUser) {
        cardListUser.setSplit(false);
        cardListUser.setHit(true);
        cardListUser.setStay(true);
        cardListUser.setDoubleDown(false);
    }
    private void handleBustOption (CardListUser cardListUser) {
        cardListUser.setSplit(false);
        cardListUser.setHit(false);
        cardListUser.setStay(false);
        cardListUser.setDoubleDown(false);
        cardListUser.setDidBust(true);
    }
    private void handleStayOption(CardListUser cardListUser){
        cardListUser.setSplit(false);
        cardListUser.setHit(false);
        cardListUser.setStay(true);
        cardListUser.setDoubleDown(false);
    }

    private void handleTurn(OutcomeUserDealer outcomeUserDealer){
        outcomeUserDealer.getCardListUsers().get(0).setSplit(false);
        outcomeUserDealer.getCardListUsers().get(0).setHit(false);
        outcomeUserDealer.getCardListUsers().get(0).setStay(false);
        outcomeUserDealer.getCardListUsers().get(0).setDoubleDown(false);
        outcomeUserDealer.getCardListUsers().get(0).setCanBet(true);
    }

//    public void checkForBust (Result<Card> result) {}

    public OutcomeUserDealer handleHit (OutcomeUserDealer outcomeUserDealer, int index) {

        List<CardListUser> cardListUsers = outcomeUserDealer.getCardListUsers();
        CardListUser cardListUser = cardListUsers.get(index);


        cardListUser.setCardList(addACard(cardListUser.getCardList()));
        cardListUsers.set(index,cardListUser);

        outcomeUserDealer.setCardListUsers(cardListUsers);
        analyzeUserOptions(outcomeUserDealer);

        return outcomeUserDealer;
    }

    public OutcomeUserDealer handleDoubleDown (OutcomeUserDealer outcomeUserDealer) {
        List<CardListUser> cardListUsers = outcomeUserDealer.getCardListUsers();
        CardListUser cardListUser = cardListUsers.get(0);

        cardListUser.setCardList(addACard(cardListUser.getCardList()));

        cardListUser.setBet(cardListUser.getBet().multiply(new BigDecimal(2)));


        outcomeUserDealer.setCardListUsers(cardListUsers);

        handleStay(outcomeUserDealer);

        return outcomeUserDealer;

    }

    public OutcomeUserDealer handleSplit (OutcomeUserDealer outcomeUserDealer) {
        OutcomeUserDealer result = new OutcomeUserDealer();
//        come back to this
        return result;
    }

    public OutcomeUserDealer handleStay (OutcomeUserDealer outcomeUserDealer) {
        List<CardListUser> cardListUsers = outcomeUserDealer.getCardListUsers();
        CardListUser cardListUser = cardListUsers.get(0);

        cardListUser.setTotal(getUserTotal(cardListUser.getCardList()));
        cardListUser.setDidBust(checkForBust(cardListUser.getCardList()));

        cardListUsers.set(0,cardListUser);
        outcomeUserDealer.setCardListUsers(cardListUsers);
        handleTurn(outcomeUserDealer);


        handleDealerTurn(outcomeUserDealer);


        outcome(outcomeUserDealer);

        return outcomeUserDealer;
    }

    private void outcome (OutcomeUserDealer outcomeUserDealer) {
        List<CardListUser> cardListUsers = outcomeUserDealer.getCardListUsers();

        for (int i = 0; i< cardListUsers.size(); i++) {
            if (cardListUsers.get(i).isDidBust()) {
                cardListUsers.get(i).setBet(cardListUsers.get(i).getBet().multiply(new BigDecimal(-1)));
                cardListUsers.get(i).addMessage(String.format("You Busted and lost $%s",
                        outcomeUserDealer.getCardListUsers().get(i).getBet().multiply(new BigDecimal(-1))));
                cardListUsers.get(i).setDidWin(-1);
            } else if (cardListUsers.get(i).getCardList().size() == 2
                    && getUserTotal(cardListUsers.get(i).getCardList()) == 21
                    && outcomeUserDealer.getCardListDealer().getCardList().size() == 2
                    && getUserTotal(outcomeUserDealer.getCardListDealer().getCardList()) == 21) {
                cardListUsers.get(i).setBet(new BigDecimal(0));
                cardListUsers.get(i).addMessage("You both got a blackjack, call it a draw");
                cardListUsers.get(i).setDidWin(0);
            }
            else if (cardListUsers.get(i).getCardList().size() == 2
                    && getUserTotal(cardListUsers.get(i).getCardList()) == 21) {
                cardListUsers.get(i).setBet(cardListUsers.get(i).getBet().multiply(new BigDecimal("1.5")));
                cardListUsers.get(i).addMessage(String.format("Nice Blackjack! You added $%s to your balance!",
                        cardListUsers.get(i).getBet()));
                cardListUsers.get(i).setDidWin(1);
            } else if (outcomeUserDealer.getCardListDealer().getCardList().size() == 2
                    && getUserTotal(outcomeUserDealer.getCardListDealer().getCardList()) == 21) {
                cardListUsers.get(i).setBet(cardListUsers.get(i).getBet().multiply(new BigDecimal(-1)));
                cardListUsers.get(i).addMessage(String.format("The dealer got a blackjack. You lost $%s",
                        cardListUsers.get(i).getBet().multiply(new BigDecimal(-1))));
                cardListUsers.get(i).setDidWin(-1);
            } else if (outcomeUserDealer.getCardListDealer().isDidBust()) {
                cardListUsers.get(i).setBet(cardListUsers.get(i).getBet().multiply(new BigDecimal("1")));
                cardListUsers.get(i).addMessage(String.format("You win $%s! The dealer busted",
                        cardListUsers.get(i).getBet()));
                cardListUsers.get(i).setDidWin(1);
            } else if (getUserTotal(outcomeUserDealer.getCardListDealer().getCardList())
                    < getUserTotal(cardListUsers.get(i).getCardList())) {
                cardListUsers.get(i).setBet(cardListUsers.get(i).getBet().multiply(new BigDecimal(1)));
                cardListUsers.get(i).addMessage(String.format("You win! $%s was added to your account",
                        cardListUsers.get(i).getBet()));
                cardListUsers.get(i).setDidWin(1);
            } else if (getUserTotal(outcomeUserDealer.getCardListDealer().getCardList())
                    > getUserTotal(cardListUsers.get(i).getCardList())) {
                cardListUsers.get(i).setBet(cardListUsers.get(i).getBet().multiply(new BigDecimal(-1)));
                cardListUsers.get(i).addMessage(String.format("You lose. $%s was taken out of your balance",
                        cardListUsers.get(i).getBet().multiply(new BigDecimal(-1))));
                cardListUsers.get(i).setDidWin(-1);
            } else if (getUserTotal(outcomeUserDealer.getCardListDealer().getCardList())
                    == getUserTotal(cardListUsers.get(i).getCardList())) {
                cardListUsers.get(i).setBet(new BigDecimal(0));
                cardListUsers.get(i).addMessage("It's a tie");
                cardListUsers.get(i).setDidWin(0);
            } else {
                outcomeUserDealer.addMessage("something went wrong");
            }

        }


        outcomeUserDealer.setCardListUsers(cardListUsers);
    }

    private OutcomeUserDealer handleDealerTurn (OutcomeUserDealer outcomeUserDealer) {
        CardListDealer cardListDealer = outcomeUserDealer.getCardListDealer();
        List<Card> dealerHand = cardListDealer.getCardList();
        int total = getDealerTotal(dealerHand);
        while (total<17){
            dealerHand = addACard(dealerHand);
            total = getDealerTotal(dealerHand);
        }
        cardListDealer.setCardList(dealerHand);
        cardListDealer.setDidBust(checkForBust(dealerHand));
        cardListDealer.setTotal(getDealerTotal(dealerHand));
        outcomeUserDealer.setCardListDealer(cardListDealer);


        return outcomeUserDealer;
    }

    private boolean checkForBust (List<Card> cards){

        return getUserTotal(cards)>21;
    }


    private int getUserTotal(List<Card> cards) {
       int total = 0;
        for (Card card : cards) {
           total += card.getCardEnum().getValue();
       }
        if (total <= 21){
            return total;
        }
//        decrease 10 for every Ace
        for (Card card :cards){
            if (card.getValue()==11){
                total -= 10;
            }
        }
        // if total is greater than 11, then no ace should have their high value.
        if (total > 11) {
            return total;
        }
//        there must be at least 1 ace.
        else{
            return total +10;
        }

    }

    private int getDealerTotal (List<Card> cards) {
        int total = 0;
        for (Card card : cards) {
            total += card.getCardEnum().getValue();
        }
        if (total <= 21){
            return total;
        }
//        decrease 10 for every Ace
        for (Card card :cards){
            if (card.getValue()==11){
                total -= 10;
            }
        }
        // if total is greater than 11, then no ace should have their high value.
        if (total > 11) {
            return total;
        }
//      toggle one Ace high if the dealer is at 7 points so he stays
        if (total > 6){
            return total+10;
        }
        return total;

    }

}
