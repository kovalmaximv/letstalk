import Vue from 'vue'
import Vuex from 'vuex'
import thinksApi from 'api/thinks'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        thinks: frontData.thinks,
        profile: frontData.profile
    },
    getters: {
        sortedThinks: state => state.thinks.sort((a,b) => -(a.id - b.id))
    },
    mutations: {
        addMessageMutation(state, think){
            state.thinks = [
                ...state.thinks,
                think
            ]
        },
        updateMessageMutation(state, think){
            const updateIndex = state.thinks.findIndex(item => item.id === think.id)
            state.thinks =[
                ...state.thinks.splice(0, updateIndex),
                think,
                ...state.thinks.splice(updateIndex+1)
            ]
        },
        removeMessageMutation(state, think){
            const deleteIndex = state.thinks.findIndex(item => item.id === think.id)

            if(deleteIndex > -1){
                state.thinks = [
                    ...state.thinks.splice(0, deleteIndex),
                    ...state.thinks.splice(deleteIndex + 1),

                ]
            }
        },
    },
    actions: {
        async addMessageAction({commit, state}, think){
            const result = await thinksApi.add(think)
            const data = await result.json()
            const index = state.thinks.findIndex(item => item.id === data.id)

            if(index > -1) {
                commit('updateMessageMutation', think)
            }else{
                commit('addMessageMutation', think)
            }
        },
        async updateMessageAction({commit}, think){
            const result = await thinksApi.update(think)
            const data = await result.json()

            commit('updateMessageMutation', think)
        },
        async removeMessageAction({commit}, think){
            const result = await thinksApi.remove(thinkArg.id)

            if (result.ok) {
                commit('removeMessageMutation', think)
            }
        },
    }
})