package org.example.Repository;

import org.example.Model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Scope("singleton")
public class UserRepositoryImpl implements UserRepository {
    private List<User> userList = new ArrayList<>();
    @Value("${app.users.init.file:not set}")
    private String sourceInitFile;
    @Value("${app.users.file:not set}")
    private String sourceCurrentFile;

    private Map<String, User> usersMap = new HashMap<>();
    private String path;
    public UserRepositoryImpl() {
        System.out.println("UserRepositoryImpl init");
    }

    public UserRepositoryImpl(List<User> userList) {
        this.userList = userList;
    }

    @PostConstruct
    public void initUsers() {
        this.path = new File(Paths.get(new File("").getAbsolutePath(), sourceCurrentFile).toUri()).getAbsolutePath();
        if (!sourceInitFile.equals("not set")) {
            URL pathInitFile = ClassLoader.getSystemResource(sourceInitFile);
            if(pathInitFile==null) {
                System.out.println("Путь в app.users.init.file указан неверно");
            }

            List<String> listString = getDataFromFile(pathInitFile.getPath());
            userList.addAll(parseUsers(listString));
            saveToFile(false);
        }

        List<String> usersFromFile = getDataFromFile(path);
        if (usersFromFile.size() != 0) {
            List<User> tempUsersList = parseUsers(usersFromFile);
            usersMap = converListUserToMap(tempUsersList);
            System.out.println("Загружены ранее сохраненные данные.");
        }
    }

    public void getListUsers() {
        userList.forEach(System.out::println);
    }

    public void addUser(User tempUser) {
        userList.add(tempUser);
        usersMap.put(tempUser.getEmail(), tempUser);
    }

    public void deleteUser(String email) throws RuntimeException {
        List<User> tempList = new ArrayList<>(getUserList());
        for(User item: tempList) {
            if(item.getEmail().equals(email)) {
                userList.remove(item);
                deleteStringFromFile(item.toString());
                System.out.println("Пользователь удален.");
                return;
            }
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Map<String, User> getUsersMap() {
        return usersMap;
    }

    private void deleteStringFromFile(String lineDelete) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((string = reader.readLine()) != null) {
                if (!lineDelete.equals(string)) {
                    stringBuffer.append(string).append("\n");
                }
            }
            string = stringBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[] buffer = new char[string.length()];
        string.getChars(0, string.length(), buffer, 0);

        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Метод записывает список пользователей в файл.
     * @param append при значении true перезаписывает файл, выполняя проверку на пользователей на уникальность
     *               по их почте
    * */
    public void saveToFile(boolean append) {
        StringBuilder stringBuilder = new StringBuilder();
        for(User item: userList) {
            stringBuilder.append(item.toString()).append("\n");
        }

        try(FileOutputStream outputStream = new FileOutputStream(path, append))
        {
            outputStream.write(stringBuilder.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getDataFromFile(String readPath) {
        List<String> listString = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(readPath)))) {
            listString = reader.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("Ранее сохраненных данных не обнаружено.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listString;
    }

    private List<User> parseUsers(List<String> listString) {
        StringBuilder stringBuilder = new StringBuilder();
        List<User> result = new ArrayList<>();
        for(String item: listString) {
            stringBuilder.append(item).append("\n");
            String[] arr = item.split(";");
            result.add(new User(arr[0], arr[1], arr[2]));
        }
        return result;
    }

    private Map<String, User> converListUserToMap(List<User> list) {
        Map<String, User> result = new HashMap<>();
        for(User item: list) {
            result.put(item.getEmail(), item);
        }

        return result;
    }
}
