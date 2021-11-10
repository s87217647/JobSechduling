import java.util.*;

public class Schedule {
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

        ArrayList<Schedule.Job> sorted = topoSort();
        if (sorted.size() < jobs.size())
            return -1;

        int max = 0;
        for(Job j : sorted)
            max = Math.max(max,j.relax());
        Job lastJob = sorted.get(sorted.size() - 1);

        return  max;
    }


    public ArrayList<Job> topoSort(){
        ArrayList<Job> topo = new ArrayList<Job>();
        for (Job job : jobs) {
            job.inDegree = 0;
            job.startTime = -1;
        }
        for (Job job: jobs)
            for (Job child : job.children)
                child.inDegree ++;

        Queue<Job> q = new LinkedList<Job>();

        for (Job job : jobs)
            if (job.inDegree == 0) {
                job.startTime = 0;
                q.add(job);
            }
        while (!q.isEmpty()){
            Job j = q.remove();
            topo.add(j);
            for (Job child : j.children) {
                child.inDegree --;
                if (child.inDegree == 0) {
                    q.add(child);
                    child.startTime = 0;
                }
            }
        }

        return topo;

    }

     class Job{
        int time;
        int startTime;
        int inDegree;
        Set<Job> children;
        Job(int time){
            this.time = time;
            children = new HashSet<Job>();
        }

        void requires(Job j){
            j.children.add(this);
        }

        int start(){
            return startTime;
        }
        int relax(){
            int max = startTime + time;
            for (Job child : children) {
                child.startTime = Math.max(child.startTime, this.startTime + time);
                max = Math.max( max,child.startTime + child.time);
            }
            return max;
        }
    }
}
