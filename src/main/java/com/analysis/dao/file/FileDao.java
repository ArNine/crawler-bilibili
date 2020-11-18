package com.analysis.dao.file;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDao {




    @Test
    public void run() {
        outPutStrToFile("F:\\text.txt", "123");
    }

    public void outPutStrToFile(String fileName, String content) {
        try{
            File file =new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            //使用true，即进行append file
            FileWriter fileWritter = new FileWriter(fileName,true);
            fileWritter.write(content);
            fileWritter.close();
            System.out.println("file finish");
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
