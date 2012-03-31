package com.intel.openAttestation.manifest.hibernate.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


/**
 * Basic Hibernate helper class, handles SessionFactory, Session and Transaction.
 * <p>
 * Uses a static initializer for the initial SessionFactory creation
 * and holds Session and Transactions in thread local variables. All
 * exceptions are wrapped in an unchecked InfrastructureException.
 *
 * @author christian@hibernate.org
 */
public class HibernateUtilHis {

    private static Log log = LogFactory.getLog(HibernateUtilHis.class);

    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaction = new ThreadLocal();
    private static final ThreadLocal threadInterceptor = new ThreadLocal();

    // Create the initial SessionFactory from the default configuration files

    static {
        try {
            configuration = new Configuration();
            sessionFactory = configuration.configure("/hibernateOat.cfg.xml").buildSessionFactory();
            // We could also let Hibernate bind it to JNDI:

            // configuration.configure().buildSessionFactory()
        } catch (Throwable ex) {
            // We have to catch Throwable, otherwise we will miss
            // NoClassDefFoundError and other subclasses of Error
            log.error("Building SessionFactory failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the SessionFactory used for this static class.
     *
     * @return SessionFactory
     */

    public static SessionFactory getSessionFactory() {
        /* Instead of a static variable, use JNDI:
                         SessionFactory sessions = null;
                         try {
                Context ctx = new InitialContext();
                String jndiName = "java:hibernate/HibernateFactory";
                sessions = (SessionFactory)ctx.lookup(jndiName);
                         } catch (NamingException ex) {
                throw new InfrastructureException(ex);
                         }
                         return sessions;
         */
    	synchronized (sessionFactory) {
    		return sessionFactory;
		}
    }

    /**
     * Returns the original Hibernate configuration.
     *
     * @return Configuration
     */

    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Rebuild the SessionFactory with the static Configuration.
     *
     */
    public static void rebuildSessionFactory() throws OATException {
        synchronized (sessionFactory) {
            try {
                sessionFactory = getConfiguration().buildSessionFactory();
            } catch (Exception ex) {
                throw new OATException(ex);
            }
        }
    }

    /**
     * Rebuild the SessionFactory with the given Hibernate Configuration.
     *
     * @param cfg
     */

    public static void rebuildSessionFactory(Configuration cfg) throws
            OATException {
        synchronized (sessionFactory) {
            try {
                sessionFactory = cfg.buildSessionFactory();
                configuration = cfg;
            } catch (Exception ex) {
                throw new OATException(ex);
            }
        }
    }

    /**
     * Retrieves the current Session local to the thread.
     * <p/>

     * If no Session is open, opens a new Session for the running thread.
     *
     * @return Session
     */
    public static Session getSession() throws OATException {
        Session s = (Session) threadSession.get();
        try {
            if (s == null) {
                log.debug("Opening new Session for this thread.");
                if (getInterceptor() != null) {
                    log.debug("Using interceptor: " + getInterceptor().getClass());
                    s = getSessionFactory().openSession(getInterceptor());
                } else {
                    s = getSessionFactory().openSession();
                }
                threadSession.set(s);
            }
        } catch (HibernateException ex) {
            throw new OATException(ex);
        }
        return s;
    }

    /**
     * Closes the Session local to the thread.
     */

    public static void closeSession() throws OATException {
        try {
            Session s = (Session) threadSession.get();
            threadSession.set(null);
            if (s != null && s.isOpen()) {
                log.debug("Closing Session of this thread.");
                s.close();
            }
        } catch (HibernateException ex) {
            throw new OATException(ex);
        }
    }

    /**
     * Start a new database transaction.
     */

    public static void beginTransaction() throws OATException {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx == null) {
                log.debug("Starting new database transaction in this thread.");
                tx = getSession().beginTransaction();
                threadTransaction.set(tx);
            }
        } catch (HibernateException ex) {
            throw new OATException(ex);
        }
    }

    /**
     * Commit the database transaction.
     */

    public static void commitTransaction() throws OATException {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx != null && !tx.wasCommitted()
                && !tx.wasRolledBack()) {
                log.debug("Committing database transaction of this thread.");
                tx.commit();
            }
            threadTransaction.set(null);
        } catch (HibernateException ex) {
            rollbackTransaction();
            throw new OATException(ex);
        }
    }

    /**
     * Commit the database transaction.
     */

    public static void rollbackTransaction() throws OATException {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            threadTransaction.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                log.debug(
                        "Tyring to rollback database transaction of this thread.");
                tx.rollback();
            }
        } catch (HibernateException ex) {
            throw new OATException(ex);
        } finally {
            closeSession();
        }
    }

    /**
     * Reconnects a Hibernate Session to the current Thread.
     *
     * @param session The Hibernate Session to be reconnected.
     */

    public static void reconnect(Session session) throws
            OATException {
        try {
            session.reconnect();
            threadSession.set(session);
        } catch (HibernateException ex) {
            throw new OATException(ex);
        }
    }

    /**
     * Disconnect and return Session from current Thread.
     *
     * @return Session the disconnected Session
     */

    public static Session disconnectSession() throws OATException {

        Session session = getSession();
        try {
            threadSession.set(null);
//            if (session.isConnected() && session.isOpen()) {
//                session.disconnect();
//            }
            session.disconnect();
        } catch (HibernateException ex) {
            throw new OATException(ex);
        }
        return session;
    }

    /**
     * Register a Hibernate interceptor with the current thread.
     * <p>

     * Every Session opened is opened with this interceptor after
     * registration. Has no effect if the current Session of the
     * thread is already open, effective on next close()/getSession().
     */
    public static void registerInterceptor(Interceptor interceptor) {
        threadInterceptor.set(interceptor);
    }

    private static Interceptor getInterceptor() {
        Interceptor interceptor =
                (Interceptor) threadInterceptor.get();
        return interceptor;
    }

}
