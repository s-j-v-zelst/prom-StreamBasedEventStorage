package org.processmining.streambasedeventstorage.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.processmining.eventstream.core.interfaces.XSEvent;
import org.processmining.eventstream.models.XSEventReader;
import org.processmining.streambasedeventstorage.algorithms.XSEventStoreReservoirCaseLevelSamplingImpl;
import org.processmining.streambasedeventstorage.algorithms.XSEventStoreReservoirEventLevelSamplingImpl;
import org.processmining.streambasedeventstorage.algorithms.XSEventStoreSlidingWindowImpl;
import org.processmining.streambasedeventstorage.parameters.XSEventStoreReservoirCaseLevelSamplingParametersImpl;
import org.processmining.streambasedeventstorage.parameters.XSEventStoreReservoirEventLevelSamplingParametersImpl;
import org.processmining.streambasedeventstorage.parameters.XSEventStoreSlidingWindowParametersImpl;

public interface XSEventStore extends XSEventReader<List<XSEvent>> {

	public class Factory {

		public static XSEventStore construct(Type type, Map<String, String> params) {
			switch (type) {
				case RESERVOIR_CASE_LEVEL :
					XSEventStoreReservoirCaseLevelSamplingParametersImpl pc = new XSEventStoreReservoirCaseLevelSamplingParametersImpl();
					pc.setSize(Integer.valueOf(params.get(KEY_SIZE)));
					pc.setMaxEntrySize(Integer.valueOf(params.get(KEY_MAX_ENTRY_SIZE)));
					pc.setSeed(Integer.valueOf(params.get(KEY_SEED)));
					return new XSEventStoreReservoirCaseLevelSamplingImpl(pc);
				case RESERVOIR_EVENT_LEVEL :
					XSEventStoreReservoirEventLevelSamplingParametersImpl pe = new XSEventStoreReservoirEventLevelSamplingParametersImpl();
					pe.setSize(Integer.valueOf(params.get(KEY_SIZE)));
					return new XSEventStoreReservoirEventLevelSamplingImpl(pe);
				case SLIDING_WINDOW :
					XSEventStoreSlidingWindowParametersImpl pw = new XSEventStoreSlidingWindowParametersImpl();
					pw.setSize(Integer.valueOf(params.get(KEY_SIZE)));
					return new XSEventStoreSlidingWindowImpl(pw);
			}
			return null;
		}

	}
	public enum Type {
		RESERVOIR_CASE_LEVEL(Arrays.asList(KEY_SIZE, KEY_MAX_ENTRY_SIZE, KEY_SEED)), RESERVOIR_EVENT_LEVEL(
				Arrays.asList(KEY_SIZE)), SLIDING_WINDOW(Arrays.asList(KEY_SIZE));

		private final List<String> parameters;

		private Type(List<String> parameters) {
			this.parameters = parameters;
		}

		public List<String> getParameters() {
			return parameters;
		}
	}
	static String KEY_MAX_ENTRY_SIZE = "max_entry_size";

	static String KEY_SEED = "seed";

	static String KEY_SIZE = "size";

	Collection<String> describedCases();

	List<XSEvent> getInFlux();

	List<XSEvent> getOutFlux();

	List<XSEvent> project(String caseIdentifier);

}
