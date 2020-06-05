package xyz.guqing.violet.app.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.mapper.RoleMapper;
import xyz.guqing.violet.app.admin.mapper.UserRoleMapper;
import xyz.guqing.violet.app.admin.model.dto.RoleDTO;
import xyz.guqing.violet.app.admin.model.entity.RoleDO;
import xyz.guqing.violet.app.admin.model.param.RoleQuery;
import xyz.guqing.violet.app.admin.service.RoleService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.common.core.model.entity.system.UserRole;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guqing
 * @date 2020-06-03
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final UserRoleMapper userRoleMapper;

    @Override
    public void saveUserRoles(Long userId, List<Long> roleIds) {
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
    }

    @Override
    public PageInfo<RoleDTO> listBy(RoleQuery roleQuery) {
        QueryRequest queryRequest = roleQuery.getQueryRequest();
        PageInfo<RoleDTO> pageInfo = new PageInfo<>();
        pageInfo.setPageSize(queryRequest.getPageSize());
        pageInfo.setCurrent(queryRequest.getCurrent());

        Long countRole = this.baseMapper.countRoleBy(roleQuery);
        if(countRole == 0) {
            pageInfo.setList(Collections.emptyList());
            pageInfo.setTotal(countRole);
            pageInfo.setPages(1L);
            return pageInfo;
        }

        List<RoleDO> userRoleMenu = this.baseMapper.findUserRoleMenu(roleQuery);
        List<RoleDTO> roleDtoList = userRoleMenu.stream().map(roleDO -> {
            RoleDTO roleDTO = new RoleDTO();
            BeanUtils.copyProperties(roleDO, roleDTO);
            roleDTO.setMenuIds(roleDO.getMenuIds());
            return roleDTO;
        }).collect(Collectors.toList());

        pageInfo.setList(roleDtoList);
        pageInfo.setTotal(countRole);
        pageInfo.setPages(VioletUtil.getPageTotal(queryRequest.getPageSize(), countRole));
        return pageInfo;
    }
}
