package com.example.bus_reservation_for_user.serviceimpl;

import com.example.bus_reservation_for_user.apiresponse.ApiResponse;
import com.example.bus_reservation_for_user.dto.Commondto;
import com.example.bus_reservation_for_user.dto.LoginDto;
import com.example.bus_reservation_for_user.dto.SignUpDTo;
import com.example.bus_reservation_for_user.entity.User;
import com.example.bus_reservation_for_user.exception.CustomizeException;
import com.example.bus_reservation_for_user.jwt.TokenGeneration;
import com.example.bus_reservation_for_user.repo.UserProfileRepo;
import com.example.bus_reservation_for_user.repo.UserRepo;
import com.example.bus_reservation_for_user.service.UserServiceClass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserServiceClass {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    ApiResponse apiResponse;
    @Autowired
    private TokenGeneration tokenGeneration;

//    private final JavaMailSender emailSender;

//    public UserService(JavaMailSender emailSender) {
//        this.emailSender = emailSender;
//    }

    @Override
    public ResponseEntity<ApiResponse> signUp(SignUpDTo signUpDto) {
        try {
            User user = new User();
            user.setName(signUpDto.getName());
            user.setEmailID(signUpDto.getEmailID());
            user.setPassword(signUpDto.getPassword());
            user.setRole(signUpDto.getRole());
            Long createAt = Instant.now().getEpochSecond();
            user.setCreateAt(createAt);
            userRepo.save(user);
            apiResponse.setMessage("SignUp Successfully");
            apiResponse.setStatus(HttpStatus.OK);
            apiResponse.setData(null);
            if (user == null) {
                userRepo.save(user);
                apiResponse.setMessage("SignUp Failed");
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setData(null);
            }
        } catch (NullPointerException e) {
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        try {
            String loginQuery = "SELECT e FROM User e WHERE e.role = :role AND e.emailID = :emailID";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("role", loginDto.getRole());
            query.setParameter("emailID", loginDto.getEmailID());
            query.getSingleResult();
            User user = query.getSingleResult();
            if (user == null){
                apiResponse.setData(null);
                apiResponse.setMessage("Login Failed");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            }
            String token = tokenGeneration.generateTokens(loginDto);
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            Long loginAt = Instant.now().getEpochSecond();
            user.setLoginAt(loginAt);
            userRepo.save(user);
            apiResponse.setData(data);
            apiResponse.setMessage("Login Successfully");
            apiResponse.setStatus(200);
        } catch (NullPointerException e) {
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse bookTicket(Commondto commonDto) throws Exception {
        try {
            String loginQuery = "SELECT e FROM User e WHERE e.emailID = :emailID";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("emailID", commonDto.getEmailID());
            query.getSingleResult();
            User user = query.getSingleResult();
            user.setUserProfile(commonDto.getUserProfile());
            Long date = System.currentTimeMillis();
            Date date1 = new Date(date);
            user.getUserProfile().setDate(date1);
            String sum = "SELECT SUM(t.ticketCount) FROM UserProfile t";
            Query query2 = entityManager.createQuery(sum);
            Long totalCount = (Long) query2.getSingleResult();
            if(totalCount <= 30) {
//                SimpleMailMessage message = new SimpleMailMessage();
//                message.setTo(commonDto.getEmailID());
//                message.setSubject("Ticked Booked");
//                message.setText("text");
//                emailSender.send(message);
                userRepo.save(user);
                userProfileRepo.save(user.getUserProfile());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Ticket Booked Successfully");
                return apiResponse;
            } else {
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                apiResponse.setMessage("Ticket Booked Failed and All Tickets are Booked ");
                apiResponse.setData(null);
            }

            return apiResponse;

        } catch (NullPointerException e) {
//            throw new Exception(e.getMessage());
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);

        }
    }

    @Override
    public ApiResponse getAvailability() {
        try {
            String sum = "SELECT SUM(t.ticketCount) FROM UserProfile t";
            Query query2 = entityManager.createQuery(sum);
            Long totalCount = (Long) query2.getSingleResult();
            if (totalCount < 30) {
                Long available = 30 - totalCount;
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("Ticket Available");
                apiResponse.setData(available);
                return apiResponse;
            } else if (totalCount >= 30) {
                apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                apiResponse.setMessage("All Ticket Are Booked ");
                apiResponse.setData(null);
                return apiResponse;
            }

        }catch (NullPointerException e) {
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse deleteOneTicket(Commondto commonDto) {
        try {
            String loginQuery = "SELECT e FROM User e WHERE e.name = :name";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("name", commonDto.getName());
            query.getSingleResult();
            User user = query.getSingleResult();

            Long totalCount = user.getUserProfile().getTicketCount();
            Long delete = totalCount - commonDto.getDeleteTicket();
            user.getUserProfile().setTicketCount(delete);
            System.out.println(delete);
            userProfileRepo.save(user.getUserProfile());
            apiResponse.setData(null);
            apiResponse.setMessage("Ticket Cancelled  Successfully");
            apiResponse.setStatus(HttpStatus.OK);
        } catch (NullPointerException e) {
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse changePassword(Commondto commonDto) {
        try {
            String loginQuery = "SELECT e FROM User e WHERE e.name = :name AND e.password =:password";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("name", commonDto.getName());
            query.setParameter("password", commonDto.getPassword());
            query.getSingleResult();
            User user = query.getSingleResult();
            if (commonDto.getPassword() != commonDto.getNewPassword()) {
                commonDto.setNewPassword(commonDto.getNewPassword());
                commonDto.setConfirmPassword(commonDto.getConfirmPassword());
                if (commonDto.getNewPassword().equals(commonDto.getConfirmPassword())) {
                    user.setPassword(commonDto.getNewPassword());
                    Long updateAt = Instant.now().getEpochSecond();
                    user.setUpdateAt(updateAt);
                    userRepo.save(user);
                    apiResponse.setStatus(HttpStatus.OK.value());
                    apiResponse.setMessage("Password Changed Successfully");
                    apiResponse.setData(null);
                    return apiResponse;
                } else {
                    apiResponse.setMessage("Update Password Filed");
                    apiResponse.setData(null);
                    apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                    return apiResponse;
                }
            } else {
                apiResponse.setMessage("Please Enter Correct Password");
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return apiResponse;
            }
        } catch (NullPointerException e) {
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ApiResponse forgotPassword(Commondto commonDto) {
        try {
            String loginQuery = "SELECT e FROM User e WHERE e.emailID = :emailID";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("emailID", commonDto.getEmailID());
            User user = query.getSingleResult();
            Random random = new Random();
            String num = String.valueOf(random.nextLong(1100000000));
            Long otpNumber = Long.valueOf(num);
            user.setOtpNumber(otpNumber);
            userRepo.save(user);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(otpNumber);
            apiResponse.setMessage("This Is Forgot Password OTP");
        } catch (NullPointerException e) {
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }

    @Override
    public ApiResponse resetPassword(Commondto commonDto) {
        try {
            String loginQuery = "SELECT e FROM User e WHERE e.otpNumber = :otpNumber";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("otpNumber", commonDto.getOtpNumber());
            User user = query.getSingleResult();
            commonDto.setNewPassword(commonDto.getNewPassword());
            commonDto.setConfirmPassword(commonDto.getConfirmPassword());
            if (commonDto.getNewPassword().equals(commonDto.getConfirmPassword())){
                user.setPassword(commonDto.getNewPassword());
                userRepo.save(user);
                apiResponse.setMessage("Password Rested Successfully");
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }else {
                apiResponse.setMessage("Password Rested Failed Please Double Check Password");
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.OK.value());
                return apiResponse;
            }

        }catch (NullPointerException e){
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
