package com.cl.url

import com.cl.url.CLQueryParameter.CLQueryParameter

/**
 * Created by mahmadzadeh on 16/06/14.
 */
case class SearchParameter(param:CLQueryParameter, value:String)  {

    override def toString :String = {
        param.toString + "=" +value
    }
}