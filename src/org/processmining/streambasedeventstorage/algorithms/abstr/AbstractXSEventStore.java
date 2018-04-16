package org.processmining.streambasedeventstorage.algorithms.abstr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.processmining.eventstream.core.interfaces.XSEvent;
import org.processmining.stream.core.abstracts.AbstractXSReader;
import org.processmining.streambasedeventstorage.models.XSEventStore;

public abstract class AbstractXSEventStore extends AbstractXSReader<XSEvent, List<XSEvent>, Object>
		implements XSEventStore {

	private final List<XSEvent> influx = new ArrayList<>();
	private final List<XSEvent> outflux = new ArrayList<>();
	private final Map<String, List<XSEvent>> traces = new HashMap<>();
	private final List<XSEvent> window = new ArrayList<>();

	public AbstractXSEventStore(String name) {
		super(name, null);
	}

	protected void addEventToTrace(XSEvent e, String caseIdentifier) {
		if (!getTraces().containsKey(caseIdentifier)) {
			getTraces().put(caseIdentifier, new ArrayList<XSEvent>());
		}
		getTraces().get(caseIdentifier).add(e);
	}

	public Collection<String> describedCases() {
		return traces.keySet();
	}

	public List<XSEvent> getInFlux() {
		return influx;
	}

	public List<XSEvent> getOutFlux() {
		return outflux;
	}

	public Class<XSEvent> getTopic() {
		return XSEvent.class;
	}

	public Map<String, List<XSEvent>> getTraces() {
		return traces;
	}

	public List<XSEvent> getWindow() {
		return window;
	}

	public List<XSEvent> project(String caseIdentifier) {
		return traces.containsKey(caseIdentifier) ? traces.get(caseIdentifier) : new ArrayList<XSEvent>();
	}

	protected void removeEventFromTrace(XSEvent e, String caseIdentifier) {
		if (getTraces().containsKey(caseIdentifier)) {
			getTraces().get(caseIdentifier).remove(e);
			if (getTraces().get(caseIdentifier).isEmpty()) {
				getTraces().remove(caseIdentifier);
			}
		}
	}

}
