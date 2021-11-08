import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Schedule {
    // How to fucking represent and compute the graph?
    ArrayList<Job> jobs;
    public Schedule(){
        jobs = new ArrayList<>();
    }

    public Job insert(int time){
        Job job = new Job(time);
        jobs.add(job);
        return job;
    }

    public Job get(int index){
        return jobs.get(index);
    }

    public int finish(){
        // find all Jobs without incoming edge. Use a dummy job as the parent.
        // ran DFS on each, DFS can actually check for cycles
        // if new set.size() < Jobs.size(). We have unreachable here.
        // Cycle detections.

        Stack<Job> stack = topologicalSort(dummy());
        int max = 0 ;

        while(!stack.isEmpty()){
            Job cur = stack.pop();
            for (Job child : cur.children){
                stack.push(child);
                if (!stack.contains(child)){
                    return -1;
                }
                cur.relax(child);
                max = Math.max(max, child.startTime + child.time);
            }
        }
        return max;
    }
    public Job dummy(){
        Job dummy = new Job(0);
        for (Job j: jobs) {
            if(j.preReq.isEmpty()) {
                dummy.children.add(j);
                //j.requires(dummy);// Sentinal, adopt all startless jobs
            }
        }
        return dummy;
    }

    public Stack topologicalSort(Job start){
        //Remember cycle detection

        Stack stack = new Stack();
        dfs(start,stack);

        return stack;

    }
    void dfs(Job job, Stack stack){
        for (Job child : job.children){
            if (!child.explored){
                dfs(child, stack);
            }
        }
        job.explored = true;
        stack.push(job);
    }
     class Job{
        int time;
        int startTime;
        Set<Job> preReq;
        Set<Job> children;
        boolean explored;
        Job(int time){
            this.time = time;
            startTime = 0;
            preReq = new HashSet<Job>();
            children = new HashSet<Job>();
            explored = false;
        }

        void requires(Job j){
            j.children.add(this);
            preReq.add(j);
        }

        int start(){
            return startTime;
        }
        void relax(Job child){
         int newStartTime = this.time + startTime;
         if (newStartTime > child.startTime){
             child.startTime = newStartTime;
         }
        }
    }
}
