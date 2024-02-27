package javassist.bytecode.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class Subroutine {
    private int start;
    private List<Integer> callers = new ArrayList();
    private Set<Integer> access = new HashSet();

    public Subroutine(int i, int i2) {
        this.start = i;
        this.callers.add(Integer.valueOf(i2));
    }

    public void addCaller(int i) {
        this.callers.add(Integer.valueOf(i));
    }

    public int start() {
        return this.start;
    }

    public void access(int i) {
        this.access.add(Integer.valueOf(i));
    }

    public boolean isAccessed(int i) {
        return this.access.contains(Integer.valueOf(i));
    }

    public Collection<Integer> accessed() {
        return this.access;
    }

    public Collection<Integer> callers() {
        return this.callers;
    }

    public String toString() {
        return "start = " + this.start + " callers = " + this.callers.toString();
    }
}
