<template>
    <div id="onethink">
        {{thinkArg.text}}
        <span style="position: absolute; right: 0">
            <input type="button" value="Edit" @click="edit">
            <input type="button" value="X" @click="del">
        </span>
    </div>
</template>

<script>
    export default {
        props: ['thinkArg', 'editThink', 'deleteThink', 'thinks'],
        methods: {
            edit() {
                this.editThink(this.thinkArg)
            },
            del() {
                this.deleteThink(this.thinkArg)
            }
        }
    }
</script>

<style>

</style>