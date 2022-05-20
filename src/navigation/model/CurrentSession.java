package navigation.model;

import model.User;

/**
 *
 * @author User
 */
public class CurrentSession {
    
    private int hits = 0;
    private int faults = 0;
    private User user;

    public int getHits() {
        return hits;
    }

    public int getFaults() {
        return faults;
    }

    public void incrementHits() {
        hits++;
    }

    public void incrementFaults() {
        faults++;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
