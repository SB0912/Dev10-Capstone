import React, { useEffect, useState } from "react";
import {  useHistory } from "react-router-dom";

function ProfilePage(props) {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [totalWins, setTotalWins] = useState();
  const [totalLosses, setTotalLosses] = useState();
  const [accountValue, setAccountValue] = useState();
  const [token, setToken] = useState();
  const history = useHistory();

  // props.loggedInUserData.map((user) => {
  //   console.log(user)
  // })
  setTimeout(populateData, 10);
  useEffect(populateData, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    populateData();
  };

  function populateData() {
    if (props.loggedInUserData) {
      fetch("http://localhost:8080/api/blackjack/profilepage", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${props.loggedInUserData.token}`,
        },
      })
        // .catch(console.error)
        .then((response) => {
          return response.json();
        })
        .then((json) => {
          setFirstName(json.firstName);
          setLastName(json.lastName);
          setUserName(json.username);
          setEmail(json.email);
          setTotalWins(json.totalWins);
          setTotalLosses(json.totalLosses);
          setAccountValue(json.accountValue);
        });
    } else {
    }
  }

  function redirect(){
    history.push("/loginpage")
  }
  return (
    <main id="profile">
      {!props.loggedInUserData ? (
        <script>
          {setTimeout(redirect, 500)}
        </script>
      ) : (
        <>
          <div className="col col-6">
            <img
              src="https://art.pixilart.com/3caa956ca09d810.png"
              width="200"
              height="200"
              id="pic"
            ></img>
          <div className="flex-container">
          <div id="userInfo" className="flex-child col col-7">
            <form>
              <div>
                <label>First Name:</label>
                <input
                  className="form-control form-control-lg"
                  value={firstName}
                  readOnly={true}
                />
              </div>
              <div>
                <label>Last Name:</label>
                <input
                  className="form-control form-control-lg"
                  value={lastName}
                  readOnly={true}
                />
              </div>
              <div>
                <label>UserName:</label>
                <input
                  className="form-control form-control-lg"
                  value={userName}
                  readOnly={true}
                />
              </div>
              <div>
                <label>Email:</label>
                <input
                  className="form-control form-control-lg"
                  value={email}
                  readOnly={true}
                />
              </div>
            </form>
              </div>
          
          <div id="stats-info" className="flex-child col col-2">
            <form>
              <div>
                <label>Total Wins:</label>
                <input
                  className="form-control form-control-lg"
                  value={totalWins}
                  readOnly={true}
                />
              </div>
              <div>
                <label>Total Losses:</label>
                <input
                  className="form-control form-control-lg"
                  value={totalLosses}
                  readOnly={true}
                />
              </div>
              <div>
                <label>Account Balance:</label>
                <input
                  className="form-control form-control-lg"
                  value={"$" + accountValue}
                  readOnly={true}
                />
              </div>
            </form>
            </div>
            </div>
          </div>
        </>
      )}
    </main>
  );
}
export default ProfilePage;
