public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.insert(8);
        Schedule.Job j1 = schedule.insert(3);
        schedule.insert(5);
        schedule.finish();

        Schedule schedule1 = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        Schedule.Job D = schedule.insert(8);
        Schedule.Job E = schedule.insert(8);
        Schedule.Job F = schedule.insert(8);
        Schedule.Job G = schedule.insert(8);

        B.requires(A);
        B.requires(D);
        D.requires(E);
        E.requires(A);
        F.requires(D);
        G.requires(D);
        G.requires(E);
        G.requires(C);
        schedule1.finish();



    }
}
