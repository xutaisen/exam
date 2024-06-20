package com.itclass.exam.manager.service.impl;

import com.itclass.exam.common.exception.MyselfException;
import com.itclass.exam.manager.mapper.SysMenuMapper;
import com.itclass.exam.manager.mapper.SysRoleMenuMapper;
import com.itclass.exam.manager.service.SysMenuService;
import com.itclass.exam.manager.utils.MenuHelper;
import com.itclass.exam.model.entity.system.SysMenu;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import com.itclass.exam.model.vo.system.SysMenuVo;
import com.itclass.exam.util.AuthContextUtil;
import org.ehcache.core.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-26 09:27
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //菜单列表
    @Override
    public List<SysMenu> findNodes() {

        //1 查询所有菜单，返回list集合
        List<SysMenu> sysMenuList = sysMenuMapper.finAll();
        if (CollectionUtils.isEmpty(sysMenuList)) {//集合为空返回null
            return null;
        }

        //2 调用工具类的方法，把返回集合封装成要求的数据格式
        List<SysMenu> buildTree = MenuHelper.buildTree(sysMenuList);


        return buildTree;
    }

    //添加菜单
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);

        //bug 如果新添加子菜单，把父菜单isHalf半开状态 1
        updateSysRoleMenuIsHalf(sysMenu);

    }


    //修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update();
    }


    //删除
    @Override
    public void removeById(Long id) {
        //如果要删除的菜单有子菜单则不能删除
        int count = sysMenuMapper.countByParentId(id);

        if (count > 0) {//大于0表示有子节点，不能删除
            throw new MyselfException(ResultCodeEnum.NODE_ERROR);//217
        }

        sysMenuMapper.remove(id);
    }


    //查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findUserMenuList() {
        // 先查询到用户id (使用AuthContextUtil工具类 ，此工具类封装了ThreadLocal ThreadLocal是jdk所提供的一个线程工具类，叫做线程变量，
        SysUser sysUser = AuthContextUtil.get();
        Long id = sysUser.getId();

        //根据userId查询可以操作的菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(id);

        //封装要求数据格式，返回
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTreeList);// 将List<SysMenu>对象转换成List<SysMenuVo>对象
    }


    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }

    //bug 如果新添加子菜单，把父菜单isHalf半开状态 1
    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());

        if (parentMenu != null) {

            // 将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());

            // 递归调用
            updateSysRoleMenuIsHalf(parentMenu);

        }
    }
}