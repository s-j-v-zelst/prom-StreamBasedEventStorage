package org.processmining.streambasedeventstorage.parameters;

import org.processmining.eventstream.readers.abstr.XSEventReaderParameters;

public class XSEventStoreReservoirEventLevelSamplingParametersImpl extends XSEventReaderParameters {

	private int size = 15;
	
	private int seed =  1337;
	
	private boolean isSeeded = false;

	public boolean isSeeded() {
		return isSeeded;
	}

	public void setSeeded(boolean isSeeded) {
		this.isSeeded = isSeeded;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
