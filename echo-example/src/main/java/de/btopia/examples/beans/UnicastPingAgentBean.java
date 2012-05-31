package de.btopia.examples.beans;

import java.io.Serializable;

import de.btopia.examples.ontology.Ping;
import de.btopia.examples.ontology.Pong;
import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.ontology.AgentDescription;
import de.dailab.jiactng.agentcore.ontology.IAgentDescription;

public class UnicastPingAgentBean extends AbstractAgentBean {
	
	/**
	 * The own Ping. Do not create this twice. Just create it and store it.
	 */
	private Ping myPing = null;
	
	@Override
	public void doStart() {
		myPing = new Ping(thisAgent.getAgentName());
	}
	
	@Override
	public void execute() {
		/*
		 * First, map all received answers [JiacMessage(Pong)] into facts [Pong].
		 */
		for (JiacMessage msg : memory.removeAll(new JiacMessage(new Pong(thisAgent.getAgentName(), null)))) {
			Pong pong = (Pong) msg.getPayload();
			memory.write(pong);
		}
		/*
		 * Second, print the number of Pongs we got already.
		 */
		log.info("there are " + memory.removeAll(new Pong()).size() + " agents send a response to me.");
		/*
		 * Retrieve the send action of this agent to get access to the agent's
		 * communication.
		 */
		Action send = retrieveAction(ICommunicationBean.ACTION_SEND);
		/*
		 * Third, search all agents and send them a Ping, except your own and except agents we got already an answer.  
		 */
		for (IAgentDescription otherAgent : this.thisAgent.searchAllAgents(new AgentDescription())) {
			if (otherAgent.equals(thisAgent.getAgentDescription())) {
				/*
				 * That's me. Skip!
				 */
				continue;
			}
			/*
			 * If we know the Draw of any agent, we got the Draw in memory. Thus, we don't need to send another request.
			 */
			Pong known = memory.read(new Pong(thisAgent.getAgentName(), otherAgent.getName()));
			if (known == null) {
				/*
				 * We don't know the Draw of this special agent. We ask him!
				 * JiacMessage got two parameter: first - the payload to send, second - an optional 'reply to' address. We
				 * need this field in this example. The send action got also two parameters: first - the JiacMessage, second
				 * - the 'send to' address.
				 */
				this.invoke(send, new Serializable[] { new JiacMessage(myPing, this.thisAgent.getAgentDescription().getMessageBoxAddress()), otherAgent.getMessageBoxAddress() });
			}
		}
		/*
		 * Fourth, if we got a Ping, response a Pong to the sender.
		 */
		for(JiacMessage msg : memory.removeAll(new JiacMessage(new Ping()))) {
			Ping ping = (Ping) msg.getPayload();
			Pong pong = new Pong(ping.sender, thisAgent.getAgentName());
			invoke(send, new Serializable[] {new JiacMessage(pong), msg.getSender()});
		}
	}
	
}
