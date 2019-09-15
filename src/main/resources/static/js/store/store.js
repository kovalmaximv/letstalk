import Vue from 'vue'
import Vuex from 'vuex'
import thinksApi from 'api/thinks'
import commentApi from 'api/comment'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        thinks,
        profile,
        ...frontData
    },
    getters: {
        sortedThinks: state => (state.thinks || []).sort((a,b) => -(a.id - b.id))
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
        addCommentMutation(state, comment){
            const updateIndex = state.thinks.findIndex(item => item.id === comment.think.id)
            const think = state.thinks[updateIndex]

            if(!think.comments.find( it => it.id === comment.id)) {
                state.thinks = [
                    ...state.thinks.splice(0, updateIndex),
                    {
                        ...think,
                        comments: [
                            ...think.comments,
                            comment
                        ]
                    },
                    ...state.thinks.splice(updateIndex + 1)
                ]
            }
        },
        addThinkPageMutation(state, thinks){
            const targetThinks = state.thinks
                .concat(thinks)
                .reduce((res, val) =>{ //reduce the same thinks
                    res[val.id] = val //put think in map, when key is think.id
                    return res
                }, {})

            state.thinks = Object.values(targetThinks)
        },
        updateTotalPagesMutation(state, totalPages){
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage){
            state.currentPage = currentPage
        }
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
            const result = await thinksApi.remove(think.id)

            if (result.ok) {
                commit('removeMessageMutation', think)
            }
        },
        async addCommentAction({commit, state}, comment){
            const response = await commentApi.add(comment)
            const data = await response.json()
            commit('addCommentMutation', data)
        },
        async loadPageAction({commit, state}){
            const response = await thinksApi.page(state.currentPage + 1)
            const data = await response.json()

            commit('addThinkPageMutation', data.thinks)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
        }
    }
})