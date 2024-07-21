package cz.cvut.nss.investmentmanagementsystem.helper.validator;

import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator extends AbstractValidator<User, Long>{
    private final UserRepository userRepository;
    @Autowired
    public UserValidator(UserRepository userRepository, UserRepository userRepository1) {
        super(userRepository);
        this.userRepository = userRepository1;
    }
}
