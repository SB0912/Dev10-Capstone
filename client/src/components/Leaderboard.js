function leaderBoard(props) {
  const unsortedMap = new Map(Object.entries(props.users));
  const unsortedArray = [...unsortedMap];



  return (
    <div className="justify-content: center row col-6" id="leaderboard">
      <table className="table table-bordered table-dark">
        <thead>
          <tr>
            <td
              scope="col"
              style={{ fontWeight: "bold", backgroundColor: "darkslategrey" }}
            >
              Place
            </td>
            <td
              scope="col"
              style={{ fontWeight: "bold", backgroundColor: "darkslategrey" }}
            >
              Username
            </td>
            <td
              scope="col"
              style={{ fontWeight: "bold", backgroundColor: "darkslategrey" }}
            >
              Total Wins
            </td>
          </tr>
        </thead>
        <tbody>
          {props.users.length === 0 ? (
            <tr>
              <td></td>
              <td>Loading...</td>
              <td></td>
            </tr>
          ) : (
            props.users.map((user, place) => {
              return (
                <tr key={user.appUserId}>
                  <td>{++place}</td>
                  <td>{user.username}</td>
                  <td>{user.totalWins}</td>
                </tr>
              );
            })
          )}
        </tbody>
      </table>
    </div>
  );
}

export default leaderBoard;