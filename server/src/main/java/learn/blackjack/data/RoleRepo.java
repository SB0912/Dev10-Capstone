package learn.blackjack.data;

import learn.blackjack.model.Role;

import java.util.List;

public interface RoleRepo {
    List<Role> findAll ();
}
