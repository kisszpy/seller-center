package com.rrs.seller.model;

import com.rrs.annotation.ObjectKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by zhaop01 on 2015/4/7.
 */
@ObjectKey(value = "seller")
@ToString
public class Seller {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;

}
