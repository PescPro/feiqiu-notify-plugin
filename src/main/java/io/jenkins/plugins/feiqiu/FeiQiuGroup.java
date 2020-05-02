package io.jenkins.plugins.feiqiu;

import org.kohsuke.stapler.DataBoundConstructor;

public class FeiQiuGroup {

    @DataBoundConstructor
    public FeiQiuGroup(long groupNum) {
        super();
        this.groupNum = groupNum;
    }


    private long groupNum ;

    public long getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(long groupNum) {
        this.groupNum = groupNum;
    }
}
