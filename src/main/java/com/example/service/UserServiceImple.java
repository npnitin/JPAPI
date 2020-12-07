package com.example.service;

import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import com.example.models.User;
import com.example.repository.UserRepository;
import com.pcloud.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public User createUser(User user) throws UserAlreadyExistsWithEmail {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser == null){
            user.setActive(false);
            user =  userRepository.save(user);
            sendActivationEmail(user);
        }else{
            throw new UserAlreadyExistsWithEmail("User already exists with email:"+user.getEmail());
        }
    return user;
    }

    private void sendActivationEmail(User user) {
        {
            String validationUrl = "https://npnitinjobportal.herokuapp.com//api/jobportal/user/validateEmail/"+user.getId();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setText(
                    "<html>" +
                            "<body border=//'solid black//'>" +
                            "<div style='border-style:solid;border-width:thin;border-color:#dadce0;border-radius:8px;padding:40px 20px'>"+
                            "<h3>Hi "+user.getId() + " ,</h3>" +
                            "<h4>Greetings from ReferralJobz</h4>" +
                            "<h4>Thanks for registering with ReferralJobz platform.</h4>"+
                            "<h4>Click on the below link for activation.</h4>"+
                            "<h4><a href=\""+validationUrl+"\" target=\"_blank\">Activate My Account</a></h4>"+
                            "<br/>"+
                            "<h4>Thank you,</h4>"+
                            "<h4>ReferralJobz Team</h4>"+
                            "</div>"+
                            "</body>" +
                            "</html>");
            message.setSubject("ReferralJobz Activation");
            javaMailSender.send(message);
        }
    }

    @Override
    public User doLoin(User user) throws InvalidCredentials, IOException, ApiError {
        ApiClient apiClient = PCloudSdk.newClientBuilder()
                .authenticator(Authenticators.newOAuthAuthenticator("uy2z7ZAPC048V3lekZb58kG7Z3YQEF47DVVVue3EzT8SB5jhfv0kX")).create();

        User existingUser = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(null!=existingUser){
            long resumeFileId = Long.valueOf(existingUser.getResumeUrl());
            FileLink link = apiClient.createFileLink(resumeFileId, DownloadOptions.DEFAULT).execute();
            existingUser.setResumeUrl(link.bestUrl().toString());

            long profilePhotoFileId = Long.valueOf(existingUser.getProfilePhotoUrl());
            link = apiClient.createFileLink(profilePhotoFileId, DownloadOptions.DEFAULT).execute();
            existingUser.setProfilePhotoUrl(link.bestUrl().toString());
            return existingUser;
        }
       throw new InvalidCredentials("Invalid credentials");
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void activateUser(String userId) {
        User user = userRepository.findById(userId).get();
        user.setActive(true);
        userRepository.save(user);
    }
}
