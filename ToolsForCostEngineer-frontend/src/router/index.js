import {createRouter, createWebHistory} from "vue-router";
import a from "@/components/CheckBillQuota.vue";
import b from "@/components/b.vue";
import c from "@/components/c.vue";

const router  = createRouter({
    routes:[
        {path: '/', component: a},
        {path: '/a', component: a},
        {path: '/b', component: b},
        {path: '/c', component: c},
    ],
    history:createWebHistory()
})

export default router