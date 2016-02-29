package by.chupilin.web.guestbook.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ItemDAOImpl {

    protected Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private SessionFactory sessionFactory;

    public Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

}
