package org.processmining.streambasedeventstorage.algorithms;

import org.processmining.eventstream.core.interfaces.XSEvent;
import org.processmining.streambasedeventstorage.algorithms.abstr.AbstractXSEventStoreReservoirSamplingImpl;
import org.processmining.streambasedeventstorage.parameters.XSEventStoreReservoirEventLevelSamplingParametersImpl;

public class XSEventStoreReservoirEventLevelSamplingImpl extends
		AbstractXSEventStoreReservoirSamplingImpl<XSEvent, XSEventStoreReservoirEventLevelSamplingParametersImpl> {

	int pckts = 0;

	public XSEventStoreReservoirEventLevelSamplingImpl(
			XSEventStoreReservoirEventLevelSamplingParametersImpl parameters) {
		super(parameters);
	}

	protected void handleNextPacket(XSEvent packet) {
		pckts++;
		getInFlux().clear();
		getOutFlux().clear();
		String caseId = packet.get(getParameters().getCaseIdentifier()).toString();
		if (pckts > getParameters().getSize()) {
			int n = getRandom().nextInt(pckts);
			if (n <= getParameters().getSize()) {
				int i = getRandom().nextInt(getParameters().getSize());
				XSEvent remEvent = getReservoir().get(i);
				getOutFlux().add(remEvent);
				getWindow().remove(remEvent);
				removeEventFromTrace(remEvent, remEvent.get(getParameters().getCaseIdentifier()).toString());
				getWindow().add(packet);
				getReservoir().set(i, packet);
				getInFlux().add(packet);
				addEventToTrace(packet, caseId);
			}
		} else {
			getInFlux().add(packet);
			getReservoir().add(packet);
			getWindow().add(packet);
			addEventToTrace(packet, caseId);
		}
	}

}
