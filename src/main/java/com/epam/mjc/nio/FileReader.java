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
        StringBuilder result = new StringBuilder();
        try(RandomAccessFile raf = new RandomAccessFile(file, "r"); FileChannel fileChannel = raf.getChannel()){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (fileChannel.read(byteBuffer) > 0){
                byteBuffer.flip();
                for(int i = 0 ; i < byteBuffer.limit(); i++){
                    result.append((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = result.toString().split("[ :\\n\\r]");
        System.out.println(Arrays.toString(split));
        return new Profile(split[2], Integer.parseInt(split[6]), split[10], Long.parseLong(split[14]));
    }

    public static void main(String[] args) {
        FileReader reader = new FileReader();
        System.out.println(reader.getDataFromFile(new File("D:/MJCschool/stage1-module7-nio-task1/src/main/resources/Profile.txt")));
    }
}
