package com.example.smaker.utilities

const val BASE_URL              = "https://smackchatappandroid.herokuapp.com/v1/"
const val SOCKET_URL            = "https://smackchatappandroid.herokuapp.com"
const val URL_REGISTER          = "${BASE_URL}account/register"
const val URL_LOGIN             = "${BASE_URL}account/login"
const val URL_CREATE_USER       = "${BASE_URL}user/add"
const val URL_GET_USER_EMAIL    = "${BASE_URL}user/byEmail/"
const val URL_GET_CHANNELS      = "${BASE_URL}channel/"
const val URL_GET_MESSAGES      ="${BASE_URL}message/byChannel/"



//BroadCast Contants

const val BROADCAST_USER_DATA_CHANGE="BROADCAST_USER_DATA_CHANGE"