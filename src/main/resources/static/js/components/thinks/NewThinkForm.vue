<template>
    <div>
        <input type="text" placeholder="WriteSomething" v-model="text">
        <input type="button" value="Save" @click="save">
    </div>
</template>

<script>
    import { sendThink } from "util/websocket";

    export default {
        props: ['thinks', 'thinkEdit'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch:{
            thinkEdit(newVal, oldVal){
                this.text = newVal.text;
                this.id = newVal.id;
            }
        },
        methods:{
            save() {
                sendThink({id: this.id, text: this.text})

                this.text = '';
                this.id = ''

                /*const think = {text: this.text};

                if(this.id){
                    this.$resource('/thinks{/id}').update({id: this.id}, think).then(result =>
                        result.json().then(data=>{
                            for(var i = 0; i < this.thinks.length; i++){
                                if(this.thinks[i].id === data.id){
                                    this.thinks.splice(i,1,data);
                                }
                            }
                        })
                    )
                }else{
                    this.$resource('/thinks{/id}').save({}, think).then(result =>
                        this.thinks.push(result.body)
                    );
                }

                this.text = '';
                this.id = ''*/
            }
        }
    }
</script>

<style>

</style>