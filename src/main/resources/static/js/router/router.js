import Vue from 'vue'
import VueRouter from 'vue-router'
import thinkslist from '../components/thinks/ThinkList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: thinkslist },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '*', component: thinkslist}
]

export default new VueRouter({
    mode: 'history',
    routes
})