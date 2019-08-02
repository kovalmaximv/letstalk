var thinkApi = Vue.resource('/thinks{/id}');


Vue.component('new-think-form', {
    props: ['thinks', 'thinkEdit'],
    data: function () {
        return {
            text: '',
            id: ''
        }
    },
    watch:{
        thinkEdit: function(newVal, oldVal){
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="WriteSomething" v-model="text">' +
        '<input type="button" value="Save" @click="save">' +
        '</div>',
    methods:{
        save: function () {
            var think = {text: this.text};

            if(this.id){
                thinkApi.update({id: this.id}, think).then(result =>
                    result.json().then(data=>{
                        for(var i = 0; i < this.thinks.length; i++){
                            if(this.thinks[i].id === data.id){
                                this.thinks.splice(i,1,data);
                            }
                        }
                    })
                )
            }else{
                thinkApi.save({}, think).then(result =>
                    this.thinks.push(result.body)
                );
            }

            this.text = '';
            this.id = ''
        }
    }
});

Vue.component('one-think', {
    props: ['thinkArg', 'editThink', 'thinks'],
    template: '<div>'+
        '{{thinkArg.text}}'+
        '<span style="position: absolute; right: 0">'+
            '<input type="button" value="Edit" @click="edit">'+
            '<input type="button" value="X" @click="del">'+
        '</span>'+
        '</div>',
    methods: {
        edit: function () {
            this.editThink(this.thinkArg);
        },
        del: function () {
            thinkApi.remove({id: this.thinkArg.id}).then(result => {
                    if (result.ok) {
                        this.thinks.splice(this.thinkArg.id, 1)
                    }
                })
        }
    }
});

Vue.component('thinks-list', {
    props: ['thinks'],
    data: function(){
        return{
            think: null
        }
    },
    template:
        '<div style="position: relative; width: 300px">'+
        '<new-think-form :thinks="thinks" :thinkEdit="think"/>'+
        '<one-think v-for="think in thinks" :key="think.id" :thinkArg = "think" :editThink="editThink" :thinks="thinks"/>'+
        '</div>',
    created: function () {
        thinkApi.get().then(result =>
            result.body.forEach(think => this.thinks.push(think))
        )
    },
    methods: {
        editThink: function (thinkArg) {
            this.think = thinkArg;
        }

    }
});

var app = new Vue({
    el: '#app',
    template: '<thinks-list :thinks = "thinks"/>',
    data: {
        thinks: []
    }
});