package de.btopia.examples.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.btopia.examples.ontology.IPing;
import de.btopia.examples.ontology.Ping;
import de.btopia.examples.ontology.Pong;
import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.ActionResult;
import de.dailab.jiactng.agentcore.action.DoAction;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;
import de.dailab.jiactng.agentcore.comm.CommunicationAddressFactory;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.IGroupAddress;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.environment.IEffector;
import de.dailab.jiactng.agentcore.environment.ResultReceiver;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;

/**
 * This agent bean does nothing. It's just hanging around, register for
 * communication addresses, publishing services, but it does never send a
 * response.
 * 
 * @author mib
 * 
 */
public class MockAgentBean extends AbstractAgentBean implements IEffector, ResultReceiver, IPing {
	/**
	 * The common group address derived by the common identifier.
	 */
	private IGroupAddress group = null;
	
	@Override
	public void doStart() {
		/*
		 * Join this group, but never answer. 
		 */
		Action joinGroup = this.retrieveAction(ICommunicationBean.ACTION_JOIN_GROUP);
		this.group = CommunicationAddressFactory.createGroupAddress(IPing.COMMON_ADDRESS);
		this.invoke(joinGroup, new Serializable[] { this.group });
	}
	
	@Override
	public void execute() {
		/*
		 * Remove useless messages. 
		 */
		memory.removeAll(new JiacMessage(new Pong()));
		memory.removeAll(new JiacMessage(new Ping()));
	}

	@Override
	public void receiveResult(ActionResult result) {
		/*
		 * This mock entities does not really need a ResultReceiver. 
		 */
	}

	@Override
	public List<? extends IActionDescription> getActions() {
		List<Action> ret = new ArrayList<Action>();
		Action echo = new Action(IPing.ACTION_PING, this, new Class[]{Ping.class}, new Class[]{Pong.class});
		echo.setScope(ActionScope.NODE);
		ret.add(echo);
		return ret;
	}

	@Override
	public void doAction(DoAction doAction) throws Exception {
		log.info(doAction.getOwner() + " is screwed by me. I do not response this service invocation.");
	}

}
