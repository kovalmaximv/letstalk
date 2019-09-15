<template>
    <router-link
            :to="`/user/${user.id}`"
            class="px-3"
    >

        <v-avatar
                v-if="user && user.userpic"
                :size="`${size || 36}px`"
        >
            <img
                    :src="user.userpic"
                    :alt="user.name"
            >
        </v-avatar>

        <v-avatar v-else :size="`${size || 36}px`" color="indigo">
            <img
                    :src="'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYcv-0ewt7HOXii0rH2Z7Ct6dBzLiL6q7ErolktZQmTVFB77W3'"
            >
        </v-avatar>
        <span>
            {{ userName }}
        </span>
    </router-link>
</template>

<script>
    export default {
        name: "UserLink",
        props: ['user', 'size'],
        computed: {
            userName()  {
                return this.user ? this.user.username : 'unknown'
            }
        },
    }
</script>

<style scoped>

</style>