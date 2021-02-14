package com.mkpatir.spacedelivery.models

data class SpaceShip(
    var name: String,
    var speed: Int,
    var materialCapacity: Int,
    var durability: Int,
    var damageCapacity: Int = 100
)