package com.aolhon.crowd.service.api;

import java.util.List;

import com.aolhon.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

/**
 * @author   aolihong
 * @email    aolhon@163.com
 * @time     2021��1��26�� ��???10:12:51
 * 
 */
public interface AdminService {
	
	/**
	 * ����һ������Ա
	 * @param admin
	 * @return
	 */
	public void savaAdmin(Admin admin);

	/**
	 * ������й���Ա��Ϣ
	 * @return
	 */
	public List<Admin> getAll();

	/**
	 * ��ѯĳ��admin��Ϣ
	 * @param loginAcct
	 * @param userPswd
	 * @return
	 */
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd);
	
	/**
	 * �����ݿ�admin���ݽ��з�ҳ
	 * @param keyword �ؼ���
	 * @param pageNum ҳ��
	 * @param pageSize ÿҳ����
	 * @return
	 */
	public PageInfo<Admin> getAdminPage(String keyword, Integer pageNum, Integer pageSize);

	/**
	 * ͨ������ԱIDɾ��ָ������Ա
	 * @param adminId
	 */
	public void removeAdmin(Integer adminId);

	/**
	 * ͨ������id��ѯ��Ӧ�Ĺ���Ա
	 * @param adminId
	 * @return
	 */
	public Admin getAdminByPrimaryKey(Integer adminId);

	/**
	 * ����admin��������
	 */
	public void updateAdmin(Admin admin);

	/**
	 * �������Ա��ɫ������Ϣ
	 * @param adminId
	 * @param roleIdList
	 */
	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

	/**
	 * ͨ����½�˺Ż��admin����
	 * @param username
	 */
	public Admin getAdminByLoginAcct(String username);
	
}
