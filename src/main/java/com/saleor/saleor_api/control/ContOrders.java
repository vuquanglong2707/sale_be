package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.dto.DtoOrders;
import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.service.SerOrders;
import com.saleor.saleor_api.service.SerProduct;
import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.ImportTicket;
import com.saleor.saleor_api.table.Orders;
import com.saleor.saleor_api.table.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/orders")
public class ContOrders {
    @Autowired
    SerOrders serOrders;

    //find theo mã hóa đơn, trạng thái hóa đơn,tên và sđt khách hàng(key: query value: trang thai hóa đơn, tên, sđt khách hàng)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getPage(Pageable pageable, String query)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        int total = serOrders.GetAll().size();
        try{
            if(pageable.getPageNumber() == 0){
                Pageable pagingSort = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<DtoOrders> data = serOrders.getDtoOrderPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Orders> data = serOrders.FindCustomerList(query);
                    if(data.size() > 15){
                        List<Orders> ordersList = data.stream()
                                .sorted(Comparator.comparing(Orders::getId))
                                .collect(Collectors.toList());
                        resp.setTotal(ordersList.size());
                        resp.setData(ordersList.subList(ordersList.size() - 10, ordersList.size()));
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
                    List<DtoOrders> data = serOrders.getDtoOrderPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Orders> data = serOrders.FindCustomerPage(pagingSort, query);
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
    @RequestMapping(value = "/findsku", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getBySKU(@RequestParam(value = "id", required = false) Long id) {
        return new ResponseEntity<>(serOrders.GetByID(id), HttpStatus.OK);
    }
    @RequestMapping(value="/ins",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> insSent(@RequestBody DtoOrders prInput)
    {
        return new ResponseEntity<>(serOrders.InsSent(prInput), HttpStatus.OK);
    }
    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        Map<String, Object> response =  new HashMap<>();
        try {
            Optional<Orders> optional = serOrders.GetByID(id);
            Orders orders = optional.get();
            response.put("data",orders);
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
}
