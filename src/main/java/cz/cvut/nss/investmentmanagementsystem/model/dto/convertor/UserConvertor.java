package cz.cvut.nss.investmentmanagementsystem.model.dto.convertor;

import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.dto.UserDto;

public class UserConvertor {
    public static UserDto toDto(User user){
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), null, user.getEmail(),
                user.getBalance(), user.getUserType());
        return userDto;
    }
    public static User getEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.id());
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setBalance(userDto.balance());
        user.setUserType(userDto.userType());
        return user;
    }
    public static User createEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.id());
        user.setUsername(userDto.username());
        user.setPassword(userDto.password());
        user.setEmail(userDto.email());
        user.setBalance(userDto.balance());
        user.setUserType(userDto.userType());
        return user;
    }
}
