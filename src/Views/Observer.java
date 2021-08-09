package Views;

import DomainLayer.Observable;

public interface Observer {

    public void update(Observable observable);

    //public void update(EnhancedObservable observable,Object arg);     


}



