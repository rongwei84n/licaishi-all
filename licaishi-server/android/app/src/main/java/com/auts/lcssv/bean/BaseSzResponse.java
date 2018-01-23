package com.auts.lcssv.bean;

import java.io.Serializable;

/**
 *
 * @author qisheng.lv
 * @date 2017/8/9
 */

public class BaseSzResponse  implements Serializable {

    private static final long serialVersionUID = -6602977500399179998L;

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
