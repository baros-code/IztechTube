package DomainLayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Video {
    private String title;
    private String contentInfo;
    private int intendedAudience;
    private Date date;
    private List<Comment> comments;
    private int numberOfLikes;
    private int numberOfDislikes;

    public Video(String title,String contentInfo,int intendedAudience) {
        this.title=title;
        this.contentInfo=contentInfo;
        this.intendedAudience=intendedAudience;
        date=new Date();
        comments=new ArrayList<Comment>();


    }

    public int getLikes() {return numberOfLikes;}

    public int getDislikes() {return numberOfDislikes;}

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public void setNumberOfDislikes(int numberOfDislikes) {
        this.numberOfDislikes = numberOfDislikes;
    }

    public Date getDate() {
        return date;
    }

    public Comment addComment(User u,String content) {
        Comment c= new Comment(u,content);
        comments.add(c);
        return c;
    }

    public void likedBy(User u) {
        numberOfLikes++;
    }
    public void dislikedBy(User u) {
        numberOfDislikes++;
    }


    public int getIntendedAudience() {
        return intendedAudience;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<String> getCommentContents(){
        List<String> res = new ArrayList<String>();
        for(Comment c: comments)
            res.add(c.getContent());
        return res;
    }


    /**
     * Method that returns two dimensional array containing comment owner and the content
     * @return String[][]
     */
    public String[][] getCommentContentsWithUserName() {
        String[][] arr = new String[comments.size()][2];
        for(int i=0;i<comments.size();i++) {
            arr[i][0] =comments.get(i).getUser().getName();
            arr[i][1] = comments.get(i).getContent();
        }
        return arr;
    }


    public String getContentInfo() {
        return contentInfo;
    }

    public String getTitle() {
        return title;
    }

    public  String displayDate() {
        SimpleDateFormat ft1 = new SimpleDateFormat("dd/MM/yyyy");
        return ft1.format(getDate());
    }

    public String displayComments() {
        String display= " Comments : {";
        for (Comment currentComment : comments) {
            display +=currentComment.getUser().getName() + ": "+ currentComment.getContent();
            display+="\n\n";
        }
        display +="}";
        return display;
    }



    public String toString() {
        return "Video Title : " + getTitle() + " ContentInfo : " + getContentInfo() + " IntendedAudience : "
                + getIntendedAudience() + " Date:" + displayDate() + displayComments();
    }




}

