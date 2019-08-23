<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            {{thinkArg.text}}
        </v-card-text>

        <v-card-actions>
            <v-btn small text rounded @click="edit">Edit</v-btn>
            <v-btn small icon @click="del">
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        props: ['thinkArg', 'editThink'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editThink(this.thinkArg)
            },
            del() {
                this.removeMessageAction(this.thinkArg)
            }
        }
    }
</script>

<style>

</style>