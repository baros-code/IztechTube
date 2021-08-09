package IO;

import DomainLayer.*;
import Views.Observer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;


public class XMLWriter implements Observer {
	
	private Observable model;
    private DocumentBuilder dBuilder;
    private Document doc;


    public XMLWriter(Observable model) {
    	this.model = model;
        init();
        model.addObserver(this);
    }


    private void init() {
        try {
            dBuilder =  DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = dBuilder.newDocument();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public void update(Observable model) {
        VideoContentPlatform platform = (VideoContentPlatform) model;
        writeXML(platform.getUsers());
    }




    public  void writeXML(List<User> users) {

        Element rootElement = doc.createElementNS("", "Users");
        doc.appendChild(rootElement);

        if(users != null && users.isEmpty() == false) {
            for(User u: users) {
                SocialUser socialUser = (SocialUser) u;
                //append child element to root element
                rootElement.appendChild(getUser(doc,socialUser.getName(),socialUser.getPassword(),
                        socialUser.getFollowings(),socialUser.getFollowers(),((VideoContentPlatform)model).getWatchList(u.getName())));
            }

            generateFile();
        }
        init(); //Refresh
    }


    private  void generateFile() {

        try {

            //for output to file or console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            //StreamResult console = new StreamResult(System.out);	//konsol icin
            StreamResult file = new StreamResult(new File("src\\IO\\users.xml"));

            //write data
            //transformer.transform(source, console);	//konsola yazmak icin
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }












    private static Node getUser(Document doc, String name, String password, List<User> followingUsers,
                                List<User> followerUsers, List<Video>watchList) {
        Element user = doc.createElement("User");


        //set id attribute
        //employee.setAttribute("id", id);

        //create name element
        user.appendChild(getUserElements(doc, user, "Name", name));

        //create age element
        user.appendChild(getUserElements(doc, user, "Password", password));

        for(User u: followingUsers) {
            user.appendChild(getUserElements(doc, user, "Following", u.getName()));
        }
        for(User u: followerUsers) {
            user.appendChild(getUserElements(doc, user, "Follower", u.getName()));
        }
        for(Video v: watchList) {
            user.appendChild(getUserElements(doc, user, "WatchList", v.getTitle()));
        }


        return user;
    }

    private static String listToString(List<User> users) {
        String result = "";
        if(users != null) {
            for(User u: users)
                result =result + u.getName() +"\n";
        }
        return result;
    }

    //utility method to create text node
    private static Node getUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public  String getPasswordByName(String username) {
        String result = "";

        NodeList nList = doc.getElementsByTagName("User");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nNode;
                if(e.getElementsByTagName("Name").item(0).getTextContent().equals(username))
                    result = e.getElementsByTagName("Password").item(0).getTextContent();
            }
        }
        return result;


    }

}



