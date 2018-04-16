package org.processmining.streambasedeventstorage.parameters;

public class XSEventStoreReservoirCaseLevelSamplingParametersImpl
		extends XSEventStoreReservoirEventLevelSamplingParametersImpl {

	private int maxEntrySize = 10;

	public int getMaxEntrySize() {
		return maxEntrySize;
	}

	public void setMaxEntrySize(int maxEntrySize) {
		this.maxEntrySize = maxEntrySize;
	}

}
