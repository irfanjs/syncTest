package org.jenkins.ci.syncTest;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Hudson;
import hudson.model.ParameterValue;
import org.kohsuke.stapler.DataBoundConstructor;
import hudson.plugins.perforce.*;
import hudson.plugins.perforce.PerforceSCM.*;
import hudson.scm.SCM;
import hudson.plugins.perforce.PerforceRepositoryBrowser;


public class ChoiceSyncParameterValue extends ParameterValue {

    private ChoiceSyncOptions.Option syncOption;

    @DataBoundConstructor
    public ChoiceSyncParameterValue(String name, ChoiceSyncOptions.Option syncOption) {
        super(name);
        this.syncOption = syncOption;
    }

    public String getSyncOption() {
        return syncOption.getOptionName();
    }

    public void setSyncOption(ChoiceSyncOptions.Option syncOption) {
        this.syncOption = syncOption;
    }

    @Override
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
    	// Get the root project
    	FreeStyleProject project = (FreeStyleProject) build.getProject();
    	
    	// Get the SCM from the root project.
    	SCM scm = project.getScm();
    	
    	// Verify that the SCM attached to the project is actually a PerforceSCM. 
    	if (scm instanceof PerforceSCM) {
    		
    		// Use perforceSCM variable to alter the current SCM.
    		PerforceSCM perforceSCM = (PerforceSCM) scm;
	    	
	    	syncOption.applyPerforceOptions(perforceSCM);
        }
    }
}
