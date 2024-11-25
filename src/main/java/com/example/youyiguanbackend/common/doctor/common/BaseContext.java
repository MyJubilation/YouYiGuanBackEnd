package com.example.youyiguanbackend.common.doctor.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author beetles
 * @date 2024/11/23
 * @Description 基于ThreadLocal封装工具类，用于保存和获取当前登录用户的信息
 */
public class BaseContext {
    // 定义一个ThreadLocal，它的泛型参数是Map，可以存储任意类型的值
    private static final ThreadLocal<Map<String, Object>> threadLocalData = ThreadLocal.withInitial(HashMap::new);

    /**
     * 设置当前登录用户的信息
     * @param key 键
     * @param value 值
     */
    public static void setCurrentInfo(String key, Object value) {
        threadLocalData.get().put(key, value);
    }

    /**
     * 获取当前登录用户的信息
     * @param key 键
     * @return 对应的值
     */
    public static Object getCurrentInfo(String key) {
        return threadLocalData.get().get(key);
    }

    /**
     * 清理ThreadLocal中的数据，避免内存泄漏
     */
    public static void clearCurrentInfo() {
        threadLocalData.remove();
    }
}
