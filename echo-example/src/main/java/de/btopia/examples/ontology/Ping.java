package de.btopia.examples.ontology;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Ping implements IFact {

	static final long serialVersionUID = 5266214592052421164L;
	public final String sender;

	public Ping() {
		this(null);
	}

	public Ping(String agent) {
		this.sender = agent;
	}
	
	@Override
	public String toString() {
		return "Ping(sender="+sender+")";
	}

}
