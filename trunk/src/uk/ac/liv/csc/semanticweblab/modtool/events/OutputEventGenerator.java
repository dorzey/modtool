package uk.ac.liv.csc.semanticweblab.modtool.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to generate an {@link OutputEvent}. It also allows the
 * addition and removal of {@link OutputEventListener}s.
 * 
 * @author pdoran
 * 
 */
public class OutputEventGenerator {
	/**
	 * List of registered {@link OutputEventListener}.
	 */
	private List<OutputEventListener> _listeners = new ArrayList<OutputEventListener>();

	/**
	 * Called to generate an {@link OutputEvent}.
	 * 
	 * @param m
	 *            The message to be passed to the {@link OutputEvent}.
	 */
	public synchronized void outputMessage(Object... m) {
		// if there are no listeners, drop everything and save time
		if (this._listeners.size() > 0) {
			StringBuilder b = new StringBuilder();
			for (Object o : m) {
				b.append(o);
				b.append(" ");
			}
			OutputEvent outputEvent = new OutputEvent(this, b.toString());
			Iterator<OutputEventListener> listeners = this._listeners
					.iterator();
			while (listeners.hasNext()) {
				listeners.next().eventReceived(outputEvent);
			}
		}
	}

	/**
	 * Registers an {@link OutputEventListener} with this class.
	 * 
	 * @param l
	 *            the {@link OutputEventListener} to be registered.
	 */
	public void addOutputEventListener(OutputEventListener l) {
		this._listeners.add(l);
	}

	/**
	 * Removes an {@link OutputEventListener} from this class.
	 * 
	 * @param l
	 *            the {@link OutputEventListener} to be registered
	 */
	public synchronized void removeOutputEventListener(OutputEventListener l) {
		this._listeners.remove(l);
	}
}