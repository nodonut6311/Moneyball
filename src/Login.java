// src/Login.java
import java.io.*;
import java.util.*;

public class Login implements Authenticatable {
    private static final String USER_DB = "data/users.txt";
    private static Map<String, String> users = new HashMap<>();

    static {
        loadUsers();
    }

    // Private constructor prevents instantiation outside
    public Login() {}

    // Load users from file
    private static void loadUsers() {
        File file = new File(USER_DB);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.err.println(ConsoleColors.RED + "Error creating user file: " + e.getMessage() + ConsoleColors.RESET);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DB))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) users.put(parts[0].trim(), parts[1].trim());
            }
        } catch (IOException e) {
            System.err.println(ConsoleColors.RED + "Error reading user file: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    // --- Implement Authenticatable interface ---
    @Override
    public boolean auth(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    @Override
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println(ConsoleColors.RED + "Username already exists!" + ConsoleColors.RESET);
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DB, true))) {
            writer.write(username + "," + password);
            writer.newLine();
            users.put(username, password);
            System.out.println(ConsoleColors.GREEN + "User registered successfully!" + ConsoleColors.RESET);
            return true;
        } catch (IOException e) {
            System.err.println(ConsoleColors.RED + "Error writing to user file: " + e.getMessage() + ConsoleColors.RESET);
            return false;
        }
    }

    // Static helpers for backward compatibility in MainApp
    public static boolean staticAuth(String username, String password) {
        Login login = new Login();
        return login.auth(username, password);
    }

    public static boolean staticRegister(String username, String password) {
        Login login = new Login();
        return login.register(username, password);
    }
}
