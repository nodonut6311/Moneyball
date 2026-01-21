// src/Savable.java
public interface Savable {
    // Abstract method that any savable class must implement
    boolean save();

    // Default method (Java 8+) demonstrating abstraction and reusability
    default void printSaveStatus(boolean success) {
        if (success) {
            System.out.println(ConsoleColors.GREEN + "✔ Data saved successfully!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "✖ Failed to save data!" + ConsoleColors.RESET);
        }
    }
}

