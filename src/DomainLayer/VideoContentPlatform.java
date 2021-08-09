package DomainLayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VideoContentPlatform extends Observable {
	private List<Video> videos;
	private List<User> users;
	private Map<String,UserInformation> usersData; // string is a unique username
	

	public VideoContentPlatform() {
		videos = new ArrayList<Video>();
		users = new ArrayList<User>();
		usersData = new HashMap<String, UserInformation>();

	}
	public VideoContentPlatform(List <User> users, List <Video> videos) {
		this.videos=videos;
		this.users=users;
		usersData=new HashMap<String, UserInformation>();
		generateAllUserInformation();

	}

	/**
	 * Method that assigns videos field to newly generated video list.
	 * @param Map<String[][],ArrayList<String[][]>> is a data that keeps both users and video information mapped.
	 */

	public void generateVideos(Map<String[][],ArrayList<String[][]>> data) {
		ArrayList<Video> allVideos = createVideos(data);
		ArrayList<String[][]> comments = (new ArrayList<ArrayList<String[][]>>(data.values())).get(0);
		addCommentsTo(allVideos, comments);			//make comment operations 
		this.videos = allVideos;
	}

	/**
	 * Private method that parses given data into video fields and creates a video list and returns it.
	 * @param Map<String[][],ArrayList<String[][]>> is a data that keeps both users and video information mapped.
	 */

	private ArrayList<Video> createVideos(Map<String[][],ArrayList<String[][]>> data) {
		Set<String[][]> keys = 	data.keySet();
		String[][] videos = ((String[][])(keys.toArray()[0]));
		ArrayList<Video> allVideos = new ArrayList<Video>();
		for(int i=0; i<videos.length;i++) {
			String title = videos[i][0];
			String contentInfo = videos[i][1];
			int intendedAudience = Integer.parseInt(videos[i][2]);
			Date date = null;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(videos[i][3]);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			int like = Integer.parseInt(videos[i][4]);
			int dislikes = Integer.parseInt(videos[i][5]);
			Video vid =  new Video(title,contentInfo,intendedAudience);
			vid.setNumberOfLikes(vid.getLikes()+like);
			vid.setNumberOfDislikes(vid.getDislikes()+dislikes);
			allVideos.add(vid);
		}
	
		return allVideos;
	}

	private void addCommentsTo(ArrayList<Video> vid,ArrayList<String[][]> comments) {
		String[][] commentsArray;
		Video v;
		for(int i=0;i<vid.size(); i++) {
			v=vid.get(i);
			commentsArray = comments.get(i);
			
			for(int j=0;j<commentsArray.length;j++) {
				String userName = commentsArray[j][0];
				String content = commentsArray[j][1];
				v.addComment(findUser(userName),content);
			}
		}
	}
	
	/////
	
	public boolean userHasWatchList(String userName) {
		List<Video> u=getWatchList(userName);
		return !(u.isEmpty());
	}


	private void generateAllUserInformation() {
		for(User u: users) {
			generateUserInformation(u);
		}
	}

	private void generateUserInformation(User u) {
		usersData.put(u.getName(), new UserInformation());	
	}

	public boolean  isUserHaveVideoInWatchList(String userName,String videoTitle) {
		UserInformation UI=findUserData(userName);
		return UI.hasVideoWatchList(videoTitle);
	}

	public boolean isUserLikedTheVideo(String userName,String videoTitle) {
		UserInformation UI=findUserData(userName);
		return UI.hasVideoLikedList(videoTitle);
	}

	public boolean isUserDisLikedTheVideo(String userName,String videoTitle) {
		UserInformation UI=findUserData(userName);
		return UI.hasVideoDisLikedList(videoTitle);
	}

	public boolean isFollowed(String user1,String user2) {
		SocialUser u= (SocialUser) findUser(user1);
		return u.hasFollowedUser(user2);
	}

	public boolean isFollower(String user1,String user2) { // silinebilinir
		SocialUser u= (SocialUser) findUser(user1);
		return u.hasFollowerUser(user2);
	}

	public boolean hasUser(String userName) {
		return (findUser(userName) != null);
	}

	public boolean hasVideo(String videoTitle) {
		return (findVideo(videoTitle) != null);
	}

	public void addSocialUser(String userName, String password) {
		if (!hasUser(userName)) {
			User newUser=new SocialUser(userName,password); // creates socialUser
			users.add(newUser);
			generateUserInformation(newUser);
		}
	}

	public void addVideo(String videoTitle,String contentInfo,int intendedAuidence) {
		if (!hasVideo(videoTitle)) {
			Video newVideo=new Video(videoTitle,contentInfo,intendedAuidence);
			videos.add(newVideo);
		}
	}
	
	public List<User> getUsers() {
		return users;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public User findUser(String userName) {
		for (User currentUser: users) {
			if (currentUser.getName().equals(userName)) {
				return currentUser;
			}
		}
		return null;
	}

	public Video findVideo(String videoTitle) {
		for (Video currentVideo: videos) {
			if (currentVideo.getTitle().equals(videoTitle)) {
				return currentVideo;
			}

		}
		return null;
	}

	public Map<String, UserInformation> getUsersData() {
		return usersData;
	}

	private UserInformation findUserData(String userName) {
		return usersData.get(userName);

	}

	public void addVideoToUsersWatchList(String userName,String videoTitle) {
		Video v=findVideo(videoTitle);
		UserInformation userInformation=findUserData(userName);
		userInformation.addVideoToWatchlist(v);
		notifyObservers();
	}

	public void  removeVideoFromUsersWatchList(String userName,String videoTitle) {
		Video v=findVideo(videoTitle);
		UserInformation userInformation=findUserData(userName);
		userInformation.removeVideoFromWatchList(v);
		notifyObservers();
	}

	public void likeOperation(String  userName,String videoTitle) {
		Video v=findVideo(videoTitle);
		User u=findUser(userName);
		v.likedBy(u);
		UserInformation userInformation=findUserData(userName);
		userInformation.addVideoToLikesList(v);
		notifyObservers();

	}
	public void disLikeOperation(String userName,String videoTitle) {
		Video v=findVideo(videoTitle);
		User u=findUser(userName);
		v.dislikedBy(u);
		UserInformation userInformation=findUserData(userName);
		userInformation.addVideoToDisLikesList(v);
		notifyObservers();

	}

	public void commentOperation(String userName,String videoTitle, String text) {
		Video v=findVideo(videoTitle);
		User u=findUser(userName);
		v.addComment(u,text);
		notifyObservers();
	}

	public List<User> getFollowersOfUser(String userName) {
		SocialUser u= (SocialUser) findUser(userName);
			return u.getFollowers();

	}

	public List<User> getFollowingsOfUser (String userName) {
		SocialUser u= (SocialUser) findUser(userName);
		return u.getFollowings();
	}

	public List<Video> getWatchList(String userName) {
		UserInformation uI=findUserData(userName);
		return uI.getWatchList();

	}

	

	public void followOperation(String userName1,String userName2) { // u1 follows u2
		SocialUser u1= (SocialUser) findUser(userName1);
		SocialUser u2= (SocialUser) findUser(userName2);
		u1.follow(u2);
		u2.addFollower(u1);
		notifyObservers();
	}
	public void unFollowOperation(String userName1,String userName2)  { // u1 unfollows u2
		SocialUser u1= (SocialUser) findUser(userName1);
		SocialUser u2= (SocialUser) findUser(userName2);
		u1.unfollow(u2);
		u2.removeFollower(u1);
		notifyObservers();
	}

	public List<String>  getUsersName(List<User> usersList) { 
		List<String> users=new ArrayList<String>();
		for (User currentUser: usersList) {
				users.add(currentUser.getName());
		}
		return users;
	}

	public List<String> getVideosTitle(List<Video> videosList) {
		List<String> videos=new ArrayList<String>();
		for (Video currentVideo: videosList) {
				videos.add(currentVideo.getTitle());
		}
		return videos;	
	}

	public Map<String,List<String>> getVideosAndComments() {
		Map<String,List<String>> videosComments=new HashMap<String,List<String>>();
		for (Video currentVideo: videos) {
			videosComments.put(currentVideo.getTitle(), currentVideo.getCommentContents());
		}
		return videosComments;

	}

	
}
