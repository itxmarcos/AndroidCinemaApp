package com.example.cinemaapp.utils.data_model

import com.example.cinemaapp.utils.api.ApiClient.actors
import java.io.Serializable

/**
 * Clase que representa los objetos que queremos manejar en nuestra aplicación. Necesitamos que sea
 * Serializable como en java para que se pueda esctibir en disco.
 */
data class Actor(val id: String, val name: String) {
    /*override fun toString(): String {
        return "$id.- Actor name: $name"
    }*/
}