package com.saleor.saleor_api.control;

import com.saleor.saleor_api.data.Resp;
import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.dto.DtoProductCatogories;
import com.saleor.saleor_api.service.SerProductCatogories;
import com.saleor.saleor_api.table.ProductCatogories;
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
@RequestMapping("api/catogories")
public class ContProductCatogories {

    @Autowired
    SerProductCatogories serProductCatogories;

    @RequestMapping(value = "/getAllProductByCato", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getAllProductCatogories(Pageable pageable) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DtoProductCatogories> data = serProductCatogories.getdtoProductCatogoriesPage(pageable);
            response.put("data", data);
            response.put("success", true);
            response.put("message", "Ok");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
        public ResponseEntity<Map<String, Object>> getAll()
        {
            Resp resp = new Resp();
            Map<String, Object> response =  new HashMap<>();
            try {
                List<ProductCatogories> data = serProductCatogories.GetAll();
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
    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        return new ResponseEntity<>(serProductCatogories.findById(id), HttpStatus.OK);
    }

//    @RequestMapping(value="/ins",method = RequestMethod.POST)
//    @CrossOrigin(origins="*",maxAge = 3600)
//    public ResponseEntity<?> insSent(@RequestBody ProductCatogories prInput)
//    {
//        return new ResponseEntity<>(serProductCatogories.save(prInput),HttpStatus.OK);
//    }

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> deleteSent(@RequestParam Long id)
    {
        return new ResponseEntity<>(serProductCatogories.delete(id),HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Map<String, Object>> getPage(Pageable pageable, String query)
    {
        Resp resp = new Resp();
        Map<String, Object> response = new HashMap<>();
        int total = serProductCatogories.GetAll().size();
        try{
            if(pageable.getPageNumber() == 0){
                Pageable pagingSort = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    List<DtoProductCatogories> data = serProductCatogories.getdtoProductCatogoriesPage(pagingSort);
                    resp.setData(data);
                    resp.setTotal(total);

                }
                else{
                    List<ProductCatogories> data = serProductCatogories.FindCatogories(query);
                    if(data.size() > 15){
                        List<ProductCatogories> userList = data.stream()
                                .sorted(Comparator.comparing(ProductCatogories::getId))
                                .collect(Collectors.toList());
                        resp.setTotal(userList.size());
                        resp.setData(userList.subList(userList.size() - 10, userList.size()));
                    }
                    else {
                        resp.setData(data);
                        resp.setTotal(data.size()   );
                    }
                }
                resp.setPage(pagingSort.getPageNumber() + 1);
                resp.setSize(pagingSort.getPageSize());
            }
            else{
                Pageable pagingSort = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.by(Sort.Order.desc("id")));
                if(query == null){
                    Page<ProductCatogories> data = serProductCatogories.GetPage(pagingSort);
                    resp.setData(data.getContent());
                    resp.setTotal(total);
                }
                else{
                    List<ProductCatogories> data = serProductCatogories.FindProductCatogoriesPage(pagingSort, query);
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

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value="/update",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> updatePro(@RequestBody DtoProductCatogories Input)
    {
        return new ResponseEntity<>(serProductCatogories.UpdateCatogories(Input), HttpStatus.OK);
    }
    @RequestMapping(value="/ins",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> insSent(@RequestBody DtoProductCatogories Input)
    {
        return new ResponseEntity<>(serProductCatogories.InsSentCatogories(Input), HttpStatus.OK);
    }
}
