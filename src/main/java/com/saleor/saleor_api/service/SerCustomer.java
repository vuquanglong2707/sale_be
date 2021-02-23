package com.saleor.saleor_api.service;

import com.saleor.saleor_api.repo.RepoCustomer;
import com.saleor.saleor_api.table.Customer;
import com.saleor.saleor_api.table.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerCustomer {
    @Autowired
    RepoCustomer repoCustomer;

    public List<Customer> GetAll()
    {
        return  repoCustomer.findAll();
    }

    public Optional<Customer> GetByID(Long id)
    {
        return  repoCustomer.findById(id);
    }

//    public Customer InsertData(Customer orders) { return repoCustomer.save(orders); }
    Map<String,Object> reponse = new HashMap<>();
    public  Object InsertData(Customer orders){
        try {
            Customer data = repoCustomer.save(orders);
            reponse.put("success", true);
            reponse.put("mesager", "ok");
            reponse.put("data", data);
            return reponse;
        }
        catch (Exception e){
            reponse.put("success", false);
            reponse.put("mesager",e.getMessage());
            return reponse;
        }

    }
    public Customer UpdateData(Customer orders) { return repoCustomer.save(orders); }

    public void Delete(Long id) { repoCustomer.deleteById(id); }


    public List<Customer> getCustomerPage(Pageable pageable){
        List<Customer> customerList = repoCustomer.findAllBy(pageable);
        return customerList;
    }
    public  List<Customer> FindCustomerList(String query){
        List<Customer> customersFind = repoCustomer.findByNameContainingOrPhoneContaining(query,query);
        List<Customer> customersSearch = new ArrayList<>();
        if(customersFind.size() > 0){
            for(Customer customer : customersFind){
                customersSearch.add(customer);
            }
        }
        return getCustomer(customersSearch);
    }

    public  List<Customer> FindCustomerPage(Pageable pageable, String query){
        Page<Customer> customersFind = repoCustomer.findByNameContainingOrPhoneContaining(pageable, query,query);
        List<Customer>customersSearch = new ArrayList<>();
        if(customersFind.getSize() > 0){
            for(Customer customer : customersFind){
                customersSearch.add(customer);
            }
        }
        return getCustomer(customersSearch);
    }

    private List<Customer> getCustomer(List<Customer>customersSearch){
        List<Customer> customersList = customersSearch.stream()
                .sorted(Comparator.comparing(Customer::getId).reversed())
                .collect(Collectors.toList());
        return customersList;

    }

}