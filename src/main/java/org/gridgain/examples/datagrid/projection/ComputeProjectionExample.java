/* @java.file.header */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.examples.datagrid.projection;

import org.gridgain.examples.datagrid.*;
import org.gridgain.grid.*;
import org.gridgain.grid.lang.*;

/**
 * Demonstrates new functional APIs.
 * <p>
 * Remote nodes should always be started as follows:
 * {@code 'ggstart.{sh|bat} config/example-cache.xml'}.
 * <p>
 * Alternatively you can run {@link CacheExampleNodeStartup} in another JVM which will start GridGain node
 * with {@code config/example-cache.xml} configuration.
 */
public class ComputeProjectionExample {
    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws GridException If example execution failed.
     */
    public static void main(String[] args) throws Exception {
        try (Grid grid = GridGain.start("config/example-cache.xml")) {
            if (grid.nodes().size() < 2) {
                System.err.println("Must have at least 2 nodes to run this example.");

                return;
            }

            System.out.println();
            System.out.println("Compute projection example started.");

            // Say hello to all nodes in the grid, including local node.
            // Note, that Grid itself also implements GridProjection.
            sayHello(grid);

            // Say hello to all remote nodes.
            sayHello(grid.forRemotes());

            // Pick random node out of remote nodes.
            GridProjection randomNode = grid.forRemotes().forRandom();

            // Say hello to a random node.
            sayHello(randomNode);

            // Say hello to all nodes residing on the same host with random node.
            sayHello(grid.forHost(randomNode.node()));

            // Say hello to all nodes that have current CPU load less than 50%.
            sayHello(grid.forPredicate(new GridPredicate<GridNode>() {
                @Override public boolean apply(GridNode n) {
                    return n.metrics().getCurrentCpuLoad() < 0.5;
                }
            }));
        }
    }

    /**
     * Print 'Hello' message on remote grid nodes.
     *
     * @param prj Grid projection.
     * @throws GridException If failed.
     */
    private static void sayHello(final GridProjection prj) throws GridException {
        // Print out hello message on all projection nodes.
        prj.compute().broadcast(
            new GridRunnable() {
                @Override public void run() {
                    // Print ID of remote node on remote node.
                    System.out.println(">>> Hello Node: " + prj.grid().localNode().id());
                }
            }
        ).get();
    }
}
