package org.processmining.streambasedeventstorage.algorithms;

import org.processmining.eventstream.core.interfaces.XSEvent;
import org.processmining.streambasedeventstorage.algorithms.abstr.AbstractXSEventStore;
import org.processmining.streambasedeventstorage.parameters.XSEventStoreSlidingWindowParametersImpl;

public class XSEventStoreSlidingWindowImpl extends AbstractXSEventStore {

	private final XSEventStoreSlidingWindowParametersImpl parameters;

	public XSEventStoreSlidingWindowImpl(final XSEventStoreSlidingWindowParametersImpl parameters) {
		this("sliding_window", parameters);
	}

	public XSEventStoreSlidingWindowImpl(final String name, final XSEventStoreSlidingWindowParametersImpl parameters) {
		super(name);
		this.parameters = parameters;
	}

	protected void handleNextPacket(XSEvent packet) {
		if (getWindow().size() > parameters.getSize()) {
			getOutFlux().clear();
			while (getWindow().size() > parameters.getSize()) {
				XSEvent removed = getWindow().get(0);
				getWindow().remove(0);
				getOutFlux().add(removed);
				removeEventFromTrace(removed, removed.get(parameters.getCaseIdentifier()).toString());
			}
		}
		getWindow().add(packet);
		getInFlux().clear();
		getInFlux().add(packet);
		addEventToTrace(packet, packet.get(parameters.getCaseIdentifier()).toString());
	}

}
