package com.example.fly.level_view_demo

import com.example.levelviewlibray.LevelViewBaseData

/**
 * Created by fly on 2017/10/28.
 */
class MyData : LevelViewBaseData {
    var ids:String?=null
    var names:String?=null
    var counts:String ?=null
    var icons:String?=null
    var times:String?=null
    var addresses:String?=null
    var matches:String?=null
    constructor() : super()
    constructor(id: String, name: String, count: String, icon: String, time: String, address: String, match: String) {
        ids=id
        names=name
        counts=count
        icons=icon
        times=time
        addresses=address
        matches=match
    }

    override fun getId(): String {
        return ids!!
    }

    override fun getName(): String {
        return names!!
    }

    override fun getCount(): String {
        return counts!!
    }

    override fun getIcon(): String {
        return icons!!
    }

    override fun getTime(): String {
        return times!!
    }

    override fun getAddress(): String {
        return addresses!!
    }

    override fun getMatch(): String {
        return matches!!
    }
}