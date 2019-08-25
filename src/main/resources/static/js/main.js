import Vue from 'vue'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import store from 'store/store'
import vuetify from 'plugins/vuetify'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import { connect } from "./util/websocket"

if(frontData.profile){
    connect()
}


/*Vue.use(Vuetify)*/

new Vue({
    vuetify: new Vuetify({
        theme: { dark: true },
    }),
    store,
    router,
    render: h => h(App)
}).$mount('#app')