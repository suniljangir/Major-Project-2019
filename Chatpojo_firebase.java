package com.secure_messaging;

/**
 * Created by Kshitij on 10/2/2018.
 */

public class Chatpojo_firebase {

        String senderid,receiverid;

    public String getLogindelid() {
        return logindelid;
    }

    public void setLogindelid(String logindelid) {
        this.logindelid = logindelid;
    }

    String logindelid;


    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    String messagetype;

        public String getSenderid() {
            return senderid;
        }

        public void setSenderid(String senderid) {
            this.senderid = senderid;
        }

        public String getReceiverid() {
            return receiverid;
        }

        public void setReceiverid(String receiverid) {
            this.receiverid = receiverid;
        }

        String sendermsg;
        String receivermsg;

        public String getSendermsg() {
            return sendermsg;
        }

        public void setSendermsg(String sendermsg) {
            this.sendermsg = sendermsg;
        }

        public String getReceivermsg() {
            return receivermsg;
        }

        public void setReceivermsg(String receivermsg) {
            this.receivermsg = receivermsg;
        }


}
