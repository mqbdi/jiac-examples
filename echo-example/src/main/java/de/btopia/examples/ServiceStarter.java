package de.btopia.examples;

import de.dailab.jiactng.agentcore.SimpleAgentNode;

public class ServiceStarter {
	private ServiceStarter() {/* hide me */
	}

	public static void main(String[] args) {
		SimpleAgentNode.main(new String[] { "classpath:service-ping-pong-agents.xml" });
	}
}
