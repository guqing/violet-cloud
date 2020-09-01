package xyz.guqing.common.support.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import xyz.guqing.violet.common.core.model.support.PageQuery;

/**
 * mybatis plus 分页和排序工具类
 * @author guqing
 * @date 2020-09-01
 */
public class PageUtils {
    private PageUtils(){}

    public static<T> Page<T> convert(PageQuery pageQuery) {
        Page<T> page = new Page<>();

        String sortField = pageQuery.getField();
        if(StringUtils.isNotBlank(sortField)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc("asc".equalsIgnoreCase(pageQuery.getOrder()));
            orderItem.setColumn(pageQuery.getField());
            page.addOrder(orderItem);
        }

        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getPageSize());
        return page;
    }
}
