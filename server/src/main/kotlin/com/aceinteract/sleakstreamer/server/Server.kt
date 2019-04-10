package com.aceinteract.sleakstreamer.server

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.nio.file.Paths


class Server(port: Int = PORT) {

    private val serverSocket = ServerSocket(port)

    init {
        val streamer = Streamer(Paths.get("../media/magic.wav").toFile())
        Thread {
            streamer.startStreaming()
        }.start()

        while (true) {

            println("Waiting for connection...")
            val socket = serverSocket.accept()!!
            println("Serving client...")
            streamer.sockets.add(socket)
//            Thread {
//                serveClient(socket)
//            }.start()

        }
    }

    private fun serveClient(socket: Socket) {

        if (socket.isConnected) {

            val songId: String
            try {

                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                songId = reader.readLine()


                // TODO: Uncomment
                // val songFile = Paths.get("../media/$songId.wav").toFile()
                val songFile = Paths.get("../media/magic.wav").toFile()

            } catch (e: SocketException) {
                println("Stop serving...")
                return
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }

        }

    }

    companion object {
        const val PORT = 5005
    }

}