package DomainLayer;

public class Comment {

    private String content;
    private User user;

    public Comment(User user, String content) {
        this.user=user;
        this.content = content;
    }


    public String getContent() {
        return content;

    }
    public User getUser() {
        return user;
    }

    public String toString() {
        return user + "Comment : " + getContent() ;
    }

}
