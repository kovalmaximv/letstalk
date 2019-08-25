<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            {{thinkArg.text}}
        </v-card-text>

        <!--media v-if="think.link" :think="thinkArg"></media-->

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
    import Media from "components/media/Media.vue";

    export default {
        components: {Media},
        props: ['thinkArg', 'editThink'],
        component: { Media },
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