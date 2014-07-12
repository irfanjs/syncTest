package org.jenkins.ci.syncTest;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.BuildListener;
import hudson.model.ParameterValue;
import hudson.model.Saveable;
import hudson.model.ParameterDefinition;
import hudson.plugins.perforce.PerforceSCM;

public class Test extends ParameterDefinition implements Saveable {

	private String defaultValue;
	
	BuildListener listener;
	
	@DataBoundConstructor
	public Test(String name, String defaultValue) {
		super(name);
		this.defaultValue = defaultValue;
		
	}
	
	public String getDefaultValue() {
        return defaultValue;
    }
	
	public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
	
	@Override
    public TestValue getDefaultParameterValue() {
		TestValue v = new TestValue(getName(), defaultValue);
        return v;
    }
	
	@Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public String getDisplayName() {
            return "Test String ";
        }
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		
		TestValue v = req.bindJSON(TestValue.class, jo);
		v.setValue(getDefaultValue());
		v.setDescription("this is test");
		try {
			
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return v;
		
	}

	@Override
	public ParameterValue createValue(StaplerRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
