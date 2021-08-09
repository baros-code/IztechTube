package Views;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DomainLayer.*;

public class ProfileView extends JPanel implements Observer {
    private FollowedView followedView;  //unfollowButton,viewWatchListButton
    private FollowerView followerView;  //-
    private WatchListView watchListView;    //removeButton   
   
    private JLabel watchListLabel;
    private JButton createWatchListButton;
    private JButton deleteVideoButton;
    private JButton unFollowButton;
    private JButton viewWatchListButton;
    
    private WatchListView followedUsersWatchList;
    
    
    public ProfileView(String userName,List<String> followedData,List<String> followerData,List<String> watchListData, VideoContentPlatform model) {
        this.setLayout(null);    
        followedView = new FollowedView(followedData, model,userName);
        followedView.setBounds(155,40,200,300);
        
        followerView = new FollowerView(followerData, model,userName);
        followerView.setBounds(400,40,200,300);
        
        watchListView = new WatchListView(watchListData, model,userName);
        watchListView.setBounds(650,40,200,300);
        
        construct(userName);
        disableWatchList();    
        add(followedView);
        add(followerView);
        add(watchListView); 

    }
    public void construct(String userName) {
    	JLabel followedLabel = new JLabel("Following Users");
    	followedLabel.setBounds(155,15,100,25);
    	
    	JLabel followersLabel = new JLabel("Follower Users");
    	followersLabel.setBounds(400,15,100,25);
    	
    	watchListLabel = new JLabel("Watch List");
    	watchListLabel.setBounds(650,15,100,25);
    	
    	JLabel userNameLabel = new JLabel("Username: " + userName);
    	userNameLabel.setBounds(5,5,120,25);
    	
    	JLabel profilePicture = new JLabel();
    	profilePicture.setIcon(new ImageIcon("profileLogos/"+userName+".png"));
    	profilePicture.setBounds(5,75,150,150);
    	
    	deleteVideoButton = new JButton("DELETE VIDEO");
        deleteVideoButton.setBounds(650, 355, 150, 23);
        
        unFollowButton = new JButton("UNFOLLOW");
        unFollowButton.setBounds(159, 355, 100, 23);
        

        viewWatchListButton = new JButton("VIEW WATCHLIST");
        viewWatchListButton.setBounds(269, 355, 150, 23);

        createWatchListButton = new JButton("CREATE WATCH LIST");
        createWatchListButton.setBounds(650,40,220,25);

        
        /*createWatchListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enableWatchList();	
			}

		});

         */
        
        add(createWatchListButton);
        add(profilePicture);
        add(userNameLabel);
        add(followedLabel);
        add(followersLabel);
        add(watchListLabel);
        add(deleteVideoButton);
        add(viewWatchListButton);
        add(unFollowButton);
    }

public void disableWatchList(){
    	createWatchListButton.setVisible(true);
    	watchListLabel.setVisible(false);
    	watchListView.setVisible(false);
    	deleteVideoButton.setVisible(false);
    }
    public void enableWatchList() {
    	createWatchListButton.setVisible(false);
    	
    	watchListLabel.setVisible(true);
    	watchListView.setVisible(true);
    	deleteVideoButton.setVisible(true);
    }
    public void setFollowedUsersVideoNames(List<String> videoNames){   
       
    }
    public boolean isWatchListExists() {
    	return watchListLabel.isVisible();
    }

    public String getSelectedFollowed() {
        return followedView.getSelectedData();
    }

    public String getSelectedFollower() {
        return followerView.getSelectedData();
    }

    public String getSelectedWatchListVideo() {
        return watchListView.getSelectedData();
    }

    public void addUnfollowListener(ActionListener log) {
        unFollowButton.addActionListener(log);
    }

    public void addViewWatchListListener(ActionListener log) {
        viewWatchListButton.addActionListener(log);
    }

    public void addDeleteVideoListener(ActionListener log) {
        deleteVideoButton.addActionListener(log);
    }


    public void addCreateWatchListListener(ActionListener log) { createWatchListButton.addActionListener(log);}
    
    public void update(Observable model) {
        followedView.update(model);
        followerView.update(model);
        watchListView.update(model);
        //followedUsersWatchList.update(model);	// takip edilenlerin videolarini gormeli
    }

    


}
