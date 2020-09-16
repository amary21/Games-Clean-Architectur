package com.amary.codexgamer.domain.model

class ResourceState(val message: String){

    companion object {
        val LOADED: ResourceState = ResourceState("Success")
        val LOADING: ResourceState = ResourceState("Running")
        val ERROR: ResourceState = ResourceState("Something went wrong")
    }
}