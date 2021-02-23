package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.dto.DTOImportTicketDetail;
import com.saleor.saleor_api.repo.RepoSupplier;
import com.saleor.saleor_api.table.ImportTicket;
import com.saleor.saleor_api.table.Supplier;
import com.saleor.saleor_api.table.WareHouse;
import com.saleor.saleor_api.utils.filterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerSupplier {
    @Autowired
    RepoSupplier repoSupplier;

    Map<String,Object> reponse = new HashMap<>();

    public  Object save(Supplier payload){
        try {
            Supplier data = repoSupplier.save(payload);
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

    public List<Supplier> GetAll(){return repoSupplier.findAll();}
    public Page<Supplier> GetPage(Pageable pageable)
    {
        return repoSupplier.findAll(pageable);
    }
    public List<Supplier> getSupplierPage(Pageable pageable){
        List<Supplier> supplierList = repoSupplier.findAllBy(pageable);
        return supplierList;
    }
    public  List<Supplier> FindSupplierList(String query){
        List<Supplier> supplierFind = repoSupplier.findByPhoneContainingOrTitleContaining(query,query);
        List<Supplier> suppliersSearch = new ArrayList<>();
        if(supplierFind.size() > 0){
            for(Supplier supplier : supplierFind){
                suppliersSearch.add(supplier);
            }
        }
        return getSupplier(suppliersSearch);
    }

    public  List<Supplier> FindSupplinerPage(Pageable pageable, String query){
        Page<Supplier> suppliersFind = repoSupplier.findByPhoneContainingOrTitleContaining(pageable, query,query);
        List<Supplier> suppliersSearch = new ArrayList<>();
        if(suppliersFind.getSize() > 0){
            for(Supplier supplier : suppliersFind){
                suppliersSearch.add(supplier);
            }
        }
        return getSupplier(suppliersSearch);
    }

    private List<Supplier> getSupplier(List<Supplier>suppliersSearch){
        List<Supplier> suppliersList = suppliersSearch.stream()
                .sorted(Comparator.comparing(Supplier::getId).reversed())
                .collect(Collectors.toList());
        return suppliersList;

    }

    public Object findById(Long id){
        try {
            Optional<Supplier> opSupplier = repoSupplier.findById(id);
            Object data = filterObject.filter(opSupplier, "Khong tim thấy nhà cung cấp ");
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
            Optional<Supplier> opSupplier = repoSupplier.findById(id);
            if(!opSupplier.isPresent()){
                reponse.put("success", false);
                reponse.put("mesager","khong tim thay id kho ");
                return reponse;
            }
            repoSupplier.deleteById(id);
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
