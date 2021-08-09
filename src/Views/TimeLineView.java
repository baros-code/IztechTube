package Views;

import javax.swing.*;

import DomainLayer.Observable;
import DomainLayer.VideoContentPlatform;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class TimeLineView extends JPanel {
    private PanelView usersView;
    private PanelView videosView;

    private JButton addToWatchListButton;
    private JButton likeButton;
    private JButton dislikeButton;
    private JButton commentButton;
    private JButton followButton;
    private JTextField newCommentTextField;
    //Map<String,ArrayList<String>> videoTitlesAndTheirComments
    public TimeLineView(Map<String,List<String>> videosAndCommentsData,List<String> userData, VideoContentPlatform model,String username) {
        this.setLayout(null);
        usersView = new UsersView(userData, model,username); 
        videosView = new VideosView(videosAndCommentsData, model,username); 
        
        usersView.setBounds(700,50,200,320);
        videosView.setBounds(15,15,350,345); //5,5,350,220
        
        add(usersView);
        add(videosView); 
        constructTimeLine();
    }



    private void constructTimeLine(){
    	
    	
        addToWatchListButton = new JButton("ADD TO WATCHLIST");
        addToWatchListButton.setBounds(20,370, 155, 23);
        this.add(addToWatchListButton);

        likeButton = new JButton("LIKE");
        likeButton.setBounds(185, 370, 85, 23);
        this.add(likeButton);

        dislikeButton = new JButton("DISLIKE");
        dislikeButton.setBounds(275, 370, 90, 23);
        this.add(dislikeButton);

        commentButton = new JButton("ADD");
        commentButton.setBounds(525,100, 80, 23);
        this.add(commentButton);
    	
        followButton = new JButton("FOLLOW");
        followButton.setBounds(710,370,100,23);
        add(followButton);
        
		JLabel allUsersLabel = new JLabel("All Users");
		allUsersLabel.setBounds(710,30,111,14);
        add(allUsersLabel);
    	
    	
		JLabel newCommentLabel = new JLabel("New Comment");
		newCommentLabel.setBounds(373, 25, 111, 14);
        add(newCommentLabel);
		
        newCommentTextField = new JTextField();
		newCommentTextField.setBounds(373, 45, 236, 44);
		add(newCommentTextField);
        newCommentTextField.setColumns(10);
        
 /*       //VIDEO LIST + VIDEO IMAGE 
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("resources/"+videosView.getVideosData().get(0)+".png"));
        JList videoNamesJList = new JList(new Vector <String>(videosView.getVideosData()));
		videoNamesJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
					imageLabel.setIcon(new ImageIcon("resources/"+videosView.getVideosData().get(videoNamesJList.getSelectedIndex())+".png"));
                }
		});
		videoNamesJList.setBounds(10, 55, 140, 200);
		imageLabel.setBounds(150, 55, 200, 200);
		add(imageLabel);
*/		//add(videoNamesJList);
        /*VIDEO LIST + VIDEO IMAGE */
        
    }

    public String getSelectedUser() {
        return usersView.getSelectedData();
    }

    public String getSelectedVideo() {
        return videosView.getSelectedData();
    }
    public String getCommentText() {
    	return newCommentTextField.getText();
    }
    public void addFollowListener(ActionListener log) {
        followButton.addActionListener(log);
    }

    public void addToWatchListListener(ActionListener log) {
        addToWatchListButton.addActionListener(log);
    }

    public void addLikeListener(ActionListener log) {
        likeButton.addActionListener(log);
    }

    public void addDislikeListener(ActionListener log) {
        dislikeButton.addActionListener(log);
    }

    public void addCommentListener(ActionListener log) {
        commentButton.addActionListener(log);
    }

    public void update(Observable model) {
            usersView.update(model);
            videosView.update(model);
    }


}
