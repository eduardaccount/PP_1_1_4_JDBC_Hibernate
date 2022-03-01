package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users_table (" +
                    "user_id INT(10) PRIMARY KEY AUTO_INCREMENT, " +
                    "user_name VARCHAR(50), " +
                    "user_lastname VARCHAR(50)," +
                    "user_age INT(3))").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица была создана без ошибок!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users_table").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица была удалена без ошибок.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.println("User под номером: " + id + " был удален из базы данных!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> results = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            results = session.createQuery("FROM User").list();
            session.getTransaction().commit();
            System.out.println("Список пользователей был загружен без ошибок!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return results;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена без ошибок!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
