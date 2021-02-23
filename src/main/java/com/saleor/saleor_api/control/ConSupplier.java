package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.service.SerSupplier;
import com.saleor.saleor_api.table.ImportTicket;
import com.saleor.saleor_api.table.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/supplier")
public class ConSupplier {
    @Autowired
    SerSupplier serSupplier;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getPage(Pageable pageable, String query)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        int total = serSupplier.GetAll().size();
        try{
            if(pageable.getPageNumber() == 0){
                Pageable pagingSort = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<Supplier> data = serSupplier.getSupplierPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Supplier> data = serSupplier.FindSupplierList(query);
                    if(data.size() > 15){
                        List<Supplier> userList = data.stream()
                                .sorted(Comparator.comparing(Supplier::getId))
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
                    List<Supplier> data = serSupplier.getSupplierPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);
                }
                else{
                    List<Supplier> data = serSupplier.FindSupplinerPage(pagingSort, query);
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
    public ResponseEntity<?> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        return new ResponseEntity<>(serSupplier.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value="/ins",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> insSent(@RequestBody Supplier prInput)
    {
        return new ResponseEntity<>(serSupplier.save(prInput),HttpStatus.OK);
    }

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> deleteSent(@RequestParam Long id)
    {
        return new ResponseEntity<>(serSupplier.delete(id),HttpStatus.OK);
    }
}
