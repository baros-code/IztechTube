package Business;

import DomainLayer.SocialUser;
import DomainLayer.User;
import DomainLayer.Video;
import DomainLayer.VideoContentPlatform;
import IO.JSONWriter;
import IO.XMLWriter;
import View.LoginView;

import java.util.ArrayList;
import java.util.List;


public class ExampleCase {

	public static void start() {

		List<Video> allVideos = new ArrayList<Video>();
		List<User> allUsers = new ArrayList<User>();
		
		List<User> followings = new ArrayList<>();
		List<User> followers = new ArrayList<>();
		
				
		User user1 = new SocialUser("user1", "111");
		User user2 = new SocialUser("user2","111");
		User user3 = new SocialUser("user3","111");

		User user6 = new SocialUser("user6","111");
		User user7 = new SocialUser("user7", "111");
		User user8 = new SocialUser("user8","111");
		User user9 = new SocialUser("user9","111");
		User admin = new SocialUser("admin","admin");
		

		followings.add(user7);
		followings.add(user8);
		followings.add(user9);
		
		followers.add(user1);
		followers.add(user2);
		followers.add(user3);


		User user4 = new SocialUser("user4", "111",new ArrayList<User>(),new ArrayList<User>());
		User user5 = new SocialUser("user5","111",new ArrayList<User>(),new ArrayList<User>());
				
		User baran = new SocialUser("baran","baran",followings,followers);

		allUsers.add(user1);
		allUsers.add(user2);
		allUsers.add(user3);
		allUsers.add(user4);
		allUsers.add(user5);
		allUsers.add(user6);
		allUsers.add(user7);
		allUsers.add(user8);
		allUsers.add(user9);
		allUsers.add(baran);
		allUsers.add(admin);

		//VIDEO
		

		Video v1 = new Video("iyte_logo","iyte_logo",1);
		Video v2 = new Video("iyte_logo_2","iyte_logo_2",1);
		Video v3 = new Video("iyte_logo_3","iyte_logo_3",1);

		v1.addComment(user1,"1comment");
		v1.addComment(user2,"1comment");
		v1.addComment(user3,"1comment");
		v1.addComment(user4,"1comment");

		v2.addComment(user1,"2comment");
		v2.addComment(user2,"2comment");
		v2.addComment(user3,"2comment");

		v3.addComment(user1,"3comment");
		v3.addComment(user2,"3comment");

		allVideos.add(v1);
		allVideos.add(v2);
		allVideos.add(v3);

		VideoContentPlatform model = new VideoContentPlatform(allUsers,allVideos);
		

		model.getWatchList("user1").add(v1);
		model.getWatchList("user1").add(v2);
		
		model.getWatchList("user2").add(v1);
		model.getWatchList("user2").add(v2);
		
		model.getWatchList("user3").add(v1);
		model.getWatchList("user3").add(v2);
		
		model.getWatchList("user4").add(v1);
		model.getWatchList("user4").add(v2);
		
		model.getWatchList("user5").add(v1);
		model.getWatchList("user5").add(v2);


		JSONWriter jsonWriter = new JSONWriter(model);
		
		XMLWriter xmlWriter = new XMLWriter(model);

		LoginView loginView = new LoginView();
		Controller controller = new Controller(loginView,model);




	}
}
