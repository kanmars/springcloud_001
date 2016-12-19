package cn.kanmars.sn.entity;

/**
 * Created by zhaojiuyang on 2015/12/8.
 */
public class UserInfo {
    private Long pk;
    private String  wx_nickName;

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getWx_nickName() {
        return wx_nickName;
    }

    public void setWx_nickName(String wx_nickName) {
        this.wx_nickName = wx_nickName;
    }
}
