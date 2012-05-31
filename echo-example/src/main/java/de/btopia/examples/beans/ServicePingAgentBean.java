package de.btopia.examples.beans;

import java.io.Serializable;

import de.btopia.examples.ontology.IPing;
import de.btopia.examples.ontology.Ping;
import de.btopia.examples.ontology.Pong;
import de.dailab.jiactng.agentcore.action.AbstractMethodExposingBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.ActionResult;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;
import de.dailab.jiactng.agentcore.environment.ResultReceiver;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;

public class ServicePingAgentBean extends AbstractMethodExposingBean implements IPing, ResultReceiver {
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
		 * First, we take a look, who was answering and write this to the logger.
		 */
		log.info("there are " + memory.removeAll(new Pong()).size() + " agents send a response to me.");
		/*
		 * Second, we search for all services with the identifier 'IPing.ACTION_PING' and call them.
		 */
		for (IActionDescription action : thisAgent.searchAllActions(new Action(IPing.ACTION_PING))) {
			if (action.getProviderDescription().equals(thisAgent.getAgentDescription())) {
				/*
				 * It's me. Skip!
				 */
				continue;
			}
			/*
			 * The service invocation got three parameter:
			 * - the service to call
			 * - the parameter for the service invocation
			 * - the agent that is the result receiver (optional)
			 */
			invoke(action, new Serializable[] { myPing }, this);
		}
	}
	
	@Expose(name = IPing.ACTION_PING, scope = ActionScope.NODE)
	/*
	 * The expose annotation at a public method of an AbstractMethodExposingBean forces the service publishing that is
	 * normally done by the IEffector.getActions() method. This is a quick way to publish Java-based JIACv services. It
	 * is not required to implement the IEffector interface twice and/or implement the getActions() method.
	 */
	public Pong echo(Ping ping) {
		/*
		 * Based on the Ping, create a Pong and return this. The ActionResult will be delivered to the invoking agent.
		 */
		Pong ret = new Pong(ping.sender, thisAgent.getAgentName());
		log.debug("got " + ping + ", returning: " + ret);
		return ret;
	}
	
	@Override
	public void receiveResult(final ActionResult result) {
		/*
		 * The method ResultReceiver.receiveResult(ActionResult) is been used to deliver the result of a service
		 * invocation we made in execute().
		 */
		String name = result.getAction().getName();
		if (IPing.ACTION_PING.equals(name)) {
			Pong pong = (Pong) result.getResults()[0];
			memory.write(pong);
		}
	}
	
}
