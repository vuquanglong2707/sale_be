package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.DtoShop;
import com.saleor.saleor_api.mapper.MapperShop;
import com.saleor.saleor_api.repo.RepoShop;
import com.saleor.saleor_api.repo.RepoUser;
import com.saleor.saleor_api.table.Shop;
import com.saleor.saleor_api.utils.filterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SerShop {
    @Autowired
    RepoShop repoShop;

    @Autowired
    MapperShop mapperShop;

    @Autowired
    RepoUser repoUser;

    Map<String, Object> reponse = new HashMap<>();


    public List<DtoShop> GetAll()
    {
        return  mapperShop.toDtoList(repoShop.findAll());
    }
    public Object getShopById(Long id){
        try {
            Optional<Shop> opShop = repoShop.findById(id);
            Object data =  filterObject.filter(opShop, "Khong tim thấy shop ");
            return data;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }
    }

    public Object save(Shop payShop){
        try {
            Shop data = repoShop.save(payShop);
            reponse.put("success", true);
            reponse.put("mesager", "ok");
            reponse.put("data", data);
            return reponse;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }
    }

    public Object delete(Long id){
        try{
            Optional<Shop> opShop = repoShop.findById(id);
            if (opShop.isPresent()) {
                repoShop.deleteById(id);
                reponse.put("success", true);
                reponse.put("mesager", "xoa thanh cong");
            } else {
                reponse.put("success", false);
                reponse.put("mesager", "khong tim thay id kho");
            }
            return reponse;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }
    }

}
