package com.epam.mjc.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;


public class FileReader {

    public Profile getDataFromFile(File file) {
        /*StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            while (reader.ready()) {
                result.append(reader.readLine().replace(": ", " ")).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       */
        StringBuilder result = new StringBuilder();
        try(RandomAccessFile raf = new RandomAccessFile(file, "r"); FileChannel fileChannel = raf.getChannel()){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(byteBuffer) > 0){
                byteBuffer.flip();
                for(int i = 0 ; i < byteBuffer.limit(); i++){
                    char symbol = (char) byteBuffer.get();
                    if(symbol == '\r') {
                        result.append(" ");
                    } else if(symbol != ':') {
                        result.append(symbol);
                    }
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = result.toString().split(" ");
        Profile profile = new Profile();
        profile.setName(split[1]);
        profile.setAge(Integer.parseInt(split[3]));
        profile.setEmail(split[5]);
        profile.setPhone(Long.parseLong(split[7]));
        return profile;
    }
}
