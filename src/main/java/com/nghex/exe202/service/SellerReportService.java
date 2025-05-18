package com.nghex.exe202.service;

import com.nghex.exe202.entity.Seller;
import com.nghex.exe202.entity.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport( SellerReport sellerReport);

}
