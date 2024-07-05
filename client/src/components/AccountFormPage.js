import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import React from "react";

function AccountFormPage(props) {
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [passhash, setPassword] = useState();
  const history = useHistory();
  const [errors, setErrors] = useState([]);
  const [isAlertVisible, setIsAlertVisible] = React.useState(false);

  const handleButtonClick = () => {
    setIsAlertVisible(true);
    setTimeout(() => {
      setIsAlertVisible(false);
    }, 3000);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    create();
  };

  const create = () => {
    const newAppUser = {
      firstName,
      lastName,
      username,
      email,
      passhash,
    };
    fetch(props.BACKEND_URL + "/api/blackjack/createuser", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(newAppUser),
    }).then((result) => {
      if (result.status === 200) {
        setErrors([]);
        clearForm();

        setTimeout(() => {
          history.push("/loginpage");
        }, 1000);
      } else {
        result.json().then((errors) => {
          setErrors(errors);
        });
      }
    });
  };

  const clearForm = () => {
    setFirstName("");
    setLastName("");
    setUsername("");
    setEmail("");
    setPassword("");
  };

  const cancelEdit = () => {
    clearForm();
    history.push("/loginpage");
  };
  return (
    <div style={{ backgroundColor: "rgb(36, 34, 48)" }}>
      <header>
        <h1 style={{ textAlign: "center", color: "azure" }}>
          Create an Account
        </h1>
      </header>
      <body style={{ backgroundColor: "rgb(36, 34, 48)", marginTop: "5%" }}>
        <div id="userInfo">
          <form
            className="col col-6"
            onSubmit={(event) => {
              handleSubmit(event);
            }}
          >
            <div>
              <label>First Name:</label>
              <input
                className="form-control form-control-lg"
                value={firstName}
                onChange={(event) => {
                  setFirstName(event.target.value);
                }}
                type="text"
                id="firstName"
              />
            </div>
            <div>
              <label>Last Name:</label>
              <input
                className="form-control form-control-lg"
                value={lastName}
                onChange={(event) => {
                  setLastName(event.target.value);
                }}
                type="text"
                id="lastName"
              />
            </div>
            <div>
              <label>UserName:</label>
              <input
                className="form-control form-control-lg"
                value={username}
                onChange={(event) => {
                  setUsername(event.target.value);
                }}
                type="text"
                id="username"
              />
            </div>
            <div>
              <label>Email:</label>
              <input
                className="form-control form-control-lg"
                value={email}
                onChange={(event) => {
                  setEmail(event.target.value);
                }}
                type="email"
                id="email"
              />
            </div>
            <div>
              <label>Password:</label>
              <input
                className="form-control form-control-lg"
                value={passhash}
                onChange={(event) => {
                  setPassword(event.target.value);
                }}
                type="password"
                id="password"
              />
            </div>
            <div className="mt-4">
              <input className="btn btn-primary" type="submit" value="Create" onClick={handleButtonClick}/>

              {isAlertVisible && (
                <div className="alert-container">
                  <div className="alert-inner">Account Created!</div>
                </div>
              )}
              <button
                className="btn btn-warning"
                onClick={() => {
                  cancelEdit();
                }}
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      </body>
    </div>
  );
}

export default AccountFormPage;
