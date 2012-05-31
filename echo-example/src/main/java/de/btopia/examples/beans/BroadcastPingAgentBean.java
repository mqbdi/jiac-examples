package de.btopia.examples.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import de.btopia.examples.ontology.IPing;
import de.btopia.examples.ontology.Ping;
import de.btopia.examples.ontology.Pong;
import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.comm.CommunicationAddressFactory;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.IGroupAddress;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;

public class BroadcastPingAgentBean extends AbstractAgentBean implements IPing {
	/**
	 * The common group address derived by the common identifier.
	 */
	private IGroupAddress group = null;
	/**
	 * The own Ping. Do not create this twice. Just create it and store it.
	 */
	private Ping myPing = null;
	/**
	 * A collection of agent names that returned an answer.
	 */
	private Set<String> respondingAgents = new HashSet<String>();
	
	@Override
	public void doStart() {
		myPing = new Ping(thisAgent.getAgentName());
		/*
		 * Every agent can communicate through the ICommunicationBean. There are
		 * several actions to support interagent communication. A group of agent
		 * join a common group address - or "channel".
		 */
		Action joinGroup = this.retrieveAction(ICommunicationBean.ACTION_JOIN_GROUP);
		this.group = CommunicationAddressFactory.createGroupAddress(IPing.COMMON_ADDRESS);
		this.invoke(joinGroup, new Serializable[] { this.group });
	}
	
	@Override
	public void execute() {
		/*
		 * Retrieve the send action of this agent to get access to the agent's
		 * communication.
		 */
		Action send = retrieveAction(ICommunicationBean.ACTION_SEND);
		/*
		 * Answer all Ping messages with a new Pong.
		 */
		for (JiacMessage msg : memory.removeAll(new JiacMessage(new Ping()))) {
			Ping ping = (Ping) msg.getPayload();
			if (thisAgent.getAgentName().equals(ping.sender)) {
				/*
				 * this is me, skip it.
				 */
				continue;
			}
			Pong pong = new Pong(ping.sender, thisAgent.getAgentName());
			invoke(send, new Serializable[] { new JiacMessage(pong), group });
		}
		
		/*
		 * Send my Ping to all other agents on this channel.
		 */
		invoke(send, new Serializable[] { new JiacMessage(myPing), group });
		/*
		 * Read all response messages that are a response (Pong) to my request
		 * (Ping).
		 */
		for (JiacMessage msg : memory.readAll(new JiacMessage(new Pong(thisAgent.getAgentName(), null)))) {
			Pong pong = (Pong) msg.getPayload();
			respondingAgents.add(pong.responder);
		}
		log.info("there are " + respondingAgents.size() + " agents send a response to me.");
		/*
		 * Clear all responses for the next execute interval.
		 */
		memory.removeAll(new JiacMessage(new Pong()));
	}
	
}
