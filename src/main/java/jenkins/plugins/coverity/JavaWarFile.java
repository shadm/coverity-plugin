/*******************************************************************************
 * Copyright (c) 2016 Synopsys, Inc
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Synopsys, Inc - initial implementation and documentation
 *******************************************************************************/
package jenkins.plugins.coverity;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import org.kohsuke.stapler.DataBoundConstructor;
import hudson.Util;
import hudson.model.Descriptor;

public class JavaWarFile extends AbstractDescribableImpl<JavaWarFile> {

    public String warFile;

    public String getWarFile() {
        return warFile;
    }

    public void setWarFile(String filePath) {
        this.warFile = filePath;
    }

    @DataBoundConstructor
    public JavaWarFile(String warFile){
        this.warFile=Util.fixEmpty(warFile);
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<JavaWarFile> {
        @Override
        public String getDisplayName() { return ""; }
    }
}
