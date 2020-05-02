package io.jenkins.plugins.feiqiu;

import org.kohsuke.stapler.DataBoundConstructor;

public class FeiQiuHost {

    /*@DataBoundConstructor
    public FeiQiuHost(long groupNum) {
        super();
        this.groupNum = groupNum;
    }*/

    @DataBoundConstructor
    public FeiQiuHost(String userName, String pcName, String mac) {
        this.userName = userName;
        this.pcName = pcName;
        this.mac = mac;
    }

    private String userName;
    private String pcName;
    private String mac;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
