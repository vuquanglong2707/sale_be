package com.saleor.saleor_api.service;


import com.saleor.saleor_api.table.AddressProvince;
import com.saleor.saleor_api.table.User;
import com.saleor.saleor_api.repo.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class SerUser {
    @Autowired
    RepoUser repoUser;

    public List<User> GetAll()
    {
        return repoUser.findAll();
    }


    public Optional<User> getUser(Long userId) {
        return repoUser.findById(userId);
    }

    public void Delete(Long id) {
        repoUser.deleteById(id);
    }

    public User AccountUpdate (User user){
        Optional<User> Optional =  repoUser.findById(user.getId());
        User userOld = Optional.get();
        userOld.setTitle(user.getTitle());
        userOld.setPhone(user.getPhone());
        userOld.setEmail(user.getEmail());
        userOld.setImage(user.getImage());
        return repoUser.save(userOld);
    }

}
