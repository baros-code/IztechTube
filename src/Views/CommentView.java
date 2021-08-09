package Views;

import DomainLayer.Observable;
import DomainLayer.VideoContentPlatform;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CommentView extends PanelView{
    
    private List<List<String>> commentLists;
    // COMMENTVIEW ILE ORTAK TEXT VIEW SEKLINDE OLABILIR
    public CommentView(ArrayList<List<String>> data, VideoContentPlatform model,String username) {
        super(data.get(0), model,username); // first video comments to construct view3
        commentLists = data;    //all video comments
        this.setLayout(null);
        construct();
    }




    public List<String> getVideoComments(int index) {
        return commentLists.get(index);
    }


    public void construct() {

        createPane(0, 0, 219, 95);

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


    /*
    public void refreshData(List<String> newData) {
    	getjList().setListData(new Vector<String>(newData));
    	revalidate();	//belkim calisir
    }
    */
    /*
    public void update(EnhancedObservable model) {
        VideoContentPlatform tempModel = (VideoContentPlatform) model;
        List<User> users= tempModel.getUsers();
        List<String> usersAsString=tempModel.getUsersName(users);
        updateData(usersAsString,599, 105, 200, 249);
        
    }
    */



    public void update(Observable model) {
          VideoContentPlatform tempModel = (VideoContentPlatform) model;
        List<List<String>> data = new ArrayList<List<String>>(tempModel.getVideosAndComments().values());
        this.commentLists = data;

    }



/*	@Override
	public void update(EnhancedObservable model) {
		// TODO Auto-generated method stub
		
	}
*/

}
