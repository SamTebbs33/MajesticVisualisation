import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samtebbs on 15/02/2016.
 */
public class EntityVisualisation {

    public static final int NUM_SITES = 30, NUM_EACH_DOMAIN = 5;
    public static final float X_SIZE = 1080, Y_SIZE = 800;
    private static final long UPDATE_DELAY = 10;
    static ArrayList<SiteEntity> sites = new ArrayList<>(NUM_SITES);
    static Frame frame;
    static HashMap<String, Integer> domainCounts = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        parseFile();
        updateLoop();
    }

    private static void updateLoop() throws InterruptedException {
        while(true) {
            // Update each entity
            for(int i = 0; i < sites.size(); i++) {
                SiteEntity site = sites.get(i);
                // Get radius, centre x and y
                float r = site.circle.width / 2, x = site.circle.x + r, y = site.circle.y + r;
                // Check for collisions between entities
                for (int j = 0; j < sites.size(); j++) {
                    SiteEntity site2 = sites.get(j);
                    if (j == i) continue;
                    float r2 = site2.circle.width / 2, x2 = site2.circle.x + r2, y2 = site2.circle.y + r2;
                    float distSquare = (x2 - x) * (x2 - x) + (y2 - y) * (y2 - y);
                    // Check if a collision occured
                    if (distSquare <= (r + r2) * (r + r2)) {
                        // If they have the same TLD, then attempt to breed
                        if(site.tld.equalsIgnoreCase(site2.tld)) {
                            if(site.breedTimer == 0 && site2.breedTimer == 0) {
                                SiteEntity child = SiteEntity.generate(site, site2);
                                child.breedTimer = 750;
                                site.breedTimer = 750;
                                site2.breedTimer = 750;
                                child.circle.x = SiteEntity.mean(site.circle.x, site2.circle.x);
                                child.circle.y = SiteEntity.mean(site.circle.y, site2.circle.y);
                                sites.add(child);
                                child.family = new FamilyTree(site.family, site2.family, child);
                                site.family.addChild(child.family);
                                site2.family.addChild(child.family);
                            }
                        } else {
                            site.onFoeCollision(site2);
                            site2.onFoeCollision(site);
                        }
                    }
                }
                // Check for collisions with the side of the screen
                if(x + r >= frame.getWidth() || x - r <= 0) site.velocity.reverseX();
                if(y + r >= frame.getHeight() || y - r <= 0) site.velocity.reverseY();
                site.circle.x += site.velocity.x;
                site.circle.y += site.velocity.y;
                // Grow with a random chance
                if(Math.random() <= 0.0001) site.grow((float) (Math.random() * 100));
                if(site.breedTimer > 0) site.breedTimer--;
                // Remove this site if its width has gone below zero
                if(site.circle.width <= 0) {
                    sites.remove(i--);
                }
            }
            frame.repaint();
            Thread.sleep(UPDATE_DELAY);
        }
    }

    private static void parseFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("majestic_10000v2.csv"));
        String line;
        // Skip the first line with the field names
        reader.readLine();
        int c = 0;
        frame = new Frame();
        while(c < NUM_SITES && (line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String tld = fields[3];
            // Check if this site can be added to the map. The number of sites for a particular TLD cannot go above NUM_EACH_DOMAIN
            if(!domainCounts.containsKey(tld) || domainCounts.get(tld) < NUM_EACH_DOMAIN) {
                SiteEntity site = new SiteEntity(fields[2], tld, Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[4]), Integer.parseInt(fields[5]));
                sites.add(site);
                c++;
                // Construct the site's family tree
                site.family = new FamilyTree(null, null, site);
                if(!domainCounts.containsKey(tld)) domainCounts.put(tld, 1);
                else domainCounts.put(tld, domainCounts.get(tld) + 1);
            }
        }
        frame.setVisible(true);
    }

}
