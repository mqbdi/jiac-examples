package de.btopia.examples.beans;

import java.io.Serializable;

import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.ActionResult;
import de.dailab.jiactng.agentcore.environment.ResultReceiver;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;

public class InitiatorAgentBean extends AbstractAgentBean implements
		ResultReceiver {

	private int countdown = 11;

	@Override
	public void execute() {
		/*
		 * In this execute() method we search for one single action by using the
		 * JIAC's template mechanism. We just use the service name and invoke
		 * the service, if someone was found (not null).
		 */
		IActionDescription action = thisAgent.searchAction(new Action(
				ResponseAgentBean.ACTION_MULTIPLE_RESULTS));
		if (action != null) {
			invoke(action, new Serializable[] { Integer.valueOf(--countdown) },
					this);
		}
	}

	@Override
	public void receiveResult(ActionResult result) {
		/*
		 * The receiveResult() method is been called, if a service invocations
		 * returns. An ActionResult contains the original invoked Action and the
		 * result values of the executed service.
		 */
		String name = result.getAction().getName();
		if (ResponseAgentBean.ACTION_MULTIPLE_RESULTS.equals(name)) {
			Serializable[] res = result.getResults();
			Serializable failure = result.getFailure();
			if (failure != null) {
				log.warn("service invocation returned failure: " + failure);
			} else {
				Integer counter = (Integer) res[0];
				Integer param = (Integer) res[1];
				Double sqrt = (Double) res[2];
				log.info(ResponseAgentBean.ACTION_MULTIPLE_RESULTS
						+ " returned three results: "
						+ String.format("%d %d %2.3f", counter, param, sqrt));
			}
		}
	}

}
