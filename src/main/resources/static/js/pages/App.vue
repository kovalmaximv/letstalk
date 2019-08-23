<template>
    <v-app>
        <v-app-bar app clipped-left>
            <v-toolbar-title>Let's talk</v-toolbar-title>
            <v-spacer></v-spacer>
            <div v-if="profile">
                {{profile.username}}
                <v-btn icon  href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </div>
        </v-app-bar>
        <v-content class="mx-12">
            <v-container v-if="profile">
                <thinks-list/>
            </v-container>
            <v-container v-else>
                <div>Необходимо <a href="/login">авторизоваться</a> </div>
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
    import thinkslist from '../components/thinks/ThinkList.vue'
    import { addHandler } from "util/websocket";

    export default {
        components:{
            'thinks-list': thinkslist
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
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
                }else{
                    console.error('Object type is unknown "${data.objectType}"')
                }
            })
        }
    }
</script>

<style>

</style>