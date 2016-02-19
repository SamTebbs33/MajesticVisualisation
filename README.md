# MajesticVisualisation

## Entity visualisation
The entity visualisation program simulates a world where each entity represents a website within the Majestic data-set. When two entities collide, they spawn a new site if they both have the same TLD, otherwise the larger entity consumes some of the smaller entity. Clicking on an entity will print its family tree (excluding the parents) to the console.

### Compiling
```
javac EntityVisualisation.java
```

### Running
```
java EntityVisualisation
```

## Category visualisation
The category visualisation program shows the most popular TLD and websites for a certain category. For the first run, I used the json_simple library to retrieve the categories for each site from api.webshrinker.com, and due to api request limits I couldn't include all 10000 sites in the Majestic data-set, and had to save the categories in the majestic_10000.csv file (which I haven't included in the repo as it may be private data).

### Compiling
```
javac CategoryVisualisation.java
```

### Running
```
java CategoryVisualisation
```
