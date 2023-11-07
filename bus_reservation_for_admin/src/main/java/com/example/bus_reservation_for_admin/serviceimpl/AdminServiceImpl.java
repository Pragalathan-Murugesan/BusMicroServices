package com.example.bus_reservation_for_admin.serviceimpl;


import com.example.bus_reservation_for_admin.api_response.ApiResponse;
import com.example.bus_reservation_for_admin.dto.Commondto;
import com.example.bus_reservation_for_admin.dto.LoginDto;
import com.example.bus_reservation_for_admin.entity.Admin;
import com.example.bus_reservation_for_admin.entity.User;
import com.example.bus_reservation_for_admin.exception.CustomizeException;
import com.example.bus_reservation_for_admin.jwt.TokenGeneration;
import com.example.bus_reservation_for_admin.repo.AdminRepo;
import com.example.bus_reservation_for_admin.repo.UserProfileRepo;
import com.example.bus_reservation_for_admin.repo.UserRepo;
import com.example.bus_reservation_for_admin.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private TokenGeneration tokenGeneration;

    @Autowired
    ApiResponse apiResponse;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AdminRepo adminRepo;

    @Override
    public ApiResponse addUserProfile(Commondto commonDto) {
try {
    Admin admin = new Admin();
    admin.setName(commonDto.getName());
    admin.setEmailID(commonDto.getEmailID());
    admin.setPassword(commonDto.getPassword());
    admin.setRole(commonDto.getRole());
    Long createAt = Instant.now().getEpochSecond();
    admin.setCreateAt(createAt);
    adminRepo.save(admin);
    apiResponse.setStatus(HttpStatus.OK.value());
    apiResponse.setMessage("admin Added Successfully");
    apiResponse.setData(null);
}catch (NullPointerException e){
    throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
}
        return apiResponse;
    }

    @Override
    public ApiResponse deleteUser(Long id) {
        try {
            userRepo.deleteById(id);
            apiResponse.setData(null);
            apiResponse.setMessage("User Deleted Successfully");
            apiResponse.setStatus(HttpStatus.OK.value());
        }catch (NullPointerException e){
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }

    @Override
    @Transactional
    public ApiResponse adminLogin(Commondto commonDto) {
        try {
            String loginQuery = "SELECT q FROM Admin q WHERE q.role = :role AND q.emailID = :emailID";
            TypedQuery<Admin> query = entityManager.createQuery(loginQuery, Admin.class);
            query.setParameter("role",commonDto.getRole());
            query.setParameter("emailID",commonDto.getEmailID());
            query.getSingleResult();
            Admin admin =  query.getSingleResult();
            if (admin==null){
                apiResponse.setData(null);
                apiResponse.setMessage("Login Failed");
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
            }
            String token = tokenGeneration.generateTokens(new LoginDto());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            apiResponse.setData(data);
            apiResponse.setMessage("Login Successfully");
            apiResponse.setStatus(HttpStatus.OK.value());
        }catch (NullPointerException e){
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }

    @Override
    @Transactional
    public ApiResponse updateUser(Commondto commonDto) {
        try {
            String loginQuery = "SELECT q FROM User q WHERE q.name = :name";
            TypedQuery<User> query = entityManager.createQuery(loginQuery, User.class);
            query.setParameter("name",commonDto.getName());
            query.getSingleResult();
            User user = query.getSingleResult();
            user.setPassword(commonDto.getPassword());
            Long updateAt = Instant.now().getEpochSecond();
            user.setUpdateAt(updateAt);
            userRepo.save(user);
            apiResponse.setData(null);
            apiResponse.setMessage("User Updated Successfully");
            apiResponse.setStatus(HttpStatus.OK.value());
        }catch (NullPointerException e){
            throw new CustomizeException("Not Valid Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return apiResponse;
    }


}

