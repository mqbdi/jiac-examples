package de.btopia.examples.ontology;

public interface IPing {

	/**
	 * This action got a parameter {@link Ping} and returns a result value of
	 * {@link Pong}.
	 */
	String ACTION_PING = "IPing.Ping";

	/**
	 * This is the identifier for a common communication address for agents.
	 */
	String COMMON_ADDRESS = "IPing.Group";

}
