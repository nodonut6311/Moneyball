import java.io.*;
import java.util.*;

public class Login {
    private static final String USER_DB = "data/users.txt";
    private static Map<String, String> users = new HashMap<>();

    static {
        loadUsers();
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DB))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    users.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error reading user file: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public static boolean auth(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public static boolean register(String username, String password) {
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
            System.out.println(ConsoleColors.RED + "Error writing to user file." + ConsoleColors.RESET);
            return false;
        }
    }
}
