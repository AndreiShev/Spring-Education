package org.example.utils;

import org.example.Model.User;
import org.example.Repository.UserRepositoryImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContactsUtils {
    private UserRepositoryImpl userRepositoryImpl;

    public ContactsUtils(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }


    public User validUser(String user) {
        String[] userDataArr = user.split(";");
        if(userDataArr.length !=3) {
            System.out.println("Ошибка. Введите данные контакта в формате Фамилия Имя Отчество;+79045556677;ivanov@email");
            return null;
        }

        boolean result = true;
        result = checkFullName(userDataArr[0], result);
        result = checkPhoneNumber(userDataArr[1], result);
        result = checkEmail(userDataArr[2], result);

        if (!result) {
            return null;
        }

        return new User(userDataArr[0], userDataArr[1], userDataArr[2]);
    }

    private boolean checkFullName(String fullName, boolean result) {
        if(fullName.split(" ").length != 3) {
            System.out.println("ФиО должно содержать три слова");
            result = false;
        }

        return result;
    }

    private boolean checkPhoneNumber(String phone, boolean result) {
        StringBuilder phoneStr = new StringBuilder(phone);

        if(phone.replaceAll("[0-9+]", "").length() != 0) {
            System.out.println("Номер должен содержать только цифры и знак +");
            result = false;
        }

        if(phoneStr.length() != 12) {
            System.out.println("Номер должен содержать 12 символов");
            result = false;
        }

        if(!phoneStr.substring(0, 1).equals("+")) {
            System.out.println("Номер должен начинаться со знака +");
            result = false;
        }

        return result;
    }

    public boolean checkEmail(String email, boolean result) {
        if(!email.contains("@")) {
            System.out.println("Почта должна содержать символ @");
            result = false;
        }

        if(email.replaceAll("[A-Za-z0-9@.]", "").length() != 0) {
            System.out.println("Почта должна содержать латинские символы, цифры, @, '.'");
            result = false;
        }

        Map<String, User> map = userRepositoryImpl.getUsersMap();
        if(map != null && userRepositoryImpl.getUsersMap().get(email) != null) {
            System.out.println("Почта \"" + email + "\" не уникальна");
            result = false;
        }

        return result;
    }
}
