package com.nghex.exe202.service;

import com.nghex.exe202.entity.HomeCategory;
import com.nghex.exe202.model.Home;

import java.util.List;

public interface HomeService {

    Home creatHomePageData(List<HomeCategory> categories);

}
