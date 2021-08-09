package Views;

import DomainLayer.*;
import javax.swing.*;
import java.util.List;
import java.util.Vector;


/**
 * Abstract class that is inherited from all panel views
 * 
 */

public abstract class PanelView extends JPanel implements Observer {
    private int selectedIndex;
    private JList jList;
    private List<String> data;
    private String loginUserName;

    private JScrollPane scroll;


    /**
     * This constructor will be implemented by all sub views, all views will add theirself as observer of the model
     */
    public PanelView(List<String> data, VideoContentPlatform model) {
        this.data = data;
        model.addObserver(this);    //ADD OBSERVER TO THE OBSERVABLE 

    }

    public PanelView(List<String> data, VideoContentPlatform model, String loginUserName) {
        this.data = data;
        model.addObserver(this);
        this.loginUserName = loginUserName;
    }

    public abstract void construct();

    
    public String getLoginUserName() {
        return loginUserName;
    }
    

    public List<String> getData() {
        return data;
    }



    /**
     * This method will update view fields and view itself, will be implemented in the all sub views.
     * @parameter video content platform
     */
   public abstract void update(Observable model);




    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void resetTheIndex() {
        setSelectedIndex(-1);
    }


    public void updateData(List<String> newData) {
    	this.data = newData;
        jList.setListData(new Vector<String>(newData));
        //repaint();
        //revalidate();
    }

    public String getSelectedData() {       //controller bu fonksiyon ile hangi elementin se�ildi�ini anlayacak
    	return data.get(selectedIndex);
    }

    public JList getjList() {
        return jList;
    }


    /**
     * Creates a scroll pane with given attributes
     */
    public void createPane(int x, int y, int width, int height) {        
    	scroll = new JScrollPane();
        jList = new JList(new Vector<String>(data));
        scroll.setBounds(x,y,width,height);
        scroll.setViewportView(jList);
        add(scroll);
         listSelection();
         setSelectedIndex(-1); // initial index

    }

    /**
     * Abstract method that will be implemented by child classes.
     */
    public abstract void listSelection(); 


/*    public void listSelection() {
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO Auto-generated method stub
                setSelectedIndex(jList.getSelectedIndex());
            }
        });
    }
*/


}
