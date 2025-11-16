package userData;

public class User {
    private String userName;
    private int  invitationsNumber=0;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
