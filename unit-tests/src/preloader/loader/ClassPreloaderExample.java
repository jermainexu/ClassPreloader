/* Copyright (c) 2014 Raymond Xu 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.*/
package preloader.loader;

import preloader.classpath.element.ClassFile;
import preloader.classpath.element.ClassPathElement;

/**
 * Created by Raymond on 2014/10/19.
 */
public class ClassPreloaderExample {
    public static void main(String[] args){
        ClassPreloader preloader = new ClassPreloader(Thread.currentThread().getContextClassLoader(),
                new PreloadCriteria(){
                    @Override
                    public boolean loadClassPathElement(ClassPathElement cpElement) {
                        return true;
                    }

                    @Override
                    public boolean loadClassFile(ClassFile classFile) {
                        return classFile.getClassName().endsWith("ClassPathProcessor");
                    }
                },
                new PreloadCallBack(){
                    @Override
                    public void classLoaded(String className) {
                        System.out.println(className + " already loaded");
                    }
                });
        preloader.preload();
    }
}
