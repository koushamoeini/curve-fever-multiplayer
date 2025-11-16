package userData;

public class UserPort {
    private User user;
    private int port;

    public UserPort(User user, int port) {
        this.user = user;
        this.port = port;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
