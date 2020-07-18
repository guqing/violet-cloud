package xyz.guqing.violet.common.core.model.support;

import lombok.Data;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 分页结果对象
 * @author guqing
 * @date 2019-12-23 23:11
 */
@Data
public class PageInfo<DTO> {
    private List<DTO> list;
    private Long total;
    private Long pages;
    private Long current;
    private Long pageSize;

    public PageInfo() {}

    public PageInfo(List<DTO> list, Long total, Long pages, Long current, Long pageSize) {
        this.list = list;
        this.total = total;
        this.pages = pages;
        this.current = current;
        this.pageSize = pageSize;
    }

    public static<T, DTO> PageInfo<DTO> convertFrom(List<T> list, Function<T, DTO> function){
        Assert.notNull(list, "The parameter of PageInfo can not be null.");
        PageInfo<DTO> pageInfo = getDtoPageInfo(function, list);
        int size = list.size();
        Long total = (long) size;
        pageInfo.setTotal(total);
        pageInfo.setPages(1L);
        pageInfo.setCurrent(1L);
        pageInfo.setPageSize(1L);
        return pageInfo;
    }

    private static <T, DTO> PageInfo<DTO> getDtoPageInfo(Function<T, DTO> function, List<T> content) {
        List<DTO> dtoList = new ArrayList<>();
        content.forEach(t -> {
            DTO dto = function.apply(t);
            dtoList.add(dto);
        });
        PageInfo<DTO> pageInfo = new PageInfo<>();
        pageInfo.setList(dtoList);
        return pageInfo;
    }
}
