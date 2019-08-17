<template>
    <div>
        <div v-if="profile">
            <div>{{profile.username}} <a href="/logout">выход</a> </div>
            <thinks-list :thinks = "thinks"/>
            </div>
        <div v-else>
            <div>Необходимо <a href="/login">авторизоваться</a> </div>
        </div>
    </div>
</template>

<script>
    import thinkslist from '../components/thinks/ThinkList.vue'
    import { addHandler } from "util/websocket";

    export default {
        components:{
            'thinks-list': thinkslist
        },
        data() {
            return {
                thinks: frontData.thinks,
                profile: frontData.profile
            }
        },
        created() {
            addHandler(data => {
                var index = -1

                for(var i = 0; i < this.thinks.length; i++) {
                    if (this.thinks[i].id === data.id) {
                        index = i
                    }
                }

                if(index > -1){
                    this.thinks.splice(index, 1, data)
                }else{
                    this.thinks.push(data)
                }
            })
        }
    }
</script>

<style>

</style>