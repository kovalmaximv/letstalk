import Vue from 'vue'
import Vuetify, {
    VApp,
    VAppBar,
    VToolbarTitle,
    VBtn,
    VIcon,
    VTextField,
    VSpacer,
    VContainer,
    VContent,
    VFooter,
    VCard,
    VCardTitle,
    VCardText,
    VCardActions,
    VLayout,
    VRating,
    VToolbar,
} from 'vuetify/lib'
import { Ripple } from 'vuetify/lib/directives'

Vue.use(Vuetify, {
    components: {
        VApp,
        VAppBar,
        VToolbarTitle,
        VTextField,
        VBtn,
        VIcon,
        VSpacer,
        VContent,
        VContainer,
        VFooter,
        VCard,
        VCardTitle,
        VCardText,
        VCardActions,
        VLayout,
        VRating,
        VToolbar,
    },
    directives: {
        Ripple,
    },
})

export default new Vuetify({ })