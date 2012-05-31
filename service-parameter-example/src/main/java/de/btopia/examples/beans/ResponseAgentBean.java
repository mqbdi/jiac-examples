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

	public static final String ACTION_MULTIPLE_RESULTS = "ResponseAgentBean.MultipleResults";

	private int invocationCounter = 0;

	private void doMultipleResults(DoAction doAction) {
		Integer arg0 = (Integer) doAction.getParams()[0];
		if (arg0 > 0) {
			Double sqrt = Math.sqrt(arg0.doubleValue());
			returnResult(doAction,
					new Serializable[] { 
						Integer.valueOf(++invocationCounter),sqrt }
			);
		} else {
			returnFailure(doAction, new String(
					"could not compute sqrt of negative numbers."));
		}
	}

	@Override
	public List<? extends IActionDescription> getActions() {
		List<Action> ret = new ArrayList<Action>();
		Action action = new Action(ACTION_MULTIPLE_RESULTS, this,
				new Class[] { Integer.class }, new Class[] { Integer.class,
						Double.class });
		action.setScope(ActionScope.NODE);
		ret.add(action);
		return ret;
	}

	@Override
	public void doAction(DoAction doAction) throws Exception {
		String name = doAction.getAction().getName();
		if (ACTION_MULTIPLE_RESULTS.equals(name)) {
			this.doMultipleResults(doAction);
		} else {
			log.error("this service invocation could not executed by me.");
		}
	}

}
