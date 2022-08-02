package com.veco.vecoapp

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}