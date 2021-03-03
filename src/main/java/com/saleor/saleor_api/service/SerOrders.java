package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.*;
import com.saleor.saleor_api.mapper.MapperOrders;
import com.saleor.saleor_api.repo.RepoCustomer;
import com.saleor.saleor_api.repo.RepoDetailOrder;
import com.saleor.saleor_api.repo.RepoOrders;
import com.saleor.saleor_api.repo.RepoProduct;
import com.saleor.saleor_api.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerOrders {
    @Autowired
    RepoOrders repoOrders;
    @Autowired
    SerOrderDetail serOrderDetail;
    @Autowired
    RepoDetailOrder repoDetailOrder;
    @Autowired
    RepoProduct repoProduct;

    @Autowired
    RepoCustomer repoCustomer;
    @Autowired
    MapperOrders mapperOrders;
    public List<DtoOrders> GetAll()
    {
        return  mapperOrders.toDtoList(repoOrders.findAll());
    }
    public Optional<Orders> GetByID(Long id)
    {
        return  repoOrders.findById(id);
    }
    public List<DtoOrders> getDtoOrderPage(Pageable pageable){
        List<Orders> orders = repoOrders.findAllBy(pageable);
        List<DtoOrders> dtoOrders = new ArrayList<DtoOrders>();
        for (Orders item : orders) {
            DtoOrders dtoOrder = mapperOrders.toDto(item);
            dtoOrder.setCustomer_name(item.getCustomer().getName());
            dtoOrder.setCustomer_phone(item.getCustomer().getPhone());
            dtoOrder.setCodeOrder(item.getCodeOrder());
            dtoOrder.setDtoOrderDetails(new ArrayList<DtoOrderDetail>(serOrderDetail.getAllOrderDetailByOrderId(item)));
            dtoOrders.add(dtoOrder);
        }
        return dtoOrders;
    }
    //Tìm kiếm theo Id và tên,sđt khách hàng
    public  List<Orders> FindCustomerList(String query){
        List<Orders> ordersFind = repoOrders.findByCodeOrder(query);
        List<Customer> customersFind  = repoCustomer.findByNameContainingOrPhoneContaining(query,query);

        List<Orders> billSearch = new ArrayList<>();

        if(ordersFind.size() > 0){
            for(Orders orders : ordersFind){
                billSearch.add(orders);
            }
        }
        return getBill(billSearch, customersFind);
    }
    public  List<Orders> FindCustomerPage(Pageable pageable, String query){
        Page<Orders> billFind = repoOrders.findByCodeOrder(pageable, query);
        List<Customer> customersFind = repoCustomer.findByNameContainingOrPhoneContaining(query, query);
        List<Orders> billSearch = new ArrayList<>();
        if(billFind.getSize() > 0){
            for(Orders bill : billFind){
                billSearch.add(bill);
            }
        }
        return getBill(billSearch, customersFind);
    }
    private List<Orders> getBill(List<Orders> billSearch, List<Customer> customersFind) {
        if (customersFind.size() > 0) {
            List<Orders> orders = repoOrders.findAll();
            for (Customer customer : customersFind) {
                for (Orders orders1 : orders) {
                    if (customer.getId().equals(orders1.getCustomer().getId())) {
                        billSearch.add(orders1);
                    }
                }
            }

        }
        List<Orders> ordersList = billSearch.stream()
                .sorted(Comparator.comparing(Orders::getId).reversed())
                .collect(Collectors.toList());
        return ordersList;
    }
    private Product checkProduct(Long id ) {
        List<Product> products = repoProduct.findAllById(id);
        if (products.size() > 0) {
            return products.get(0);
        }
        return null;
    }
    private boolean iscodeExist(String code) {
        List<Orders> ListCodeOrder = repoOrders.findByCodeOrderContaining(code);

        if (ListCodeOrder != null && ListCodeOrder.size() > 0  ) {
            return true;
        }
        return false;
    }
    private Customer checkCustomerNameAndPhone(String name, String phone) {
        List<Customer> customerList = repoCustomer.findByNameAndPhone(name, phone);
        if (customerList.size() > 0) {
            return customerList.get(0);
        }
        return null;
    }
    Map<String, Object> response = new HashMap<>();
    public Object  InsSent(DtoOrders dtoOrders){
        try{
            // check code order
            if (iscodeExist(dtoOrders.getCodeOrder())) {
                response.put("message", "mã order đã tồn tại!!");
                response.put("success", false);
                return response;
            }
            String customer_name = dtoOrders.getCustomer_name();
            String customer_phone = dtoOrders.getCustomer_phone();
            Customer customer = checkCustomerNameAndPhone(customer_name,customer_phone);
            if (customer == null) {
                Customer newCustomer = new Customer();
                newCustomer.setName(customer_name);
                newCustomer.setPhone(customer_phone);
                customer = repoCustomer.save(newCustomer);
            }
            Orders orders = new Orders();
            orders.setAddress(dtoOrders.getAddress());
            orders.setCash_money(dtoOrders.getCash_money());
            orders.setCodeOrder(dtoOrders.getCodeOrder());
            orders.setCreatedBy(dtoOrders.getCreatedBy());
            orders.setDiscount_money(dtoOrders.getDiscount_money());
            orders.setDiscount_percent(dtoOrders.getDiscount_percent());
            orders.setDistrict_id(dtoOrders.getDistrict_id());
            orders.setCreatedDate(dtoOrders.getCreatedDate());
            orders.setModifiedDate(new Date());
            orders.setModified_by(dtoOrders.getModified_by());
            orders.setOrder_status(dtoOrders.getOrder_status());
            orders.setOrder_status(dtoOrders.getOrder_status());
            orders.setPaid_money(dtoOrders.getPaid_money());
            orders.setProvince_id(dtoOrders.getProvince_id());
            orders.setTotal_money(dtoOrders.getTotal_money());
            orders.setWard_id(dtoOrders.getWard_id());
//            Optional<Customer> customer = repoCustomer.findById(dtoOrders.getCustomer_id());

//            if(!customer.isPresent()){
//                response.put("success", false);
//                return response;
//            }
            orders.setCustomer(customer);
            orders = repoOrders.save(orders);
            Orders newOrder = new Orders();
            newOrder = repoOrders.save(orders);
            orders.setId(newOrder.getId());

            List<DtoOrderDetail> dtoOrderDetails = dtoOrders.getDtoOrderDetails();;
            for(DtoOrderDetail item : dtoOrderDetails){
                String title = item.getProductTitle();
                Long id=item.getProductId();
                Product product1 = checkProduct(id);
                if(product1 == null){
                    response.put("message", "Sản phẩm ko tìm thấy ");
                    response.put("success", false);
                    return response;
                }
                OrderDetail newOrderDetail = new OrderDetail();
                newOrderDetail.setProperty(item.getProperty());
                newOrderDetail.setVariation(item.getVariation());
                newOrderDetail.setQuantity(item.getQuantity());
                newOrderDetail.setTotalMoney(product1.getSalePrice()*item.getQuantity());
                newOrderDetail.setProduct(product1);
                newOrderDetail.setOrders(newOrder);
                newOrderDetail = repoDetailOrder.save(newOrderDetail);
                item.setId(newOrderDetail.getId());
                item.setTotalMoney(newOrderDetail.getTotalMoney());
                item.setProductTitle(newOrderDetail.getProduct().getName());
                Double total=product1.getQuantityCurrent()-item.getQuantity();
                Product product2 = new Product();
                product2.setId(product1.getId());
                product2.setContent(product1.getContent());
                product2.setCreatedDate(new Date());
                product2.setBarCode(product1.getBarCode());
                product2.setDescs(product1.getDescs());
                product2.setName(product1.getName());
                product2.setModifiedDate(new Date());
                product2.setCreatedBy(product1.getCreatedBy());
                product2.setModifiedBy(product1.getModifiedBy());
                product2.setSku(product1.getSku());
                product2.setPrice(product1.getPrice());
                product2.setSalePrice(product1.getSalePrice());
                product2.setQrCode(product1.getQrCode());
                product2.setIsActive(product1.getIsActive());
                product2.setQuantitySold(product1.getQuantitySold()+1);
                product2.setQuantityCurrent(total);
                product2.setImages(product1.getImages());
                product2.setUnits(product1.getUnits());
                product2.setProductCatogories(product1.getProductCatogories());
                product2 = repoProduct.save(product2);

            }
            response.put("data", dtoOrders);
            response.put("success", true);
            return response;
        }
        catch(Exception e){
            response.put("success", false);
            response.put("mesager", e.getMessage());
            return response;
        }
    }

}
