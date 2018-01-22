package Database;

import Entities.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

                cfg.addAnnotatedClass(Client.class);
                cfg.addAnnotatedClass(Order.class);
                cfg.addAnnotatedClass(SeasonTicket.class);
                cfg.addAnnotatedClass(SportProgram.class);
                cfg.addAnnotatedClass(Training.class);
                cfg.addAnnotatedClass(Trainer.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(cfg.getProperties());
                sessionFactory = cfg.buildSessionFactory(builder.build());
            }
            return sessionFactory;
        } catch (Throwable e) {
            System.err.println("Creation SessionFactory failed" + e);
            throw  new ExceptionInInitializerError(e);
        }
    }

    public static <T> List<T> getCollection(Class c) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(c)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<T> l = criteria.list();
        session.close();
        return l;
    }

    public static <T> T getObject(Class c, int id) {
        Session session = getSession();
        T o = (T)session.createCriteria(c)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return o;
    }

    public static void createObject(Object o) {
        Session session = getSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }

    public static void updateObject(Object o) {
        Session session = getSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteObject(Object o) {
        Session session = getSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static int fillDatabase() {
        Session session = getSession();
        session.close();
        Client c1 = new Client(1, "abc", "1234567");
        Client c2 = new Client(2, "def", "8901234");
        SeasonTicket s = new SeasonTicket(1, 123, 1);

        SportProgram p = new SportProgram(1, "sportProgram");
        Trainer tr = new Trainer(1, "Hohoho", 10000);

        Order o1 = new Order(1, true);
        o1.setClient(c1);
        o1.setTicket(s);

        Order o2 = new Order(2, false);
        o2.setClient(c2);
        o2.setTicket(s);

        Training tg = new Training(1, "Supa-dupa training");
        tg.setProgram(p);
        tg.setTrainer(tr);
        s.setTraining(tg);

        createObject(c1);
        createObject(c2);
        createObject(p);
        createObject(tr);
        createObject(tg);
        createObject(s);
        createObject(o1);
        createObject(o2);

        return 0;
    }
}
