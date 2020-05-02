package io.jenkins.plugins.feiqiu;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.*;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Weijianbo
 */
public class FeiQiuNotifier extends Notifier {

    /**
     * 飞秋群列表
     */
    private final List<FeiQiuGroup> feiQiuGroups;

    public FeiQiuHost getFeiQiuHost() {
        return feiQiuHost;
    }

    /**
     * 飞秋群列表
     */
    private final FeiQiuHost feiQiuHost;

    /**
     * 消息内容
     */
    private final String message;


    private PrintStream logger;

    @DataBoundConstructor
    public FeiQiuNotifier(List<FeiQiuGroup> feiQiuGroups,String userName,String pcName,String mac, String message) {
        this.feiQiuGroups = new ArrayList<FeiQiuGroup>(feiQiuGroups);
        this.feiQiuHost = new FeiQiuHost(userName,pcName,mac);
        this.message = message;
    }


    public List<FeiQiuGroup> getFeiQiuGroups() {
        return feiQiuGroups;
    }

    public String getMessage() {
        return message;
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws UnsupportedEncodingException {

        logger = listener.getLogger();

        Jenkins.getInstance();
        StringBuffer jobURL = new StringBuffer();
        try {
            jobURL.append(build.getEnvironment(listener).expand("${JENKINS_URL}")); //获取JENKINS_URL,需要在系统设置中修改Jenkins Location/Jenkins URL
            jobURL.append("/job");
            jobURL.append("/" + build.getEnvironment(listener).expand("${JOB_NAME}")); //获取JOB_NAME
            jobURL.append("/" + build.getEnvironment(listener).expand("${BUILD_ID}")); //获取BUILD_ID
            jobURL.append("/" + "console");
            logger.println("jobURL = " + jobURL);
        } catch (Exception e) {
            logger.println("tokenmacro expand error.");
        }

        String msg = "各位小伙伴，项目";
        msg += build.getFullDisplayName();
        if (build.getResult() == Result.SUCCESS) {
            msg += "编译成功！" + message;
        } else {
            msg += "编译失败了...";

        }
        msg += "jenkins地址:" + jobURL;

        for (FeiQiuGroup feiQiuGroup : feiQiuGroups) {
            FeiqUtil.sendGroupMsg(msg,feiQiuGroup,feiQiuHost);
        }

        return true;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends
            BuildStepDescriptor<Publisher> {

        public DescriptorImpl() {
            load();
        }

        private boolean isNumeric(String str) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
            return true;
        }

        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        /**
         * jenkins中显示名称
         *
         * @return
         */
        public String getDisplayName() {
            return "飞秋通知";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            save();
            return super.configure(req, formData);
        }

    }
}

