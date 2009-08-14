package uk.ac.liv.csc.semanticweblab.modtool.events;

/**
 * The listener interface for receiving {@link OutputEvent}s. The class that is interested in processing an {@link OutputEvent} 
 * implements this interface.
 * 
 * @author pdoran
 *
 */
public interface OutputEventListener 
{
	/**
	 * Invoked when an {@link OutputEvent} occurs.
	 * @param event
	 */
    public void eventReceived( OutputEvent event );
}