<template>
    <v-layout row>
        <v-text-field
                label="New think"
                placeholder="WriteSomething"
                v-model="text"
        />
        <v-btn @click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        props: ['thinkEdit'],
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
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                const think = {id: this.id, text: this.text};

                if(this.id){
                    this.updateMessageAction(think)
                }else{
                    this.addMessageAction(think)
                }

                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>

</style>