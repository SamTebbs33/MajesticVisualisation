import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Created by samtebbs on 15/02/2016.
 */
public class SiteEntity {

    public static Random rand = new Random();
    public int rank, tldRank, refSubNets, refIPs, breedTimer = 0;
    public Ellipse2D.Float circle;
    public Vector velocity = new Vector(Math.random(), Math.random());
    public static final float START_RADIUS = 100f;
    public String domain, tld;
    public FamilyTree family;

    public SiteEntity(String domain, String tld, int rank, int tldRank, int refSubNets, int refIPs) {
        this.rank = rank;
        this.tld = tld;
        this.domain = domain;
        this.tldRank = tldRank;
        this.refSubNets = refSubNets;
        this.refIPs = refIPs;
        float radius = (float) (START_RADIUS + Math.random() * 25f);
        circle = new Ellipse2D.Float(randFloat(EntityVisualisation.frame.getWidth()- radius * 2) + radius, randFloat(EntityVisualisation.frame.getHeight() - radius * 2) + radius, radius, radius);
    }

    private float randFloat(float max) {
        return rand.nextFloat() * max;
    }

    public void onFoeCollision(SiteEntity site) {
        velocity.reverse();
        // If this site's width is less than the others, then reduce this site's width
        if(circle.width < site.circle.width) {
            float toSubtract = site.circle.width - circle.width;
            grow(-toSubtract);
        }
    }

    public void grow(float v) {
        circle.width += v;
        circle.height += v;
    }

    public static SiteEntity generate(SiteEntity site, SiteEntity site2) {
        // Construct a new entity with the average values of the two sites
        int rank = mean(site.rank, site2.rank);
        int tldRank = site.tldRank;
        int refSubnets = mean(site.refSubNets, site2.refSubNets);
        int refIPs = mean(site.refIPs, site2.refIPs);
        StringBuffer domain = new StringBuffer();
        String dom1 = site.domain.substring(0, site.domain.indexOf('.')), dom2 = site2.domain.substring(0, site2.domain.indexOf('.'));
        int len = Math.max(dom1.length(), dom2.length());
        // Create a domain with a mix of each site's domains
        for(int i = 0; i < len; i++) {
            if(i >= dom1.length()) domain.append(dom2.charAt(i));
            else if(i >= dom2.length()) domain.append(dom1.charAt(i));
            else {
                if(rand.nextBoolean()) domain.append(dom1.charAt(i));
                else domain.append(dom2.charAt(i));
            }
        }
        domain.append('.').append(site.tld);
        return new SiteEntity(domain.toString(), site.tld, rank, tldRank, refSubnets, refIPs);
    }

    public static int mean(int a, int b) {
        return (a + b) / 2;
    }

    public static float mean(float a, float b) {
        return (a + b) / 2f;
    }

    public String toString(String prefix) {
        return prefix + domain;
    }
}

class Vector {
    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void reverseX() {
        x = -x;
    }

    public void reverseY() {
        y = -y;
    }

    public void reverse() {
        reverseX();
        reverseY();
    }
}
