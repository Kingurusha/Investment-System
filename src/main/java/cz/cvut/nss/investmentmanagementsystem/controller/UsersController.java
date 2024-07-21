package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
    // create new user
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody User user){
        userService.create(user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get user by id
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        User user = userService.get(userId);
        return ResponseEntity.ok(user);
    }
    // update user
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user){
        userService.update(user);
    }
    // delete user
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId){
        userService.delete(userId);
    }
    // find user by username or email
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByUsernameOrEmail(@RequestParam String usernameOrEmail){
        User user = userService.findUserByUsernameOrEmail(usernameOrEmail);
        return ResponseEntity.ok(user);
    }
    // get all portfolios from user account
    @GetMapping(value = "/{userId}/portfolios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Portfolio>> getAllUserPortfolios(@PathVariable Long userId){
        List<Portfolio> portfolios = userService.getAllUserPortfolio(userId);
        return ResponseEntity.ok(portfolios);
    }
    // partial update user profile
    @PatchMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UserProfileUpdate(@PathVariable Long userId, @RequestBody Map<String, Object> updates){
        userService.UserProfileUpdate(userId, updates);
    }
}
