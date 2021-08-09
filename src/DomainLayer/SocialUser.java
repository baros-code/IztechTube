package DomainLayer;

import java.util.ArrayList;
import java.util.List;

public class SocialUser extends DomainLayer.User {
    private List<User> followings;
    private List<User> followers;


    public SocialUser(String name, String password) {
        super(name, password);
        followings = new ArrayList<User>();
        followers = new ArrayList<User>();
    }

    public SocialUser(String name,String password, List<User> followings,List<User> followers) {
        super(name,password);
        this.followings=followings;
        this.followers=followers;
    }


     public void follow(User u) {
        followings.add(u);

    }

    public boolean hasFollowedUser(String userName) {
        for (User user: followings) {
            if (user.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFollowerUser(String userName) {
        for (User user: followers) {
            if (user.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
     public void unfollow(User u) {
        followings.remove(u);
    }

    public void addFollower(User u) {
        followers.add(u);
    }
    public void removeFollower(User u) {
        followers.remove(u);
    }

    public  List<User> getFollowings() {
        return followings;
    }

     public List<User> getFollowers() {
        return followers;
    }

    private String displayFollowings() {
        String display= " Following Users : {";
        for (DomainLayer.User currentUser : followings) {
            display +=currentUser.getName();
            display+=",";
        }
        display +="}";
        return display;

    }
    private String displayFollowers() {
        String display= " Follower Users : {";
        for (DomainLayer.User currentUser : followers) {
            display +=currentUser.getName();
            display+=",";
        }
        display +="}";
        return display;

    }

    public String toString() {
        return super.toString() + displayFollowings() + displayFollowers();
    }

     public String toStringwithPassword() {
        return super.toStringWithPassword() + displayFollowings() + displayFollowers();
    }


}
