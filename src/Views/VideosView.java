package Views;

import DomainLayer.Observable;
import DomainLayer.Video;
import DomainLayer.VideoContentPlatform;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class VideosView extends PanelView {

    private JLabel imageLabel;	//to display video
    private CommentView commentView;
    
    /**
     * 
     LABEL GELECEK 
     */
    public VideosView(Map<String,List<String>> data, VideoContentPlatform model,String username) {  //key is videoTitle and List is belonged comments.
        super(VideosView.toArrayList(data.keySet()),model);    
        this.setLayout(null);
        commentView = new CommentView(new ArrayList<List<String>>(data.values()),model,username);
        commentView.setBounds(130,250,250,100);	//deneme
        add(commentView);
        construct();
    }


    public void construct() {
        
        JLabel videosLabel = new JLabel("Videos");
        videosLabel.setBounds(10,0,111,14);
        add(videosLabel);
    	
    	imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("resources/"+getVideosData().get(0)+".png"));
        imageLabel.setBounds(130, 20, 220, 220);
    	add(imageLabel);
    	

        createPane(10,20,100,325);
    }


    public void listSelection() {
        getjList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent  e) {
            	int newIndex = getjList().locationToIndex(e.getPoint());
                imageLabel.setIcon(new ImageIcon("resources/"+getVideosData().get(newIndex)+".png"));
            	setSelectedIndex(newIndex);

            	commentView.setSelectedIndex(newIndex);
                commentView.updateData(commentView.getVideoComments(newIndex));
            }
        });
    }

    
    public List<String> getVideosData(){
        return super.getData();
    }
    
    public void update(Observable model) {
        VideoContentPlatform tempModel = (VideoContentPlatform) model;
        List<Video> videos=tempModel.getVideos();
        List<String> videosAsString=tempModel.getVideosTitle(videos);
        updateData(videosAsString);
        resetTheIndex();
    }

    private static List<String> toArrayList(Set<String> set){
        List<String> arrList = new ArrayList<String>();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            arrList.add((String) iterator.next());
        }
        return arrList;
    }

}
