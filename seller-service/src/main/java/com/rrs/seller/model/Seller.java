package com.rrs.seller.model;

import com.google.common.collect.Lists;
import com.rrs.annotation.ObjectKey;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by zhaop01 on 2015/4/7.
 */
@ObjectKey(value = "seller")
@ToString
@XmlRootElement(name="seller")
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
    @Getter
    @Setter
    private List<Seller> lists = Lists.newArrayListWithExpectedSize(10);

}
