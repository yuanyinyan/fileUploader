package com.socketExercise.util;

import java.io.Serializable;

/**
 * 表示客户机和服务器之间传输的指令数据
 * <p/>
 * Created by yuanyin on 16/2/1.
 */
public class CommandTransfer implements Serializable {
    private String cmd;
    private Object data;
    private boolean flag;
    private String result;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object object) {
        this.data = object;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
