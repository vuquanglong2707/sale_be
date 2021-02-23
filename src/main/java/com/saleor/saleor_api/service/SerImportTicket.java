package com.saleor.saleor_api.service;

import com.saleor.saleor_api.dto.DTOImportTicket;
import com.saleor.saleor_api.dto.DTOImportTicketDetail;
import com.saleor.saleor_api.dto.DtoProduct;
import com.saleor.saleor_api.dto.DtoVariation;
import com.saleor.saleor_api.mapper.MapperImportTicket;
import com.saleor.saleor_api.mapper.MapperImportTicketDetail;
import com.saleor.saleor_api.repo.*;
import com.saleor.saleor_api.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SerImportTicket {

    @Autowired
    RepoImportTicket repoImportTicket;

    @Autowired
    MapperImportTicket mapperImportTicket;

    @Autowired
    MapperImportTicketDetail mapperImportTicketDetail;

    @Autowired
    RepoSupplier repoSupplier;

    @Autowired
    RepoProduct repoProduct;

    @Autowired
    RepoUnit repoUnit;

    @Autowired
    RepoImportTicketDetail repoImportTicketDetail;

    public Optional<ImportTicket> GetByID(Long id)
    {
        return  repoImportTicket.findById(id);
    }

    public List<DTOImportTicketDetail> getAllImportDetailByTiket(ImportTicket importTicket) {
        List<ImportTicketDetail> importTicketDetails = repoImportTicketDetail.findAllByImportTicket(importTicket);
        List<DTOImportTicketDetail> dtoImportTicketDetails = new ArrayList<DTOImportTicketDetail>();
        for (ImportTicketDetail item : importTicketDetails) {
            DTOImportTicketDetail dtoImportTicketDetail = mapperImportTicketDetail.toDto(item);
            dtoImportTicketDetail.setProductName(item.getProduct().getName());
            dtoImportTicketDetail.setProductId(item.getProduct().getId());
            dtoImportTicketDetails.add(dtoImportTicketDetail);
        }
        return dtoImportTicketDetails;
    }
    public List<DTOImportTicket> getTicketPage(Pageable pageable){
        List<ImportTicket> importTicketList = repoImportTicket.findAllBy(pageable);
        List<DTOImportTicket> dtoImportTickets = new ArrayList<DTOImportTicket>();
        for (ImportTicket item : importTicketList) {
            DTOImportTicket dtoImportTicket = mapperImportTicket.toDto(item);
            dtoImportTicket.setSupplierTitle(item.getSupplier().getTitle());
            dtoImportTicket.setSupplierId(item.getSupplier().getId());
            dtoImportTicket.setImportTicketDetails(new ArrayList<DTOImportTicketDetail>(getAllImportDetailByTiket(item)));
            dtoImportTickets.add(dtoImportTicket);
        }
        return dtoImportTickets;
    }
    public List<Product> GetAll()

    {
        return repoProduct.findAll();
    }
    public Page<ImportTicket> getAllImportTicketInMonth(String query, Pageable pageable){

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String[] newData = query.split("/");
            String fromData = newData[0];
            String toData = newData[1];
            String fromDate = fromData.concat(" 00:00:00");
            String toDate = toData.concat(" 23:59:59");

            Date newFromDate = formatter.parse(fromDate);
            Date newToDate = formatter.parse(toDate);
            if (pageable.getPageNumber() == 0) {
                Pageable pagingSort = PageRequest.of(pageable.getPageNumber(), 10);
                Page<ImportTicket> dataImportTicket = repoImportTicket.findAllWithCreationDateTimeBefore(newFromDate, newToDate, pagingSort);
                return dataImportTicket;
            }
            else{
                Page<ImportTicket> dataImportTicket = repoImportTicket.findAllWithCreationDateTimeBefore(newFromDate, newToDate, pageable);
                return dataImportTicket;

            }
        }
        catch ( Exception e){
            return null;
        }
    }
    //Tìm kiếm theo Id và tên,sđt khách hàng
    public  List<ImportTicket> FindSupplierList(String query){
        List<ImportTicket> importTicketsFind = repoImportTicket.findByTitle(query);
        List<Supplier> subInfoFind  = repoSupplier.findByPhoneContainingOrTitleContaining(query,query);

        List<ImportTicket> billSearch = new ArrayList<>();

        if(importTicketsFind.size() > 0){
            for(ImportTicket importTicket : importTicketsFind){
                billSearch.add(importTicket);
            }
        }
        return getBill(billSearch, subInfoFind);
    }
    public  List<ImportTicket> FindSupplierPage(Pageable pageable, String query){
        Page<ImportTicket> billFind = repoImportTicket.findByTitleContaining(pageable, query);
        List<Supplier> subInfoFind = repoSupplier.findByPhoneContainingOrTitleContaining(query, query);
        List<ImportTicket> billSearch = new ArrayList<>();
        if(billFind.getSize() > 0){
            for(ImportTicket bill : billFind){
                billSearch.add(bill);
            }
        }
        return getBill(billSearch, subInfoFind);
    }
    private List<ImportTicket> getBill(List<ImportTicket> billSearch, List<Supplier> subInfoFind) {
        if (subInfoFind.size() > 0) {
            List<ImportTicket> importTicketList = repoImportTicket.findAll();
            for (Supplier supplier : subInfoFind) {
                for (ImportTicket importTicket : importTicketList) {
                    if (supplier.getId().equals(importTicket.getSupplier().getId())) {
                        billSearch.add(importTicket);
                    }
                }
            }

        }
        List<ImportTicket> importTicketList = billSearch.stream()
                .sorted(Comparator.comparing(ImportTicket::getId).reversed())
                .collect(Collectors.toList());
        return importTicketList;
    }
    Map<String, Object> response = new HashMap<>();

    private Product checkProduct(Long id ,String name) {
        List<Product> products = repoProduct.findByIdAndName(id, name);
        if (products.size() > 0) {
            return products.get(0);
        }
        return null;
    }
    public Object  InsSent(DTOImportTicket repose){
        try{
            ImportTicket orderTicket = new ImportTicket();

            List<Product> listProduct = repoProduct.findBy();

            orderTicket.setCreateBy(repose.getCreateBy());

            orderTicket.setCreatedDate(new Date());

            orderTicket.setOrderCode(repose.getOrderCode());

            orderTicket.setModifiedBy(repose.getModifiedBy());

            orderTicket.setNote(repose.getNote());

            orderTicket.setModifiedBy(repose.getModifiedBy());

            orderTicket.setPhone(repose.getPhone());

            orderTicket.setShipCode(repose.getShipCode());

            orderTicket.setTitle(repose.getTitle());

            orderTicket.setTotal(repose.getTotal());

            Optional<Supplier> opSupplier = repoSupplier.findById(repose.getSupplierId());

            if(!opSupplier.isPresent()){
                response.put("success", false);
                return response;
            }
            orderTicket.setSupplier(opSupplier.get());

            List<DTOImportTicketDetail> orderTicketDetail = repose.getImportTicketDetails();

            ImportTicket newOrder = new ImportTicket();

            newOrder = repoImportTicket.save(orderTicket);

            repose.setId(newOrder.getId());

            repose.setSupplierTitle(opSupplier.get().getTitle());

            for(DTOImportTicketDetail item : orderTicketDetail){
                String title = item.getProductName();
                Long id=item.getProductId();
                Product product1 = checkProduct(id,title);
                if(product1 == null){
                    response.put("message", "Sản phẩm ko tìm thấy ");
                    response.put("success", false);
                    return response;
                }
                ImportTicketDetail newOrderDetail = new ImportTicketDetail();
                newOrderDetail.setImportPrice(item.getImportPrice());
                newOrderDetail.setTotalPrice(item.getTotalPrice());
                newOrderDetail.setTotalQuantity(item.getTotalQuantity());
                newOrderDetail.setProduct(product1);
                newOrderDetail.setImportTicket(newOrder);
                newOrderDetail = repoImportTicketDetail.save(newOrderDetail);
                item.setId(newOrderDetail.getId());
                item.setProductName(newOrderDetail.getProduct().getName());
                Double total=product1.getQuantityCurrent()+item.getTotalQuantity();
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
                product2.setQuantitySold(product1.getQuantitySold());
                product2.setQuantityCurrent(total);
                product2.setImages(product1.getImages());
                product2.setUnits(product1.getUnits());
                product2.setProductCatogories(product1.getProductCatogories());
                product2 = repoProduct.save(product2);
            }
            response.put("data", repose);
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
