package com.rrs.seller.service;

/**
 * Created by zhaop01 on 2015/4/7.
 */
public interface SellerService {

    /**
     * 商家入驻
     */
    public void apply();

    /**
     * 用户登录
     */
    public boolean auth(String username,String password);

    /**
     * 用户登出
     */
    public void logout();

}
