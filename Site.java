/**
 * Created by samtebbs on 19/02/2016.
 */
public class Site {
    public String domain, tld;
    public int rank, tldRank, refSubNets, refIPs;
    public String[] categories;

    public Site(String domain, String[] categories, String tld, int rank, int tldRank, int refSubNets, int refIPs) {
        this.categories = categories;
        this.domain = domain;
        this.tld = tld;
        this.rank = rank;
        this.tldRank = tldRank;
        this.refSubNets = refSubNets;
        this.refIPs = refIPs;
    }

    @Override
    public String toString() {
        return domain;
    }
}
