package xyz.guqing.common.support.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.lang.NonNull;
import xyz.guqing.violet.common.core.model.support.PageInfo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author guqing
 * @date 2020-07-17
 */
public class PageConvert {
    public static<T, DTO> PageInfo<DTO> convertFrom(@NonNull IPage<T> page, Function<T, DTO> function){
        List<DTO> collect = page.getRecords()
                .stream()
                .map(function)
                .collect(Collectors.toList());

        PageInfo<DTO> pageInfo = new PageInfo<>();
        pageInfo.setList(collect);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPages(page.getPages());
        pageInfo.setCurrent(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        return pageInfo;
    }
}
