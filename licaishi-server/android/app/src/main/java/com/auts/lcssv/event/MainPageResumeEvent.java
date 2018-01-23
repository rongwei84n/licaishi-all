package com.auts.lcssv.event;

/**
 * Created by qisheng.lv on 2017/7/5.
 */

public class MainPageResumeEvent {
    private boolean isResume;
    private int position;

    public MainPageResumeEvent(boolean isResume,int position) {
        this.isResume = isResume;
        this.position = position;
    }

    public boolean isResume() {
        return isResume;
    }

    public void setResume(boolean resume) {
        isResume = resume;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
