import { useHistory } from "react-router-dom";

function LandingPage() {
  const history = useHistory();

  return (
    <div id="playMe">
      <h1 style={{ textAlign: "center", color: "beige" }}>
        Welcome to Diamond Aces!
      </h1>
      <button
        className="btn btn-success"
        onClick={() => {
          history.push("/blackjack");
        }}
        style={{ marginLeft: "36%", marginTop: "5%" }}
      >
        <img
          src="https://media.istockphoto.com/id/182850924/photo/blackjack.jpg?s=612x612&w=0&k=20&c=f-hHjWSP9C86tVFL2NTN0xV0-GhcI3brmjSN0jes8A8="
          width="500"
        ></img>
      </button>
      <h2 style={{ textAlign: "center", color: "beige" }}>Click to Play</h2>
    </div>
  );
}
export default LandingPage;
