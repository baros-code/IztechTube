package IO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLReader {

    private static Map<String,String> userNameAndPasswords = new HashMap<>();
    private static Map<String,List<String>>  userNameAndFollowings = new HashMap<>();
    private static Map<String,List<String>>  userNameAndWatchLists =  new HashMap<>();


    public static void read(String filename) {

        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("User");
            //System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    List<String> tempFollowings = new ArrayList<>();
                    List<String> tempWatchList = new ArrayList<>();
                    String tempName = eElement.getElementsByTagName("Name").item(0).getTextContent();               //get userName
                    String tempPassword = eElement.getElementsByTagName("Password").item(0).getTextContent();       //get password
                    //System.out.println("Following: "+ tempFollowing);
                    //System.out.println("Follower: "+ tempFollower);
                    userNameAndPasswords.put(tempName,tempPassword);            //map userName with his/her password

                    int index = 0;
                    Node tempFollowing;
                    do{
                        tempFollowing = eElement.getElementsByTagName("Following").item(index);
                        if(tempFollowing != null)
                            tempFollowings.add(tempFollowing.getTextContent());     //add followings of that user
                        index++;
                    }while(tempFollowing != null);

                    userNameAndFollowings.put(tempName,tempFollowings);         //map userName with his/her followings

                    index = 0;
                    Node tempVideo;
                    do{
                        tempVideo = eElement.getElementsByTagName("WatchList").item(index);
                        if(tempVideo != null)
                            tempWatchList.add(tempVideo.getTextContent());     //add videos of that user
                        index++;
                    }while(tempVideo != null);

                    userNameAndWatchLists.put(tempName,tempWatchList);          //map userName with his/her videos

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> getUserNameAndPasswords() {
        return userNameAndPasswords;
    }
    public static Map<String,List<String>> getUserNameAndFollowings() {
        return userNameAndFollowings;
    }
    public static Map<String,List<String>> getUserNameAndWatchLists() {
        return userNameAndWatchLists;
    }



}
