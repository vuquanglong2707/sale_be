package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.service.SerCustomer;
import com.saleor.saleor_api.service.SerProduct;
import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.Product;
import com.saleor.saleor_api.table.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("api/customer")
public class ContCustomer {
    @Autowired
    SerCustomer serCustomer;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getPage(Pageable pageable, String query)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        int total = serCustomer.GetAll().size();
        try{
            if(pageable.getPageNumber() == 0){
                Pageable pagingSort = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<Customer> data = serCustomer.getCustomerPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Customer> data = serCustomer.FindCustomerList(query);
                    if(data.size() > 15){
                        List<Customer> userList = data.stream()
                                .sorted(Comparator.comparing(Customer::getId))
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
                    List<Customer> data = serCustomer.getCustomerPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Customer> data = serCustomer.FindCustomerPage(pagingSort, query);
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

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        Map<String, Object> response =  new HashMap<>();
        try {
            Optional<Customer> optional = serCustomer.GetByID(id);
            Customer customer = optional.get();
            response.put("data",customer);
            response.put("success",true);
            response.put("message", "Ok");
            return new ResponseEntity<>(response,HttpStatus.OK);

        }
        catch (Exception e){
            response.put("success",false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/ins", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> Insert(@RequestBody Customer customer)
    {
        return new ResponseEntity<>(serCustomer.InsertData(customer), HttpStatus.OK);
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> Update(@RequestBody Customer customer)
    {
        return new ResponseEntity<>(serCustomer.UpdateData(customer), HttpStatus.OK);
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> Delete(@RequestParam(value = "id", required =false) Long id) {
        Customer data= serCustomer.GetByID(id).orElse(null);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            serCustomer.Delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
