package IO;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.parser.JSONParser;

public class JSONReader {

	public static Map<String[][],ArrayList<String[][]>> data = new HashMap<String[][],ArrayList<String[][]>>();
	private static JSONParser parser = new JSONParser();
	
	/**
	 * Method that reads json file in given path and fills the Map object that contains
	 * video fields as two dimensional String[][] array in key and
	 * comment data ArrayList of two dimensional String[][] array in value of the Map object
	 * 
	 * @param path
	 */
	public static void read(String path)  {
		org.json.simple.JSONObject p;
		try {

			p = (org.json.simple.JSONObject) parser.parse(new FileReader(path));
			org.json.simple.JSONArray videos = (org.json.simple.JSONArray) p.get("Videos");
			
			Iterator<Object> videoIterator = videos.iterator();
			int videoCount = 0;
			
			String[][] videosArray = new String[videos.size()][6];
			ArrayList<String[][]> commentsList = new ArrayList<String[][]>();
			//reading videos
			while(videoIterator.hasNext()) {
				int commentCount = 0;
				org.json.simple.JSONObject vid = (org.json.simple.JSONObject) videoIterator.next();
				String title = (String) vid.get("Title");
				String contentInfo = (String) vid.get("ContentInfo");
				String intendedAudience = vid.get("IntendedAudience").toString();
				String UploadDate = (String) vid.get("UploadDate");
				String likes = vid.get("Likes").toString();
				String dislikes =vid.get("Dislikes").toString();

				videosArray[videoCount][0] = title;
				videosArray[videoCount][1] = contentInfo;
				videosArray[videoCount][2] = intendedAudience;
				videosArray[videoCount][3] = UploadDate;
				videosArray[videoCount][4] = likes;
				videosArray[videoCount][5] = dislikes;
				videoCount++;
				
				org.json.simple.JSONArray c = (org.json.simple.JSONArray) vid.get("Comments");
				Iterator<Object> commentIterator = c.iterator();
				String[][] commentsArray = new String[c.size()][2];
				//reading comments
				while(commentIterator.hasNext()) {
					org.json.simple.JSONObject currentCommentObject = (org.json.simple.JSONObject) commentIterator.next();
					String userName = (String) currentCommentObject.get("UserName");
					String commentContent = (String) currentCommentObject.get("CommentContent");
					
					commentsArray[commentCount][0] = userName;
					commentsArray[commentCount][1] = commentContent;
					commentCount++;			
				}
				commentsList.add(commentsArray);
			}
			data.put(videosArray, commentsList);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}
