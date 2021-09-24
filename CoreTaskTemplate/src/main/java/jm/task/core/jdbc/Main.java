package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        User[] users = {new User("Dmitriy", "Morozov", (byte) 22),
                new User("Petr", "Glushkov", (byte) 22),
                new User("Egor", "Busalaev", (byte) 34),
                new User("Max", "Miller", (byte) 23)};
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());

        }
        System.out.println("\n" + userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.shutdown();
    }
}