<template>
    <div style="position: relative; width: 300px">
        <new-think-form :thinks="thinks" :thinkEdit="think"/>
        <one-think v-for="think in thinks"
                   :key="think.id"
                   :thinkArg = "think"
                   :editThink="editThink"
                   :deleteThink="deleteThink"
                   :thinks="thinks"/>
    </div>
</template>

<script>
    import onethink from 'components/thinks/OneThink.vue'
    import newthinkform from 'components/thinks/NewThinkForm.vue'
    export default {
        props: ['thinks'],
        components:{
            'one-think': onethink,
            'new-think-form': newthinkform
        },
        data(){
            return{
                think: null
            }
        },
        methods: {
            editThink(thinkArg) {
                this.think = thinkArg;
            },
            deleteThink(thinkArg){
                this.$resource('/thinks{/id}').remove({id: thinkArg.id}).then(result => {
                    if (result.ok) {
                        this.thinks.splice(thinkArg.id, 1)
                    }
                })
            }

        }
    }
</script>

<style>

</style>