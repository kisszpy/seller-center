package com.rrs.seller;

import com.rrs.seller.model.Seller;
import com.rrs.serializer.JsonSerializer;
import com.rrs.session.CustomerSession;

/**
 * Created by zhaop01 on 2015/4/7.
 */
public class Main {
    public static void main(String[] args) {
        /*ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/metro-seller-context.xml");
        SellerDao dao = app.getBean(SellerDao.class);*/
       /* for(int i = 0;i<1000;i++) {
            Seller s = new Seller();
            s.setId(i);
            s.setEmail("中国");
            dao.leftPush("seller:list",s);
        }*/
        /*Seller seller = dao.leftPop("seller:list");
        System.out.println(seller);
        long len = dao.getListSize("seller:list");
        System.out.println(len);

        dao.set("teacher","wang",120);*/

        Seller seller = new Seller();
        seller.setId(10);
        seller.setName("ewwfew");
        seller.setEmail("fdsfds@fdsfd");

        JsonSerializer jsonSerializer = new JsonSerializer();
        String value = jsonSerializer.serialize(seller);


        Seller s2 = jsonSerializer.deserialize(value,Seller.class);
        System.out.println(s2);

        String valuex = "{\"id\":\"f528764df528764d84b97dee70ab86da\",\"creationTime\":1428547513963,\"lastAccessedTime\":1428547513963,\"maxInactiveInterval\":0,\"sessionContextMap\":{},\"dirty\":false}";
        CustomerSession session = jsonSerializer.deserialize(valuex, CustomerSession.class);
        System.out.println(session.getId());



    }
}
