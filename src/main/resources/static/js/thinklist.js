var thinkApi = Vue.resource('/thinks{/id}');

Vue.component('one-think', {
    props: ['thinkArg'],
    template: '<div>{{thinkArg.text}}</div>'
});

Vue.component('thinks-list', {
    props:['thinks'],
    template: '<div><one-think v-for="think in thinks" :key="think.id" :thinkArg = "think"/></div>',
    created: function () {
        thinkApi.get().then(result =>
            result.body.forEach(think =>this.thinks.push(think))
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<thinks-list :thinks = "thinks"/>',
    data: {
        thinks: [{id: '111', text: 'fuck'}]
    }
});