/* Copyright (c) 2014 Raymond Xu 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.*/
package preloader.loader;

import preloader.classpath.ClassPathProcessor;
import preloader.classpath.element.ClassFile;
import preloader.classpath.element.ClassPathElement;
import preloader.classpath.visitor.ClassPathVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Raymond on 2014/10/19.
 */
public class ClassPreloader {
    private ClassPathProcessor processor;
    private PreloadCriteria criteria;
    private PreloadCallBack callback;
    private Set<String> loadedClasses;


    public ClassPreloader(ClassLoader classLoader, PreloadCriteria criteria, PreloadCallBack callback){
        this.processor = new ClassPathProcessor(classLoader);
        this.criteria = criteria;
        this.callback = callback;
        this.loadedClasses = new HashSet<>();
    }

    public void preload(){
        processor.accept(new PreloaderVisitor());
    }

    class PreloaderVisitor extends ClassPathVisitorAdapter {
        @Override
        public boolean visitEnter(ClassPathElement cpElement) {
            return criteria.loadClassPathElement(cpElement);
        }

        @Override
        public boolean visit(ClassFile classFile) {
            String className = classFile.getClassName();
            if(criteria.loadClassFile(classFile) && !loadedClasses.contains(className)){
                loadedClasses.add(className);
                try {
                    processor.getClassLoader().loadClass(className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                callback.classLoaded(className);
                return true;
            }
            return false;
        }
    }
}


