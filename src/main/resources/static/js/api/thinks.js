import Vue from 'vue'

const thinks = Vue.resource('/thinks{/id}')

export default {
    add: think => thinks.save({}, think),
    update: think => thinks.update({id: think.id}, think),
    remove: id => thinks.remove({id})
}