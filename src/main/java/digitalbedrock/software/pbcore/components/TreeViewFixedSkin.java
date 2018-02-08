package digitalbedrock.software.pbcore.components;

import javafx.scene.control.IndexedCell;
import javafx.scene.control.TreeView;

public class TreeViewFixedSkin extends com.sun.javafx.scene.control.skin.TreeViewSkin {

    private final TreeView treeView;

    public TreeViewFixedSkin(TreeView treeView) {
        super(treeView);

        this.treeView = treeView;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        double computedHeight = topInset + bottomInset;
        System.out.println(" ");
        System.out.println(flow.getCellCount());
        for (int i = 0; i < flow.getCellCount(); i++) {
            final IndexedCell cell = flow.getCell(i);
            if (!cell.isEmpty()) {
                computedHeight += cell.getHeight();
                System.out.println(cell.getHeight());
            }
        }

        return computedHeight;
    }
}
