package com.zw.knight.util.qy.pojp;

import com.google.gson.annotations.SerializedName;

/**
 * 微信数据主体
 *
 * @author zw
 * @date 2019/10/18
 */
public class WeChatRes {
    /**
     * touser : UserID1|UserID2|UserID3
     * toparty : PartyID1|PartyID2
     * totag : TagID1 | TagID2
     * msgtype : text
     * agentid : 1
     * text : {"content":""}
     */
    @SerializedName("touser")
    private String toUser;
    @SerializedName("toparty")
    private String toParty;
    @SerializedName("totag")
    private String toTag;
    @SerializedName("msgtype")
    private String msgType;
    @SerializedName("agentid")
    private int agentId;
    private TextBean text;

    public WeChatRes(String toParty, String msgType, int agentId) {
        this.toParty = toParty;
        this.msgType = msgType;
        this.agentId = agentId;
        this.text = new TextBean();
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getToTag() {
        return toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public TextBean getText() {
        return text;
    }

    public void setText(TextBean text) {
        this.text = text;
    }

    public static class TextBean {
        // 信息内容
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
