package xyz.guqing.common.support.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.violet.common.core.utils.VioletUtil;

/**
 * 处理排序工具类
 *
 * @author guqing
 */
public class SortUtil {
    /**
     * 处理排序（分页情况下） for mybatis-plus
     *
     * @param pageQuery         分页查询参数
     * @param page              Page
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(PageQuery pageQuery, Page<?> page, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getPageSize());
        String sortField = pageQuery.getField();
        if (camelToUnderscore) {
            sortField = VioletUtil.camelToUnderscore(sortField);
            defaultSort = VioletUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(pageQuery.getField())
                && StringUtils.isNotBlank(pageQuery.getOrder())
                && !StringUtils.equalsIgnoreCase(pageQuery.getField(), "null")
                && !StringUtils.equalsIgnoreCase(pageQuery.getOrder(), "null")) {
            if (StringUtils.equals(pageQuery.getOrder(), VioletConstant.ORDER_DESC)) {
                page.addOrder(OrderItem.desc(sortField));
            } else {
                page.addOrder(OrderItem.asc(sortField));
            }
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                if (StringUtils.equals(defaultOrder, VioletConstant.ORDER_DESC)) {
                    page.addOrder(OrderItem.desc(defaultSort));
                } else {
                    page.addOrder(OrderItem.asc(defaultSort));
                }
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param pageQuery 分页查询参数
     * @param page    Page
     */
    public static void handlePageSort(PageQuery pageQuery, Page<?> page) {
        handlePageSort(pageQuery, page, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param pageQuery         分页查询参数
     * @param page              Page
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(PageQuery pageQuery, Page<?> page, boolean camelToUnderscore) {
        handlePageSort(pageQuery, page, null, null, camelToUnderscore);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param pageQuery         分页查询参数
     * @param wrapper           wrapper
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(PageQuery pageQuery, QueryWrapper<?> wrapper, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        String sortField = pageQuery.getField();
        if (camelToUnderscore) {
            sortField = VioletUtil.camelToUnderscore(sortField);
            defaultSort = VioletUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(pageQuery.getField())
                && StringUtils.isNotBlank(pageQuery.getOrder())
                && !StringUtils.equalsIgnoreCase(pageQuery.getField(), "null")
                && !StringUtils.equalsIgnoreCase(pageQuery.getOrder(), "null")) {
            if (StringUtils.equals(pageQuery.getOrder(), VioletConstant.ORDER_DESC)) {
                wrapper.orderByDesc(sortField);
            } else {
                wrapper.orderByAsc(sortField);
            }
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                if (StringUtils.equals(defaultOrder, VioletConstant.ORDER_DESC)) {
                    wrapper.orderByDesc(defaultSort);
                } else {
                    wrapper.orderByAsc(defaultSort);
                }
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param pageQuery 分页查询参数
     * @param wrapper wrapper
     */
    public static void handleWrapperSort(PageQuery pageQuery, QueryWrapper<?> wrapper) {
        handleWrapperSort(pageQuery, wrapper, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param pageQuery         分页查询参数
     * @param wrapper           wrapper
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(PageQuery pageQuery, QueryWrapper<?> wrapper, boolean camelToUnderscore) {
        handleWrapperSort(pageQuery, wrapper, null, null, camelToUnderscore);
    }
}
