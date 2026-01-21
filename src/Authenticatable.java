// src/Authenticatable.java
public interface Authenticatable {
    boolean auth(String username, String password);
    boolean register(String username, String password);
}
