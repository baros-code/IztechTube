package View;

import javax.swing.*;

import DomainLayer.VideoContentPlatform;
import Views.WatchListView;

import java.util.List;

public class FollowedUsersWatchList extends JFrame {
    
    private WatchListView watchListView;
        
    public FollowedUsersWatchList(List<String> data, VideoContentPlatform model, String username){

        JPanel pane = new JPanel();
        pane.setLayout(null);
        this.watchListView =new WatchListView(data,model,username);
        watchListView.setBounds(0, 0, 180, 290);
        pane.add(watchListView);
        this.setContentPane(pane);
        this.setTitle(username);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

    
}