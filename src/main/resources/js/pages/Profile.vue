<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
                <div class="title mb-3">{{profile.username}} profile</div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img style="max-width: 100%; height: auto;" :src="'data:image/png;base64,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'"></v-img>
                    </v-flex>
                    <v-flex class="px-1">
                        <v-layout column>
                            <v-flex>{{profile.username}}</v-flex>
                            <v-flex>{{profile.lastVisit}}</v-flex>
                            <v-flex>
                                {{profile.subscriptions && profile.subscriptions.length}} subscriptions
                            </v-flex>
                            <router-link
                                    v-if="isMyProfile"
                                    :to="`/subscriptions/${profile.id}`"
                            >
                                {{profile.subscribers && profile.subscribers.length}} subscribers
                            </router-link>
                            <v-flex v-else>
                                {{profile.subscribers && profile.subscribers.length}} subscribers
                            </v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-btn
                    v-if="!isMyProfile"
                    @click="changeSubscriptions"
                >
                    {{isISubscribed ? 'Unsubscribe': 'Subscribe'}}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from '../api/profile'

    export default {
        name: "Profile",
        data(){
            return {
                profile: {}
            }
        },
        computed: {
            isMyProfile(){
                return !this.$route.params.id ||
                    this.$route.params.id === this.$store.state.profile.id
            },
            isISubscribed(){
                return this.profile.subscribers &&
                    this.profile.subscribers.find(subscription => {
                        return subscription.subscriber === this.$store.state.profile.id
                    })
            }
        },
        watch: {
            '$route'(){
                this.updateProfile()
            }
        },
        methods: {
            async changeSubscriptions(){
                const data = await profileApi.changeSubscriptions(this.profile.id)
                this.profile = await data.json()
            },
            async updateProfile(){
                const id = this.$route.params.id || this.$store.state.profile.id

                const data = await profileApi.get(id)
                this.profile = await data.json()

                this.$forceUpdate()
            }
        },
        beforeMount() {
            this.updateProfile()
        }
    }
</script>

<style scoped>
</style>