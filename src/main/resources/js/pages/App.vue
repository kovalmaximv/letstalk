<template>
    <v-app>
        <v-app-bar app clipped-left>
            <v-toolbar-title >Let's talk</v-toolbar-title>
            <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showThinks">
                Thinks
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn text
                   v-if="profile"
                   :disabled="$route.path === '/user'"
                   @click="showProfile">
                {{profile.username}}
            </v-btn>
            <v-btn icon  href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-app-bar>
        <v-content>
            <v-container class="fill-height" fluid>
                <router-view></router-view>
            </v-container>
        </v-content>

        <v-footer app>
            <v-spacer></v-spacer>
            <span >Koval Maxim &copy; 2019</span>
        </v-footer>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import { addHandler } from "../util/websocket";

    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations([
                'addMessageMutation',
                'updateMessageMutation',
                'removeMessageMutation',
                'addCommentMutation'
            ]),
            showThinks(){
                this.$router.push('/')
            },
            showProfile(){
                this.$router.push('/user')
            },
        },
        created() {
            addHandler(data => {
                if(data.objectType === 'THINK'){
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.error('Event type is unknown "${data.eventType}"')
                    }
                }else if(data.objectType === 'COMMENT'){
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.body)
                            break
                        default:
                            console.error('Event type is unknown "${data.eventType}"')
                    }
                }else{
                    console.error('Object type is unknown "${data.objectType}"')
                }
            })
        },
        beforeMount() {
            if(!this.profile){
                this.$router.replace("/auth")
            }
        }
    }
</script>

<style>
</style>