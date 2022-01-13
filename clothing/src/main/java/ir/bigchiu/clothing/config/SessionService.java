package ir.bigchiu.clothing.config;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class SessionService {

    private SessionFactory hibernateFactory;

    @Autowired
    public SessionService(EntityManagerFactory factory) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    public Session getSession() {
        return hibernateFactory.openSession();
    }
}
