/* Copyright (c) 2014 Raymond Xu 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.*/
package preloader.classpath.element;

import preloader.classpath.visitor.ClassPathVisitor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * One ClassPathElement represents one path in the CLASSPATH environment variable
 * Created by Raymond on 2014/10/19.
 */
public class ClassPathElement implements ClassPathNode{
    private static final Pattern JARFILE = Pattern.compile(".+\\.jar(\\.[0-9]+)?$", Pattern.CASE_INSENSITIVE);

    private URI path;

    public URI getPath(){
        return path;
    }

    public ClassPathElement(URI path){
        try {
            this.path = new URI(path.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean accept(ClassPathVisitor visitor) {
        if(visitor.visitEnter(this)){
            if(JARFILE.matcher(path.toString().trim()).matches()){
                try (JarFile jar = new JarFile(new File(path))){
                    Enumeration<JarEntry> files = jar.entries();
                    while(files.hasMoreElements()){
                        JarEntry jarEntry = files.nextElement();
                        String name = jarEntry.getName().trim();
                        InputStream is = jar.getInputStream(jarEntry);
                        handleClasspathFile(visitor, name);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else{
                //plain file or a directory
                File file = new File(path);
                handleFile(visitor, file, true, null);
            }
        }
        return visitor.visitLeave(this);
    }

    private void handleFile(ClassPathVisitor visitor, File file, boolean isTop, String path){
        String name = file.getName().trim();
        String newPath = isTop ? null : path == null ? name : path + "/" + name;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f : files){
                handleFile(visitor, f, false, newPath);
            }
        } else{
            handleClasspathFile(visitor, newPath);
        }
    }

    private void handleClasspathFile(ClassPathVisitor visitor, String fileName){
        if(fileName.endsWith(".class")){
            new ClassFile(fileName).accept(visitor);
        }else if(fileName.endsWith(".properties")){
            new PropertiesFile(fileName).accept(visitor);
        }else{
            new ResourceFile(fileName).accept(visitor);
        }
    }
}
