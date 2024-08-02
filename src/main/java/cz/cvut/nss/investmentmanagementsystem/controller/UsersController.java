package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.dto.PortfolioDto;
import cz.cvut.nss.investmentmanagementsystem.model.dto.UserDto;
import cz.cvut.nss.investmentmanagementsystem.model.dto.convertor.PortfolioConvertor;
import cz.cvut.nss.investmentmanagementsystem.model.dto.convertor.UserConvertor;
import cz.cvut.nss.investmentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto){
        User user = UserConvertor.createEntity(userDto);
        userService.create(user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", user.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get user by id
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        User user = userService.get(userId);
        UserDto userDto = UserConvertor.toDto(user);
        return ResponseEntity.ok(userDto);
    }
    // update user
    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        User user = UserConvertor.getEntity(userDto);
        user.setId(userId);
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
    public ResponseEntity<UserDto> getUserByUsernameOrEmail(@RequestParam String usernameOrEmail){
        User user = userService.findUserByUsernameOrEmail(usernameOrEmail);
        UserDto userDto = UserConvertor.toDto(user);
        return ResponseEntity.ok(userDto);
    }
    // get all portfolios from user account
    @GetMapping(value = "/{userId}/portfolios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PortfolioDto>> getAllUserPortfolios(@PathVariable Long userId){
        List<PortfolioDto> portfolios = userService.getAllUserPortfolio(userId).stream()
                .map(PortfolioConvertor::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(portfolios);
    }
    // partial update user profile
    @PatchMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void UserProfileUpdate(@PathVariable Long userId, @RequestBody Map<String, Object> updates){
        userService.UserProfileUpdate(userId, updates);
    }
}
