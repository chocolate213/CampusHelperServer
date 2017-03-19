package cn.jxzhang.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created on 2016-11-07 00:12
 * <p>Title:       JsonUtils</p>
 * <p>Description: Json Utils</p>
 * <p>Company:     [Company Name]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 *
 * @author <a href=zhangjx_dev@163.com>J.X.Zhang</a>
 * @version 1.0
 */
public class JsonUtils {

    private JsonUtils() { /* cannot be instantiated */ }

    private static class GsonGenerator {
        private static final Gson instance = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private static Gson getInstance() {
        return GsonGenerator.instance;
    }

    /**
     * This method serializes the specified object into its equivalent Json representation.
     *
     * @param object the object for which Json representation is to be created setting for Gson
     * @return Json representation of {@code src}.
     */
    public static String toJson(Object object) {
        return getInstance().toJson(object);
    }

    /**
     * This method deserializes the specified Json into an object of the specified class.
     *
     * @param json the string from which the object is to be deserialized
     * @param type The specific genericized type of src. You can obtain this type by using the
     *             {@link com.google.gson.reflect.TypeToken} class. For example, to get the type for
     *             {@code Collection<Foo>}, you should use:
     *             <pre>
     *                 Type typeOfT = new TypeToken&lt;Collection&lt;Foo&gt;&gt;(){}.getType();
     *             </pre>
     * @param <T>  the type of the desired object
     * @return an object of type T from the string.
     */
    public static <T> T fromJson(String json, Type type) {
        return getInstance().fromJson(json, type);
    }

    /**
     * This method deserializes the specified Json into an object of the specified class.
     *
     * @param json     the string from which the object is to be deserialized
     * @param classOfT the class of T
     * @param <T>      the type of the desired object
     * @return an object of type T from the string.
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getInstance().fromJson(json, classOfT);
    }
}
