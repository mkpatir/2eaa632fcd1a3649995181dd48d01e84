package com.mkpatir.spacedelivery.models

enum class SpaceShipProperty {
    DURABILITY,
    SPEED,
    CAPACITY
}

enum class CheckProperty{
    CONTINUE,
    CONTINUE_WITH_MISSING_COUNT,
    MISSING_PROPERTY
}

enum class TravelEndReason{
    UGS_OVER,
    EUS_OVER,
    INSUFFICIENT_EUS,
    FINISHED_ALL_TRAVEL,
    DAMAGE_OVER,
}