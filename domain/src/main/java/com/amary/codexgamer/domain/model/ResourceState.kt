package com.amary.codexgamer.domain.model

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class ResourceState (val status: Status, val message: String){

    companion object {
        val LOADED: ResourceState = ResourceState(Status.SUCCESS, "Success")
        val LOADING: ResourceState = ResourceState(Status.RUNNING, "Running")
        val ERROR: ResourceState = ResourceState(Status.FAILED, "Something went wrong")
    }
}