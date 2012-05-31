package de.btopia.examples.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.DoAction;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;
import de.dailab.jiactng.agentcore.environment.IEffector;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;

public class ResponseAgentBean extends AbstractAgentBean implements IEffector {

	/**
	 * This action got one parameter and returns three result values.
	 */
	public static final String ACTION_MULTIPLE_RESULTS = "ResponseAgentBean.MultipleResults";

	private int invocationCounter = 0;

	private void doMultipleResults(DoAction doAction) {
		/*
		 * A service invocation (DoAction) contains an array of parameter. The
		 * number and types of parameter should be identical to the published
		 * Action.
		 */
		Integer arg0 = (Integer) doAction.getParams()[0];
		if (arg0 > 0) {
			Double sqrt = Math.sqrt(arg0.doubleValue());
			returnResult(doAction,
					new Serializable[] { Integer.valueOf(++invocationCounter),
							arg0, sqrt });
		} else {
			returnFailure(doAction, new String(
					"could not compute sqrt of negative numbers."));
		}
	}

	@Override
	public List<? extends IActionDescription> getActions() {
		/*
		 * The getActions() method will be used to publish the services of this
		 * agent bean. It returns a list of actions, i.e., a list of service
		 * signatures. An action specifies: (1.) the service name (String) (2.)
		 * the provider that should be called (IEffector) (3.) the parameter
		 * types (Class[]) (4.)the result types (Class[]) and (5.) since
		 * JIAC~5.1.0 the action scope that specifies the publishing range. This
		 * method will be called within the usual agents life cycle.
		 */
		List<Action> ret = new ArrayList<Action>();
		Action action = new Action(ACTION_MULTIPLE_RESULTS, this,
				new Class[] { Integer.class }, new Class[] { Integer.class,
						Integer.class, Double.class });
		action.setScope(ActionScope.NODE);
		ret.add(action);
		return ret;
	}

	@Override
	public void doAction(DoAction doAction) throws Exception {
		/*
		 * The doAction method will be called by the agents execution cycle on
		 * DoAction events. Usually, a DoAction will be delivered to an agent,
		 * if the agent publishes one or more services.
		 * 
		 * A DoAction contains the Action that was called, the parameter values
		 * for this invocation and a description of the invoking agent, if the
		 * agent is interested in the service results.
		 */
		String name = doAction.getAction().getName();
		if (ACTION_MULTIPLE_RESULTS.equals(name)) {
			/*
			 * We just delegate the invocation to a special method.
			 */
			this.doMultipleResults(doAction);
		} else {
			log.error("this service invocation could not executed by me.");
		}
	}

}
