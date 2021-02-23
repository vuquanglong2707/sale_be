package com.saleor.saleor_api.control.controlpublic;

import com.saleor.saleor_api.reponse.message.ResponseStatusMessage;
import com.saleor.saleor_api.service.AddressService;
import com.saleor.saleor_api.table.AddressDistrict;
import com.saleor.saleor_api.table.AddressProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "*", maxAge = 3600)

public class ContAddress {

    @Autowired
    AddressService addressService;


    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/province")
    public ResponseEntity<?> provinceList() {
        List<AddressProvince> provinces = addressService.getProvinces();
        ResponseStatusMessage statusMessage = new ResponseStatusMessage(true, "success", provinces);
        return new ResponseEntity<ResponseStatusMessage>(statusMessage, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/district/{provinceID}")
    public ResponseEntity<?> districtList(@PathVariable String provinceID) {

        Optional<AddressProvince> province = addressService.getProvince(provinceID);
        if (!province.isPresent()) {
            ResponseStatusMessage statusMessage = new ResponseStatusMessage(false, "Không tìm thấy tỉnh/thành phố",
                    null);
            return new ResponseEntity<ResponseStatusMessage>(statusMessage, HttpStatus.BAD_REQUEST);
        }
        ResponseStatusMessage statusMessage = new ResponseStatusMessage(true, "success", province.get().getDistricts());
        return new ResponseEntity<ResponseStatusMessage>(statusMessage, HttpStatus.OK);

    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/ward/{districtID}")
    public ResponseEntity<?> wardList(@PathVariable String districtID) {

        Optional<AddressDistrict> district = addressService.getDistrict(districtID);

        if (!district.isPresent()) {
            ResponseStatusMessage statusMessage = new ResponseStatusMessage(false, "Không tìm thấy quận/huyện", null);
            return new ResponseEntity<ResponseStatusMessage>(statusMessage, HttpStatus.BAD_REQUEST);
        }

        ResponseStatusMessage statusMessage = new ResponseStatusMessage(true, "success", district.get().getWards());
        return new ResponseEntity<ResponseStatusMessage>(statusMessage, HttpStatus.OK);
    }
}
