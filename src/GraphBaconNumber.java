//GraphBaconNumber.java   Lauren Dickman      2/18/2012
import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.String;
import java.util.List;

import bridges.base.*;
import bridges.connect.Bridges;
import bridges.data_src_dependent.ActorMovieIMDB;
import bridges.connect.DataSource;

import static bridges.base.Color.*;

//  This class implements computing the Bacon Number of an actor.
//  The Bacon number of an actor or actress is the number of degrees of
//  separation (see Six degrees of separation) they have from actor Kevin
//  Bacon, as defined by the game known as Six Degrees of Kevin Bacon.
//  It applies the Erdős number concept to the movie industry.
//
//  Source : Wikipedia

public class GraphBaconNumber {
    public static void main(String[] args) throws Exception {

        // Initialize BRIDGES with your credentials
        Bridges bridges = new Bridges(4, "LDICKMAN", "353700408687");

        // set title for visualization
        bridges.setTitle("Bacon Number: IMDB Actor-Movie Data");
        bridges.setDescription("Highlights the shortest path between two actors in a Movie Actor graph.");

        // use an adjacency list based graph
        GraphAdjListSimple<String> gr = new GraphAdjListSimple<>();

        // build the actor-movie graph; numPairs maxes out at 1813
        buildActorMovieGraph(gr, 1813, bridges);

        // TODO #1: Highlight the "Leonard_Nimoy" node and the movie
        // nodes he is connected to in color #1 (your choice); do the same for
        // "Kevin_Bacon_(I)" in color #2 (your choice).
        //
        //  To set node attributes, you can do something like
        //  gr.getVisualizer(key-val).setColor(color-name); you can also use RGB values
        //     to specify a color: e.g., bridges.base.Color(47, 95, 186)
        //     See: Bridges Color class documentation and bit.ly/googleColorPicker
        //
        //  To set link attributes, you can do something like
        //  gr.getLinkVisualizer(src-key-val, dest-key-val).setColor(color-name)
        //
        //  Similarly for other attributes (see the docs on LinkVisualizer
        //  and ElementVisualizer classes, Bridges website).
        // set the data structure handle, and visualize the input graph
//        bridges.setDataStructure(gr);
//        bridges.visualize();

        getBaconNumber(gr, "Kevin_Bacon_(I)", "Leonard_Nimoy");

        gr.getVisualizer("Leonard_Nimoy").setColor("yellow");
        gr.getVisualizer("Kevin_Bacon_(I)").setColor(new Color(230, 60, 179));

        for (Edge<String, String> edge_movie : gr.outgoingEdgeSetOf("Kevin_Bacon_(I)")) {
            for (Edge<String, String> edge2_movie : gr.outgoingEdgeSetOf("Leonard_Nimoy")) {
                gr.getLinkVisualizer(edge_movie.getFrom(), edge_movie.getTo()).setColor(new Color(230, 60, 179));
                gr.getVisualizer(edge_movie.getTo()).setColor(new Color(230, 60, 179));
                gr.getLinkVisualizer(edge2_movie.getFrom(), edge2_movie.getTo()).setColor("yellow");
                gr.getVisualizer(edge2_movie.getTo()).setColor("yellow");
            }
        }

        gr.getVisualizer("Christian_Slater").setColor("red");
        gr.getVisualizer("Murder_in_the_First_(1995)").setColor("red");
        gr.getVisualizer("Star_Trek_VI:_The_Undiscovered_Country_(1991)").setColor("red");

        gr.getLinkVisualizer("Christian_Slater","Star_Trek_VI:_The_Undiscovered_Country_(1991)").setColor("red");
        gr.getLinkVisualizer("Christian_Slater","Star_Trek_VI:_The_Undiscovered_Country_(1991)").setThickness(3.0f);

        gr.getLinkVisualizer("Star_Trek_VI:_The_Undiscovered_Country_(1991)","Leonard_Nimoy").setColor("red");
        gr.getLinkVisualizer("Star_Trek_VI:_The_Undiscovered_Country_(1991)","Leonard_Nimoy").setThickness(3.0f);

        gr.getLinkVisualizer("Murder_in_the_First_(1995)", "Christian_Slater").setColor("red");
        gr.getLinkVisualizer("Murder_in_the_First_(1995)", "Christian_Slater").setThickness(3.0f);

        gr.getLinkVisualizer("Kevin_Bacon_(I)", "Murder_in_the_First_(1995)").setColor("red");
        gr.getLinkVisualizer("Kevin_Bacon_(I)", "Murder_in_the_First_(1995)").setThickness(3.0f);

        bridges.setDataStructure(gr);
        bridges.visualize();
    }

    // numPairs determines how many actor-movie pairs will be included in the
    // graph (max value 1813)
    public static void buildActorMovieGraph (GraphAdjListSimple<String> gr,
                                             int numPairs,
                                             Bridges bridges) {

        // get the actor movie IMDB data through the BRIDGES API
        try {

            // Get a List of ActorMovieIMDB objects from Bridges
            DataSource ds = bridges.getDataSource();
            List<ActorMovieIMDB> actor_list =
                    ds.getActorMovieIMDBData(numPairs);

            String actor, movie;
            for (int k = 0; k < actor_list.size(); k++) {

                // get the actor and movie names
                actor = actor_list.get(k).getActor();
                movie = actor_list.get(k).getMovie();

                // our graph needs to have a unique set of actors and movies;
                // so create the actor and movie vertices only if they don't already
                // exist; use a Java HashMap object to check for that

                // vertices is a map, each key is a String (actor name or movie title)
                HashMap<String, Element<String>> vertices = gr.getVertices();

                // add new actor vertex to the graph if it does not already exist
                if (!vertices.containsKey(actor))
                    gr.addVertex(actor, actor);

                // add new movie vertex to the graph if it does not already exist
                if (!vertices.containsKey(movie))
                    gr.addVertex(movie, movie);

                // add the edge
                // undirected graph is simulated with directed edges in both directions
                gr.addEdge(actor, movie);
                gr.addEdge(movie, actor);
            }
        }
        catch (Exception exc) {
        }
    }
    //
    // Computes the Bacon Number of a an actor (#links that takes you from the
    // source actor to the destination actor.
    //
    public static int getBaconNumber (
            GraphAdjListSimple<String> gr,  // input graph
            String src_actor,               // source actor
            String dest_actor)              // destination actor
    {
        // THIS IS FOR LATER -- not "PART A"
        // TODO #2: Perform a BFS traversal of the graph from src_actor. You will
        // need to maintain distance information (basically the number of links from
        // the source actor until the destination node is reached). Keep
        // parent information as the traversal progresses; once the destination
        // node is found, you will use the parent pointers to follow them to
        // the source node (source node's parent is null).

        // TODO #3: Highlight all edges and vertices on the shortest path
        // between src_actor and dest_actor; Make the nodes in the path bigger,
        // and the links thicker so that it stands out.

        return 0;
    }
} // class GraphBaconNumber
