package com.heiyu.messaging.service;

import com.heiyu.messaging.DAO.UserDAO;
import com.heiyu.messaging.DAO.UserValidationCodeDAO;
import com.heiyu.messaging.enums.Gender;
import com.heiyu.messaging.model.User;
import com.heiyu.messaging.model.UserValidationCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

import static com.heiyu.messaging.DAO.passwordDAO.md5;

@RestController
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserValidationCodeDAO userValidationCodeDAO;

    private static final String SALT = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
    public void register(String username,
                         String password,
                         String repeatPassword,
                         String email,
                         Gender gender,
                         String address,
                         String nickname) throws Exception {
        if(!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("passwords do not match");
        }

        if(password.length()<5) {
            throw new Exception("password is too short");
        }
        if(email==null) {
            throw new NullPointerException("email is empty");
        }
        if(username==null) {
            throw new NullPointerException("username is empty");
        }
        if(userDAO.findByUsername(username)!=null) {
            throw new Exception("Username is taken");
        }
        if(userDAO.findByEmail(email)!=null) {
            throw new Exception("Email is registered");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5(password+SALT));
        user.setEmail(email);
        user.setGender(gender);
        user.setAddress(address);
        user.setNickname(nickname);
        user.setRegisterTime(new Date());
        user.setValid(false);

        this.userDAO.insert(user);
        System.out.println(user.getId());

        Random random = new Random();
        String validationCode = String.format("%06d", random.nextInt(0,100000));

        UserValidationCode userValidationCode = new UserValidationCode();
        userValidationCode.setValidationCode(validationCode);
        userValidationCode.setUserId(user.getId());
        //generate 6-digit code
        //save code into database
        //send email with 6-digit code to #{email}
        //change is_valid (0 or 1) based on user response
        userValidationCodeDAO.insert(userValidationCode);
    }

    public String login(String username, String password) throws Exception {
        User user = this.userDAO.findByUsername(username);
        if(!md5(password+SALT).equals(user.getPassword())) {
            throw new Exception("wrong password");
        }

        String loginToken = RandomStringUtils.randomAlphanumeric(128);
        Date lastloginTime = new Date();
        this.userDAO.login(loginToken, lastloginTime, user.getId());
        return loginToken;
    }

    public User authenticate(String loginToken) throws Exception {
        User user = this.userDAO.findByLoginToken(loginToken);
        if (user == null) {
            throw new Exception("You have not logged in.");
        }
        if (new Date().getTime() - user.getLastLoginTime().getTime() >= 14 * 24 * 60 * 60 * 1000) {
            throw new Exception("Login token has expired");
        }
        return user;
    }

    public User findByUsername(String username) throws Exception {
        User user = this.userDAO.findByUsername(username);
        if (user == null) {
            throw new Exception("User does not exist.");
        }
        return user;
    }

    public User findById(int userId) throws Exception {
        User user = this.userDAO.findById(userId);
        if (user == null) {
            throw new Exception("User does not exist");
        }
        return user;
    }

    public void activate(String username, String validationCode) throws Exception {
        User user = this.userDAO.findByUsername(username);
        System.out.println(user.getId()+"#"+user.getUsername()+"#"+user.getNickname());
        UserValidationCode userValidationCode = this.userValidationCodeDAO.findByUserId(user.getId());
        System.out.println(userValidationCode.getValidationCode());

        if(!userValidationCode.getValidationCode().equals(validationCode)) {
            throw new Exception("Validation code is not matched");
        }

        this.userDAO.updateToValid(user.getId());
        this.userValidationCodeDAO.deleteById(userValidationCode.getUserId());
    }
}

//username email not empty and unique