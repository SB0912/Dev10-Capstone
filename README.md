# Dev-10-Capstone

### Project was called Diamond aces: a blackjack game website complete with a currency and betting system, account creation and management, and a leaderboard for tracking wins against other registered users.
   - Purpose was to implement all of the techniques, skills, and languages, and libraries we had learned throughout the course in a fun and interesting display, while simultaneously challenging our abilities to learn new concepts on the fly with the implementation of graphic design and animation using SVG's.
   - I contributed in the back-end with implementing and testing security, safely storing user information in the database, and pipelining it to the front-end. I also helped implement design elements and game logic on the front end using CSS/HTML, JS, and React.
   - We used Java/Maven/JDBC/Spring and MySQL/Docker on the backend, and React/JS, SVG (animation), and Bootstrap on the frontend. We also used GitHub extensively to collaborate and merge our work together seamlessly.

**Schedule breakdown:**<br>

1. Create SQL DDL (Security)<br>
   - create tables for **users**, **roles**, and **userRoles** bridge table
   - add columns to **user** table with user_id, username, passhash and email
   - add columns to **role** table with role_id, and name
   - add columns to userRoles table with user_id, and role_id
   - populate tables with data as needed
2. Create SQL DDL (app components)<br>
    * create tables for **decks**, **currency**, **ranksystem**

3. Serverside
    * add CardCombination, Cards, Currency, Dealer, HandOutcome, and suits models
        * CarCombination: add variables for whichRound, bet, stay, total(getters/setters), setRound, canSplit, canHit
        * handOutcome: add multiplier for cardcombination by to calculate payouts(all different scenarios)
        * suits ENUM
3. security
    * add classes for AppUserController, AuthController, AppUserMapper, UserRepo, UserDatabaseRepo, UserService, AppUser, JwtConverter, JwtRequestFilter, LoginRequest, and Security Config


