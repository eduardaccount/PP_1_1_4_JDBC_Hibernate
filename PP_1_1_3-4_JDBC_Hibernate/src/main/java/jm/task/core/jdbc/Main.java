package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Aleksey", "Ivanov", (byte) 22);
        userService.saveUser("Sergey", "Smirnov", (byte) 12);
        userService.saveUser("Ivan", "Sidorov", (byte) 35);
        userService.saveUser("Alex", "Mohov", (byte) 82);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeAll();
    }
}
