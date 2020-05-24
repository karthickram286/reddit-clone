package com.karthickram.redditclone.service;

import com.karthickram.redditclone.dto.RegisterRequest;
import com.karthickram.redditclone.exception.RedditCloneException;
import com.karthickram.redditclone.models.NotificationEmail;
import com.karthickram.redditclone.models.User;
import com.karthickram.redditclone.models.VerificationToken;
import com.karthickram.redditclone.repository.UserRepository;
import com.karthickram.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedTime(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail(
                "Activate your account",
                user.getEmail(),
                "Thank you for signing up to Reddit clone app. Please click the below url to activate your account " +
                        "http://localhost:8085/api/auth/accountVerification/" + token
        ));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new RedditCloneException("Auth token is invalid"));

        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.orElseThrow(() -> new RedditCloneException("User email id not found: " + email));

        User user = userOptional.get();
        user.setEnabled(true);

        userRepository.save(user);
    }
}
