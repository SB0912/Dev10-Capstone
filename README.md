# Dev-10-Capstone

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
        *
3. security
    * add classes for AppUserController, AuthController, AppUserMapper, UserRepo, UserDatabaseRepo, UserService, AppUser, JwtConverter, JwtRequestFilter, LoginRequest, and Security Config


