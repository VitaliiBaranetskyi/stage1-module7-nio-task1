package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder result = new StringBuilder();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r"); FileChannel fileChannel = raf.getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                for (int i = 0; i < byteBuffer.limit(); i++) {
                    char symbol = (char) byteBuffer.get();
                    if (symbol != ':') {
                        result.append(symbol);
                    }
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = new StringBuilder(result.toString().replace(System.getProperty("line.separator"), " "));
        String[] split = result.toString().split(" ");
        return new Profile(split[1], Integer.parseInt(split[3]), split[5], Long.parseLong(split[7]));
    }
}
