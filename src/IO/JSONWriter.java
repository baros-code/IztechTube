package IO;


import DomainLayer.Observable;
import DomainLayer.Video;
import DomainLayer.VideoContentPlatform;
import Views.Observer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONWriter implements Observer{	//update doldurulacak

	private JSONObject obj;

	//The constructor
	public JSONWriter(Observable model) {
		model.addObserver(this);
		obj = new JSONObject();
	}

	private void writeJSONFile(JSONArray input) {
		obj.put("Videos",input);
		try {
			FileWriter fileWriter = new FileWriter("src\\IO\\videos.json");
			fileWriter.write(obj.toString());
			fileWriter.flush();
			fileWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private JSONObject createVideoObject(Video video) { // 
		JSONObject videoObject = new JSONObject();
		videoObject.put("Title",video.getTitle()); 
		videoObject.put("ContentInfo",video.getContentInfo());
		videoObject.put("IntendedAudience",video.getIntendedAudience());
		videoObject.put("UploadDate",video.displayDate());
		videoObject.put("Likes",video.getLikes());
		videoObject.put("Dislikes",video.getDislikes());
		
		JSONArray commentObject = new JSONArray();	
		String [][] commentsArray = video.getCommentContentsWithUserName();
		for(int i=0;i<commentsArray.length;i++) {
				JSONObject tempComment = new JSONObject();
				tempComment.put("UserName", commentsArray[i][0]);
				tempComment.put("CommentContent", commentsArray[i][1]);
				
				commentObject.put(tempComment);
		}
		videoObject.put("Comments", commentObject);

		return videoObject;
	}
	
	private JSONArray createVideosAsJson(List<Video> videos) {
		JSONArray videosObject = new JSONArray();
		for(Video vid: videos) {
				JSONObject tempVideo = createVideoObject(vid);
				videosObject.put(tempVideo);
		}
		return videosObject;
	}

	public void saveAsJSON(List<Video> videos) {
		JSONArray array = createVideosAsJson(videos);
		writeJSONFile(array);
	}
	@Override
	public void update(Observable observable) {
		VideoContentPlatform platform = (VideoContentPlatform) observable;
		saveAsJSON(platform.getVideos());
	}


}



