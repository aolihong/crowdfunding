package com.aolhon.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aolhon.crowd.entity.Menu;
import com.aolhon.crowd.entity.MenuExample;
import com.aolhon.crowd.mapper.MenuMapper;
import com.aolhon.crowd.service.api.MenuService;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021年2月4日 上午10:25:34
 * 
 */
@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getAllMenu() {
		return menuMapper.selectByExample(new MenuExample());
	}

	@Override
	public void addMenu(Menu menu) {
		menuMapper.insert(menu);
	}

	@Override
	public void updateMenu(Menu menu) {

		// 有选择性的修改
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public void removeMenu(Integer id) {
		menuMapper.deleteByPrimaryKey(id);
	}

}
