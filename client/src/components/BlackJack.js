// import { wait } from "@testing-library/user-event/dist/utils";
import { hasSelectionSupport } from "@testing-library/user-event/dist/utils";
import { useState } from "react";
import { useEffect } from "react";
import Deck from "./subcomponents/Deck.js";
import Icon from "./subcomponents/test";
import "../App.css";

function BlackJack(props) {
  const [outcome, setOutcome] = useState();
  const [userHand, setUserHand] = useState([]);
  const [dealerHand, setDealerHand] = useState([]);

  const [canHit, setCanHit] = useState();
  const [canStay, setCanStay] = useState();
  const [canDoubleDown, setCanDoubleDown] = useState();
  const [canSplit, setCanSplit] = useState();
  const [playerTurn, setPlayerTurn] = useState();
  const [theBet, setTheBet] = useState("");

  const [numberOfPlayers, setNumberOfPlayers] = useState(1);
  const [canBet, setCanBet] = useState(true);

  const [url, setUrl] = useState();

  let tempArray = [];

  const [isLoggedIn, setIsLoggedIn] = useState();
  const [loggedUsername, setLoggedUsername] = useState();
  const [messages, setMessages] = useState([]);
  const [accountTotal, setAccountTotal] = useState("");

  useEffect(pageLoad, []);

  function pageLoad() {
    setCanBet(false);
    // setMessages([...messages, "Place a bet to start!"])
    if (props.loggedInUserData) {
      setIsLoggedIn(props.loggedInUserData);
      setLoggedUsername(props.loggedInUserData.userData.sub);
    } else {
      setLoggedUsername("guestUser");
    }
  }

  function disableButtons() {
    document.getElementById("user-hit").className = "btn btn-secondary";
    document.getElementById("user-hit").disabled = "disabled";
    document.getElementById("user-stay").className = "btn btn-secondary";
    document.getElementById("user-stay").disabled = "disabled";
    document.getElementById("user-double").className = "btn btn-secondary";
    document.getElementById("user-double").disabled = "disabled";
    document.getElementById("user-split").className = "btn btn-secondary";
    document.getElementById("user-split").disabled = "disabled";
  }

  const handleBet = () => {
    setCanBet(false);
    getHandsToStart();
  };

  const getHandsToStart = () => {
    dealAnimation();

    document.getElementById("card2").className = "hidden";

    fetch(`http://localhost:8080/api/blackjack/play/start/${numberOfPlayers}`, {
      method: "PUT",
      body: theBet,
    })
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        setOutcome(json);

        setDealerHand(json.cardListDealer.cardList);

        setUserHand(json.cardListUsers.at(0).cardList);
        setCanDoubleDown(json.cardListUsers.at(0).doubleDown);
        setCanHit(json.cardListUsers.at(0).hit);
        setCanStay(json.cardListUsers.at(0).stay);
        setCanSplit(json.cardListUsers.at(0).split);
        setPlayerTurn(json.cardListUsers.at(0).index);
        setTheBet(theBet);
        
        if (json.cardListUsers.at(0).canBet) {
          setMessages([...messages, json.cardListUsers.at(0)]);
          updateStats(json);
        }
      });
  };

  function handleHit() {
    hitUserAnimation();
    fetch(`http://localhost:8080/api/blackjack/play/hit/${playerTurn}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(outcome),
    })
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        setOutcome(json);
        setDealerHand(json.cardListDealer.cardList);

        setUserHand(json.cardListUsers.at(0).cardList);
        setCanDoubleDown(json.cardListUsers.at(0).doubleDown);
        setCanHit(json.cardListUsers.at(0).hit);
        setCanStay(json.cardListUsers.at(0).stay);
        setCanSplit(json.cardListUsers.at(0).split);
        setTheBet(json.cardListUsers.at(0).bet);
        setPlayerTurn(json.cardListUsers.at(0).index);
        
        if (json.cardListUsers.at(0).canBet) {
          setMessages([...messages, json.cardListUsers.at(0)]);

          updateStats(json);
        }
      });
  }

  function handleStay() {
    fetch(`http://localhost:8080/api/blackjack/play/stay`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(outcome),
    })
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        setOutcome(json);
        setCanDoubleDown(json.cardListUsers.at(0).doubleDown);
        setCanHit(json.cardListUsers.at(0).hit);
        setCanStay(json.cardListUsers.at(0).stay);
        setCanSplit(json.cardListUsers.at(0).split);
        setDealerHand(json.cardListDealer.cardList);

        setMessages([...messages, json.cardListUsers.at(0)]);
        console.log(`${json.cardListUsers.at(0).messages}`);
        console.log(messages)
        updateStats(json);
      });
  }

  function hitUserAnimation() {
    let cardHit = document.getElementById("5");
    cardHit.classList.remove(cardHit.getAttribute("class"));
    void cardHit.offsetWidth;
    setTimeout(() => {
      cardHit.classList.add("user-draw-one");
    }, 1);
  }

  function dealAnimation() {
    let card_1 = document.getElementById("1");
    card_1.classList.remove(card_1.getAttribute("class"));
    void card_1.offsetWidth;
    setTimeout(() => {
      card_1.classList.add("dealer-draw-one");
    }, 1);

    let card_2 = document.getElementById("2");
    card_2.classList.remove(card_2.getAttribute("class"));
    void card_2.offsetWidth;
    setTimeout(() => {
      card_2.classList.add("dealer-draw-two");
    }, 1);

    let card_3 = document.getElementById("3");
    card_3.classList.remove(card_3.getAttribute("class"));
    void card_3.offsetWidth;
    setTimeout(() => {
      card_3.classList.add("user-draw-one");
    }, 1);

    let card_4 = document.getElementById("4");
    card_4.classList.remove(card_4.getAttribute("class"));
    void card_4.offsetWidth;
    setTimeout(() => {
      card_4.classList.add("user-draw-two");
    }, 1);
  }

  function handleDouble() {
    fetch(`http://localhost:8080/api/blackjack/play/doubledown`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(outcome),
    })
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        setOutcome(json);
        setDealerHand(json.cardListDealer.cardList);
        setUserHand(json.cardListUsers.at(0).cardList);
        setCanDoubleDown(json.cardListUsers.at(0).doubleDown);
        setCanHit(json.cardListUsers.at(0).hit);
        setCanSplit(json.cardListUsers.at(0).split);
        setTheBet(json.cardListUsers.at(0).bet);
        setCanStay(json.cardListUsers.at(0).stay);
        setMessages([...messages, json.cardListUsers.at(0)]);

        console.log(`${json.cardListUsers.at(0).messages}`);

        updateStats(json);
      });
  }

  function updateStats(json) {
    document.getElementById("card2").className = "visible";

    fetch(`http://localhost:8080/api/blackjack/play/${loggedUsername}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(json),
    }).then((response) => {
      if (response.status === 204) {
        setTheBet("");
        console.log("updated");
      } else {
        console.log(`${response.statusText}`);
      }
    })
  }

  function handleSplit() {
    console.log("You split");
  }

  return (
    <main id="game" style={{ backgroundColor: "rgb(36, 34, 48)" }}>
      <div className="flex-grid">
        <div className="left-fifth" id="chatbox">
        <div className="container1">
              <p>Place a bet to start!</p>
            </div>
          {messages.map((outcome) => {
            return(
            <div className="container1">
              <p>{outcome.messages}</p>
            </div>
          );
          })}
        </div>

        <div className="right-side upper-side" id="gametable">
          <Deck />
          <div className="dealerHand">
            <img
              className="hidden"
              src={"./backs/astronaut.svg"}
              alt="card"
              id="card2"
            ></img>
            {dealerHand.map((dealerCard) => {
              return (
                <div>
                  <img
                    src={"./fronts/" + dealerCard.url}
                    alt="card"
                    width="64%"
                  ></img>
                </div>
              );
            })}
          </div>
          <div className="playerHand">
            {userHand.map((userCard) => {
              return (
                <div>
                  <img
                    src={"./fronts/" + userCard.url}
                    alt="card"
                    width="60%"
                    className="card"
                  ></img>
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <div className="lower-fifth right-side" id="allbuttons">
        <div
          className="btn-group-lg"
          role="group"
          aria-label="Basic mixed styles example"
        >
          <label htmlFor="bet" ></label>
          <input
            className="form-control"
            value={theBet}
            onChange={(event) => {
              if (event.target.value === "" || event.target.value <= 0) {
                setCanBet(false);
                setTheBet(event.target.value);
              } else {
                setCanBet(true);
                setTheBet(event.target.value);
              }
            }}
            editable={canBet}
            required={true}
            disabled={canStay}
            selectTextOnFocus={canBet}
            type="number"
            id="bet"
          />
          <button
            id="user-bet"
            type="button"
            disabled={canBet === true ? false : true}
            className={
              canBet === true ? "btn btn-success" : "btn btn-secondary"
            }
            onClick={handleBet}
          >
            Bet
          </button>
          <label htmlFor="user-stay"> Easter Egg</label>
          <button
            id="user-stay"
            type="button"
            disabled={canStay === true ? false : true}
            className={
              canStay === true ? "btn btn-primary" : "btn btn-secondary"
            }
            onClick={handleStay}
          >
            Stay
          </button>
          <button
            id="user-hit"
            type="button"
            disabled={canHit === true ? false : true}
            className={canHit === true ? "btn btn-danger" : "btn btn-secondary"}
            onClick={handleHit}
          >
            Hit
          </button>
          <button
            id="user-double"
            type="button"
            disabled={canDoubleDown === true ? false : true}
            className={
              canDoubleDown === true
                ? "btn btn-warning user"
                : "btn btn-secondary"
            }
            onClick={handleDouble}
          >
            Double
          </button>
          <button
            id="user-split"
            type="button"
            disabled={canSplit === true ? false : true}
            className={
              canSplit === true ? "btn btn-success" : "btn btn-secondary"
            }
            onClick={handleSplit}
          >
            Split
          </button>
        </div>
      </div>

      <script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossOrigin="anonymous"
      ></script>
      <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V"
        crossOrigin="anonymous"
      ></script>
    </main>
  );
}

export default BlackJack;
