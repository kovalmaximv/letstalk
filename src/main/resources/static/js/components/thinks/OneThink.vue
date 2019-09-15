<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <user-link
                :user="thinkArg.author"
                size="48"
            ></user-link>
            <div class="pt-3">
                {{thinkArg.text}}
            </div>
        </v-card-text>
        <media class="mx-auto" v-if="thinkArg.link" :think="thinkArg"></media>
        <v-card-actions>
            <v-btn small text rounded @click="edit">Edit</v-btn>
            <v-btn small icon @click="del">
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
        <comment-list :comments="thinkArg.comments" :think-id="thinkArg.id"></comment-list>
    </v-card>
</template>

<script>
    import { mapActions } from 'vuex'
    import Media from "components/media/Media.vue";
    import CommentList from "../comment/CommentList.vue";
    import UserLink from "components/UserLink.vue";

    export default {
        props: ['thinkArg', 'editThink'],
        components:{
            UserLink,
            CommentList,
            'media': Media,
        },
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