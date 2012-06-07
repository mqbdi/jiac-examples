package de.btopia.examples;

import java.util.Random;

import de.dailab.jiactng.agentcore.action.AbstractMethodExposingBean;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;

/** 
 * This service provider provides different actions with different action scopes. With modified agent node
 * configurations you can demonstrate, that the actions are not visible for every agent. Thus, agents could only invoke
 * actions with a visible scope.
 */
public final class ServiceProviderBean extends AbstractMethodExposingBean {
	
	/**
	 * This action could only be invoked within the same agent.
	 */
	public static final String ACTION_ECHO_AGENT = "ServiceProviderBean.EchoAgent";
	/**
	 * This action could be invoked by any agent on the same node.
	 */
	public static final String ACTION_ECHO_NODE = "ServiceProviderBean.EchoNode";
	/**
	 * This action could be invoked by any agent within the same multi-agent platform or multi-agent application.
	 */
	public static final String ACTION_ECHO_GLOBAL = "ServiceProviderBean.EchoGlobal";
	/**
	 * This action will be published as W3C webservice and could be invoked by any agent that reaches the providing
	 * agent.
	 */
	public static final String ACTION_ECHO_WEBSERVICE = "ServiceProviderBean.EchoWebservice";
	
	private final Random random = new Random(System.nanoTime());
	
	@Expose(name = ACTION_ECHO_AGENT, scope = ActionScope.AGENT)
	public Integer echoAgent() {
		return Integer.valueOf(this.random.nextInt());
	}
	
	@Expose(name = ACTION_ECHO_NODE, scope = ActionScope.NODE)
	public Integer echoNode() {
		return Integer.valueOf(this.random.nextInt());
	}
	
	@Expose(name = ACTION_ECHO_GLOBAL, scope = ActionScope.GLOBAL)
	public Integer echoGlobal() {
		return Integer.valueOf(this.random.nextInt());
	}
	
	@Expose(name = ACTION_ECHO_WEBSERVICE, scope = ActionScope.WEBSERVICE)
	public Integer echoWebservice() {
		return Integer.valueOf(this.random.nextInt());
	}
	
}
