package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.DTOImportTicketDetail;
import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.dto.DtoVariation;
import com.saleor.saleor_api.mapper.MapperProduct;
import com.saleor.saleor_api.mapper.MapperProductCatogories;
import com.saleor.saleor_api.mapper.MapperVariation;
import com.saleor.saleor_api.repo.*;
import com.saleor.saleor_api.table.*;
import com.saleor.saleor_api.utils.filterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerProduct {
    @Autowired
    RepoProduct repoProduct;
    @Autowired
    RepoProductCatogories repoProductCatogories;


    @Autowired
    SerProductCatogories serProductCatogories;

    @Autowired
    RepoUnit repoUnit;

    @Autowired
    RepoWareHouse repoWareHouse;

    @Autowired
    RepoVariation repoVariation;

    @Autowired
    MapperProduct mapperProduct;

    @Autowired
    MapperVariation mapperVariation;

    @Autowired
    MapperProductCatogories mapperProductCatogories;

    public List<DtoProduct> getAllProductByProductCatogoriesId(ProductCatogories productCatogories) {
        List<Product> product = repoProduct.findAllByProductCatogories(productCatogories);
        List<DtoProduct> dtoProductList = new ArrayList<DtoProduct>();
        for (Product item : product) {
            DtoProduct dtoProduct = mapperProduct.toDto(item);
            dtoProduct.setUnitsTitle(item.getUnits().getName());
            dtoProduct.setProductCategoriesName(item.getProductCatogories().getTitle());
            dtoProduct.setProductCategoriesId(item.getProductCatogories().getId());
//            dtoProduct.setDtoVariationList(new ArrayList<DtoVariation>(getAllVariationByProductId(item)));
            dtoProductList.add(dtoProduct);
        }
        return dtoProductList;
    }
//    public List<DtoProduct> getAllProductByProductCatogoriesIds(Long id) {
//        List<Product> product = repoProduct.findAllByProductCatogoriesid(id);
//        List<DtoProduct> dtoProductList = new ArrayList<DtoProduct>();
//        for (Product item : product) {
//            DtoProduct dtoProduct = mapperProduct.toDto(item);
//            dtoProduct.setUnitsTitle(item.getUnits().getName());
//            dtoProduct.setProductCategoriesName(item.getProductCatogories().getTitle());
////            dtoProduct.setDtoVariationList(new ArrayList<DtoVariation>(getAllVariationByProductId(item)));
//            dtoProductList.add(dtoProduct);
//        }
//        return dtoProductList;
//    }
    public List<DtoVariation> getAllVariationByProductId(Product product) {
        List<Variation> variations = repoVariation.findAllByProduct(product);
        List<DtoVariation> dtoVariationList = new ArrayList<DtoVariation>();
        for (Variation item : variations) {
            DtoVariation dtoVariation = mapperVariation.toDto(item);

            dtoVariationList.add(dtoVariation);
        }
        return dtoVariationList;
    }
    public List<DtoProduct> getDtoProductPage(Pageable pageable){
        List<Product> products = repoProduct.findAllBy(pageable);
        List<DtoProduct> dtoProducts = new ArrayList<DtoProduct>();
        for (Product item : products) {
            DtoProduct dtoProduct = mapperProduct.toDto(item);
            dtoProduct.setUnitsTitle(item.getUnits().getName());
            dtoProduct.setProductCategoriesName(item.getProductCatogories().getTitle());
            dtoProduct.setProductCategoriesId(item.getProductCatogories().getId());
            dtoProduct.setDtoVariationList(new ArrayList<DtoVariation>(getAllVariationByProductId(item)));
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }
    public List<DtoProduct> getDtoProduct(){
        List<Product> products = repoProduct.findProductByQuantitySoldLimitedTo();
        List<DtoProduct> dtoProducts = new ArrayList<DtoProduct>();
        for (Product item : products) {
            DtoProduct dtoProduct = mapperProduct.toDto(item);
            dtoProduct.setUnitsTitle(item.getUnits().getName());
            dtoProduct.setProductCategoriesId(item.getProductCatogories().getId());
            dtoProduct.setProductCategoriesName(item.getProductCatogories().getTitle());
            dtoProduct.setDtoVariationList(new ArrayList<DtoVariation>(getAllVariationByProductId(item)));
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }
    public List<DtoProduct> getDtoProductById(Long id){
        List<Product> products = repoProduct.findAllById(id);
        List<DtoProduct> dtoProducts = new ArrayList<DtoProduct>();
        for (Product item : products) {
            DtoProduct dtoProduct = mapperProduct.toDto(item);
            dtoProduct.setUnitsTitle(item.getUnits().getName());
            dtoProduct.setProductCategoriesName(item.getProductCatogories().getTitle());
            dtoProduct.setDtoVariationList(new ArrayList<DtoVariation>(getAllVariationByProductId(item)));
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }
    public List<Product> GetAll()

    {
        return repoProduct.findAll();
    }

    //Tìm kiếm theo Id và tên,sđt khách hàng
    public  List<Product> FindCatogoriesList(String query){
        List<Product> productsFind = repoProduct.findAllByName(query);
        List<ProductCatogories> catogoriesFinds  = repoProductCatogories.findByTitleContainingAndCodeContaining(query,query);

        List<Product> productsSearch = new ArrayList<>();

        if(productsFind.size() > 0){
            for(Product product : productsFind){
                productsSearch.add(product);
            }
        }
        return getBill(productsSearch, catogoriesFinds);
    }
    public  List<Product> FindCatogoriesPage(Pageable pageable, String query){
        Page<Product> productsFind = repoProduct.findAllByName(pageable, query);
        List<ProductCatogories> catogoriesFind = repoProductCatogories.findByTitleContainingAndCodeContaining(query, query);
        List<Product> productsSearch = new ArrayList<>();
        if(productsFind.getSize() > 0){
            for(Product product1 : productsFind){
                productsSearch.add(product1);
            }
        }
        return getBill(productsSearch, catogoriesFind);
    }
    private List<Product> getBill(List<Product> productsSearch, List<ProductCatogories> catogoriesFind) {
        if (catogoriesFind.size() > 0) {
            List<Product> productList = repoProduct.findAll();
            for (ProductCatogories productCatogories : catogoriesFind) {
                for (Product product : productList) {
                    if (productCatogories.getId().equals(product.getProductCatogories().getId())) {
                        productList.add(product);
                    }
                }
            }

        }
        List<Product>  products= productsSearch.stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .collect(Collectors.toList());
        return products;
    }



    // check sku khi them san pham moi
    private boolean isSkuExist(String sku) {
        List<Product> ListSku = repoProduct.findBySkuContaining(sku);

        if (ListSku != null && ListSku.size() > 0  ) {
            return true;
        }
        return false;
    }

    // check xem don vi tinh co ton tai hay khong
    private Units checkProductUnit(String unit) {
        List<Units> productUnitOpt = repoUnit.findByName(unit);
        if (productUnitOpt.size() > 0) {
            return productUnitOpt.get(0);
        }
        return null;
    }
    // check xem loại sản phẩm có tồn tại không
    private ProductCatogories checkProductCatogories(String catogories) {
        List<ProductCatogories> productCatogories = repoProductCatogories.findByTitle(catogories);
        if (productCatogories.size() > 0) {
            return productCatogories.get(0);
        }
        return null;
    }
    public Object UpdatePro(DtoProduct dtoProduct) {
        Map<String, Object> response = new HashMap<String,Object>();
        try {
            // check sku
            if (isSkuExist(dtoProduct.getSku())) {
                response.put("message", "mã sku đã tồn tại!!");
                response.put("success", false);
                return response;
            }
            // tìm tên đơn vị tính nêu có thì trả ra
            String unit = dtoProduct.getUnitsTitle();
            Units units = checkProductUnit(unit);
            if (units == null) {
                Units newUnits = new Units();
                newUnits.setName(unit);
                newUnits.setActive(true);
                units = repoUnit.save(newUnits);
            }
            // tìm loại sản phẩm nếu có thì trả ra
            String catogories = dtoProduct.getProductCategoriesName();
            ProductCatogories catogories1 = checkProductCatogories(catogories);
            if(catogories1 == null){
                response.put("message", "Loại sản phẩm chưa có");
                response.put("success", false);
                return response;
            }
            Product product = new Product();
            product.setId(dtoProduct.getId());
            product.setContent(dtoProduct.getContent());
            product.setCreatedDate(new Date());
            product.setBarCode(dtoProduct.getBarCode());
            product.setDescs(dtoProduct.getDescs());
            product.setDescsDetail(dtoProduct.getDescsDetail());
            product.setName(dtoProduct.getName());
            product.setModifiedDate(new Date());
            product.setCreatedBy(dtoProduct.getCreatedBy());
            product.setModifiedBy(dtoProduct.getModifiedBy());
            product.setSku(dtoProduct.getSku());
            product.setPrice(dtoProduct.getPrice());
            product.setSalePrice(dtoProduct.getSalePrice());
            product.setQrCode(dtoProduct.getQrCode());
            product.setIsActive(dtoProduct.getIsActive());
            product.setQuantitySold(dtoProduct.getQuantitySold());
            product.setQuantityCurrent(dtoProduct.getQuantityCurrent());
            product.setImages(dtoProduct.getImages());
            product.setUnits(units);
            product.setProductCatogories(catogories1);
            Product coproduct = repoProduct.save(product);
            product.setId(coproduct.getId());
            List<DtoVariation> dtoVariationList = dtoProduct.getDtoVariationList();
            for(DtoVariation item : dtoVariationList){
                Variation newVariation = new Variation();
                newVariation.setTitle(item.getTitle());
                newVariation.setCreateBy(item.getCreateBy());
                newVariation.setCreatedDate(new Date());
                newVariation.setProperties(item.getProperties());
                newVariation.setProduct(coproduct);
                newVariation = repoVariation.save(newVariation);
                item.setId(newVariation.getId());
            }
            response.put("data", dtoProduct);
            response.put("success", true);
            return response;
        } catch (Exception ex) {
            response.put("data", ex);
            response.put("success", false);
            return response;
        }
    }
    public Object InsSent(DtoProduct dtoProduct) {
        Map<String, Object> response = new HashMap<String,Object>();
        try {
            // check sku
            if (isSkuExist(dtoProduct.getSku())) {
                response.put("message", "mã sku đã tồn tại!!");
                response.put("success", false);
                return response;
            }
            // tìm tên đơn vị tính nêu có thì trả ra
            String unit = dtoProduct.getUnitsTitle();
            Units units = checkProductUnit(unit);
            if (units == null) {
                Units newUnits = new Units();
                newUnits.setName(unit);
                newUnits.setActive(true);
                units = repoUnit.save(newUnits);
            }
            // tìm loại sản phẩm nếu có thì trả ra
            String catogories = dtoProduct.getProductCategoriesName();
            ProductCatogories catogories1 = checkProductCatogories(catogories);
            if(catogories1 == null){
                response.put("message", "Loại sản phẩm chưa có");
                response.put("success", false);
                return response;
            }
            Double quantity=0.0;
            Product product = new Product();
            product.setContent(dtoProduct.getContent());
            product.setCreatedDate(new Date());
            product.setBarCode(dtoProduct.getBarCode());
            product.setDescs(dtoProduct.getDescs());
            product.setDescsDetail(dtoProduct.getDescsDetail());
            product.setName(dtoProduct.getName());
            product.setModifiedDate(new Date());
            product.setCreatedBy(dtoProduct.getCreatedBy());
            product.setModifiedBy(dtoProduct.getModifiedBy());
            product.setSku(dtoProduct.getSku());
            product.setPrice(dtoProduct.getPrice());
            product.setSalePrice(dtoProduct.getSalePrice());
            product.setQrCode(dtoProduct.getQrCode());
            product.setIsActive(dtoProduct.getIsActive());
            product.setQuantitySold(dtoProduct.getQuantitySold());
            product.setQuantityCurrent(quantity);
            product.setImages(dtoProduct.getImages());
            product.setUnits(units);
            product.setProductCatogories(catogories1);
            Product coproduct = repoProduct.save(product);
            product.setId(coproduct.getId());
            List<DtoVariation> dtoVariationList = dtoProduct.getDtoVariationList();
            for(DtoVariation item : dtoVariationList){
                Variation newVariation = new Variation();
                newVariation.setTitle(item.getTitle());
                newVariation.setCreateBy(item.getCreateBy());
                newVariation.setCreatedDate(new Date());
//                newVariation.setIsActive(item.getActive());
                newVariation.setProperties(item.getProperties());
                newVariation.setProduct(coproduct);
                newVariation = repoVariation.save(newVariation);
                item.setId(newVariation.getId());
            }
            response.put("data", dtoProduct);
            response.put("success", true);
            return response;
        } catch (Exception ex) {
            response.put("data", ex);
            response.put("success", false);
            return response;
        }
    }
    public Optional<Product> GetByID(Long id)
    {
        return  repoProduct.findById(id);
    }
    Map<String, Object> response = new HashMap<String,Object>();
    public Object findBySku(String sku){

        try {
            Optional<Product> product = repoProduct.findBySku(sku);
            Object data = filterObject.filter(product, "Khong tim thấy danh muc san pham ");
            return data;
        }
        catch (Exception e){
            response.put("success", false);
            response.put("mesager",e.getMessage());
            return response;
        }
    }

    public Object delete(Long id){
        try{
            Optional<Product> product = repoProduct.findById(id);
            if (product.isPresent()) {
                repoProduct.deleteById(id);
                response.put("success", true);
                response.put("mesager", "xoa thanh cong");
            } else {
                response.put("success", false);
                response.put("mesager", "khong tim thay id kho");
            }
            return response;
        }
        catch (Exception e){
            response.put("success", false);
            response.put("mesager",e.getMessage());
            return response;
        }
    }
}
