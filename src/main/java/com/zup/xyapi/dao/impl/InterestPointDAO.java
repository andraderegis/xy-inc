package com.zup.xyapi.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zup.xyapi.dao.BaseDAO;
import com.zup.xyapi.model.InterestPoint;

@Repository("InterestPointDAO")
@Transactional
public class InterestPointDAO extends BaseDAO<InterestPoint> {

	@SuppressWarnings("unchecked")
	public List<InterestPoint> listByProximity(final int lat, final int lng, final int distance){
		
		SQLQuery query = getCurrentSession().createSQLQuery("select id, name, coordinateX, coordinateY, (6371 * "
				+ "acos("
				+ "cos(radians(:pLat)) * "
				+ "cos(radians(coordinateX)) * "
				+ "cos(radians(:pLng) - radians(coordinateY)) + "
				+ "sin(radians(:pLat)) * "
				+ "sin(radians(coordinateX))"
				+ ") / 100 ) "
				+ "as distance "
				+ "from interestpoint "
				+ "having distance <= :pDistance");
		
		query.addEntity(InterestPoint.class);
		
		query.setParameter("pLat", lat);
		query.setParameter("pLng", lng);
		query.setParameter("pDistance", distance);
		
		return query.list();
	}

}

