package com.aceinteract.sleakstreamer.client


class ClientApplication {

    companion object {

        @JvmStatic
        @Throws(Exception::class)
        fun main(args: Array<String>) {
            println("Hello Client")
            val client = Client()
            client.startAudioStream("001dw3")
        }

    }

}
