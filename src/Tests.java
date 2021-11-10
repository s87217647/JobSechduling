import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class Tests {
    @org.junit.Test
    public void topologicalSortingTest(){
        Schedule schedule = new Schedule();
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

        ArrayList<Schedule.Job> sorted = schedule.topoSort();

        //ACEDBFG




        System.out.println("This is the end, hold your breath and count to 10");
    }
    @org.junit.Test
    public void topologicalSortingTest2(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        Schedule.Job D = schedule.insert(8);
        Schedule.Job E = schedule.insert(8);
        Schedule.Job F = schedule.insert(8);

        A.requires(E);
        A.requires(F);
        B.requires(E);
        B.requires(D);
        C.requires(F);
        D.requires(C);



        //This past as well
    }

    @org.junit.Test
    public void topologicalSortingTest3(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        Schedule.Job D = schedule.insert(8);
        Schedule.Job E = schedule.insert(8);
        Schedule.Job F = schedule.insert(8);
        Schedule.Job G = schedule.insert(8);
        Schedule.Job H = schedule.insert(8);

        B.requires(A);
        C.requires(B);
        D.requires(A);
        E.requires(D);
        F.requires(C);
        G.requires(C);
        G.requires(F);
        G.requires(H);
        H.requires(B);
        H.requires(E);

        //Yep, cool, topological sorting is done.
    }
    @org.junit.Test
    public void topologicalSortingTest4() {
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        A.requires(B);
        B.requires(C);
        C.requires(A);

        ArrayList<Schedule.Job> sorted = schedule.topoSort();
        assertEquals(sorted.size(), 0);

        Schedule.Job D = schedule.insert(8);
        A.requires(D);

        assertEquals(schedule.topoSort().size(), 1);

    }

    @org.junit.Test
    public void relaxationTest1(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);

        assertEquals(8, schedule.finish());
        assertEquals(schedule.get(0).startTime,0 );
        assertEquals(schedule.get(1).startTime,0 );
        assertEquals(schedule.get(2).startTime,0 );

        A.requires(B);
        B.requires(C);
        assertEquals(24, schedule.finish());

    }

    @org.junit.Test
    public void relaxationTest2(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        A.requires(B);
        A.requires(C);
        assertEquals(16, schedule.finish());


    }

    @org.junit.Test
    public void relaxationTest3(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);


        assertEquals(8, schedule.finish());


        A.requires(B);
        B.requires(C);
        C.requires(A);


        // Cycles
        assertEquals(-1, schedule.finish());


    }
    @org.junit.Test
    public void relaxationTest4(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        assertEquals(8, schedule.finish());


        A.requires(B);
        B.requires(C);
        C.requires(A);


        // Cycles
        assertEquals(-1, schedule.finish());


    }
    @org.junit.Test
    public void taylorsTest(){
        Schedule schedule = new Schedule();
        schedule.insert(8); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5
        assertEquals(schedule.finish(),8); //should return 8, since job 0 takes time 8 to complete.
        /* Note it is not the earliest completion time of any job, but the earliest the entire set can complete. */
        schedule.get(0).requires(schedule.get(2)); //job 2 must precede job 0
        assertEquals(schedule.finish(), 13); //should return 13 (job 0 cannot start until time 5)
        schedule.get(0).requires(j1); //job 1 must precede job 0
        assertEquals(schedule.finish(),13); //should return 13
        assertEquals(schedule.get(0).start(),5); //should return 5
        assertEquals(j1.start(),0); //should return 0
        assertEquals(schedule.get(2).start(),0); //should return 0
        j1.requires(schedule.get(2)); //job 2 must precede job 1
        assertEquals(schedule.finish(), 16); //should return 16
        assertEquals(schedule.get(0).start(),8); //should return 8
        assertEquals(schedule.get(1).start(),5); //should return 5
        assertEquals(schedule.get(2).start(),0); //should return 0
        schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)
        assertEquals(schedule.finish(), -1); //should return -1
         assertEquals(schedule.get(0).start(), -1); //should return -1
        assertEquals(schedule.get(1).start(), -1); //should return -1
        assertEquals(schedule.get(2).start(),0); //should return 0 (no loops in prerequisites)
    }

    @Test
    public void perfTest() {
        Schedule schedule = new Schedule();
        Schedule.Job j1 = null;
        Schedule.Job j2 = null;
        int max = 50000; //even only
        for (int i = 0; i < max; i++) {
            Schedule.Job j = schedule.insert(i + 1);
            if (i % 2 == 1) {
                j1 = j;
                if (j2 != null) {
                    j2.requires(j);
                }
            } else {
                j2 = j;
                if (j1 != null) {
                    j1.requires(j2);
                }
            }
        }
        assertEquals((1 + max) * (max/2), schedule.finish());
    }

    @Test
    public void simpleTest() {
        Schedule zschedule = new Schedule();
        Schedule.Job j0 = zschedule.insert(1); //adds job 0 with time 1
        Schedule.Job j1 = zschedule.insert(2); //adds job 1 with time 2
        Schedule.Job j2 = zschedule.insert(3); //adds job 2 with time 3
        assertEquals(3, zschedule.finish());
        j0.requires(j1);
        j1.requires(j2);
        assertEquals(6,zschedule.finish());
    }

    @Test
    public void simpleCycleTest() {
        Schedule zschedule = new Schedule();
        Schedule.Job j0 = zschedule.insert(1); //adds job 0 with time 1
        Schedule.Job j1 = zschedule.insert(2); //adds job 1 with time 2
        Schedule.Job j2 = zschedule.insert(3); //adds job 2 with time 3
        j0.requires(j1);
        j1.requires(j2);
        j2.requires(j0);
        assertEquals(-1,zschedule.finish());
        zschedule = new Schedule();
        j0 = zschedule.insert(1);
        j1 = zschedule.insert(2);
        j2 = zschedule.insert(3);
        Schedule.Job j3 = zschedule.insert(4);
        j0.requires(j1);
        j0.requires(j2);
        j1.requires(j2);
        j2.requires(j3);
        j0.requires(j3);
        j1.requires(j3);
        assertEquals(10,zschedule.finish());

    }

}
