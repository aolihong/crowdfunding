package com.aolhon.crowd.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private Integer id;

    private Integer pid;

    private String name;

    private String url;

    private String icon;
    
    // 因为要求为树形结构，所以每个实体类中必有自身类集合的属性。这样才能装下分支节点、叶子节点
    private List<Menu> children = new ArrayList<>();
    
    // open属性是设置树形结构节点的默认开放状态，默认是设置为打开
    private boolean open = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}