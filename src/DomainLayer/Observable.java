package DomainLayer;

import Views.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

	private List<Observer> observers = new ArrayList<Observer>();


	public void addObserver(Observer observer) {
	    observers.add(observer);
    }


    public void notifyObservers() {
        for(Observer currentObserver: observers ) {
                currentObserver.update(this);
        }
    }
}
