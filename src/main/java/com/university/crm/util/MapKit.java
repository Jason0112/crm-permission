package com.university.crm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.university.crm.bean.CourseExam;
import com.university.crm.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * date: 2018/8/20
 * description :
 *
 * @author : zhencai.cheng
 */
public class MapKit {

    /**
     * 创建HashMap实例，创建后需要扩展容量不见意使用此方法（适合于明确大小）
     *
     * @param size 大小
     * @param <K>  key类型
     * @param <V>  value类型
     * @return map实例
     */
    public static <K, V> Map<K, V> newMap(int size) {
        size = (int) (size / 0.75) + 1;
        return new HashMap<>(size);
    }

    public static JSONObject newJSONObject(int size) {
        size = (int) (size / 0.75) + 1;
        return new JSONObject(size);
    }


    public static JSONObject of(Object... os) {
        JSONObject o = newJSONObject(os.length / 2);
        for (int i = 0; i < os.length; ) {
            if (os[i] == null) {
                o.put(null, os[i + 1]);
            } else {
                o.put(os[i].toString(), os[i + 1]);
            }
            i += 2;
        }
        return o;
    }

    /**
     * 处理分页
     *
     * @param queryMap 请求参数
     */
    public static void processPage(JSONObject queryMap) {
        if (StringUtils.isBlank(queryMap.getString(Constant.PAGE_SIZE))) {
            queryMap.put(Constant.PAGE_SIZE, 10);
        }
        if (StringUtils.isBlank(queryMap.getString(Constant.START_ROW))) {
            queryMap.put(Constant.START_ROW, 0);
        }
    }

    /**
     * 获取对象某个属性值
     *
     * @param object   对象
     * @param property 对象中的属性
     * @return 返回属性值
     */
    public static String getValue(Object object, String property) {
        Object value = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (propertyName.equals(property)) {
                    value = descriptor.getReadMethod().invoke(object);
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (value == null) {
            return null;
        }
        if (value instanceof Number || value instanceof String) {
            return value.toString();
        } else if (value instanceof Date) {
            return DateKit.format((Date) value);
        } else {
            return JSON.toJSONString(value);
        }
    }


    public static String getOrDefault(Object object, String property, String defaultValue) {
        String value = getValue(object, property);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value;
    }


    public static JSONObject covertMap(Object object, String... properties) {
        if (object == null) {
            return newJSONObject(1);
        }
        try {
            JSONObject map;
            if (properties.length == 0) {
                BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                map = newJSONObject(properties.length);
                for (PropertyDescriptor descriptor : propertyDescriptors) {
                    String propertyName = descriptor.getName();
                    Object value = descriptor.getReadMethod().invoke(object);
                    map.put(propertyName, value);
                }
            } else {
                map = newJSONObject(properties.length);
                for (String property : properties) {
                    BeanWrapper wrapper = new BeanWrapperImpl(object);
                    Object value = wrapper.getPropertyValue(property);
                    map.put(property, value);
                }
            }
            return map;
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return newJSONObject(1);
        }
    }

    /**
     * map 转bean
     *
     * @param objectMap map集合
     * @param clazz     对象class
     * @param <T>       对象类型
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T covertBean(JSONObject objectMap, Class<T> clazz) {
        BeanWrapper beanInfo = new BeanWrapperImpl(clazz);
        T object = (T) beanInfo.getWrappedInstance();
        beanInfo.registerCustomEditor(Date.class, new DateEditor());
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            if (beanInfo.isWritableProperty(entry.getKey())) {
                beanInfo.setPropertyValue(entry.getKey(), entry.getValue());
            }
        }
        return object;
    }

    /**
     * 获取对象属性值
     *
     * @param object     对象
     * @param replaceMap 对象中的属性和外部定义的字段名称  例：key:start_time value: startTime
     * @return 返回属性值  例：key:startTime value :2018-11-01 00:00:00
     */
    public static JSONObject getValue(Object object, JSONObject replaceMap) {
        JSONObject valueMap = newJSONObject(replaceMap.size());
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor descriptor : propertyDescriptors) {
                String propertyName = descriptor.getName();
                if (replaceMap.containsKey(propertyName)) {
                    Object value = descriptor.getReadMethod().invoke(object);
                    String templateKey = replaceMap.getString(propertyName);
                    valueMap.put(templateKey, value);
                }
            }
            return valueMap;
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return valueMap;
    }


    /**
     * 移除map中空key或者value空值
     *
     * @param map 检测集合
     */
    public static void removeNullEntry(Map<String, Object> map) {
        removeNullKey(map);
        removeNullValue(map);
    }

    /**
     * 移除map的空key
     *
     * @param map 检测集合
     */
    public static void removeNullKey(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> set = map.entrySet();
        set.removeIf(entry -> StringUtils.isBlank(entry.getKey()));
    }

    /**
     * 移除map中的value空值
     *
     * @param map 集合
     */
    public static void removeNullValue(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = set.iterator();
        while (iterator.hasNext()) {
            remove(iterator.next(), iterator);
        }
    }

    /**
     * Iterator 是工作在一个独立的线程中，并且拥有一个 mutex 锁。
     * Iterator 被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，
     * 所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出 java.util.ConcurrentModificationException 异常。
     * 所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。
     * 但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。
     *
     * @param entry    entry对象
     * @param iterator 迭代器
     */
    private static void remove(Map.Entry<String, Object> entry, Iterator iterator) {
        Object obj = entry.getValue();
        if (obj instanceof String) {
            String str = (String) obj;
            if (StringUtils.isBlank(str)) {
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        JSONObject map = new JSONObject();
        map.put("userID", 1);
        map.put("candidateNO", UUID.randomUUID().toString());
        map.put("examBeginTime", DateKit.format(LocalDate.now()));
        map.put("examBeginTime111", DateKit.format(LocalDate.now()));
        CourseExam exam = covertBean(map, CourseExam.class);
        System.out.println(exam.getExamNO());
        System.out.println(exam.getUserID());
        System.out.println(exam.getExamBeginTime());

        JSONObject result = covertMap(exam);

        System.out.println(result.toJSONString());
    }
}
