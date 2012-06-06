package de.btopia.examples.beans;

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
		/*
		 * First we search for the 'exception service'.
		 */
		IActionDescription exception = this.thisAgent.searchAction(new Action(ServiceProviderBean.ACTION_EXCEPTION));
		if (exception != null) {
			/*
			 * The invoke method is overloaded. At least you need two parameters: service name (a.k.a. action name) and the
			 * parameters for this service. Another method got a third parameter: the ResultReceiver that got action
			 * results. At last a fourth parameter: invocation timeout, the time to wait within the service provider should
			 * return a result.
			 */
			/*
			 * The third parameter specified this agent bean to receive results. Results will be delivered to the
			 * 'receiveResult(ActionResult)' method.
			 */
			this.invoke(exception, new Serializable[] {}, this);
		}
		/*
		 * Second we search for the other service that is working.
		 */
		IActionDescription random = this.thisAgent.searchAction(new Action(ServiceProviderBean.ACTION_RANDOM));
		if (random != null) {
			this.invoke(random, new Serializable[] {}, this);
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
