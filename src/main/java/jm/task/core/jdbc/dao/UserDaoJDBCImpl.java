package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String CREATE = "CREATE TABLE usertable (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(30),\n" +
                "    lastname VARCHAR(30),\n" +
                "    age INT\n" +
                ")";
        String QUERY = "SELECT * FROM usertable";

        try(Statement statement = util.getConnection().createStatement()){
            try {
                if(statement.execute(QUERY)) {
                    System.out.println("Table already exists");
                }
            } catch (Exception e) {
                statement.execute(CREATE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String DROP = "DROP TABLE usertable";

        try(Statement statement = util.getConnection().createStatement()) {
            try {
                statement.execute(DROP);
            } catch (Exception e) {
                System.out.println("Table is not exists");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute("INSERT INTO usertable (name, lastname, age) VALUES ('" +
                    name + "', '" + lastName + "', " + age + ")");
            System.out.println("User с именем " + name + " успешно добавлен");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String DELETE = "DELETE FROM usertable where id=" + id;

        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute(DELETE);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String QUERY = "SELECT * FROM usertable";

        List<User> users = new ArrayList<>();

        try(Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                System.out.println(user);
                users.add(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String CLEAN = "DELETE FROM usertable";

        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute(CLEAN);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
