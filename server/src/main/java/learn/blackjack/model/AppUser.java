package learn.blackjack.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {
    Integer appUserId = -1;
    String username;
    String firstName;
    String lastName;
    String passhash;
    String password;
    String email;
    Boolean enabled = true;
    String role;

    int roleId;
    int currencyId = 1;

    BigDecimal accountValue = new BigDecimal(0);

    int totalWins = -1;

    int totalLosses = -1;

    CardListUser userHand = null;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CardListUser getUserHand() {
        return userHand;
    }

    public void setUserHand(CardListUser userHand) {
        this.userHand = userHand;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public BigDecimal getAccountValue() {
        return accountValue;
    }

    public void setAccountValue(BigDecimal accountValue) {
        this.accountValue = accountValue;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> toReturn = new ArrayList<>();

        toReturn.add(new SimpleGrantedAuthority("ROLE_" + role));

        return toReturn;
    }

    @Override
    public String getPassword() {
        return passhash;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }




    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
