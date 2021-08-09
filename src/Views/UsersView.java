package Views;

import DomainLayer.Observable;
import DomainLayer.VideoContentPlatform;
import DomainLayer.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UsersView extends PanelView {

    // COMMENTVIEW ILE ORTAK TEXT VIEW SEKLINDE OLABILIR
    public UsersView(List<String> data, VideoContentPlatform model,String username) {
        super(data,model,username);
        this.setLayout(null);
        construct();
    }

    public void construct() {
        createPane(10,20,180,290);
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
        List<User> users= tempModel.getUsers();
        List<String> usersAsString=tempModel.getUsersName(users);
        updateData(usersAsString);
        resetTheIndex();
    }

    

}
