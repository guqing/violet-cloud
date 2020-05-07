package xyz.guqing.violet.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.violet.common.core.entity.system.Menu;

import java.util.List;

/**
 * @author guqing
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取用户权限集
     *
     * @param username 用户名
     * @return 权限集合
     */
    List<Menu> findUserPermissions(String username);
}