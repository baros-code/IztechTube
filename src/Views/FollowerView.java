package Views;

import DomainLayer.Observable;
import DomainLayer.VideoContentPlatform;
import DomainLayer.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class FollowerView extends PanelView {

    public FollowerView(List<String> data, VideoContentPlatform model,String username) {
        super(data,model,username);
        this.setLayout(null);
        construct();
    }


    public void construct() {
        createPane(0, 0, 180, 290);
    }
    public void listSelection() {
        getjList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                setSelectedIndex(getjList().locationToIndex(e.getPoint()));
            }
        });
    }

    public void update(Observable model) {
        VideoContentPlatform tempModel = (VideoContentPlatform) model;
        List<User> followersData =  tempModel.getFollowersOfUser(getLoginUserName());
        List<String> dataAsString=tempModel.getUsersName(followersData);
        updateData(dataAsString);
        resetTheIndex();
    }





}
