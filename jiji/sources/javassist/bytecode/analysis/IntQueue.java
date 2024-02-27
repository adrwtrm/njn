package javassist.bytecode.analysis;

import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
class IntQueue {
    private Entry head;
    private Entry tail;

    /* loaded from: classes2.dex */
    private static class Entry {
        private Entry next;
        private int value;

        private Entry(int i) {
            this.value = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add(int i) {
        Entry entry = new Entry(i);
        Entry entry2 = this.tail;
        if (entry2 != null) {
            entry2.next = entry;
        }
        this.tail = entry;
        if (this.head == null) {
            this.head = entry;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.head == null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int take() {
        Entry entry = this.head;
        if (entry != null) {
            int i = entry.value;
            Entry entry2 = this.head.next;
            this.head = entry2;
            if (entry2 == null) {
                this.tail = null;
            }
            return i;
        }
        throw new NoSuchElementException();
    }
}
