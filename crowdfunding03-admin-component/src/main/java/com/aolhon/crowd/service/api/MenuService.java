package com.aolhon.crowd.service.api;

import java.util.List;

import com.aolhon.crowd.entity.Menu;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月4日 上午10:24:54
 * 
 */
public interface MenuService {

	/**
	 * 得到所有菜单
	 * @return
	 */
	public List<Menu> getAllMenu();

	/**
	 * 添加一个菜单Menu
	 * @param menu
	 * @return
	 */
	public void addMenu(Menu menu);

	/**
	 * 修改一个菜单
	 * @param menu
	 */
	public void updateMenu(Menu menu);

	/**
	 * 删除一个菜单
	 * @param id
	 */
	public void removeMenu(Integer id);
}
