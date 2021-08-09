package Views;

import DomainLayer.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class WatchListView extends PanelView {

    public WatchListView(List<String> data, VideoContentPlatform model,String username) {
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
                setSelectedIndex(getjList().locationToIndex(e.getPoint()));
            }
        });
    }
    public List<String> getVideosData() {
        return getData();
    }
    
    public void update(Observable model) {
        VideoContentPlatform tempModel = (VideoContentPlatform) model;
        List<Video> watchList =  tempModel.getWatchList(getLoginUserName());
        List<String> watchListAsString=tempModel.getVideosTitle(watchList);
        updateData(watchListAsString);

    }
}
