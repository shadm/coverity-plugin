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
import hudson.EnvVars;
import hudson.Util;
import org.kohsuke.stapler.DataBoundConstructor;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONException;
import net.sf.json.JSONSerializer;

public class InvocationAssistance {
    private final String buildArguments;
    private final String analyzeArguments;
    private final String commitArguments;
    private final String csharpAssemblies;
    private List<String> javaWarFilesNames;
    private final List<JavaWarFile> javaWarFiles;
    private final String csharpMsvscaOutputFiles;
    private final boolean csharpAutomaticAssemblies;
    private final boolean csharpMsvsca;
    private final String saOverride;
    private EnvVars envVars;

    private final boolean isUsingMisra;
    private final String misraConfigFile;
    private JSONObject misraMap;

    private final boolean isCompiledSrc;
    private final boolean isScriptSrc;

    private final boolean isUsingPostCovBuildCmd;
    private final String postCovBuildCmd;
    private JSONObject postCovBuildJSON;

    private final boolean isUsingPostCovAnalyzeCmd;
    private final String postCovAnalyzeCmd;
    private JSONObject postCovAnalyzeJSON;

    /**
     * Do not wrap any executables with these names with cov-build. Format is comma-separated list.
     */
    private final String covBuildBlacklist;

    /**
     * Absolute path to the intermediate directory that Coverity should use. Null to use the default.
     */
    private final String intermediateDir;

    public boolean getUseAdvancedParser() {
        return useAdvancedParser;
    }

    private final boolean useAdvancedParser;

    public InvocationAssistance(boolean isUsingPostCovBuildCmd, String postCovBuildCmd, boolean isUsingPostCovAnalyzeCmd, String postCovAnalyzeCmd, boolean isCompiledSrc, boolean isScriptSrc, String buildArguments, String analyzeArguments, String commitArguments, String intermediateDir, boolean isUsingMisra, String misraConfigFile, String csharpAssemblies, List<String> javaWarFilesNames, String csharpMsvscaOutputFiles, boolean csharpAutomaticAssemblies, boolean csharpMsvsca, String saOverride, String covBuildBlacklist, List<JavaWarFile> javaWarFiles, boolean useAdvancedParser) {
        this.useAdvancedParser = useAdvancedParser;
        this.isUsingPostCovBuildCmd = isUsingPostCovBuildCmd;
        this.postCovBuildCmd = postCovBuildCmd;
        this.isUsingPostCovAnalyzeCmd = isUsingPostCovAnalyzeCmd;
        this.postCovAnalyzeCmd = postCovAnalyzeCmd;
        this.isCompiledSrc = isCompiledSrc;
        this.isScriptSrc = isScriptSrc;
        this.isUsingMisra = isUsingMisra;
        this.misraConfigFile = misraConfigFile;
        this.javaWarFiles = javaWarFiles;
        this.intermediateDir = Util.fixEmpty(intermediateDir);
        this.buildArguments = Util.fixEmpty(buildArguments);
        this.analyzeArguments = Util.fixEmpty(analyzeArguments);
        this.commitArguments = Util.fixEmpty(commitArguments);
        this.csharpAssemblies = Util.fixEmpty(csharpAssemblies);
        this.javaWarFilesNames = javaWarFilesNames;
        this.csharpMsvscaOutputFiles = Util.fixEmpty(csharpMsvscaOutputFiles);
        this.csharpMsvsca = csharpMsvsca;
        this.csharpAutomaticAssemblies = csharpAutomaticAssemblies;
        this.saOverride = Util.fixEmpty(saOverride);
        this.covBuildBlacklist = Util.fixEmpty(covBuildBlacklist);
    }

    @DataBoundConstructor
    public InvocationAssistance(String postCovBuildJSON, String postCovAnalyzeJSON, boolean isCompiledSrc, boolean isScriptSrc, String buildArguments, String analyzeArguments, String commitArguments, String intermediateDir, String misraMap, String csharpAssemblies, List<JavaWarFile> javaWarFiles, String csharpMsvscaOutputFiles, boolean csharpAutomaticAssemblies, boolean csharpMsvsca, String saOverride, String covBuildBlacklist, boolean useAdvancedParser) {
        JSONObject pBuildJSON = null;
        JSONObject pAnalyzeJSON = null;
        JSONObject misraJSON = null;
        try {
            pBuildJSON = (JSONObject)JSONSerializer.toJSON(postCovBuildJSON);
        } catch (JSONException e) { }
        try {
            pAnalyzeJSON = (JSONObject)JSONSerializer.toJSON(postCovAnalyzeJSON);
        } catch (JSONException e) { }
        try {
            misraJSON = (JSONObject)JSONSerializer.toJSON(misraMap);
        } catch (JSONException e) { }
        this.isCompiledSrc = isCompiledSrc;
        this.isScriptSrc = isScriptSrc;
        this.postCovBuildJSON = pBuildJSON;
        this.useAdvancedParser = useAdvancedParser;
        if(this.postCovBuildJSON != null) {
            this.postCovBuildCmd = (String) pBuildJSON.get("postCovBuildCmd");
        } else {
            this.postCovBuildCmd = null;
        }
        this.isUsingPostCovBuildCmd = this.postCovBuildJSON != null;
        this.postCovAnalyzeJSON = pAnalyzeJSON;
        if(this.postCovAnalyzeJSON != null) {
            this.postCovAnalyzeCmd = (String) pAnalyzeJSON.get("postCovAnalyzeCmd");
        } else {
            this.postCovAnalyzeCmd =null;
        }
        this.isUsingPostCovAnalyzeCmd = this.postCovAnalyzeJSON != null;
        this.misraMap = misraJSON;
        if(this.misraMap != null) {
            this.misraConfigFile = (String) misraJSON.get("misraConfigFile");
        } else {
            this.misraConfigFile = null;
        }
        this.isUsingMisra = this.misraMap != null;
        this.intermediateDir = Util.fixEmpty(intermediateDir);
        this.buildArguments = Util.fixEmpty(buildArguments);
        this.analyzeArguments = Util.fixEmpty(analyzeArguments);
        this.commitArguments = Util.fixEmpty(commitArguments);
        this.csharpAssemblies = Util.fixEmpty(csharpAssemblies);
        List<String> tempJavaWarFilesPaths = new ArrayList<String>();
        if(javaWarFiles != null && !javaWarFiles.isEmpty()){
            for(JavaWarFile javaWarFile : javaWarFiles){
                tempJavaWarFilesPaths.add(javaWarFile.getWarFile());
            }
        }
        this.javaWarFilesNames = tempJavaWarFilesPaths;
        this.javaWarFiles = javaWarFiles;
        this.csharpMsvscaOutputFiles = Util.fixEmpty(csharpMsvscaOutputFiles);
        this.csharpMsvsca = csharpMsvsca;
        this.csharpAutomaticAssemblies = csharpAutomaticAssemblies;
        this.saOverride = Util.fixEmpty(saOverride);
        this.covBuildBlacklist = Util.fixEmpty(covBuildBlacklist);
    }

    public String getPostCovBuildCmd() {
        return postCovBuildCmd;
    }

    public boolean getIsUsingPostCovBuildCmd() {
        return isUsingPostCovBuildCmd;
    }

    public boolean getIsUsingPostCovAnalyzeCmd() {
        return isUsingPostCovAnalyzeCmd;
    }

    public String getPostCovAnalyzeCmd() {
        return postCovAnalyzeCmd;
    }


    public boolean getIsCompiledSrc() {
        return isCompiledSrc;
    }

    public boolean getIsScriptSrc() {
        return isScriptSrc;
    }

    public String getBuildArguments() {
        return buildArguments;
    }

    public String getAnalyzeArguments() {
        return analyzeArguments;
    }

    public String getCommitArguments() {
        return commitArguments;
    }

    public String getIntermediateDir() {
        return intermediateDir;
    }

    public String getCsharpAssemblies() {
        return csharpAssemblies;
    }

    public List<JavaWarFile> getJavaWarFiles() {
        return javaWarFiles;
    }

    public List<String> getJavaWarFilesNames() {
        return javaWarFilesNames;
    }

    public String getCsharpMsvscaOutputFiles() {
        return csharpMsvscaOutputFiles;
    }

    public boolean getCsharpMsvsca() {
        return csharpMsvsca;
    }

    public boolean getCsharpAutomaticAssemblies() {
        return csharpAutomaticAssemblies;
    }

    public String getSaOverride() {
        return saOverride;
    }

    public String getCovBuildBlacklist() {
        return covBuildBlacklist;
    }

    public boolean getIsUsingMisra() {
        return isUsingMisra;
    }

    public String getMisraConfigFile(){
        return misraConfigFile;
    }



    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        InvocationAssistance that = (InvocationAssistance) o;

        if(csharpAutomaticAssemblies != that.csharpAutomaticAssemblies) return false;
        if(csharpMsvsca != that.csharpMsvsca) return false;
        if(analyzeArguments != null ? !analyzeArguments.equals(that.analyzeArguments) : that.analyzeArguments != null)
            return false;
        if(buildArguments != null ? !buildArguments.equals(that.buildArguments) : that.buildArguments != null)
            return false;
        if(commitArguments != null ? !commitArguments.equals(that.commitArguments) : that.commitArguments != null)
            return false;
        if(covBuildBlacklist != null ? !covBuildBlacklist.equals(that.covBuildBlacklist) : that.covBuildBlacklist != null)
            return false;
        if(csharpAssemblies != null ? !csharpAssemblies.equals(that.csharpAssemblies) : that.csharpAssemblies != null)
            return false;
        if(csharpMsvscaOutputFiles != null ? !csharpMsvscaOutputFiles.equals(that.csharpMsvscaOutputFiles) : that.csharpMsvscaOutputFiles != null)
            return false;
        if(intermediateDir != null ? !intermediateDir.equals(that.intermediateDir) : that.intermediateDir != null)
            return false;
        if(javaWarFiles != null ? !javaWarFiles.equals(that.javaWarFiles) : that.javaWarFiles != null) return false;
        if(saOverride != null ? !saOverride.equals(that.saOverride) : that.saOverride != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = buildArguments != null ? buildArguments.hashCode() : 0;
        result = 31 * result + (analyzeArguments != null ? analyzeArguments.hashCode() : 0);
        result = 31 * result + (commitArguments != null ? commitArguments.hashCode() : 0);
        result = 31 * result + (csharpAssemblies != null ? csharpAssemblies.hashCode() : 0);
        result = 31 * result + (javaWarFiles != null ? javaWarFiles.hashCode() : 0);
        result = 31 * result + (csharpMsvscaOutputFiles != null ? csharpMsvscaOutputFiles.hashCode() : 0);
        result = 31 * result + (csharpAutomaticAssemblies ? 1 : 0);
        result = 31 * result + (csharpMsvsca ? 1 : 0);
        result = 31 * result + (saOverride != null ? saOverride.hashCode() : 0);
        result = 31 * result + (covBuildBlacklist != null ? covBuildBlacklist.hashCode() : 0);
        result = 31 * result + (intermediateDir != null ? intermediateDir.hashCode() : 0);
        return result;
    }

    /**
     * For each variable in override, use that value, otherwise, use the value in this object.
     *
     * @param override source
     * @return a new, merged InvocationAssistance
     */
    public InvocationAssistance merge(InvocationAssistance override) {
        String analyzeArguments = override.getAnalyzeArguments() != null ? override.getAnalyzeArguments() : getAnalyzeArguments();
        String buildArguments = override.getBuildArguments() != null ? override.getBuildArguments() : getBuildArguments();
        String commitArguments = override.getCommitArguments() != null ? override.getCommitArguments() : getCommitArguments();
        String covBuildBlacklist = override.getCovBuildBlacklist() != null ? override.getCovBuildBlacklist() : getCovBuildBlacklist();
        String csharpAssemblies = override.getCsharpAssemblies() != null ? override.getCsharpAssemblies() : getCsharpAssemblies();
        String intermediateDir = override.getIntermediateDir() != null ? override.getIntermediateDir() : getIntermediateDir();
        List<String> javaWarFilesNames = override.getJavaWarFilesNames() != null ? override.getJavaWarFilesNames() : getJavaWarFilesNames();
        String csharpMsvscaOutputFiles = override.getCsharpMsvscaOutputFiles() != null ? override.getCsharpMsvscaOutputFiles() : getCsharpMsvscaOutputFiles();
        boolean csharpAutomaticAssemblies = override.getCsharpAutomaticAssemblies();
        boolean csharpMsvsca = override.getCsharpMsvsca();
        String saOverride = override.getSaOverride() != null ? override.getSaOverride() : getSaOverride();
        boolean isUsingMisra = override.getIsUsingMisra();
        String misraConfigFile = override.getMisraConfigFile() != null ? override.getMisraConfigFile() : getMisraConfigFile();
        boolean isCompiledSrc = override.getIsCompiledSrc();
        boolean isScriptSrc = override.getIsScriptSrc();
        boolean isUsingPostBuildCmd = override.getIsUsingPostCovBuildCmd();
        String postBuildCmd = override.getPostCovBuildCmd();
        boolean isUsingPostCovAnalyzeCmd = override.getIsUsingPostCovAnalyzeCmd();
        String postCovAnalyzeCmd = override.getPostCovAnalyzeCmd();
        List<JavaWarFile> javaWarFiles = override.getJavaWarFiles();
        boolean useAdvancedParser = override.getUseAdvancedParser();
        return new InvocationAssistance(isUsingPostBuildCmd, postBuildCmd, isUsingPostCovAnalyzeCmd, postCovAnalyzeCmd, isCompiledSrc, isScriptSrc, buildArguments, analyzeArguments, commitArguments, intermediateDir, isUsingMisra, misraConfigFile, csharpAssemblies, javaWarFilesNames, csharpMsvscaOutputFiles, csharpAutomaticAssemblies, csharpMsvsca, saOverride, covBuildBlacklist, javaWarFiles, useAdvancedParser);
    }

    // Sets the environment varibles for the project so that we can replace environment varibles
    public void setEnvVars(EnvVars environment){
        this.envVars = environment;
    }

    public String checkIAConfig(){
        boolean delim = true;
        String errorText = "Errors with your \"Perform Coverity build/analyze/commit\" options: \n";
        // Making sure they pick a test language
        if(isUsingMisra){
            if(misraConfigFile == null){
                delim = false;
            } else if (misraConfigFile.isEmpty()){
                delim = false;
            } else if (misraConfigFile.trim().isEmpty()){
                delim = false;
            }
        }

        if(delim){
            errorText = "Pass";
        } else {
            errorText += "[Error] No MISRA configuration file was specified. \n";
        }

        return errorText;
    }

}