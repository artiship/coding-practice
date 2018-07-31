package com.me.coreJava;

import com.sun.javafx.util.Logging;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class JavaClassLoaders {
    @Test public void
    printClassloaders() {
        
        assertNull(ArrayList.class.getClassLoader());

        assertThat(Logging.class.getClassLoader().toString(),
                startsWith("sun.misc.Launcher$ExtClassLoader"));

        assertThat(JavaClassLoaders.class.getClassLoader().toString(),
                startsWith("sun.misc.Launcher$AppClassLoader@"));
    }
}
