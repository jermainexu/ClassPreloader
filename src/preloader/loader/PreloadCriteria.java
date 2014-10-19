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
 * Working as an adaptor for class loader. The action for visit ClassFile is determined, i.e. Load that class!
 * Using this Criteria to determine whether we should skip the file or not.
 * Created by Raymond on 2014/10/19.
 */
public interface PreloadCriteria {
    boolean loadClassPathElement(ClassPathElement cpElement);

    boolean loadClassFile(ClassFile classFile);
}
