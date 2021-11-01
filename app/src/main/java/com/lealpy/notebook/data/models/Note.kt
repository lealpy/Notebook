package com.lealpy.notebook.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Note (

    @PrimaryKey
    var id : Long? = null,
    var dateStart : Long? = null,
    var dateFinish : Long? = null,
    var name : String? = null,
    var description : String? = null
) : RealmObject()