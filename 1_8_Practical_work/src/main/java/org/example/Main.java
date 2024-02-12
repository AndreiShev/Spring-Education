package org.example;

import org.example.Config.DefaultAppConfig;
import org.example.Model.User;
import org.example.Repository.UserRepositoryImpl;
import org.example.utils.ContactsUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class);
        UserRepositoryImpl userRepositoryImpl = context.getBean(UserRepositoryImpl.class);
        ContactsUtils utils = context.getBean(ContactsUtils.class);
        printDescription();

        try {
            runCommand(userRepositoryImpl, utils);
        } finally {
            ((ConfigurableApplicationContext)context).close();
        }
    }

    private static void printDescription() {
        System.out.println("Доступные команды: \n" +
                "\tlist - вывести список контактов\n" +
                "\tadd - добавить\n" +
                "\tdel - удалить(по почте)\n" +
                "\tsave - сохранить контакты в файл на диске\n" +
                "\tend - завершить работу");
    }

    private static void runCommand(UserRepositoryImpl userRepositoryImpl, ContactsUtils utils) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду: ");
            String command = in.nextLine().trim().toLowerCase();

            switch (command) {
                case "list":
                    userRepositoryImpl.getListUsers();
                    break;
                case "add":
                    addUser(in, utils, userRepositoryImpl);
                    break;
                case "del":
                    delUser(in, userRepositoryImpl);
                    break;
                case "save":
                    userRepositoryImpl.saveToFile(true);
                    break;
                case "end":
                    return;
                default:
                    System.out.println("Команда введена неверно. Повторите ввод.");
            }
        }
    }

    private static void addUser(Scanner in, ContactsUtils utils, UserRepositoryImpl userRepositoryImpl) {
        System.out.println("Напишите данные контакта в формате Иванов Иван Иванович;+79045556677;ivanov@email.com "
                + "или cancel для отмены");
        User user = null;
        while(user == null) {
            String strUser = in.nextLine().trim();
            if(strUser.equals("cancel")) {
                break;
            }

            user = utils.validUser(strUser);
            if(user != null ) userRepositoryImpl.addUser(user);
        }
    }

    private static void delUser(Scanner in, UserRepositoryImpl userRepositoryImpl) {
        System.out.println("Введите почту контакта:");
        userRepositoryImpl.deleteUser(in.nextLine().trim());
    }


}