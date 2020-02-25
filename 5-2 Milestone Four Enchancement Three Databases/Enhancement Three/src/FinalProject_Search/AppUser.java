
package cs499application;

public class AppUser {
    
    private String username;
    private String Email;
    private boolean isOnline;

    public AppUser(String username, String Email, boolean isOnline) {
        this.username = username;
        this.Email = Email;
        this.isOnline = isOnline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
    
    
    
    
}
