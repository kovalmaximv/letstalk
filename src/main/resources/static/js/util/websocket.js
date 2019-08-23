import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

let stompClient = null
const handlers = []

export function connect() {
    const socket = new SockJS('/gs-guide-websocket')
    stompClient = Stomp.over(socket)
    stompClient.debug = () => {}
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/activityThink', think => {
            handlers.forEach(handler => handler(JSON.parse(think.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}

export function sendThink(think) {
    stompClient.send("/app/changeThink", {}, JSON.stringify(think))
}