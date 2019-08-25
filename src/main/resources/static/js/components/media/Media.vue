<template>
    <v-card>
        <v-flex v-if="type === 'href'" xs12 sm6 offset-sm3>
            <v-img v-if="think.linkCover" :src="think.linkCover" aspect-ratio="2.75"></v-img>
            <v-card-title>
                <div>
                    <h3>
                        <a :href="think.link">{{think.linkTitle || think.link}}</a>
                    </h3>
                    <div v-if="think.linkDescription">{{think.linkDescription}}</div>
                </div>
            </v-card-title>
        </v-flex>

        <v-flex v-if="type === 'image'" xs12 sm6 offset-sm3>
            <a :href="think.link">
                <v-img v-if="think.linkCover" :src="think.linkCover" aspect-ratio="2.75"></v-img>
                {{think.link}}
            </a>
        </v-flex>

        <v-flex v-if="type === 'youtube'" xs12 sm6 offset-sm3>
            <you-tube :src="think.link"></you-tube>
        </v-flex>
    </v-card>

</template>

<script>
    import YouTube from './YouTube.vue'
    export default {
        name: "Media",
        components: { YouTube },
        props: ['think'],
        data(){
            return{
                type: 'href'
            }
        },
        beforeMount() {
            if(this.think.link.indexOf('youtu') > -1){
                this.type = 'youtube'
            }else if(this.think.link.match(/\.(jpeg|jpg|gif|png)$/) !== null){
                this.type = 'image'
            } else {
                this.type = 'href'
            }
        }
    }
</script>

<style scoped>

</style>