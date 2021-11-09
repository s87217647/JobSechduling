import org.junit.Assert;

import java.util.Arrays;
import java.util.Stack;

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
        Schedule.Job dummy = schedule.dummy();
        Stack stack = schedule.topologicalSort(dummy);

        stack.pop();
        assertSame(stack.pop(), A);


        Schedule.Job temp = null;
        while (!stack.isEmpty()){
          temp = (Schedule.Job) stack.pop();
        }
        Assert.assertSame(temp, G);
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

        Schedule.Job temp = null;

        temp = (Schedule.Job) stack.pop();


        assertEquals(temp.startTime,0);

        while (!stack.isEmpty()){
            temp = (Schedule.Job) stack.pop();
        }
        Assert.assertSame(temp, B);

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

        stack.pop();
        assertSame(stack.pop(), A);

        System.out.println("This is the end, hold your breath and count to 10");
        Schedule.Job temp = null;
        while (!stack.isEmpty()){
            temp = (Schedule.Job) stack.pop();
        }
        Assert.assertSame(temp, G);
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
    @org.junit.Test
    public void relaxationTest4(){
        Schedule schedule = new Schedule();
        Schedule.Job A = schedule.insert(8);
        Schedule.Job B = schedule.insert(8);
        Schedule.Job C = schedule.insert(8);
        assertEquals(8, schedule.finish());


        A.requires(B);
        B.requires(C);

        // Cycles
        assertEquals(-1, schedule.finish());


    }
    @org.junit.Test
    public void taylorsTest(){
        Schedule schedule = new Schedule();
        schedule.insert(8); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5
        schedule.finish(); //should return 8, since job 0 takes time 8 to complete.
        /* Note it is not the earliest completion time of any job, but the earliest the entire set can complete. */
        schedule.get(0).requires(schedule.get(2)); //job 2 must precede job 0
        schedule.finish(); //should return 13 (job 0 cannot start until time 5)
        schedule.get(0).requires(j1); //job 1 must precede job 0
        schedule.finish(); //should return 13
        schedule.get(0).start(); //should return 5
        j1.start(); //should return 0
        schedule.get(2).start(); //should return 0
        j1.requires(schedule.get(2)); //job 2 must precede job 1
        schedule.finish(); //should return 16
        schedule.get(0).start(); //should return 8
        schedule.get(1).start(); //should return 5
        schedule.get(2).start(); //should return 0
        schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)
        schedule.finish(); //should return -1
        schedule.get(0).start(); //should return -1
        schedule.get(1).start(); //should return -1
        schedule.get(2).start(); //should return 0 (no loops in prerequisites)
    }

}
