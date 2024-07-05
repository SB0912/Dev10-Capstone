package learn.blackjack.controller;

import learn.blackjack.domain.Result;
import learn.blackjack.domain.UserService;
import learn.blackjack.model.AppUser;
import learn.blackjack.model.OutcomeUserDealer;
import learn.blackjack.security.JwtConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/blackjack")
public class AppUserController {
    @Autowired
    UserService service;

    @Autowired
    PasswordEncoder bcryptEncoder;

    AuthenticationManager authManager;
    JwtConverter converter;

    public AppUserController(AuthenticationManager authManager, JwtConverter converter) {
        this.authManager = authManager;
        this.converter = converter;
    }


    @GetMapping("/profilepage")
    ResponseEntity loginUser(){

        AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = currentUser.getUsername();

        Result<AppUser> lookupResult = service.getSettingsByUsername( username );

        if(lookupResult.isSuccess()) {
            return ResponseEntity.ok( lookupResult.getPayload() );
        }

        return ResponseEntity.badRequest().body(lookupResult.getErrorMessages());
    }

    @PostMapping ("/createuser")
    ResponseEntity createUser(@RequestBody AppUser appUser){

        String hashedPassword = bcryptEncoder.encode(appUser.getPassword());
        appUser.setPasshash(hashedPassword);
        Result<AppUser> lookupResult = service.createAsAnyone(appUser);

        if(lookupResult.isSuccess()) {
            return ResponseEntity.ok( lookupResult.getPayload() );
        }

        return ResponseEntity.badRequest().body(lookupResult.getErrorMessages());
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable int userId) {
        if (service.deleteAsUser(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin")
    public List<AppUser> findAll(){
        return service.getAllUsers();
    }

}
