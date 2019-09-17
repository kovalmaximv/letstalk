<template>
    <v-layout row class="px-6">
        <v-text-field
                label="Add comment"
                placeholder="WriteSomething"
                v-model="text"
                @keyup.enter="save"
        />
        <v-btn @click="save">
            Add
        </v-btn>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "CommentForm",
        props: ['ThinkId'],
        data() {
            return{
                text: ''
            }
        },
        methods: {
            ...mapActions(['addCommentAction']),
            async save(){
                await this.addCommentAction({
                    text: this.text,
                    think: {
                        id: this.ThinkId
                    }
                })

                this.text = ''
            }
        }
    }
</script>

<style scoped>

</style>