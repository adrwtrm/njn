package javassist.bytecode.analysis;

import java.util.ArrayList;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.stackmap.BasicBlock;

/* loaded from: classes2.dex */
public class ControlFlow {
    private Block[] basicBlocks;
    private CtClass clazz;
    private Frame[] frames;
    private MethodInfo methodInfo;

    public ControlFlow(CtMethod ctMethod) throws BadBytecode {
        this(ctMethod.getDeclaringClass(), ctMethod.getMethodInfo2());
    }

    public ControlFlow(CtClass ctClass, MethodInfo methodInfo) throws BadBytecode {
        this.clazz = ctClass;
        this.methodInfo = methodInfo;
        this.frames = null;
        Block[] blockArr = (Block[]) new BasicBlock.Maker() { // from class: javassist.bytecode.analysis.ControlFlow.1
            @Override // javassist.bytecode.stackmap.BasicBlock.Maker
            protected BasicBlock makeBlock(int i) {
                return new Block(i, ControlFlow.this.methodInfo);
            }

            @Override // javassist.bytecode.stackmap.BasicBlock.Maker
            protected BasicBlock[] makeArray(int i) {
                return new Block[i];
            }
        }.make(methodInfo);
        this.basicBlocks = blockArr;
        if (blockArr == null) {
            this.basicBlocks = new Block[0];
        }
        int length = this.basicBlocks.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            Block block = this.basicBlocks[i];
            block.index = i;
            block.entrances = new Block[block.incomings()];
            iArr[i] = 0;
        }
        for (int i2 = 0; i2 < length; i2++) {
            Block block2 = this.basicBlocks[i2];
            for (int i3 = 0; i3 < block2.exits(); i3++) {
                Block exit = block2.exit(i3);
                Block[] blockArr2 = exit.entrances;
                int i4 = exit.index;
                int i5 = iArr[i4];
                iArr[i4] = i5 + 1;
                blockArr2[i5] = block2;
            }
            for (Catcher catcher : block2.catchers()) {
                Block block3 = catcher.node;
                Block[] blockArr3 = block3.entrances;
                int i6 = block3.index;
                int i7 = iArr[i6];
                iArr[i6] = i7 + 1;
                blockArr3[i7] = block2;
            }
        }
    }

    public Block[] basicBlocks() {
        return this.basicBlocks;
    }

    public Frame frameAt(int i) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(this.clazz, this.methodInfo);
        }
        return this.frames[i];
    }

    public Node[] dominatorTree() {
        int length = this.basicBlocks.length;
        if (length == 0) {
            return null;
        }
        Node[] nodeArr = new Node[length];
        boolean[] zArr = new boolean[length];
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            nodeArr[i] = new Node(this.basicBlocks[i]);
            zArr[i] = false;
        }
        Access access = new Access(nodeArr) { // from class: javassist.bytecode.analysis.ControlFlow.2
            @Override // javassist.bytecode.analysis.ControlFlow.Access
            BasicBlock[] exits(Node node) {
                return node.block.getExit();
            }

            @Override // javassist.bytecode.analysis.ControlFlow.Access
            BasicBlock[] entrances(Node node) {
                return node.block.entrances;
            }
        };
        nodeArr[0].makeDepth1stTree(null, zArr, 0, iArr, access);
        do {
            for (int i2 = 0; i2 < length; i2++) {
                zArr[i2] = false;
            }
        } while (nodeArr[0].makeDominatorTree(zArr, iArr, access));
        Node.setChildren(nodeArr);
        return nodeArr;
    }

    public Node[] postDominatorTree() {
        boolean z;
        int length = this.basicBlocks.length;
        if (length == 0) {
            return null;
        }
        Node[] nodeArr = new Node[length];
        boolean[] zArr = new boolean[length];
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            nodeArr[i] = new Node(this.basicBlocks[i]);
            zArr[i] = false;
        }
        Access access = new Access(nodeArr) { // from class: javassist.bytecode.analysis.ControlFlow.3
            @Override // javassist.bytecode.analysis.ControlFlow.Access
            BasicBlock[] exits(Node node) {
                return node.block.entrances;
            }

            @Override // javassist.bytecode.analysis.ControlFlow.Access
            BasicBlock[] entrances(Node node) {
                return node.block.getExit();
            }
        };
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (nodeArr[i3].block.exits() == 0) {
                i2 = nodeArr[i3].makeDepth1stTree(null, zArr, i2, iArr, access);
            }
        }
        do {
            for (int i4 = 0; i4 < length; i4++) {
                zArr[i4] = false;
            }
            z = false;
            for (int i5 = 0; i5 < length; i5++) {
                if (nodeArr[i5].block.exits() == 0 && nodeArr[i5].makeDominatorTree(zArr, iArr, access)) {
                    z = true;
                }
            }
        } while (z);
        Node.setChildren(nodeArr);
        return nodeArr;
    }

    /* loaded from: classes2.dex */
    public static class Block extends BasicBlock {
        public Object clientData;
        Block[] entrances;
        int index;
        MethodInfo method;

        Block(int i, MethodInfo methodInfo) {
            super(i);
            this.clientData = null;
            this.method = methodInfo;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // javassist.bytecode.stackmap.BasicBlock
        public void toString2(StringBuffer stringBuffer) {
            super.toString2(stringBuffer);
            stringBuffer.append(", incoming{");
            int i = 0;
            while (true) {
                Block[] blockArr = this.entrances;
                if (i < blockArr.length) {
                    stringBuffer.append(blockArr[i].position).append(", ");
                    i++;
                } else {
                    stringBuffer.append("}");
                    return;
                }
            }
        }

        BasicBlock[] getExit() {
            return this.exit;
        }

        public int index() {
            return this.index;
        }

        public int position() {
            return this.position;
        }

        public int length() {
            return this.length;
        }

        public int incomings() {
            return this.incoming;
        }

        public Block incoming(int i) {
            return this.entrances[i];
        }

        public int exits() {
            if (this.exit == null) {
                return 0;
            }
            return this.exit.length;
        }

        public Block exit(int i) {
            return (Block) this.exit[i];
        }

        public Catcher[] catchers() {
            ArrayList arrayList = new ArrayList();
            for (BasicBlock.Catch r1 = this.toCatch; r1 != null; r1 = r1.next) {
                arrayList.add(new Catcher(r1));
            }
            return (Catcher[]) arrayList.toArray(new Catcher[arrayList.size()]);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class Access {
        Node[] all;

        abstract BasicBlock[] entrances(Node node);

        abstract BasicBlock[] exits(Node node);

        Access(Node[] nodeArr) {
            this.all = nodeArr;
        }

        Node node(BasicBlock basicBlock) {
            return this.all[((Block) basicBlock).index];
        }
    }

    /* loaded from: classes2.dex */
    public static class Node {
        private Block block;
        private Node[] children;
        private Node parent = null;

        Node(Block block) {
            this.block = block;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer("Node[pos=");
            stringBuffer.append(block().position());
            stringBuffer.append(", parent=");
            Node node = this.parent;
            stringBuffer.append(node == null ? "*" : Integer.toString(node.block().position()));
            stringBuffer.append(", children{");
            int i = 0;
            while (true) {
                Node[] nodeArr = this.children;
                if (i < nodeArr.length) {
                    stringBuffer.append(nodeArr[i].block().position()).append(", ");
                    i++;
                } else {
                    stringBuffer.append("}]");
                    return stringBuffer.toString();
                }
            }
        }

        public Block block() {
            return this.block;
        }

        public Node parent() {
            return this.parent;
        }

        public int children() {
            return this.children.length;
        }

        public Node child(int i) {
            return this.children[i];
        }

        int makeDepth1stTree(Node node, boolean[] zArr, int i, int[] iArr, Access access) {
            int i2 = this.block.index;
            if (zArr[i2]) {
                return i;
            }
            zArr[i2] = true;
            this.parent = node;
            BasicBlock[] exits = access.exits(this);
            if (exits != null) {
                int i3 = i;
                for (BasicBlock basicBlock : exits) {
                    i3 = access.node(basicBlock).makeDepth1stTree(this, zArr, i3, iArr, access);
                }
                i = i3;
            }
            int i4 = i + 1;
            iArr[i2] = i;
            return i4;
        }

        boolean makeDominatorTree(boolean[] zArr, int[] iArr, Access access) {
            boolean z;
            Node ancestor;
            int i = this.block.index;
            if (zArr[i]) {
                return false;
            }
            zArr[i] = true;
            BasicBlock[] exits = access.exits(this);
            if (exits != null) {
                z = false;
                for (BasicBlock basicBlock : exits) {
                    if (access.node(basicBlock).makeDominatorTree(zArr, iArr, access)) {
                        z = true;
                    }
                }
            } else {
                z = false;
            }
            BasicBlock[] entrances = access.entrances(this);
            if (entrances != null) {
                for (BasicBlock basicBlock2 : entrances) {
                    Node node = this.parent;
                    if (node != null && (ancestor = getAncestor(node, access.node(basicBlock2), iArr)) != this.parent) {
                        this.parent = ancestor;
                        z = true;
                    }
                }
            }
            return z;
        }

        private static Node getAncestor(Node node, Node node2, int[] iArr) {
            while (node != node2) {
                if (iArr[node.block.index] < iArr[node2.block.index]) {
                    node = node.parent;
                } else {
                    node2 = node2.parent;
                }
                if (node != null) {
                    if (node2 == null) {
                    }
                }
                return null;
            }
            return node;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setChildren(Node[] nodeArr) {
            int length = nodeArr.length;
            int[] iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = 0;
            }
            for (Node node : nodeArr) {
                Node node2 = node.parent;
                if (node2 != null) {
                    int i2 = node2.block.index;
                    iArr[i2] = iArr[i2] + 1;
                }
            }
            for (int i3 = 0; i3 < length; i3++) {
                nodeArr[i3].children = new Node[iArr[i3]];
            }
            for (int i4 = 0; i4 < length; i4++) {
                iArr[i4] = 0;
            }
            for (Node node3 : nodeArr) {
                Node node4 = node3.parent;
                if (node4 != null) {
                    Node[] nodeArr2 = node4.children;
                    int i5 = node4.block.index;
                    int i6 = iArr[i5];
                    iArr[i5] = i6 + 1;
                    nodeArr2[i6] = node3;
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class Catcher {
        private Block node;
        private int typeIndex;

        Catcher(BasicBlock.Catch r2) {
            this.node = (Block) r2.body;
            this.typeIndex = r2.typeIndex;
        }

        public Block block() {
            return this.node;
        }

        public String type() {
            return this.typeIndex == 0 ? "java.lang.Throwable" : this.node.method.getConstPool().getClassInfo(this.typeIndex);
        }
    }
}
