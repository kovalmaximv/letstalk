<template>
    <v-container>
        <v-layout align-space-around justify-start column>
            <new-think-form class="mx-12" :thinkEdit="think"/>
            <one-think v-for="think in sortedThinks"
                       :key="think.id"
                       :thinkArg = "think"
                       :editThink="editThink" />
            <lazy-loader></lazy-loader>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapGetters } from 'vuex'
    import onethink from '../../components/thinks/OneThink.vue'
    import newthinkform from '../../components/thinks/NewThinkForm.vue'
    import LazyLoader from "../../components/LazyLoader.vue";

    export default {
        components:{
            LazyLoader,
            'one-think': onethink,
            'new-think-form': newthinkform
        },
        data(){
            return{
                think: null
            }
        },
        computed: mapGetters(['sortedThinks']),
        methods: {
            editThink(thinkArg) {
                this.think = thinkArg;
            }

        }
    }
</script>

<style>

</style>