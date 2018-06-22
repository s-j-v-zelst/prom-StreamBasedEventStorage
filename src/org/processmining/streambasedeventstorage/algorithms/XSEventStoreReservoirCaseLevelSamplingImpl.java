package org.processmining.streambasedeventstorage.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.processmining.eventstream.core.interfaces.XSEvent;
import org.processmining.streambasedeventstorage.algorithms.abstr.AbstractXSEventStoreReservoirSamplingImpl;
import org.processmining.streambasedeventstorage.parameters.XSEventStoreReservoirCaseLevelSamplingParametersImpl;

public class XSEventStoreReservoirCaseLevelSamplingImpl extends
		AbstractXSEventStoreReservoirSamplingImpl<List<XSEvent>, XSEventStoreReservoirCaseLevelSamplingParametersImpl> {

	private int pckts = 0;
	private List<String> caseShadow = new ArrayList<>();

	public XSEventStoreReservoirCaseLevelSamplingImpl(XSEventStoreReservoirCaseLevelSamplingParametersImpl parameters) {
		super(parameters);		
	}

	protected void handleNextPacket(XSEvent packet) {
		pckts++;
		getInFlux().clear();
		getOutFlux().clear();
		String caseId = packet.get(getParameters().getCaseIdentifier()).toString();
		int i = caseShadow.indexOf(caseId);
		if (i == -1) {
			if (getReservoir().size() < getParameters().getSize()) {
				i = caseShadow.size();
				caseShadow.add(caseId);
			} else {
				int n = getRandom().nextInt(pckts);
				if (n <= getParameters().getSize()) {
					i = getRandom().nextInt(getParameters().getSize());
				}
			}
		}
		if (i != -1) {
			placeEventOnIndex(packet, i);
		}
	}

	private void placeEventOnIndex(XSEvent e, int index) {
		getInFlux().add(e);
		String newCaseId = e.get(getParameters().getCaseIdentifier()).toString();
		getWindow().add(e);
		addEventToTrace(e, newCaseId);
		if (getReservoir().size() == index) {
			getReservoir().add(new ArrayList<XSEvent>());
			getReservoir().get(index).add(e);
		} else {
			if (caseShadow.get(index).equals(newCaseId)) {
				getReservoir().get(index).add(e);
				if (getReservoir().get(index).size() > getParameters().getMaxEntrySize()) {
					XSEvent del = getReservoir().get(index).remove(0);
					getOutFlux().add(del);
					getWindow().remove(del);
					removeEventFromTrace(del, newCaseId);
				}
			} else {
				String oldCaseId = caseShadow.get(index);
				Iterator<XSEvent> it = getReservoir().get(index).iterator();
				while (it.hasNext()) {
					XSEvent del = it.next();
					getOutFlux().add(del);
					getWindow().remove(del);
					removeEventFromTrace(del, oldCaseId);
					it.remove();
				}
				getReservoir().get(index).add(e);
				caseShadow.set(index, newCaseId);
			}
		}
	}

}
