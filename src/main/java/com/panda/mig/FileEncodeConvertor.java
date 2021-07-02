package com.panda.mig;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEncodeConvertor {

    public static void main(String[] args) {
        File file = new File("/Users/pandamig/IdeaProjects/project/src/main/java");
        prepareFile(file);
        convertFile(file,"GBK","UTF-8");
        delBakFile(file);

    }
    public static void prepareFile(File dir){
        System.out.println("准备目录["+dir.getName()+"]");
        File[] files = dir.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                prepareFile(f);
            }else if(!f.getName().endsWith(".bak")){
                String oriPath = f.getAbsolutePath();
                File bakFile = new File(oriPath+".bak");
                f.renameTo(bakFile);
                System.out.println("准备备份文件["+bakFile.getName()+"]");
            }
        }
    }

    public static void convertFile(File dir,String orgEncode,String newEncode){
        System.out.println("开启目录["+dir.getName()+"]["+orgEncode+"]["+newEncode+"]");
        File[] files = dir.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                convertFile(f,orgEncode,newEncode);
            }else if(f.getName().endsWith(".bak")){
                String oriPath = f.getAbsolutePath();
                File newFile = new File(oriPath.substring(0, oriPath.length() - 4));
                try{
                    FileUtils.writeStringToFile(newFile,FileUtils.readFileToString(f,orgEncode),newEncode);
                    System.out.println("文件转码["+newFile.getName()+"]");
                }catch (IOException e){
                    System.out.println("文件编码转换失败["+f.getName()+"]["+newFile.getName()+"]["+e.getMessage()+"]");
                }
            }
        }
    }

    public static void delBakFile(File dir){
        System.out.println("准备清理目录["+dir.getName()+"]");
        File[] files = dir.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                delBakFile(f);
            }else if(f.getName().endsWith(".bak")){
                try {
                    FileUtils.forceDelete(f);
                    System.out.println("清理备份文件["+f.getName()+"]");
                } catch (IOException e) {
                    System.out.println("清理备份文件失败["+f.getName()+"]["+e.getMessage()+"]");
                }
            }
        }
    }
}
