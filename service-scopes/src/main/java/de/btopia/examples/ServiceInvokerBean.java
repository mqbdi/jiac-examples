package de.btopia.examples;

import java.io.Serializable;
import java.util.Arrays;

import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.ActionResult;
import de.dailab.jiactng.agentcore.environment.ResultReceiver;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;

public final class ServiceInvokerBean extends AbstractAgentBean implements ResultReceiver {
	
	@Override
	public void execute() {
		
		IActionDescription agent = this.thisAgent.searchAction(new Action(ServiceProviderBean.ACTION_ECHO_AGENT));
		if (agent != null) {
			this.invoke(agent, new Serializable[] {}, this);
		}
		else {
			log.warn("could not found action: " + ServiceProviderBean.ACTION_ECHO_AGENT);
		}
		
		IActionDescription node = this.thisAgent.searchAction(new Action(ServiceProviderBean.ACTION_ECHO_NODE));
		if (node != null) {
			this.invoke(node, new Serializable[] {}, this);
		}
		else {
			log.warn("could not found action: " + ServiceProviderBean.ACTION_ECHO_NODE);
		}
		
		IActionDescription global = this.thisAgent.searchAction(new Action(ServiceProviderBean.ACTION_ECHO_GLOBAL));
		if (global != null) {
			this.invoke(global, new Serializable[] {}, this);
		}
		else {
			log.warn("could not found action: " + ServiceProviderBean.ACTION_ECHO_GLOBAL);
		}
		
		IActionDescription webservice = this.thisAgent.searchAction(new Action(ServiceProviderBean.ACTION_ECHO_WEBSERVICE));
		if (webservice != null) {
			this.invoke(webservice, new Serializable[] {}, this);
		}
		else {
			log.warn("could not found action: " + ServiceProviderBean.ACTION_ECHO_WEBSERVICE);
		}
		
	}
	
	@Override
	public void receiveResult(final ActionResult result) {
		String name = result.getAction().getName();
		/*
		 * The action ServiceProviderBean.ACTION_EXCEPTION should be return an exception, the results are empty. The
		 * action ServiceProviderBean.ACTION_RANDOM returns a result, the failure field is empty.
		 */
		this.log.info("action '" + name + "'; failure: " + String.valueOf(result.getFailure()) + "; result: " + Arrays.toString(result.getResults()));
	}
	
}
