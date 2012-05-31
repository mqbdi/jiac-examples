package de.btopia.examples.ontology;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Pong implements IFact {
	
	static final long serialVersionUID = -5069077255554833222L;
	public final String sender;
	public final String responder;
	
	public Pong() {
		this(null, null);
	}
	
	public Pong(String sender, String responder) {
		this.sender = sender;
		this.responder = responder;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Pong)) {
			return false;
		}
		Pong other = (Pong) obj;
		return this.sender == other.sender && this.responder == other.responder;
	}
	
	@Override
	public int hashCode() {
		int s = sender != null ? sender.hashCode() : 3;
		int r = responder != null ? responder.hashCode() : 5;
		return s * 31 + r;
	}
	
	@Override
	public String toString() {
		return "Pong(sender=" + sender + ",responder=" + responder + ")";
	}
	
}
