import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by samtebbs on 15/02/2016.
 */
public class Frame extends JFrame {

    public Frame() throws HeadlessException {
        super("SiteEntity simulator");
        this.setLayout(new BorderLayout());
        this.setSize((int) EntityVisualisation.X_SIZE, (int) EntityVisualisation.Y_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new SiteView(), BorderLayout.CENTER);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                SiteEntity site = getSite(x, y);
                if(site != null) {
                    System.out.println(site.family);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    private SiteEntity getSite(int x, int y) {
        // Check if the x and y positions intersect with any entities
        for(SiteEntity site : EntityVisualisation.sites) {
            if(x >= site.circle.x && x <= site.circle.x + site.circle.width) {
                if(y >= site.circle.y && y <= site.circle.y + site.circle.height) return site;
            }
        }
        return null;
    }
}

class SiteView extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < EntityVisualisation.sites.size(); i++) {
            SiteEntity site = EntityVisualisation.sites.get(i);
            g2d.setColor(Color.BLUE);
            g2d.fill(site.circle);
            g2d.setColor(Color.WHITE);
            // Print the site's domain in the entity's centre
            g2d.setFont(new Font("default", Font.BOLD, (int)(site.circle.width / (site.domain.length() + 3)) * 2));
            g2d.drawString(site.domain, site.circle.x + 5, site.circle.y + site.circle.height / 2);
        }
    }
}
