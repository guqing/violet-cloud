package xyz.guqing.violet.app.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.violet.common.core.model.entity.system.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<Menu> findUserMenus(String username);
}
