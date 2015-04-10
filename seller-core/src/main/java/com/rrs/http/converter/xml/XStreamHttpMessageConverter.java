package com.rrs.http.converter.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;

/**
 * Created by zhaop01 on 2015/4/10.
 */
public class XStreamHttpMessageConverter extends MarshallingHttpMessageConverter {


    private final XStream xStream;

    public XStreamHttpMessageConverter(){
        this.xStream = new XStream(new StaxDriver());
        this.xStream.autodetectAnnotations(true);
    }

    @Override
    protected Object readFromSource(Class<?> clazz, HttpHeaders headers, Source source) throws IOException {
        System.out.println("read");

        return null;
    }

    @Override
    protected void writeToResult(Object o, HttpHeaders headers, Result result) throws IOException {
        System.out.println("编写XML");
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return false;
    }
}
