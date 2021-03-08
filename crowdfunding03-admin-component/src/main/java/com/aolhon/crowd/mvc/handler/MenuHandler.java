package com.aolhon.crowd.mvc.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aolhon.crowd.entity.Menu;
import com.aolhon.crowd.service.api.MenuService;
import com.aolhon.crowd.util.ResultEntity;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月4日 上午10:26:44
 * 菜单（树形结构） 的控制器 
 */
@Controller
public class MenuHandler {

	@Autowired
	private MenuService menuService;
	
	@ResponseBody
	@RequestMapping("/menu/remove.json")
	public ResultEntity<Menu> removeMenu(@RequestParam("id") Integer id){
		// 执行删除
		menuService.removeMenu(id);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 更改菜单
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/menu/update.json")
	public ResultEntity<Menu> updateMenu(Menu menu){
		// 执行更改
		menuService.updateMenu(menu);
		
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 添加一个菜单
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/menu/add.json")
	public ResultEntity<Menu> addMenu(Menu menu){
		// 执行添加
		menuService.addMenu(menu);
		
		// 没有带数据返回，前端页面的重载由前端页面重新发一个请求
		return ResultEntity.successWithoutData();
	}
	
	/**
	 * 得到整个数据结构数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/menu/get/whole/tree.json")
	public ResultEntity<Menu> getWholeTree(){
		// 获得所有没有分类的menu对象集合
		List<Menu> menuList = menuService.getAllMenu();
		                                                                                     
		// 创建一个menu对象，作为最后的返回数据
		Menu root = null;
		
		// 创建map
		Map<Integer, Menu> menuMap = new HashMap<Integer, Menu>();
		
		// 把查询到的原始menu对象集合数据封装到一个menu对象中
		for (Menu menu : menuList) {
			
			// 取出每个menu的id
			Integer id = menu.getId();
			
			menuMap.put(id, menu);
		}
		
		for (Menu menu : menuList) {
			// 取出pid
			Integer pid = menu.getPid();
			
			// 得到根节点
			if(pid == null){
				root = menu;
				// 结束本次循环
				continue;
			}
			
			// 得到父节点
			Menu fatherMenu = menuMap.get(pid);
			
			fatherMenu.getChildren().add(menu);
		}
		
		return ResultEntity.successWithData(root);
	}
}
