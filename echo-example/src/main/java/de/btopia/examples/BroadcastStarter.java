package de.btopia.examples;

import de.dailab.jiactng.agentcore.SimpleAgentNode;

public class BroadcastStarter {
	private BroadcastStarter() {/* hide me */
	}

	public static void main(String[] args) {
		SimpleAgentNode.main(new String[] { "classpath:broadcast-ping-pong-agents.xml" });
	}
}
