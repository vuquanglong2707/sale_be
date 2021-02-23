package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.payload.JwtAuthenticationResponse;
import com.saleor.saleor_api.payload.LoginRequest;
import com.saleor.saleor_api.payload.SignUpRequest;
import com.saleor.saleor_api.repo.RepoUser;
import com.saleor.saleor_api.security.CurrentUser;
import com.saleor.saleor_api.security.JwtTokenProvider;
import com.saleor.saleor_api.security.UserPrincipal;
import com.saleor.saleor_api.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RepoUser repoUser;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login/token")
    public ResponseEntity<?> loginWithToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String jwt = bearerToken.substring(7, bearerToken.length());
        User user = null;
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            Long userId = tokenProvider.getUserIdFromJWT(jwt);
            Optional<User> optionalUser = repoUser.findById(userId);
            if(optionalUser.isPresent()){
                user = optionalUser.get();
            }
        }

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, user));
    }

    @PostMapping("/adduser")
    public Resp registerUser(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody SignUpRequest signUpRequest) {
        Resp resp = new Resp();
        User result = new User();
        //Creating user's account if this table is null
        User user = new User(signUpRequest.getImage(), signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getActive(),  signUpRequest.getPhone(),signUpRequest.getEmail(), signUpRequest.getTitle() );
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(true);
            result = repoUser.save(user);
            resp.setData(result);
            resp.setSuccess(true);
            resp.setMsg("Thêm mới tài khoản thành công!");
        } catch (Exception ex) {
            resp.setSuccess(false);
            resp.setMsg("Tài khoản này đã tồn tại " );
        }

        return resp;
    }

}
