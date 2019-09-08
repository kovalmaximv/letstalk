<template>
    <v-list-item>
        <v-list-item-avatar v-if="comment.author && comment.author.userpic">
            <img :src="comment.author.userpic">
        </v-list-item-avatar>

        <v-list-item-avatar v-else>
            <img :src="'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYcv-0ewt7HOXii0rH2Z7Ct6dBzLiL6q7ErolktZQmTVFB77W3'">
        </v-list-item-avatar>
        <v-list-item-content>
            <v-list-item-title>
                {{comment.text}}
            </v-list-item-title>
            <v-list-item-subtitle>
                {{comment.author.username}}
            </v-list-item-subtitle>
        </v-list-item-content>
    </v-list-item>
</template>

<script>
    export default {
        name: "CommentItem",
        props: ['comment']
    }
</script>

<style scoped>

</style>