package navigation.model;

import model.Problem;

/**
 *
 * @author User
 */
public class ObservableProblem {
    private int id;
    private Problem problem;

    public ObservableProblem(int id, Problem problem) {
        this.id = id;
        this.problem = problem;
    }

    public int getId() {
        return id;
    }

    public Problem getProblem() {
        return problem;
    }

    @Override
    public String toString() {
        return id + "   " + problem.getText();
    }
}
