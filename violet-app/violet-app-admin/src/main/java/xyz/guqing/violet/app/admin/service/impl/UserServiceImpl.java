package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.mapper.UserMapper;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.entity.UserDO;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public PageInfo<UserDTO> listByPage(UserQuery userQuery) {
        QueryRequest queryRequest = userQuery.getQueryRequest();
        Long current = queryRequest.getCurrent();
        Long pageSize = userQuery.getQueryRequest().getPageSize();

        PageInfo<UserDTO> pageInfo = new PageInfo<>();
        pageInfo.setCurrent(current);
        pageInfo.setPageSize(pageSize);

        // 没有数据返回空集合分页对象
        Long userCount = countUserBy(userQuery);
        pageInfo.setTotal(userCount);
        if(userCount == 0) {
            pageInfo.setPages(1L);
            pageInfo.setList(Collections.emptyList());
            return pageInfo;
        }

        // 查询
        userQuery.setQueryRequest(queryRequest);
        List<UserDO> userByPage = this.baseMapper.findUserBy(userQuery);
        List<UserDTO> userDtoList = userByPage.stream()
                .map(user -> (UserDTO)new UserDTO().convertFrom(user))
                .collect(Collectors.toList());
        pageInfo.setList(userDtoList);

        // 设置分页数
        Long pageTotal = VioletUtil.getPageTotal(pageSize, userCount);
        pageInfo.setPages(pageTotal);
        return pageInfo;
    }

    @Override
    public Long countUserBy(UserQuery userQuery) {
        return this.baseMapper.countUserBy(userQuery);
    }


}
