package es.unex.gps.weathevent.interfaces

import es.unex.gps.weathevent.model.Event

interface OnClickEventListener {
    fun onEventClick(event: Event)

    fun onEventDelete(event: Event)
}