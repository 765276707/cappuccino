package com.github.xzb617.cappuccino.server.base;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 分页助理
 * @author xzb617
 * @date 2022/1/13 3:27
 * @description:
 */
public class Page {

    /**
     * 将同数据类型的 PageInfo 转换成 PageData
     * @param pi
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> PageData toData(PageInfo<T> pi) {
        return PageData.builder()
                .pageNum(pi.getPageNum())
                .pageSize(pi.getPageSize())
                .total(pi.getTotal())
                .list(pi.getList());
    }

    /**
     * 将不同数据类型的 PageInfo 转换成 PageData
     * @param pi
     * @param targetClass 转换的目标数据类型
     * @param <R>
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R, T> PageData toData(PageInfo<R> pi, Class<T> targetClass) {
        if (targetClass == null) {
            throw new IllegalArgumentException("page targetClass can not be null.");
        }
        // 集合类型转换
        Collection<T> collectList = pi.getList()
                .stream()
                .map(source -> convertClassType(source, targetClass))
                .collect(Collectors.toList());
        return PageData.builder()
                .pageNum(pi.getPageNum())
                .pageSize(pi.getPageSize())
                .total(pi.getTotal())
                .list(collectList);
    }

    /**
     * 对象转换成目标类的对象
     * @param sourceObject 源对象
     * @param targetClass 目标类
     * @return R
     */
    private static <R> R convertClassType(Object sourceObject, Class<R> targetClass) {
        // 源对象 和 目标类为null 则返回null
        if (sourceObject==null || targetClass==null) {
            return null;
        }
        // 进行转换
        try {
            R targetObject = targetClass.newInstance();
            BeanUtils.copyProperties(sourceObject, targetObject);
            return targetObject;
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }
    }
}
