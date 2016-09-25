package com.ldb.util.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BeanHandler implements ResultSetHandler{

	private Class<?> clazz;
    public BeanHandler(Class<?> clazz){
        this.clazz = clazz;
    }
    public Object handler(ResultSet rs) {
        try{
            if(!rs.next()){
                return null;
            }
            Object bean = clazz.newInstance();
            //得到结果集元数据
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();//得到结果集中有几列数据
            for(int i=0;i<columnCount;i++){
                String coulmnName = metadata.getColumnName(i+1);//得到每列的列名
                Object coulmnData = rs.getObject(i+1);
                Field f = clazz.getDeclaredField(coulmnName);//反射出类上列名对应的属性
                f.setAccessible(true);
                f.set(bean, coulmnData);
            }
            return bean;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
