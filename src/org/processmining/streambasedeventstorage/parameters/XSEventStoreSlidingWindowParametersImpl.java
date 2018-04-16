package org.processmining.streambasedeventstorage.parameters;

import org.processmining.eventstream.readers.abstr.XSEventReaderParameters;

public class XSEventStoreSlidingWindowParametersImpl extends XSEventReaderParameters {

	private int size = 1000;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
