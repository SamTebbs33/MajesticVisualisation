import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by samtebbs on 17/02/2016.
 */
public class CategoryVisualisation {

    static String key = "", secret = "", endpoint = "https://api.webshrinker.com/categories/v2/";
    static final String[] tableColumns = new String[]{"Domain", "Global Rank", "TLD Rank", "Ref IPs", "Ref Subnets"};
    static Base64.Encoder encoder = Base64.getEncoder();
    // Maps categories to maps of domains to sites
    static HashMap<String, HashMap<String, LinkedList<Site>>> categoryMap = new HashMap<>();
    // Couldn't use all sites due to API request limits
    static final int NUM_SITES = 40;
    static JTable table;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("majestic_10000v2.csv"));
        String line;
        int c = 0;
        // Read in all sites
        while (c++ < NUM_SITES && (line = reader.readLine()) != null) {
            Site site = getSite(line.split(","));
            for (String category : site.categories) {
                addSiteToCategory(site, category);
            }
        }
        // Print each category
        System.out.println("Categories:");
        for(String category : categoryMap.keySet()) System.out.println(category);
        launchGUI();
    }

    private static void addSiteToCategory(Site site, String category) {
        if (categoryMap.containsKey(category)) {
            HashMap<String, LinkedList<Site>> domainMap = categoryMap.get(category);
            if (domainMap.containsKey(site.tld)) domainMap.get(site.tld).add(site);
            else {
                LinkedList<Site> siteList = new LinkedList<>();
                siteList.add(site);
                domainMap.put(site.tld, siteList);
            }
        } else {
            LinkedList<Site> siteList = new LinkedList<>();
            siteList.add(site);
            HashMap<String, LinkedList<Site>> domainMap = new HashMap<>();
            domainMap.put(site.tld, siteList);
            categoryMap.put(category, domainMap);
        }
    }

    public static void launchGUI() {
        System.out.println("Launch");
        SiteFrame frame = new SiteFrame();
        table = frame.table;
        frame.show();
    }

    public static Site getSite(String[] fields) {
        String domain = fields[2];
        // The below code fetches the site's categories from the API, but is now unused as the categories are saved in the majestic_10000.csv file due to API request limits
       /* System.out.println(domain);
        URL url = new URL(String.format("%s%s", endpoint, encoder.encodeToString(("http://www." + domain).getBytes())));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", String.format("Basic %s", encoder.encodeToString((key + ":" + secret).getBytes())));
        JSONObject obj = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
        JSONArray categories = (JSONArray) ((JSONObject)((JSONArray)obj.get("data")).get(0)).get("categories");*/
        String[] categories = new String[fields.length - 12];
        for (int i = 0; i < fields.length - 12; i++) categories[i] = fields[i + 12];
        return new Site(domain, categories, fields[3], Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[4]), Integer.parseInt(fields[5]));
    }

    public static void onSearch(JTextField textField) {
        String category = textField.getText();
        if (categoryMap.containsKey(category)) {
            // Get all TLDs in the category
            HashMap<String, LinkedList<Site>> domainMap = categoryMap.get(category);
            LinkedList<Site> sites = null;
            int most = 0;
            // Get the TLD with the most sites
            for (String tld : domainMap.keySet()) {
                LinkedList<Site> list = domainMap.get(tld);
                if (list.size() > most) {
                    most = list.size();
                    sites = list;
                }
            }
            // Add the rows to the table
            if (sites != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setNumRows(0);
                for (Site site : sites) {
                    model.addRow(new Object[]{site.domain, site.rank, site.tldRank, site.refIPs, site.refSubNets});
                }
            }
        } else JOptionPane.showMessageDialog(null, "Unrecognised category");
    }

}
