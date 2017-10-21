package data;

import java.util.ArrayList;

/**
 * Optimization parameters
 * @author boukhatem
 *
 */
public class Parameters
{
	/**
	 * The time of the optimization in second
	 */
	private final long optimisationTime;
	
	/**
	 * The acceptable GAP from the optimal solution (between 0 and 1)
	 */
	private final double optimisationGap;
	
	/**
	 * List of succession constraints. List of id of tasks to be done one after another. The string is as follow {@code "id1, id2"}, 
	 * where {@code id1} and {@code id2} are respectively the ID of the first task and second task
	 */
	private final ArrayList<String> successionConstraints;

	
	/**
	 * Transilien constructor
	 * @param temps optimization time (in second)
	 * @param gap the gap accepted by the optimization
	 */
	public Parameters(long temps, double gap)
	{
		this.optimisationTime = temps;
		this.optimisationGap = gap;
		this.successionConstraints = null;
	}
	
	/**
	 * FRET constructor
	 * @param temps optimization time (in second)
	 * @param gap the gap accepted by the optimization
	 * @param successionConstraints list of succession constraints
	 */
	public Parameters(long temps, double gap, ArrayList<String> successionConstraints)
	{
		this.optimisationTime = temps;
		this.optimisationGap = gap;
		this.successionConstraints = successionConstraints;
	}
	
	
	public long getOptimisationTime() {
		return optimisationTime;
	}

	public double getOptimisationGap() {
		return optimisationGap;
	}

	public ArrayList<String> getSuccessionConstraints() {
		return successionConstraints;
	}
}
