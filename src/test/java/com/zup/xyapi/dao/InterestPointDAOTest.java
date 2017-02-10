package com.zup.xyapi.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zup.xyapi.conf.DevelopmentHibernateConf;
import com.zup.xyapi.conf.ProductionHibernateConf;
import com.zup.xyapi.conf.TestHibernateConf;
import com.zup.xyapi.dao.impl.InterestPointDAO;
import com.zup.xyapi.model.InterestPoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ProductionHibernateConf.class, DevelopmentHibernateConf.class,
		TestHibernateConf.class })
@ActiveProfiles("test")
@Transactional
public class InterestPointDAOTest {

	@Autowired
	private InterestPointDAO dao;

	private InterestPoint interestPoint;

	@Test
	public void shouldBeListSuccess() {

		try {
			assertNotNull(dao.list());
		} catch (Exception e) {
			testException(e);
		}
	}

	@Test
	public void shouldBeListByProximitySuccess() {
		
		try {
			List<InterestPoint> result = dao.listByProximity(20, 10, 10);
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			testException(e);
		}
	}

	@Test
	@Ignore
	public void modelValidateErrorBeforeSave() {

		try {
			interestPoint = new InterestPoint(null, 10, 30);

			dao.save(interestPoint);
		} catch (Exception e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void shouldBeSaveSuccess() {

		try {
			interestPoint = new InterestPoint("Supermercado", 10, 30);

			dao.save(interestPoint);

			InterestPoint point = dao.findById(1);

			assertNotNull(point);
		} catch (Exception e) {
			testException(e);
		}
	}

	private void testException(Exception e) {
		e.printStackTrace();
		fail("Exception: " + e.getMessage());
	}

}
