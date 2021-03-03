package com.saleor.saleor_api.control;
import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.service.SerProduct;
import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.ImportTicket;
import com.saleor.saleor_api.table.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/product")

public class ContProduct {
    @Autowired
    SerProduct serProduct;

//    @RequestMapping(value = "/findname", method = RequestMethod.GET)
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public ResponseEntity<?> getBySKU(@RequestParam(value = "id", required = false) String query) {
//        return new ResponseEntity<>(serProduct.FindByName(query), HttpStatus.OK);
//    }
    @RequestMapping(value = "/bysku", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getBySku(@RequestParam(value = "sku", required =false) String sku)
    {
        return new ResponseEntity<>(serProduct.findBySku(sku), HttpStatus.OK);
    }
    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        Resp resp = new Resp();
        Map<String, Object> response =  new HashMap<>();
        try {
            List<DtoProduct> optional = serProduct.getDtoProductById(id);
            resp.setSuccess(true);
            resp.setMsg("Ok");
            resp.setData(optional);
            response.put("data",resp.getData());
            response.put("success",resp.getSuccess());
            response.put("message", resp.getMsg());
            return new ResponseEntity<>(response,HttpStatus.OK);

        }
        catch (Exception e){
            response.put("success",false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @RequestMapping(value = "/byidcato", method = RequestMethod.GET)
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public ResponseEntity<Map<String, Object>> getByIDCato(@RequestParam(value = "id", required =false) Long id)
//    {
//        Resp resp = new Resp();
//        Map<String, Object> response =  new HashMap<>();
//        try {
//            List<DtoProduct> optional = serProduct.getAllProductByProductCatogoriesIds(id);
//            resp.setSuccess(true);
//            resp.setMsg("Ok");
//            resp.setData(optional);
//            response.put("data",resp.getData());
//            response.put("success",resp.getSuccess());
//            response.put("message", resp.getMsg());
//            return new ResponseEntity<>(response,HttpStatus.OK);
//
//        }
//        catch (Exception e){
//            response.put("success",false);
//            response.put("error", e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @RequestMapping(value="/ins",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> insSent(@RequestBody DtoProduct prInput)
    {
        return new ResponseEntity<>(serProduct.InsSent(prInput), HttpStatus.OK);
    }
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> updatePro(@RequestBody DtoProduct prInput)
    {
        return new ResponseEntity<>(serProduct.UpdatePro(prInput), HttpStatus.OK);
    }
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> deleteSent(@RequestParam Long id)
    {
        return new ResponseEntity<>(serProduct.delete(id),HttpStatus.OK);
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getPage(Pageable pageable, String query)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        int total = serProduct.GetAll().size();
        try{
            if(pageable.getPageNumber() == 0){
                Pageable pagingSort = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<DtoProduct> data = serProduct.getDtoProductPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Product> data = serProduct.FindCatogoriesList(query);
                    if(data.size() > 15){
                        List<Product> userList = data.stream()
                                .sorted(Comparator.comparing(Product::getId))
                                .collect(Collectors.toList());
                        resp.setTotal(userList.size());
                        resp.setData(userList.subList(userList.size() - 10, userList.size()));
                    }
                    else {
                        resp.setData(data);
                        resp.setTotal(total);
                    }
                }
                resp.setPage(pagingSort.getPageNumber() + 1);
                resp.setSize(pagingSort.getPageSize());
            }
            else{
                Pageable pagingSort = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<DtoProduct> data = serProduct.getDtoProductPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Product> data = serProduct.FindCatogoriesPage(pagingSort, query);
                    resp.setData(data);
                    resp.setTotal(data.size());
                }
                resp.setPage(pageable.getPageNumber());
                resp.setSize(pageable.getPageSize());
            }
            resp.setSuccess(true);
            resp.setMsg("Ok");

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("page", resp.getPage());
            metadata.put("size", resp.getSize());
            metadata.put("total", resp.getTotal());

            response.put("data", resp.getData());
            response.put("success", resp.getSuccess());
            response.put("message", resp.getMsg());
            response.put("metaData", metadata);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            resp.setSuccess(false);
            response.put("success",resp.getSuccess());
            response.put("error", e);
            resp.setMsg(" Không tìm thấy dữ liệu vừa nhập. " );
            response.put("message",resp.getMsg());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/getmax", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getAllDto()
    {
        Resp resp = new Resp();
        Map<String, Object> response =  new HashMap<>();
        try {
            List<DtoProduct> data = serProduct.getDtoProduct();
            resp.setSuccess(true);
            resp.setMsg("Ok");
            resp.setData(data);
            response.put("data",resp.getData());
            response.put("success",resp.getSuccess());
            response.put("message", resp.getMsg());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            resp.setSuccess(false);
            response.put("success",resp.getSuccess());
            response.put("error", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}