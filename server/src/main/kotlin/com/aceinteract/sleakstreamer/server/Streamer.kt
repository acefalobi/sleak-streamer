package com.aceinteract.sleakstreamer.server

import java.io.File
import java.io.FileInputStream
import java.net.Socket
import javax.sound.sampled.AudioSystem

class Streamer(private val songFile: File) {

    val sockets = ArrayList<Socket>()

    fun startStreaming() {


        val fileInputStream = FileInputStream(songFile)

        val audioInputStream = AudioSystem.getAudioInputStream(songFile)

        val bytesPerFrame = when {
            audioInputStream.format.frameSize == AudioSystem.NOT_SPECIFIED -> 1
            else -> audioInputStream.format.frameSize
        }
        val bufferSize = AUDIO_BUFFER * bytesPerFrame

        val buffer = ByteArray(bufferSize)

        var noOfBytesRead: Int = bufferSize
        while (noOfBytesRead > 0) {

            noOfBytesRead = fileInputStream.read(buffer)

            sockets.forEach { it.getOutputStream().write(buffer) }

            println("$noOfBytesRead bytes written")
            Thread.sleep(1000)

        }

    }

    companion object {

        private const val AUDIO_BUFFER = 4096

    }

}