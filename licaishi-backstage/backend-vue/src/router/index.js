import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: "/",
            redirect: "/login"
        },
        {
            path: "/readme",
            component: resolve => require(["../components/common/Home.vue"], resolve),
            children: [
                {
                    path: "",
                    redirect: "firmware"
                },
                {
                    path: "firmware",
                    component: resolve => require(["../components/page/OTAManage/firmware.vue"], resolve)
                },
                {
                    path: "firmwareUpdate",
                    component: resolve => require(["../components/page/OTAManage/firmwareUpdate.vue"], resolve)
                },
                {
                    path: "deviceList",
                    component: resolve => require(["../components/page/OTAManage/deviceList.vue"], resolve)
                },
                {
                    path: "deviceDetail",
                    component: resolve => require(["../components/page/OTAManage/deviceDetail.vue"], resolve)
                },
                {
                    path: "singleMac",
                    component: resolve => require(["../components/page/DeviceSystem/singleMac.vue"], resolve)
                },
                {
                    path: "betweenMac",
                    component: resolve => require(["../components/page/DeviceSystem/betweenMac.vue"], resolve)
                },
                {
                    path: "macData",
                    component: resolve => require(["../components/page/DeviceSystem/macData.vue"], resolve)
                },
                {
                    path: "accountDetail",
                    component: resolve => require(["../components/page/SystemManage/accountDetail.vue"], resolve)
                },
                {
                    path: "accountRole",
                    component: resolve => require(["../components/page/SystemManage/accountRole.vue"], resolve)
                },
                {
                  path: "accountAuthority",
                  component: resolve => require(["../components/page/SystemManage/accountAuthority.vue"], resolve)
                },
                {
                  path: "phVersionMgr",
                  component: resolve => require(["../components/page/AppManage/phihomeManage.vue"], resolve)
                },
                {
                  path: "AIVersionMgr",
                  component: resolve => require(["../components/page/AppManage/versionManage.vue"], resolve)
                },
                {
                  path: "bannerMgr",
                  component: resolve => require(["../components/page/AppManage/homeBanner.vue"], resolve)
                },
                {
                  path: "channelMgr",
                  component: resolve => require(["../components/page/AppManage/cannalManage.vue"], resolve)
                },
                {
                  path: "userDetailPhiHome",
                  component: resolve => require(["../components/page/userManage/phicommDetail.vue"], resolve)
                },
                {
                  path: "userDetailAI",
                  component: resolve => require(["../components/page/userManage/AIDetail.vue"], resolve)
                },
                {
                  path: "portData",
                  component: resolve => require(["../components/page/statisticAnalysis/portData.vue"], resolve)
                },
                {
                  path: "electricQuantityData",
                  component: resolve => require(["../components/page/statisticAnalysis/electricData.vue"], resolve)
                }
            ]
        },
        {
            path: "/login",
            component: resolve => require(["../components/page/Login.vue"], resolve)
        }
    ]
});
