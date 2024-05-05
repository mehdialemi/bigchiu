package ir.inabama.utils;

import ir.inabama.common.InitialDataLoader;
import ir.inabama.auth.JwtAuthenticationResponse;
import ir.inabama.auth.LoginRequest;
import ir.inabama.auth.SignUpRequest;
import ir.inabama.auth.security.JwtAuthenticationFilter;
import ir.inabama.user.AccountType;
import ir.inabama.user.UserProfile;
import ir.inabama.user.UserType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class UserUtils {
    public static SignUpRequest lastUser;
    private static Logger logger = Logger.getLogger(UserUtils.class.getName());
    private static Random random = new Random();

    public static HttpHeaders registerUser(RestTemplate restTemplate, String server) throws URISyntaxException {
        return registerUser(restTemplate, server, newSignUpRequest());
    }

    public static HttpHeaders registerUser(RestTemplate restTemplate, String server, SignUpRequest signUpRequest)
            throws URISyntaxException {
        lastUser = signUpRequest;
        ResponseEntity<JwtAuthenticationResponse> jwt = restTemplate.postForEntity(
                new URI(server + "/api/auth/signup"), lastUser, JwtAuthenticationResponse.class);
        logger.info("user registered by token: " + jwt.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, jwt.getBody().toString());
        return headers;
    }

    public static HttpHeaders loginAdmin(RestTemplate restTemplate, String server)
            throws URISyntaxException {
        LoginRequest loginRequest =
                new LoginRequest(InitialDataLoader.DEFAULT_ADMIN_USERNAME, InitialDataLoader.DEFAULT_ADMIN_PASSWORD);

        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(
                new URI(server + "/api/auth/signin"), loginRequest, JwtAuthenticationResponse.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, response.getBody().toString());
        headers.add("Content-Type", "application/json");
        return headers;
    }

    public static UserProfile createUserProfile(String username) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        userProfile.setName("mehdi");
        userProfile.setEmail(username + "@email.com");
        userProfile.setAddress("baseUrl.mehdi");
        userProfile.setPhone("0213322244");
        userProfile.setMobile("09127292815");
        userProfile.setAccountType(AccountType.ADVERTISER);
        userProfile.setRegisterImageUrl("http://localhost/123.png");
        userProfile.setRegisterNumber("1234332");
        userProfile.setNationalCode("2345632445");
        userProfile.setUserType(UserType.PERSON);
        userProfile.setWebsite("http://www.myweb.com");
        userProfile.setBusinessCode("223332");
        return userProfile;
    }

    public static SignUpRequest newSignUpRequest() {
        SignUpRequest comSignup = new SignUpRequest();
        comSignup.setUsername("username" + random.nextInt());
        comSignup.setPassword("password" + random.nextInt());
        comSignup.setConfirmedPassword(comSignup.getPassword());
        return comSignup;
    }

    public static JwtAuthenticationResponse login(RestTemplate restTemplate, String baseUrl, LoginRequest loginRequest) throws URISyntaxException {
        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(
                new URI(baseUrl + "/api/auth/signin"), loginRequest, JwtAuthenticationResponse.class);
        return response.getBody();
    }
}
