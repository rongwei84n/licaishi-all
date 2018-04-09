import { otherRouter, appRouter } from '@/router/router';

const app = {
    state: {
        menuList: [],   // 菜单结构
        routers: [
            otherRouter,
            ...appRouter, //默认的菜单
        ]
    },
    mutations: {
        /**
         * 获取当前的菜单
         */
        updateMenulist(state) {
            state.menuList = appRouter;
        }
    }
};

export default app;
