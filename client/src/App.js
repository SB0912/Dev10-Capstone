import "./App.css";
import "./components/styles/cards.css"
import "./components/styles/profilepage.css"
import "./components/styles/messages.css"
import LoginPage from "./components/LoginPage";
import LandingPage from "./components/LandingPage";
import BlackJack from "./components/BlackJack";
import ProfilePage from "./components/ProfilePage";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import NavBar from "./components/NavBar";
import { useEffect, useState } from "react";
import userContext from "./components/AuthContext";
import AccountFormPage from "./components/AccountFormPage";
import LeaderBoard from "./components/Leaderboard";
import jwtDecode from "jwt-decode";

function App() {
  const BACKEND_URL = "http://localhost:8080";

  let currentUserData = localStorage.getItem("userData");

  if (currentUserData) {
    currentUserData = JSON.parse(currentUserData);
  }

  const [loggedInUserData, setLoggedInUserData] = useState(currentUserData);
  const [users, setUsers] = useState([]);

  const getall = () => {
    fetch("http://localhost:8080/api/blackjack/admin")
      // .catch(console.error)
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        setUsers(json);
      });
  };

  useEffect(getall, []);

  return (
    <div className="App">
      <userContext.Provider value={loggedInUserData}>
        <Router>
          <NavBar setLoggedInUserData={setLoggedInUserData} />
          <Switch>
            <Route path="/loginpage">
              <LoginPage
                setLoggedInUserData={setLoggedInUserData}
                BACKEND_URL={BACKEND_URL}
              />
            </Route>
            <Route path="/blackjack">
              <BlackJack loggedInUserData={loggedInUserData} />
            </Route>
            <Route path="/profilepage">
              <ProfilePage loggedInUserData={loggedInUserData} />
            </Route>
            <Route path="/createuser">
              <AccountFormPage BACKEND_URL={BACKEND_URL} />
            </Route>
            <Route path="/leaderboard">
              <LeaderBoard users={users} />
            </Route>
          </Switch>
          <Switch>
            <Route exact path="/">
              <LandingPage />
            </Route>
          </Switch>
        </Router>
      </userContext.Provider>
    </div>
  );
}

export default App;
