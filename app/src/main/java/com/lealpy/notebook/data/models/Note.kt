package com.lealpy.notebook.data.models

import java.sql.Timestamp

data class Note (
    val id : Long,
    val dateStart : Timestamp,
    val dateFinish : Timestamp,
    val name : String,
    val description : String
)