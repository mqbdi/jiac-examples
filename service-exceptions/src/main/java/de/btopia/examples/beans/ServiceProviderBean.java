package de.btopia.examples.beans;

import java.util.Random;

import de.dailab.jiactng.agentcore.action.AbstractMethodExposingBean;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;

public final class ServiceProviderBean extends AbstractMethodExposingBean {
	
	public static final String ACTION_EXCEPTION = "ServiceProviderBean.ExceptionService";
	public static final String ACTION_RANDOM = "ServiceProviderBean.RandomService";
	
	private final Random random = new Random(System.nanoTime());
	
	@Expose(name = ACTION_EXCEPTION, scope = ActionScope.NODE)
	public Integer resultIsException() throws Exception {
		/*
		 * Throwing an exception is completely legal, but produces an ugly stack trace that is hard to debug.
		 * A better way, use informative messages or do not use @Expose annotations. Use the traditional way extending the
		 * AbstractAgentBean and implement an IEffector.
		 */
		throw new Exception("ich habe keine Lust zu antworten");
	}
	
	@Expose(name = ACTION_RANDOM, scope = ActionScope.NODE)
	public Integer resultRandomNumber() throws Exception {
		/*
		 * Auto-boxing is possible but error prone.
		 */
		/*
		 * parameter and results should be at least serializable!
		 */
		return Integer.valueOf(this.random.nextInt());
	}
	
}
