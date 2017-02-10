package com.zup.xyapi.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDAO<T> {
	
	@Autowired
	SessionFactory sessionFactory;
	
	private final Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDAO(){
		this.clazz = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T object) {
		getCurrentSession().saveOrUpdate(object);
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Integer id){
		return (T) getCurrentSession().get(this.clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> list(){
		return getCurrentSession().createCriteria(this.clazz).list();
	}
	
}
