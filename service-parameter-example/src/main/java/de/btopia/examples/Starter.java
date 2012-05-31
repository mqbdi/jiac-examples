package de.btopia.examples;

import de.dailab.jiactng.agentcore.SimpleAgentNode;

public final class Starter {

	private Starter() {
		/*
		 * hide me
		 */
	}

	public static void main(String[] args) {
		SimpleAgentNode
				.main(new String[] { "classpath:multiple-results-conf.xml" });
	}

}
