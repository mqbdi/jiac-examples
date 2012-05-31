package de.btopia.examples;

import de.dailab.jiactng.agentcore.SimpleAgentNode;

public class UnicastStarter {
	private UnicastStarter() {/* hide me */
	}
	
	public static void main(String[] args) {
		SimpleAgentNode.main(new String[] { "classpath:unicast-ping-pong-agents.xml" });
	}
}
