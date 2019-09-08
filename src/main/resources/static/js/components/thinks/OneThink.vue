<template>
    <v-card class="my-2">
        <v-card-text primary-title>
            <div>
                <v-avatar v-if="thinkArg.author && thinkArg.author.userpic" size="48px">
                    <img
                            :src="thinkArg.author.userpic"
                            :alt="thinkArg.author.username"
                    >
                </v-avatar>
                <v-avatar v-else size="48px" color="indigo">
                    <img
                            :src="'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYcv-0ewt7HOXii0rH2Z7Ct6dBzLiL6q7ErolktZQmTVFB77W3'"
                    >
                </v-avatar>
                <span class="pl-3">
                    {{ authorName }}
                </span>
            </div>
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

    export default {
        props: ['thinkArg', 'editThink'],
        components:{
            CommentList,
            'media': Media,
        },
        computed: {
            authorName()  {
                return this.thinkArg.author ? this.thinkArg.author.username : 'unknown'
            }
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