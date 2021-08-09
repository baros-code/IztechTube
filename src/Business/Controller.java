package Business;

import DomainLayer.*;
import View.*;
import Views.*;
import DomainLayer.Video;
import IO.JSONReader;
import IO.XMLReader;
import View.IztechTubeView;
import View.LoginView;
import DomainLayer.User;
import DomainLayer.VideoContentPlatform;
import View.FollowedUsersWatchList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Controller  {

	private LoginView loginView;
	private IztechTubeView tubeView;
	private VideoContentPlatform model;
	private String loginUserName;

	

	/**
	 * 
	 * Constructor that show the Login page to the user and reads the data from file.
	 */
	public Controller(LoginView loginView, VideoContentPlatform model) {
		this.loginView = loginView;
		this.model = model;
		((LoginView)loginView).addLoginListener(new LoginListener());
		loginView.setSize(300,200);
		loginView.setVisible(true);
		readDatasFromFile("src\\IO\\users.xml","src\\IO\\videos.json");
	}
	/**
	 * 
	 * method that creates and adds the listeners to the Iztech View
	 */

	private void prepareIztechView() {
		
		this.tubeView= new IztechTubeView(loginUserName,model);
		((IztechTubeView)tubeView).addUnfollowListener(new UnfollowListener());
		((IztechTubeView)tubeView).addFollowListener(new FollowListener());
		((IztechTubeView)tubeView).addCommentListener(new CommentAddButtonListener());
		((IztechTubeView)tubeView).addLikeListener(new LikeButtonListener());
		((IztechTubeView)tubeView).addDislikeListener(new DislikeButtonListener());
		((IztechTubeView)tubeView).addDeleteVideoListener(new DeleteButtonListener());
		((IztechTubeView)tubeView).addToWatchListListener(new AddToWatchListButtonListener());
		((IztechTubeView)tubeView).addViewWatchListListener(new ViewWatchListListener());
		((IztechTubeView)tubeView).addCreateWatchListListener(new CreateWatchListListener());
		tubeView.setSize(1000,500);
		tubeView.setVisible(true);

	}
	/**
	 * 
	 * 
	 * @param usersFilename is a xml file name to reach the users information
	 * @param videosFilename is a json file name to reach the videos information
	 */
	public void readDatasFromFile(String usersFileName,String videosFileName) {
		XMLReader.read(usersFileName);
		Map<String,String>  userNameAndPassword=XMLReader.getUserNameAndPasswords();
		addUsersToModel(userNameAndPassword); // user is created
		Map<String,List<String>> userNameAndFollowings=XMLReader.getUserNameAndFollowings();
		addFollowingsToUser(userNameAndFollowings); // followings is created
		
		JSONReader.read(videosFileName);	
		model.generateVideos(JSONReader.data);	// video is created	
		addVideosToWatchList();
	}
	/**
	 * 
	 * @method that adds the videos to the watchlist of the user that taken from xml file.
	 */
	private void addVideosToWatchList() {
		Map<String,List<String>> userNameAndWatchLists=XMLReader.getUserNameAndWatchLists();
		
		for (String currentUser:userNameAndWatchLists.keySet()) {
			List<String> currentVideos=userNameAndWatchLists.get(currentUser);
			for (String currentVideo: currentVideos) {
				if(!model.isUserHaveVideoInWatchList(currentUser,currentVideo))
					model.addVideoToUsersWatchList(currentUser,currentVideo);
			}
		}
	}

	/**
	 * 
	 * @param : It is the argument that takes from xml file by using XMLReader
	 * 
	 */
	private void addFollowingsToUser(Map<String,List<String>> userNameAndFollowings) {
		for (String currentUser:userNameAndFollowings.keySet()) {
			List<String> currentFollowings=userNameAndFollowings.get(currentUser);
			for (String currentFollowing: currentFollowings) {
				if(!model.isFollowed(currentUser, currentFollowing))
					model.followOperation(currentUser,currentFollowing);
			}
		}
	}

	/**
	 * 
	 *  @param : It is the argument that takes from xml file by using XMLreader
	 * 	method adds all users to the model.
	 */

	private void addUsersToModel(Map<String,String> usersAndPasswords) {
		for (String currentName: usersAndPasswords.keySet()) {
			String currentPassword=usersAndPasswords.get(currentName);
			model.addSocialUser(currentName,currentPassword);
		}

	}



	public void addUserToModel(String userName,String password) {
		model.addSocialUser(userName, password);
	}

	public void addVideoToModel(String videoTitle,String contentInfo,int intendedAuidence) {
		model.addVideo(videoTitle, contentInfo, intendedAuidence);
	}

	/**
	 * 
	 * It is the class that provides view watch list of the followed user button.
	 */

	class ViewWatchListListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String followedUser = tubeView.getFollowedUserOfProfileView();
				List<Video> videosOfUser = model.getWatchList(followedUser);
				List<String> videosTitle = model.getVideosTitle(videosOfUser);
				FollowedUsersWatchList f = new FollowedUsersWatchList(videosTitle, model, followedUser);
				f.setSize(240, 290);
				f.setVisible(true);

			} catch (Exception ex) {
				tubeView.showError("No user selected to view the watchList!");
			}

		}
	}
	/**
	 * 
	 * @param : it is the argument that is used for print string
	 * 
	 *method provides precondition checker if user didn't select the index of scrollview. 
	 */
	private void IndexIsNotSelected(String object ) {
		tubeView.showError("Please select the " + object );
	}

	/**
	 * 
	 * 
	 * class that provides "add to watch List button" to add video to user's watch list.
	 */

	class AddToWatchListButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String videoTitle = tubeView.getSelectedVideo();
				if (!tubeView.isWatchListExists() && (!(model.userHasWatchList(loginUserName)))) {
					tubeView.showError("You should create a watchList from Profile!");
				} else if (model.isUserHaveVideoInWatchList(loginUserName, videoTitle)) {
					tubeView.showError("You already have added the video to your watch list!");
				} else {
					model.addVideoToUsersWatchList(loginUserName, videoTitle);
				}
			} catch (IndexOutOfBoundsException ex) {
				IndexIsNotSelected("Video");

			}

		}
	}
	/**
	 * 
	 * class that provides  "add comment button" to add comment to the video.
	 * 
	 */
	class CommentAddButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) { 
			try {
			String commentText = tubeView.getCommentText();
			String videoTitle = tubeView.getSelectedVideo();
			if (commentText.equals("")) {
				tubeView.showError("Comment is invalid! Try again!");
			} else {
				model.commentOperation(loginUserName, videoTitle, commentText);
			}
		}		catch (IndexOutOfBoundsException ex) {
			IndexIsNotSelected("Video");
		}
		}
	}
	/**
	 * 
	 *  Class that provides "like button" to like the video.
	 */
	class LikeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) { try {
			String videoTitle = tubeView.getSelectedVideo();
			if (model.isUserLikedTheVideo(loginUserName, videoTitle)) {
				tubeView.showError("You have already liked the video!");
			} else {
				model.likeOperation(loginUserName, videoTitle);
			}
		} catch (IndexOutOfBoundsException ex) {
			IndexIsNotSelected("Video");
		}
		}

	}
	
	/**
	 * 
	 *  Class that provides "dislike button" to dislike the video.
	 */

	class DislikeButtonListener implements ActionListener{ 		@Override
	public void actionPerformed(ActionEvent e){ try {
		String videoTitle = tubeView.getSelectedVideo();
		if (model.isUserDisLikedTheVideo(loginUserName, videoTitle)) {
			tubeView.showError("You have already disliked the video!");
		} else {
			model.disLikeOperation(loginUserName, videoTitle);
		}
	} catch (IndexOutOfBoundsException ex) {
		IndexIsNotSelected("Video");
	}
	}
	}
	/**
	 * 
	 *  Class that provides "delete button" to delete the video.
	 */

	class DeleteButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String videoTitle = tubeView.getDeletedVideo();
				model.removeVideoFromUsersWatchList(loginUserName, videoTitle);
			} catch (Exception ex) {
				tubeView.showError("No video selected to remove!");
			}
		}
	}
	/**
	 * 
	 *  Class that provides "unfollow button" to unfollow the user.
	 */
	class UnfollowListener implements ActionListener  {
		public void actionPerformed(ActionEvent e)  {
			try {
				String unfollowedUser = tubeView.getFollowedUserOfProfileView();
				model.unFollowOperation(loginUserName, unfollowedUser);
			}
			catch (IndexOutOfBoundsException ex) {
				tubeView.showError("No user selected  to unfollow");
			}

		}
	}
	/**
	 * 
	 *  Class that provides "create watch list button" if user didn't create the watch list. If
	 * user has a watch list, button will be invisible.
	 */

	class CreateWatchListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				tubeView.enableWatchList();
		}
	}

	/**
	 * 
	 *  Class that provides "follow button" to follow the user.
	 */

	class FollowListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { try {
			String followedUser = tubeView.getFollowedUser();
			if (model.isFollowed(loginUserName, followedUser)) {
				tubeView.showError("You have already followed the user!");
			} 
			else if(followedUser.equals(loginUserName)) {
				tubeView.showError("You can not follow yourself!");
			}
			else {
				model.followOperation(loginUserName, followedUser);
			}
		} catch (IndexOutOfBoundsException ex) {
			IndexIsNotSelected("User");
		}
		}
	}

	/**
	 * 
	 * method that provides a precondition to check user's userName and password while loginView is visible.
	 */

	private boolean isLoginValid(String inputName,String inputPassword) {
		User inputUser=model.findUser(inputName);
		if (inputUser != null && inputUser.getPassword().equals(inputPassword))  {
			return true;

		}
		return false;

	}



	/**
	 * 
	 * It is a class that provides login button for logging the system.
	 */

	class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = "";
			String password;
			try {
				username = loginView.getUsername();
				password = new String(loginView.getPassword());
				if(isLoginValid(username, password)) {	
					loginView.setVisible(false); 	
					System.out.println("Username: " + username + " Password: " + password);
					loginUserName=username;
					prepareIztechView();
					if (model.userHasWatchList(loginUserName)) {
						tubeView.enableWatchList();
					}
				}

				else
					throw new IllegalArgumentException();

			} catch (IllegalArgumentException ex) {
				loginView.showError("Wrong username or password.");
			}
		}
	}

}
