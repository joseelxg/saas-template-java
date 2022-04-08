package com.hailas.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author wulin
 * @version 1.0.0
 * @created 2019/8/12.
 */
public class XmlUtils {

    public static <T> String toString(T object){
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        xStream.processAnnotations(object.getClass());
        return xStream.toXML(object);
    }

    public static <T> T fromXml(String xml, Class<T> valueType){
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(valueType);
        return (T) xStream.fromXML(xml);
    }
}
