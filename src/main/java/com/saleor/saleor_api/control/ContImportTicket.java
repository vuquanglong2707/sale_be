package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.payload.PayShop;
import com.saleor.saleor_api.service.SerImportTicket;
import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.ImportTicket;
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
@RequestMapping("api/importproduct")
public class ContImportTicket {
    @Autowired
    SerImportTicket serImportTicket;
    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        Map<String, Object> response =  new HashMap<>();
        try {
            Optional<ImportTicket> optional = serImportTicket.GetByID(id);
            ImportTicket importTicket = optional.get();
            response.put("data",importTicket);
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
    @RequestMapping(value="/ins",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> insSent(@RequestBody DTOImportTicket prInput)
    {
        return new ResponseEntity<>(serImportTicket.InsSent(prInput), HttpStatus.OK);
    }
    //find theo mã hóa đơn, trạng thái hóa đơn,tên và sđt khách hàng(key: query value: trang thai hóa đơn, tên, sđt khách hàng)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getPage(Pageable pageable, String query)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        int total = serImportTicket.GetAll().size();
        try{
            if(pageable.getPageNumber() == 0){
                Pageable pagingSort = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<DTOImportTicket> data = serImportTicket.getTicketPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<ImportTicket> data = serImportTicket.FindSupplierList(query);
                    if(data.size() > 15){
                        List<ImportTicket> userList = data.stream()
                                .sorted(Comparator.comparing(ImportTicket::getId))
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
                    List<DTOImportTicket> data = serImportTicket.getTicketPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<ImportTicket> data = serImportTicket.FindSupplierPage(pagingSort, query);
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
    // Tìm kiếm từ ngày đến ngày (key:query value:05-01-2020/20-03-2020)
    @RequestMapping(value = "/findinrange", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getAllNewShop(@RequestParam String query, Pageable pageable)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        Page<ImportTicket> data = serImportTicket.getAllImportTicketInMonth(query,pageable);
        if(data.getContent().isEmpty()){
            resp.setSuccess(false);
            resp.setMsg("Không có dữ liệu hoặc Dữ liệu bạn vừa nhập không hợp lệ. ");
            response.put("success",resp.getSuccess());
            response.put("message",resp.getMsg());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            resp.setSuccess(true);
            resp.setMsg("Ok");
            resp.setData(data.getContent());
            resp.setPage(data.getPageable().getPageNumber());
            resp.setSize(data.getPageable().getPageSize());
            resp.setTotal(data.getTotalElements());

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
    }
}
