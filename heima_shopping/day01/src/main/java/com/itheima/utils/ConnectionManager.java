package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.itheima.utils.C3P0Utils;

/*
 * con���Ӷ���Ĺ�����:
 *   �ṩ����:
 *     ��ȡ����
 *     ��������
 *     �ύ����
 *     �ع�����
 *     �ͷ���Դ
 */
public class ConnectionManager{
	//��Աλ��,����ThreadLocal����
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	private static Connection con;
	
	//��ȡ���ӵķ���
	public static Connection getConnection() throws SQLException{
		//ȡ������,��local�����ȡ
		con = local.get();
		if(con==null){
			//��һ������,��local������,�ȴ洢����
			local.set(C3P0Utils.getConnection());
			con = local.get();
		}
		return con;
	}
	
	//��������ķ���
	public static void begin() throws SQLException{
		getConnection();
		con.setAutoCommit(false);
	}
	//�ύ����ķ���
	public static void commit() throws SQLException{
		con.commit();
	}
	//�ع�����
	public static void rollback() throws SQLException{
		con.rollback();
	}
	//�ͷ���Դ
	public static void close(){
		try {
			con.close();
			local.remove();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
}
