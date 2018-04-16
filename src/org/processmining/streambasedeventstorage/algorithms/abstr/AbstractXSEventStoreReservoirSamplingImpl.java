package org.processmining.streambasedeventstorage.algorithms.abstr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.processmining.streambasedeventstorage.parameters.XSEventStoreReservoirEventLevelSamplingParametersImpl;

public abstract class AbstractXSEventStoreReservoirSamplingImpl<T1, T2 extends XSEventStoreReservoirEventLevelSamplingParametersImpl>
		extends AbstractXSEventStore {

	private final List<T1> reservoir = new ArrayList<>();
	private final Random r;
	private final T2 parameters;

	public AbstractXSEventStoreReservoirSamplingImpl(T2 parameters) {
		super("reservoir");
		this.parameters = parameters;
		r = parameters.isSeeded() ? new Random(parameters.getSeed()) : new Random();
	}

	public List<T1> getReservoir() {
		return reservoir;
	}

	public Random getRandom() {
		return r;
	}

	public T2 getParameters() {
		return parameters;
	}

}
