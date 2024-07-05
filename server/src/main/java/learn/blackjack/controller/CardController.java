package learn.blackjack.controller;

import learn.blackjack.domain.CardService;
import learn.blackjack.domain.Result;
import learn.blackjack.domain.UserService;
import learn.blackjack.domain.UserStatsService;
import learn.blackjack.model.AppUser;
import learn.blackjack.model.Card;
import learn.blackjack.model.CardListUser;
import learn.blackjack.model.OutcomeUserDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/blackjack")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    UserStatsService userStatsService;

    @PutMapping("/play/start/{numberOfPlayers}")
    ResponseEntity<Object> getStart(@PathVariable int numberOfPlayers, @RequestBody String theBet){
        OutcomeUserDealer cardResults = cardService.makeStart(numberOfPlayers, theBet);

        return ResponseEntity.ok(cardResults);
    }
    
    @PutMapping("/play/hit/{index}")
    ResponseEntity<Object> handleHit (@RequestBody OutcomeUserDealer outcomeUserDealer,@PathVariable int index){
        OutcomeUserDealer cardResults = cardService.handleHit(outcomeUserDealer, index);


        return ResponseEntity.ok(cardResults);


    }
    @PutMapping("/play/stay")
    ResponseEntity<Object> handleStay (@RequestBody OutcomeUserDealer outcomeUserDealer){
        OutcomeUserDealer cardResults = cardService.handleStay(outcomeUserDealer);


        return ResponseEntity.ok(cardResults);


    }

    @PutMapping("/play/doubledown")
    ResponseEntity<Object> handleDoubleDown (@RequestBody OutcomeUserDealer outcomeUserDealer){
        OutcomeUserDealer cardResults = cardService.handleDoubleDown(outcomeUserDealer);


        return ResponseEntity.ok(cardResults);


    }



    @PutMapping("/play/split")
    ResponseEntity<Object> handleSplit (@RequestBody OutcomeUserDealer outcomeUserDealer){
        OutcomeUserDealer cardResults = cardService.handleSplit(outcomeUserDealer);


        return ResponseEntity.ok(cardResults);


    }




    @PutMapping("/play/{username}")
    public ResponseEntity<Object> update(@PathVariable String username,
                                         @RequestBody OutcomeUserDealer outcomeUserDealer) {

        Result<AppUser> result = userStatsService.updateStats(username, outcomeUserDealer);
        if (result.isSuccess()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.badRequest().body("something went wrong");
    }

}
