import org.junit.Assert;

import java.util.Arrays;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

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
        Schedule.Job dummy = schedule.dummy();
        Stack stack = schedule.topologicalSort(dummy);
        //This looks fine

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

        Stack stack = schedule.topologicalSort(schedule.dummy());

        System.out.println("This is the end, hold your breath and count to 10");

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

        Stack stack = schedule.topologicalSort(schedule.dummy());

        System.out.println("This is the end, hold your breath and count to 10");

        //Yep, cool, topological sorting is done.
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


}
