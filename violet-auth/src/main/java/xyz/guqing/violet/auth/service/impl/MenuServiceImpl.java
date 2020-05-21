package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.entity.Menu;
import xyz.guqing.violet.auth.model.mapper.MenuMapper;
import xyz.guqing.violet.auth.service.MenuService;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
