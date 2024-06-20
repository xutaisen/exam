package com.itclass.exam.manager.utils;

import com.itclass.exam.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**封装树形菜单数据工具类
 * @author: 徐泰森
 * @create: 2024-03-26 09:47
 **/

public class MenuHelper {


    //递归实现封装过程
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){

        //创建list集合，用于封装最终数据
       List<SysMenu> trees = new ArrayList<>();

       //遍历所有菜单集合
        for (SysMenu sysMenu : sysMenuList){
            //找到递归的入口 条件parent_id=0
            //写方法实现找下层过程 方法参数：1.当前第一层菜单 2.所有菜单集合
            if (sysMenu.getParentId().longValue() == 0){
               trees.add(finChildren(sysMenu , sysMenuList));
            }
        }

        return trees;
    }

    //递归查找下层菜单
    public static SysMenu finChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
       sysMenu.setChildren(new ArrayList<>());
       //递归
        //sysMenu的id 和sysMenuList的parent_id相同
        for (SysMenu sm : sysMenuList){
            //判断id和parent_id是否相同
            if(sysMenu.getId().longValue() == sm.getParentId().longValue()){
                //sm就是下层数据，进行封装
                sysMenu.getChildren().add(finChildren(sm, sysMenuList));
            }
        }
        return sysMenu;
    }
}