package org.at.cig.common;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by otarasenko on 5/4/15.
 */
public class State extends HashSet<Object> {

    public void merge(Set<?> integers) {
        addAll(integers);
    }
}
