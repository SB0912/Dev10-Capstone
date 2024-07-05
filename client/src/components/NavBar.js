import { useContext } from "react";
import { useHistory } from "react-router-dom";
import userContext from "./AuthContext";

function NavBar({ setLoggedInUserData }) {
  const history = useHistory();

  const user = useContext(userContext);

  function handleLogout() {
    localStorage.removeItem("userData");
    setLoggedInUserData(null);
    console.log("see ya!");
    history.push("/loginpage");
  }

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <a className="navbar-brand" id="aces" href="/">
        <img
          src="https://freepngimg.com/thumb/diamond/30147-1-diamond-vector-clip-art-thumb.png"
          width="40"
          height="40"
          className="d-inline-block align-top"
        />
        Diamond Aces
      </a>
      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNavDropdown"
        aria-controls="navbarNavDropdown"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>
      <div className="collapse navbar-collapse" id="navbarNavDropdown">
        <ul className="navbar-nav">
          <li className="nav-item">
            <a className="nav-link" href="blackjack">
              Play
            </a>
          </li>
          <li className="nav-item">
            <a className="nav-link" href="leaderboard">
              Leaderboard
            </a>
          </li>
          <li className="nav-item">
            <a className="nav-link" href="profilepage">
              Profile
            </a>
          </li>
        </ul>
      </div>
      {user ? (
        <button
          className="btn btn-danger float-right"
          id="login"
          onClick={handleLogout}
        >
          Log Out {user.userData.sub}
        </button>
      ) : (
        <a
          className="btn btn-primary float-right"
          id="logout"
          href="/loginpage"
        >
          Login
        </a>
      )}
    </nav>
  );
}

export default NavBar;
