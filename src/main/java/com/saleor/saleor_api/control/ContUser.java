package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.repo.RepoUser;
import com.saleor.saleor_api.table.User;
import com.saleor.saleor_api.service.SerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/user")
public class ContUser {

    @Autowired
    SerUser serUser;

    @Autowired
    RepoUser repoUser;

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getUserById(@RequestParam(value = "id", required = false) Long id) {
        Map<String,Object> response = new HashMap<>();
        Optional<User> data = serUser.getUser(id);
        User user = data.get();
        if(!data.isPresent()){
            response.put("success", false);
            response.put("data",null);
        }
        else{
            response.put("success", true);
            response.put("data",user);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping( value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> updateSent( @RequestBody User User) {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        try {
            User user = serUser.AccountUpdate(User);
            resp.setData(user);
            response.put("data", resp.getData());
            response.put("success",true);
            response.put("message", "ok");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

