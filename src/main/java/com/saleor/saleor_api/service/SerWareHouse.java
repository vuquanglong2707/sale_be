package com.saleor.saleor_api.service;

import com.saleor.saleor_api.payload.ResWareHouse;
import com.saleor.saleor_api.repo.RepoShop;
import com.saleor.saleor_api.repo.RepoWareHouse;
import com.saleor.saleor_api.table.Shop;
import com.saleor.saleor_api.table.WareHouse;
import com.saleor.saleor_api.utils.filterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SerWareHouse {
    @Autowired
    RepoWareHouse repoWareHouse;

    @Autowired
    RepoShop repoShop;

    Map<String, Object>  reponse = new HashMap<>();


    public Object save(ResWareHouse resWareHouse){
        try {
            Optional<Shop> opShop = repoShop.findById(resWareHouse.getShopId());
            if (!opShop.isPresent()) {
                reponse.put("success", false);
                reponse.put("mesager", "khong tim thay id shop ");
                return reponse;
            }
            WareHouse newWare = new WareHouse();
            newWare.setShop(opShop.get());
            newWare.setDistrict_id(resWareHouse.getDistrict_id());
            newWare.setWard_id(resWareHouse.getWard_id());
            newWare.setProvince_id(resWareHouse.getProvince_id());
            newWare.setPhone(resWareHouse.getPhone());
            newWare.setManager(resWareHouse.getManager());
            newWare.setTitle(resWareHouse.getTitle());
            newWare.setAddress(resWareHouse.getAddress());
            newWare.setLocation(resWareHouse.getLocation());
            WareHouse data = repoWareHouse.save(newWare);
            reponse.put("success", true);
            reponse.put("data", data);
            reponse.put("mesager", "ok");
            return reponse;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }
    }

    public Object findById(Long id){
        try {
            Optional<WareHouse> opWareHouse = repoWareHouse.findById(id);
            Object data = filterObject.filter(opWareHouse, "Khong tim thấy kho ");
            return data;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }
    }

    public Object delete(Long id){
        try{
            Optional<WareHouse> opWarehouse = repoWareHouse.findById(id);
            if(!opWarehouse.isPresent()){
                reponse.put("success", false);
                reponse.put("mesager","khong tim thay id kho ");
                return reponse;
            }
                 repoWareHouse.deleteById(id);
                reponse.put("success", true);
                reponse.put("mesager","xoa thanh cong");
                return reponse;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }
    }

}
