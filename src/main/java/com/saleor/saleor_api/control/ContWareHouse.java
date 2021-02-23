package com.saleor.saleor_api.control;

import com.saleor.saleor_api.payload.ResWareHouse;
import com.saleor.saleor_api.service.SerWareHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/warehouse")
public class ContWareHouse {
    @Autowired
    SerWareHouse serWareHouse;

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> getByID(@RequestParam(value = "id", required =false) Long id)
    {
        return new ResponseEntity<>(serWareHouse.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value="/ins",method = RequestMethod.POST)
    @CrossOrigin(origins="*",maxAge = 3600)
    public ResponseEntity<?> insSent(@RequestBody ResWareHouse prInput)
    {
        return new ResponseEntity<>(serWareHouse.save(prInput),HttpStatus.OK);
    }

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<?> deleteSent(@RequestParam Long id)
    {
        return new ResponseEntity<>(serWareHouse.delete(id),HttpStatus.OK);
    }
}
