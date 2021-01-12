package xyz.guqing.common.support.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * mybatis plus 分页和排序工具类
 *
 * @author guqing
 * @date 2020-09-01
 */
public class PageUtils {
    private PageUtils() {
    }

    public static <T> Page<T> convert(PageQuery pageQuery) {
        Page<T> page = new Page<>();

        String sortField = pageQuery.getField();
        if (StringUtils.isNotBlank(sortField)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(VioletConstant.ORDER_ASC.equalsIgnoreCase(pageQuery.getOrder()));
            // 驼峰转下划线
            String underscoreField = VioletUtil.camelToUnderscore(sortField);
            orderItem.setColumn(underscoreField);
            page.addOrder(orderItem);
        }

        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getPageSize());
        return page;
    }

    public static <T, DTO> PageInfo<DTO> convertToPageInfo(@NonNull IPage<T> page, Function<T, DTO> function) {
        List<DTO> records = page.getRecords()
                .stream()
                .map(function)
                .collect(Collectors.toList());

        return convertTo(page, records);
    }

    public static <T, DTO> PageInfo<DTO> convertTo(IPage<T> page, List<DTO> records) {
        PageInfo<DTO> pageInfo = new PageInfo<>();
        pageInfo.setList(records);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPages(page.getPages());
        pageInfo.setCurrent(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        return pageInfo;
    }
}
