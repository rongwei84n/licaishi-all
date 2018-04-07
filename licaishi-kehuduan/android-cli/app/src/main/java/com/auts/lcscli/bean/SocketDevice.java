package com.auts.lcscli.bean;

/**
 * Created by qisheng.lv on 2017/7/14.
 */

public class SocketDevice {

    public DeviceSw device;

    public class DeviceSw{
        private int sw0;

        private int sw1;

        private int sw2;

        private int sw3;

        private int sw4;

        private int sw5;

        public int getSw0() {
            return sw0;
        }

        public void setSw0(int sw0) {
            this.sw0 = sw0;
        }

        public int getSw1() {
            return sw1;
        }

        public void setSw1(int sw1) {
            this.sw1 = sw1;
        }

        public int getSw2() {
            return sw2;
        }

        public void setSw2(int sw2) {
            this.sw2 = sw2;
        }

        public int getSw3() {
            return sw3;
        }

        public void setSw3(int sw3) {
            this.sw3 = sw3;
        }

        public int getSw4() {
            return sw4;
        }

        public void setSw4(int sw4) {
            this.sw4 = sw4;
        }

        public int getSw5() {
            return sw5;
        }

        public void setSw5(int sw5) {
            this.sw5 = sw5;
        }
    }


}
