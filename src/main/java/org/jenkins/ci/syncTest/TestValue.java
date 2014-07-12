package org.jenkins.ci.syncTest;

import java.util.Map;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import hudson.model.BuildListener;
import hudson.model.ParameterValue;
import hudson.plugins.perforce.PerforceSCM;
import hudson.scm.SCM;

public class TestValue extends ParameterValue {
	
	
	 private String value;
	@DataBoundConstructor
	public TestValue(String name, String value) {
		super(name,value);
		
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	PerforceSCM scm;
	@Override
	 public void buildEnvVars(AbstractBuild<?,?> build, EnvVars env ) {
		
	FreeStyleProject project = (FreeStyleProject) build.getProject();
    	
    	// Get the SCM from the root project.
    	SCM scm = project.getScm();
	      
	       if (scm instanceof PerforceSCM)
	       {
	    	   ((PerforceSCM) scm).setForceSync(true);
	            ((PerforceSCM) scm).setDisableSyncOnly(false);
	            ((PerforceSCM) scm).setP4Label(null);
	       }
	    }
}
