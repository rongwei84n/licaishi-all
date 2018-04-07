package com.auts.lcscli.event;

/**
 * 修改工作室名称
 */

public class ChangeWorkstudioEvent {
    private String workstudio;

    public ChangeWorkstudioEvent(String workstudio) {
        this.workstudio = workstudio;
    }

    public String getWorkstudio() {
        return workstudio;
    }

    public void setWorkstudio(String workstudio) {
        this.workstudio = workstudio;
    }
}
