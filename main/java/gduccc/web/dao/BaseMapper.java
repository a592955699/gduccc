package gduccc.web.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import gduccc.web.annotation.MyBatisDao;

@MyBatisDao
public interface BaseMapper<T>{
	T GetById(@Param("pk")Object pk);
	int Insert(T entity);
	int Update(T entity);
	int Delete(T entity);
	ArrayList<T> GetAllList();
}
