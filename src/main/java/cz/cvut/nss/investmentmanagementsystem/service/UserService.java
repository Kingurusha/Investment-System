package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.UserValidator;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.enums.UserType;
import cz.cvut.nss.investmentmanagementsystem.repository.PortfolioRepository;
import cz.cvut.nss.investmentmanagementsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements CrudService<User, Long>{
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserValidator userValidator;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PortfolioRepository portfolioRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.userValidator = userValidator;
    }
    @Override
    @Transactional
    public void create(User user){
        userRepository.save(user);
        LOG.debug("Create user {}.", user);
    }
    @Override
    @Transactional(readOnly = true)
    public User get(Long userId){
        userValidator.validateExistById(userId);
        return userRepository.findById(userId).get();
    }
    @Override
    @Transactional
    public void update(User user){
        userValidator.validateExistById(user.getId());
        userRepository.save(user);
        LOG.debug("Update user {}.", user);
    }
    @Override
    @Transactional
    public void delete(Long userId){
        userValidator.validateExistById(userId);
        userRepository.deleteById(userId);
        LOG.debug("Delete user with ID {}.", userId);
    }
    /**
     * Finds a user by username or email.
     *
     * @param usernameOrEmail the username or email to search for
     * @return the found user
     */
    @Transactional(readOnly = true)
    public User findUserByUsernameOrEmail(String usernameOrEmail){
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(()-> new IllegalArgumentException("Not found user by: " + usernameOrEmail));
    }
    /**
     * Retrieves all portfolios for a user.
     *
     * @param userId the ID of the user
     * @return the list of portfolios
     */
    @Transactional(readOnly = true)
    public List<Portfolio> getAllUserPortfolio(Long userId){
        return portfolioRepository.findAllByUserId(userId);
    }
    /**
     * Partially updates a user's profile.
     *
     * @param userId  the ID of the user
     * @param updates the map of fields to update and their new values
     */
    @Transactional
    public void UserProfileUpdate(Long userId, Map<String, Object> updates){
        userValidator.validateExistById(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        for (Map.Entry<String, Object> entry: updates.entrySet()){
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            switch (fieldName){
                case "username" -> {
                    if (value instanceof String newUsername){
                        user.setUsername(newUsername);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for username");
                    }
                }
                case "email" -> {
                    if (value instanceof String newEmail){
                        user.setEmail(newEmail);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for email");
                    }
                }
                case "userType" -> {
                    if (value instanceof UserType newUserType){
                        user.setUserType(newUserType);
                    } else {
                        throw new IllegalArgumentException("Invalid value type for user type");
                    }
                }
                default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
            }
        }
        userRepository.save(user);
        LOG.debug("Updated user with ID {}", userId);
    }

}
