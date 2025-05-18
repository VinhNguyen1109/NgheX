package com.nghex.exe202.service;

import com.nghex.exe202.entity.Deal;

import java.util.List;

public interface DealService {
    Deal createDeal(Deal deal);
//    List<Deal> createDeals(List<Deal> deals);
    List<Deal> getDeals();
    Deal updateDeal(Deal deal,Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;

}
