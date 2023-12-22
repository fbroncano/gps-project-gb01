package es.unex.gps.weathevent.interfaces

import es.unex.gps.weathevent.model.User

interface UserParam {
    fun getUserFragment() : User
}