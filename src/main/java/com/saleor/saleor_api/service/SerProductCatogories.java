package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.DtoProductCatogories;
import com.saleor.saleor_api.mapper.MapperProductCatogories;
import com.saleor.saleor_api.repo.RepoProductCatogories;
import com.saleor.saleor_api.repo.RepoWareHouse;
import com.saleor.saleor_api.table.*;
import com.saleor.saleor_api.utils.filterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerProductCatogories {

    @Autowired
    RepoProductCatogories repoProductCatogories;

    @Autowired
    RepoWareHouse repoWareHouse;
    @Autowired
    SerUser serUser;

    @Autowired
    SerProduct serProduct;

    @Autowired
    SerProductCatogories serProductCatogories;
    @Autowired
    MapperProductCatogories mapperProductCatogories;
//    public List<DtoProductCatogories> getdtoProductCatogoriesPage(Pageable pageable){
//        List<ProductCatogories> productCatogories= repoProductCatogories.findAllBy(pageable);
//        List<DtoProductCatogories> dtoProductCatogories= new ArrayList<>();
//        for(ProductCatogories item:productCatogories){
//            DtoProductCatogories dtoProductCatogories1= mapperProductCatogories.toDto(item);
////            dtoProductCatogories1.setDtoProducts(new ArrayList<DtoProduct>(serProduct.getAllProductByProductCatogoriesId(item)));
//            dtoProductCatogories.add(dtoProductCatogories1);
//        }
//        return dtoProductCatogories;
//    }
    public List<DtoProductCatogories> getdtoProductCatogoriesPage(Pageable pageable){
        List<ProductCatogories> productCatogories= repoProductCatogories.findAllBy(pageable);
        List<DtoProductCatogories> dtoProductCatogories= new ArrayList<>();
        for(ProductCatogories item:productCatogories){
            DtoProductCatogories dtoProductCatogories1= mapperProductCatogories.toDto(item);

            dtoProductCatogories1.setWareHouseTitle(item.getWareHouse().getTitle());
//            dtoProductCatogories1.setDtoProducts(new ArrayList<DtoProduct>(serProduct.getAllProductByProductCatogoriesId(item)));
            dtoProductCatogories.add(dtoProductCatogories1);
        }
        return dtoProductCatogories;
    }

    Map<String,Object> reponse = new HashMap<>();
    public Object GetByID(Long id)
    {
        return  repoProductCatogories.findById(id);
    }

//    private Optional<WareHouse> checkWareHouse(Long id) {
//            Optional<WareHouse> wareHouses = repoWareHouse.findById(id);
//            if(wareHouses==null){
//                return null;
//            }
//            return wareHouses;
//
//    }

//    public Object save(ProductCatogories response){
//        try{
//            ProductCatogories newdata = repoProductCatogories.save(response);
//            reponse.put("data", newdata);
//            reponse.put("success", true);
//            reponse.put("mesager", "ok");
//            return reponse;
//        }
//        catch (Exception e){
//            reponse.put("success", false);
//            reponse.put("mesager", e.getMessage());
//            return  reponse;
//        }
//    }

    public Object findById(Long id){
        try {
            Optional<ProductCatogories> opCatogories = repoProductCatogories.findById(id);
            Object data = filterObject.filter(opCatogories, "Khong tim thấy danh muc san pham ");
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
            Optional<ProductCatogories> opCatogories = repoProductCatogories.findById(id);
            if(!opCatogories.isPresent()){
                reponse.put("success", false);
                reponse.put("mesager","Khong tim thấy danh muc san pham ");
                return reponse;
            }
            repoProductCatogories.deleteById(id);
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
    public Page<ProductCatogories> GetPage(Pageable pageable)
    {
        return repoProductCatogories.findAll(pageable);
    }

    public List<ProductCatogories> GetAll()

    {
        return repoProductCatogories.findAll();
    }

    public  List<ProductCatogories> FindCatogories(String query){
        List<ProductCatogories> productCatogoriesFind = repoProductCatogories.findByTitle(query);
        List<ProductCatogories> productCatogoriesSearch = new ArrayList<>();
        if(productCatogoriesFind.size() > 0){
            for(ProductCatogories productCatogories : productCatogoriesFind){
                productCatogoriesSearch.add(productCatogories);
            }
        }
        return getProductCatogories(productCatogoriesSearch);
    }

    public  List<ProductCatogories> FindProductCatogoriesPage(Pageable pageable, String query){
        Page<ProductCatogories> productCatogoriesFind = repoProductCatogories.findByTitle(pageable, query);
        List<ProductCatogories> productCatogoriesSearch = new ArrayList<>();
        if(productCatogoriesFind.getSize() > 0){
            for(ProductCatogories productCatogories : productCatogoriesFind){
                productCatogoriesSearch.add(productCatogories);
            }
        }
        return getProductCatogories(productCatogoriesSearch);
    }
    private List<ProductCatogories> getProductCatogories(List<ProductCatogories> productCatogoriesSearch){
        List<ProductCatogories> productCatogories = productCatogoriesSearch.stream()
                .sorted(Comparator.comparing(ProductCatogories::getId).reversed())
                .collect(Collectors.toList());
        return productCatogories;

    }
    // check xem kho có tồn tại chưa
    private WareHouse checkWareHouse(String warehouse) {
        List<WareHouse> wareHouses = repoWareHouse.findByTitle(warehouse);
        if (wareHouses.size() > 0) {
            return wareHouses.get(0);
        }
        return null;
    }
    public Object UpdateCatogories (DtoProductCatogories dtoProductCatogories) {
        Map<String, Object> response = new HashMap<String,Object>();
        try {
            String wareHouseTitle = dtoProductCatogories.getWareHouseTitle();
            WareHouse wareHouse = checkWareHouse(wareHouseTitle);
            if(wareHouse == null){
                response.put("message", "Loại sản phẩm chưa có");
                response.put("success", false);
                return response;
            }
            ProductCatogories productCatogories = new ProductCatogories();
            productCatogories.setId(dtoProductCatogories.getId());
            productCatogories.setModifiedDate(new Date());
            productCatogories.setCreateBy(dtoProductCatogories.getCreateBy());
            productCatogories.setModifiedBy(dtoProductCatogories.getModifiedBy());
            productCatogories.setCreatedDate(new Date());
            productCatogories.setIsActive(dtoProductCatogories.getIsActive());
            productCatogories.setTitle(dtoProductCatogories.getTitle());
            productCatogories.setCode(dtoProductCatogories.getCode());
            productCatogories.setWareHouse(wareHouse);
            ProductCatogories coproductCatogories = repoProductCatogories.save(productCatogories);
            response.put("data", coproductCatogories);
            response.put("success", true);
            return response;
        } catch (Exception ex) {
            response.put("data", ex);
            response.put("success", false);
            return response;
        }
    }
    public Object InsSentCatogories (DtoProductCatogories dtoProductCatogories) {
        Map<String, Object> response = new HashMap<String,Object>();
        try {
            String wareHouseTitle = dtoProductCatogories.getWareHouseTitle();
            WareHouse wareHouse = checkWareHouse(wareHouseTitle);
            if(wareHouse == null){
                response.put("message", "Loại sản phẩm chưa có");
                response.put("success", false);
                return response;
            }
            ProductCatogories productCatogories = new ProductCatogories();
//            productCatogories.setId(dtoProductCatogories.getId());
            productCatogories.setModifiedDate(new Date());
            productCatogories.setCreateBy(dtoProductCatogories.getCreateBy());
            productCatogories.setModifiedBy(dtoProductCatogories.getModifiedBy());
            productCatogories.setCreatedDate(new Date());
            productCatogories.setIsActive(dtoProductCatogories.getIsActive());
            productCatogories.setTitle(dtoProductCatogories.getTitle());
            productCatogories.setCode(dtoProductCatogories.getCode());
            productCatogories.setWareHouse(wareHouse);
            ProductCatogories coproductCatogories = repoProductCatogories.save(productCatogories);
            response.put("data", coproductCatogories);
            response.put("success", true);
            return response;
        } catch (Exception ex) {
            response.put("data", ex);
            response.put("success", false);
            return response;
        }
    }

}
