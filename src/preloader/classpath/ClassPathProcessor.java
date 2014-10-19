/* Copyright (c) 2014 Raymond Xu 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.*/
package preloader.classpath;

import preloader.classpath.element.ClassPathElement;
import preloader.classpath.element.ClassPathNode;
import preloader.classpath.visitor.ClassPathVisitor;
import preloader.loader.PreloadCallBack;
import preloader.loader.PreloadCriteria;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Raymond on 2014/10/19.
 */
public class ClassPathProcessor implements ClassPathNode{
    private ClassLoader classLoader;

    public ClassPathProcessor(){
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public ClassPathProcessor(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    private Set<URI> getClassPathEntries(ClassLoader classLoader){
        Set<URI> entries = new HashSet<>();
        ClassLoader parent = classLoader.getParent();
        if(parent != null){
            entries.addAll(getClassPathEntries(parent));
        }
        if(classLoader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader)classLoader;
            for(URL entry : urlClassLoader.getURLs()){
                URI uri;
                try{
                    uri = entry.toURI();
                }catch(URISyntaxException e){
                    throw new IllegalArgumentException(e);
                }
                entries.add(uri);
            }
        }
        return entries;
    }

    public boolean accept(ClassPathVisitor visitor){
        Set<URI> list = getClassPathEntries(classLoader);
        for(URI element : list){
            ClassPathElement cpElement = new ClassPathElement(element);
            if(!cpElement.accept(visitor)){
                break;
            }
        }
        return visitor.visit(this);
    }

    public ClassLoader getClassLoader(){
        return classLoader;
    }
}
