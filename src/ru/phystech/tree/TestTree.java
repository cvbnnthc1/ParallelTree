package ru.phystech.tree;

import java.util.*;

import static ru.phystech.tree.LockPartiallyExternalTree.*;

public class TestTree {
    public static void main(String[] args) throws InterruptedException {
        Node root = new Node(Integer.MAX_VALUE, null, null);
        List<Thread> testers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testers.add(new Tester(i, root));
        }
        for (Thread tester: testers) {
            tester.start();
        }
        for (Thread tester: testers) {
            tester.join();
        }
    }

    static class Tester extends Thread {
        private final int number;
        private final Node root;

        Tester(int number, Node root) {
            this.number = number;
            this.root = root;
        }

        @Override
        public void run() {
                Set<Integer> added = new HashSet<>();
                Set<Integer> deleted = new HashSet<>();
                Random random = new Random();
                for (int j = 0; j < 1000; j++) {
                    int curVal = random.nextInt() % 10000 + number * 10000;
                    insert(root, curVal);
                    if (curVal % 3 == 0) {
                        delete(root, curVal);
                        deleted.add(curVal);
                    } else {
                        added.add(curVal);
                    }
                }
                boolean addRes = true;
                for (int curVal: added) {
                    addRes = addRes && contains(root, curVal);
                }
                boolean delRes = true;
                for (int curVal: deleted) {
                    delRes = delRes && !contains(root, curVal);
                }
                System.out.println(addRes);
                System.out.println(delRes);
        }
    }
}
