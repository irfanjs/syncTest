package org.jenkins.ci.syncTest;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.model.ParameterValue;
import hudson.model.ParameterDefinition;

public class SyncOptionTest extends ParameterDefinition {

	public SyncOptionTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParameterValue createValue(StaplerRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
