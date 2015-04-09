package com.rrs.seller.service;

/**
 * Created by zhaop01 on 2015/4/7.
 */
public class SerllerServiceImpl implements SellerService {

    @Override
    public void apply() {

    }

    @Override
    public boolean auth(String username, String password) {
        if("admin".equals(username)&&"admin".equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public void logout() {

    }

}
