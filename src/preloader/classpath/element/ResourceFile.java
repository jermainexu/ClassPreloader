/* Copyright (c) 2014 Raymond Xu 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.*/
package preloader.classpath.element;

import preloader.classpath.visitor.ClassPathVisitor;

/**
 * Created by Raymond on 2014/10/19.
 */
public class ResourceFile implements ClassPathNode{
    private String fileName;

    public ResourceFile(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }

    @Override
    public boolean accept(ClassPathVisitor visitor) {
        return visitor.visit(this);
    }
}
