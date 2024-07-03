package com.onurcemkarakoc.core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RemoteInfo(val count: Int, val pages: Int, val next: String?, val prev: String?)