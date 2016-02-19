import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * Created by samtebbs on 17/02/2016.
 * A recursive data structure that represents the family tree of a particular entity.
 * Family trees can be recursed over to display the entity's entire family history and offspring.
 */
public class FamilyTree {

    private FamilyTree parent1, parent2;
    private LinkedList<FamilyTree> children = new LinkedList<>();
    private SiteEntity root;

    public FamilyTree(FamilyTree parent1, FamilyTree parent2, SiteEntity root) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.root = root;
    }

    public void addChild(FamilyTree site) {
        children.add(site);
    }

    public FamilyTree getParent1() {
        return parent1;
    }

    public FamilyTree getParent2() {
        return parent2;
    }

    public LinkedList<FamilyTree> getChildren() {
        return children;
    }

    public SiteEntity getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String prefix) {
        StringBuffer sb = new StringBuffer(root.toString(prefix));
        for(FamilyTree child : children) sb.append(String.format("%n%sChild: %s", prefix, child.toString(prefix + "\t")));
        return sb.append("\n").toString();
    }
}
