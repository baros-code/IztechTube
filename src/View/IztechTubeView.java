package View;

import Views.ProfileView;
import Views.TimeLineView;
import DomainLayer.VideoContentPlatform;
import DomainLayer.Observable;
import Views.Observer;
import javax.swing.*;

import java.awt.event.ActionListener;


/**
 * View that is showed after the login , contains all buttons and sub views
 */
public class IztechTubeView extends JFrame implements Observer {

	private VideoContentPlatform model;
	private JTabbedPane tabbedPane;
	private ProfileView profileView;
	private TimeLineView timelineView;
	private String loginUser;

	

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public void setModel(VideoContentPlatform model) {
		this.model=model;
		model.addObserver(this);

	}
	public IztechTubeView(String loginUser, VideoContentPlatform model) { 																																	
		this.loginUser = loginUser;
		this.model = model;
		model.addObserver(this);
		construct(); 
	}

	public void construct() {
		getContentPane().setLayout(null);
		getContentPane().setBounds(0, 0, 1000, 600);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1000, 600);
		add(tabbedPane);

		profileView = new ProfileView(loginUser, model.getUsersName(model.getFollowingsOfUser(loginUser)),
				model.getUsersName(model.getFollowersOfUser(loginUser)),
				model.getVideosTitle(model.getWatchList(loginUser)), model); 

		timelineView = new TimeLineView(model.getVideosAndComments(), model.getUsersName(model.getUsers()),
				model,loginUser);

		tabbedPane.addTab("Timeline", null, timelineView, null);
		tabbedPane.addTab("Profile", null, profileView, null);
	}

	public void enableWatchList() {
		profileView.enableWatchList();
	}

	public void addUnfollowListener(ActionListener al) {
		profileView.addUnfollowListener(al);
	}

	public void addViewWatchListListener(ActionListener al) {
		profileView.addViewWatchListListener(al);
	}

	public void addFollowListener(ActionListener al) {
		timelineView.addFollowListener(al);
	}

	public void addCommentListener(ActionListener al) {
		timelineView.addCommentListener(al);
	}

	public void addDeleteVideoListener(ActionListener al) {
		profileView.addDeleteVideoListener(al);
	}

	public void addToWatchListListener(ActionListener al) {
		timelineView.addToWatchListListener(al);
	}

	public void addLikeListener(ActionListener al) {
		timelineView.addLikeListener(al);
	}

	public void addDislikeListener(ActionListener al) {
		timelineView.addDislikeListener(al);
	}

	public void addCreateWatchListListener(ActionListener al) {profileView.addCreateWatchListListener(al);}

	public String getFollowedUser() {
		return timelineView.getSelectedUser();
	}

	public String getSelectedVideo() {
			return timelineView.getSelectedVideo();
	}

	public String getDeletedVideo() {
		return profileView.getSelectedWatchListVideo();
	}

	public String getFollowedUserOfProfileView() {
		return profileView.getSelectedFollowed();
	}

	public String getCommentText() {
		return timelineView.getCommentText();
	}
	public boolean isWatchListExists() {
	    return profileView.isWatchListExists();
	}

	@Override
	public void update(Observable observable) {
			profileView.update(model);
			timelineView.update(model);
	}

	public void showError(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	
	
}
