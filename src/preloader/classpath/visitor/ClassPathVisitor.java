/* Copyright (c) 2014 Raymond Xu 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.*/
package preloader.classpath.visitor;

import preloader.classpath.ClassPathProcessor;
import preloader.classpath.element.ClassFile;
import preloader.classpath.element.ClassPathElement;
import preloader.classpath.element.PropertiesFile;
import preloader.classpath.element.ResourceFile;

/**
 * Created by Raymond on 2014/10/19.
 */
public interface ClassPathVisitor {
    /**
     * Action when we are going to process the ClassPathElement
     * @return a boolean return value is used to determine if we need to process this element or not.
     * Return true if yes, return false if no.
     */
    boolean visitEnter(ClassPathElement cpElement);

    /**
     * Action when we are going to leave the ClassPathElement
     * @return a boolean return value is used to determine if we need to continue processing the following element or not.
     * Return true if yes, return false if no.
     */
    boolean visitLeave(ClassPathElement cpElement);

    boolean visit(ClassFile classFile);

    boolean visit(PropertiesFile propFile);

    boolean visit(ResourceFile resourceFile);

    boolean visit(ClassPathProcessor processor);
}
