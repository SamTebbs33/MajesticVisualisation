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
The category visualisation program shows the most popular TLD and websites for a certain category.

### Compiling
```
javac -cp ".:json_simple.jar" CategoryVisualisation.java
```

### Running
```
java -cp ".:json_simple.jar" CategoryVisualisation
```
