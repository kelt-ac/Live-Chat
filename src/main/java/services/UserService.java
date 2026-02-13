package services;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    //validation de l'email
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    //validation du password
    private static final String PASSWORD_PATTERN_STRONG =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_PATTERN_STRONG);

    public User register(String firstName, String lastName, String email, String password, String phone) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("the e-mail is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("The password is required");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("the first name is required");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("the first name is required");
        }

        /*if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Format d'email invalide");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                    "Le mot de passe doit contenir au moins 8 caractères, " +
                            "une majuscule, une minuscule, un chiffre et un caractère spécial"
            );
        }*/

        if (userDAO.existsByEmail(email)) {
            throw new RuntimeException("Un compte avec cet email existe déjà");
        }

        User user = new User();
        user.setEmail(email.toLowerCase().trim());
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setPhone(phone.trim());

        return userDAO.create(user);
    }
/*
    private boolean isValidPassword(String password) {
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        email = email.trim().toLowerCase();
        if (email.length() > 254){
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
    }

 */
}
