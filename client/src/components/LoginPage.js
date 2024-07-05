import { useContext, useState } from "react";
import { Redirect, useHistory } from "react-router-dom";
import jwtDecode from "jwt-decode";
import userContext from "./AuthContext";

function LoginPage({ setLoggedInUserData, props }) {
  const [username, setUserName] = useState();

  const [password, setPassword] = useState();

  const [loginData, setLoginData] = useState({ username: "", password: "" });

  const history = useHistory();

  const ctx = useContext(userContext);

  function handleSubmit(evt) {
    evt.preventDefault();

    const userCredentials = {
      username,
      password,
    };

    fetch("http://localhost:8080/api/security/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(userCredentials),
    })
      .then((response) => {
        if (response.status === 200) {
          console.log("Welcome back");
          return response.json();
        } else if (response.status === 403) {
          console.log("INVALID USERNAME/PASSWORD");
        } else {
          console.log(response);
        }
      })
      .then((jwtContainer) => {
        const jwt = jwtContainer.jwt;
        console.log(jwt);
        const decodedJwt = jwtDecode(jwt);
        console.log(decodedJwt);
        
        const fullLoginData = {
          token: jwt,
          userData: decodedJwt,
        };
        
        localStorage.setItem("userData", JSON.stringify(fullLoginData));
        
        setLoggedInUserData(fullLoginData);
        history.push("/profilepage");
        history.push("/profilepage");
      });
  }

  //   function handleInputChange(evt) {
  //     const changedInput = evt.target;
  //     const loginDataCopy = { ...loginData };

  //     loginDataCopy[changedInput.id] = changedInput.value;

  //     setLoginData(loginDataCopy);
  //   }

  return (
    <div className="form-center">
      <form id="LoginPage">
        <div className="form-group row">
          <label
            htmlFor="inputUsername3"
            className="col-sm-5 col-form-label"
          ></label>
          <div className="col-sm-2">
            <input
              type="userName"
              className="form-control"
              id="inputUsername3"
              placeholder="Username"
              value={username}
              onChange={(event) => {
                setUserName(event.target.value);
              }}
            />
          </div>
        </div>
        <div className="form-group row">
          <label
            htmlFor="inputPassword3"
            className="col-sm-5 col-form-label"
          ></label>
          <div className="col-sm-2">
            <input
              type="password"
              className="form-control"
              id="inputPassword3"
              placeholder="Password"
              value={password}
              onChange={(event) => {
                setPassword(event.target.value);
              }}
            />
          </div>
        </div>
        <div className="d-flex justify-content-center">
          <button
            type="submit"
            className="btn btn-success"
            onClick={handleSubmit}
          >
            Sign in
          </button>
          <button
            type="submit"
            className="btn btn-primary"
            onClick={() => {
              history.push("/createuser");
            }}
          >
            Create Account
          </button>
        </div>
      </form>
    </div>
  );
}
export default LoginPage;
