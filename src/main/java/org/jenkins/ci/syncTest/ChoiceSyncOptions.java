
package org.jenkins.ci.syncTest;

import java.io.IOException;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Exported;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.Describable;
import hudson.model.ParameterValue;
import hudson.model.AbstractBuild;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.ParameterDefinition;
import hudson.plugins.perforce.PerforceSCM;
import hudson.util.XStream2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChoiceSyncOptions extends ParameterDefinition {

    public static final String NORMAL_SYNC = "Normal Sync";
    public static final String DISABLE_SYNC = "Disable Sync";
    public static final String FORCE_SYNC = "Force Sync";
    public static final String CHANGE_SYNC = "Sync based on Change-list";
    public static final String LBL_SYNC = "Sync based on Label";
    private String defaultValue = "Normal";
    
    //public String label;
    
    @DataBoundConstructor
    public ChoiceSyncOptions(String name) {
        super("sync-parameter");        
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject json) {
            // stapler-class in form data tells Stapler which Fruit subclass to use
        Option option = new HeadRevesion();
        BuildListener listener = null;
        listener.getLogger().println("valus is : " + option.getOptionName() );
        
        try {
            option = req.bindJSON(Option.class, req.getSubmittedForm().getJSONObject("option"));
            setSelectedOption(option);
        } catch (ServletException ex) {
            Logger.getLogger(ChoiceSyncOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ChoiceSyncParameterValue("sync-parameter", option);
    }
    
    //public String getTxtbx() {
//                return label;
            
  //  }
    
    public String[] getSyncOptions(){
        return new String[]{NORMAL_SYNC, DISABLE_SYNC, FORCE_SYNC,CHANGE_SYNC,LBL_SYNC};
    }
    
    public DescriptorExtensionList<Option,Descriptor<Option>> getOptionDescriptors() {
        return Hudson.getInstance().<Option,Descriptor<Option>>getDescriptorList(Option.class);
    }
    
    @Override
    public ParameterValue createValue(StaplerRequest sr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {

        @Override
        public String getDisplayName() {
            return "Enable Perforce sync options at runtime";
        }
    }
    
    @Override
    public ParameterValue getDefaultParameterValue() {
            String defaultValue = "Inactive";
            return new ChoiceSyncParameterValue(defaultValue, new Inactive());
           
    }
    
    private Option selectedOption = null;
    
    protected boolean normal_def = true;
        
        public boolean isDefault() {
            return normal_def;
        }
    public String getDefaultValue(){
       // return new Normal();
    	
    	return "Normal";
    }
    
    
    public Option getSelectedOption() {
        return selectedOption;
    }
    
    public void setSelectedOption(Option selectedOption) {
        this.selectedOption = selectedOption;
    }
    
    public static abstract class Option implements ExtensionPoint, Describable<Option> {
        protected String name;
        protected Option(String name) { this.name = name; }
        
        public Descriptor<Option> getDescriptor() {
            return Hudson.getInstance().getDescriptor(getClass());
        }
        
        public String getOptionName(){
        	return this.name;
        }
        
        public abstract void applyPerforceOptions(PerforceSCM scm);
    }

    public static class OptionDescriptor extends Descriptor<Option> {
        public OptionDescriptor(Class<? extends Option> clazz) {
            super(clazz);
        }
        public String getDisplayName() {
            return clazz.getSimpleName();
        }
    }

    public static class HeadRevesion extends Option {
        @DataBoundConstructor public HeadRevesion() {
            super("HeadRevesion");
          //  normal_def = true;
        }
       
        @Extension public static final OptionDescriptor D = new OptionDescriptor(HeadRevesion.class);

        @Override
        public void applyPerforceOptions(PerforceSCM scm) {
            scm.setForceSync(false);
            scm.setDisableSyncOnly(false);
            scm.setP4Label(null);
           // normal_def = true;
            
          
        }
    }
    
    public static class Inactive extends Option {
        @DataBoundConstructor public Inactive() {
            super("Inactive");
        }
        @Extension public static final OptionDescriptor D = new OptionDescriptor(Inactive.class);

        @Override
        public void applyPerforceOptions(PerforceSCM scm) {
            scm.setForceSync(false);
            scm.setDisableSyncOnly(true);
            scm.setP4Label(null);
        }
    }

    public static class Power extends Option {
     //   private String label;
        @DataBoundConstructor public Power() {
            super("Power");
            //this.label = label;
        }
      //  public String getLabel() {
       //     return label;
       // }
        @Extension public static final OptionDescriptor D = new OptionDescriptor(Power.class);

        @Override
        public void applyPerforceOptions(PerforceSCM scm) {
            scm.setForceSync(true);
            scm.setDisableSyncOnly(false);
            scm.setP4Label(null);
        }
    }
    
   // public static class ChangeList extends Option {
    //    private String label;
    //    @DataBoundConstructor public ChangeList(String label) {
     //       super("ChangeList");
     //       this.label = label;
     //   }
     //   public String getLabel() {
     //       return label;
     //   }
     //   @Extension public static final OptionDescriptor D = new OptionDescriptor(ChangeList.class);

      //  @Override
     //   public void applyPerforceOptions(PerforceSCM scm) {
     //       scm.setForceSync(true);
     //       scm.setDisableSyncOnly(false);
     //       scm.setP4Label(label);
    //    }
   // }
    
    public static class Label extends Option {
        private String label = null;
        @DataBoundConstructor public Label(String label) {
            super("Label");
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
        @Extension public static final OptionDescriptor D = new OptionDescriptor(Label.class);

        @Override
        public void applyPerforceOptions(PerforceSCM scm) {
            scm.setForceSync(false);
            scm.setDisableSyncOnly(false);
            scm.setP4Label(label);
                 
        }
       
    }
    
}