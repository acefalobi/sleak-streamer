package com.aceinteract.sleakstreamer.client

import java.io.*
import java.net.Socket
import javax.sound.sampled.*

class Client(port: Int = PORT) {

    private val socket = Socket("127.0.0.1", port)

    fun startAudioStream(songId: String) {

        if (socket.isConnected) {

            requestInputStream(songId)

            val audioInputStream = AudioSystem.getAudioInputStream(BufferedInputStream(socket.getInputStream()))

            val audioFormat = AudioFormat(44100.0F, 16, 2, true, false)

            val info = DataLine.Info(SourceDataLine::class.java, audioFormat)
            val soundLine = AudioSystem.getLine(info) as SourceDataLine
            soundLine.open(audioFormat)
            soundLine.start()

            writeBytesToDataLine(audioInputStream, soundLine)

        }

    }

    private fun requestInputStream(songId: String) {

        if (socket.isConnected) {
            val bufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            bufferedWriter.appendln(songId)
            bufferedWriter.flush()
        }

    }

    private fun writeBytesToDataLine(inputStream: AudioInputStream, dataLine: SourceDataLine) {

        val buffer = ByteArray(AUDIO_BUFFER)

        var noOfBytesRead: Int = AUDIO_BUFFER
        while (noOfBytesRead > 0) {

            noOfBytesRead = inputStream.read(buffer)

            dataLine.write(buffer, 0, noOfBytesRead)

            println("$noOfBytesRead bytes read")

        }

    }


    companion object {
        private const val PORT = 5005

        private const val AUDIO_BUFFER = 4096
    }
}