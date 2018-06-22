package org.processmining.streambasedeventstorage.algorithms.abstr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.processmining.streambasedeventstorage.parameters.XSEventStoreReservoirEventLevelSamplingParametersImpl;

public abstract class AbstractXSEventStoreReservoirSamplingImpl<T1, T2 extends XSEventStoreReservoirEventLevelSamplingParametersImpl>
		extends AbstractXSEventStore<T2> {

	private final List<T1> reservoir = new ArrayList<>();
	private final Random r;

	public AbstractXSEventStoreReservoirSamplingImpl(final T2 parameters) {
		super("reservoir", parameters);
		r = parameters.isSeeded() ? new Random(parameters.getSeed()) : new Random();
	}

	public List<T1> getReservoir() {
		return reservoir;
	}

	public Random getRandom() {
		return r;
	}

}
