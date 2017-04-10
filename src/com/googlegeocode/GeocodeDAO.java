package com.googlegeocode;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import com.utility.HibernateSessionFactory;

public class GeocodeDAO {
	SessionFactory seesionFactory = HibernateSessionFactory.getSessionFactory();
	
	public void insertOrUpdate(GeocodeResponseVO responseVO)
	{
		Session session = seesionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(responseVO);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<GeocodeResponseVO> getList(String jobId){
		Session session = seesionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(GeocodeResponseVO.class);
		criteria.add(Restrictions.eq("job_id", jobId));
		List<GeocodeResponseVO> list = criteria.list();
		session.getTransaction().commit();
		return list;
	}
	
}
