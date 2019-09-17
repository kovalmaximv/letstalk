import Vue from 'vue'
import VueRouter from 'vue-router'
import thinkslist from '../components/thinks/ThinkList.vue'
import Auth from '../pages/Auth.vue'
import Profile from '../pages/Profile.vue'
import Subscriptions from "../pages/Subscriptions.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: thinkslist },
    { path: '/auth', component: Auth },
    { path: '/user/:id?', component: Profile },
    { path: '/subscriptions/:id', component: Subscriptions},
    { path: '*', component: thinkslist}
]

export default new VueRouter({
    mode: 'history',
    routes
})