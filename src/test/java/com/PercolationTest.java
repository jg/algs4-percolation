import org.junit.*;
import static org.junit.Assert.*;

public class PercolationTest {
    private java.util.List emptyList;

    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        emptyList = new java.util.ArrayList();
    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        emptyList = null;
    }

    @Test
    public void testSimpleCase() {
      Percolation p = new Percolation(2);
      p.open(1,1); p.open(2,1);
      assertTrue(p.isFull(1,1));
      assertTrue(!p.isFull(2,2));
      assertTrue(!p.isFull(1,2));
    }

    @Test
    public void testInit() {
      Percolation p = new Percolation(4);
      assertTrue(!p.isOpen(1,1));
      assertTrue(!p.isOpen(1,2));
      assertTrue(!p.isOpen(2,1));
      assertTrue(!p.isOpen(2,2));
      assertTrue(!p.isOpen(1,4));
      assertTrue(!p.isOpen(4,4));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void throwsIndexOutOfBoundsException() {
      Percolation p = new Percolation(4);
      assertTrue(p.isOpen(5,2));
    }

    @Test
    public void testImg1Case() {
      Percolation p = new Percolation(8);
      assertTrue(!p.percolates());
      p.open(1,1); p.open(1,2); p.open(1,6); p.open(1,7); p.open(1,8); p.open(2,1); p.open(2,4); p.open(2,5); p.open(2,6); p.open(2,7); p.open(2,8); p.open(3,4); p.open(3,5); p.open(3,6); p.open(3,7); p.open(4,3); p.open(4,4); p.open(4,6); p.open(4,7); p.open(4,8); p.open(5,2); p.open(5,3); p.open(5,4); p.open(5,6); p.open(5,7); p.open(6,2); p.open(6,7); p.open(6,8); p.open(7,1); p.open(7,3); p.open(7,5); p.open(7,6); p.open(7,7); p.open(7,8); p.open(8,1); p.open(8,2); p.open(8,3); p.open(8,4); p.open(8,6);
      assertTrue(p.isFull(4,3));
      assertTrue(p.isFull(5,3));
      assertTrue(!p.isFull(7,1));
      assertTrue(!p.isFull(8,1));
      assertTrue(!p.isFull(8,4));
      assertTrue(p.isFull(8,6));
      assertTrue(p.isFull(4,8));
      assertTrue(p.percolates());
    }

    @Test
    public void testImg2Case() {
      Percolation p = new Percolation(8);
      assertTrue(!p.percolates());
      for (int i = 1; i <= 8; i++) 
        for (int j = 1; j <= 8; j++)
          assertTrue(!p.isOpen(i,j));
      p.open(1,3); p.open(1,4); p.open(1,6); p.open(2,1); p.open(2,2); p.open(2,3); p.open(2,4); p.open(2,5); p.open(3,1); p.open(3,2); p.open(3,5); p.open(3,6); p.open(4,3); p.open(4,4); p.open(4,5); p.open(4,6); p.open(4,7); p.open(5,1); p.open(5,7); p.open(5,8); p.open(6,2); p.open(6,4); p.open(6,5); p.open(6,6); p.open(7,2); p.open(7,3); p.open(7,5); p.open(7,6); p.open(7,8); p.open(8,1); p.open(8,3); p.open(8,7);

      assertTrue(!p.percolates());
      assertTrue(p.isFull(3,1));
      assertTrue(p.isFull(3,2));
      assertTrue(p.isFull(4,3));
      assertTrue(p.isFull(4,4));
      assertTrue(p.isFull(4,7));
      assertTrue(p.isFull(5,7));
      assertTrue(p.isFull(5,8));
      assertTrue(!p.isFull(8,1));
      assertTrue(!p.isFull(6,1));
      assertTrue(!p.isFull(8,3));
      assertTrue(!p.isFull(8,7));
      assertTrue(!p.isFull(7,8));
    }

    @Test
    public void testCase1() {
      Percolation p = new Percolation(2);
      assertTrue(!p.percolates());
      p.open(2,2); p.open(2,1); p.open(1,2); p.open(1,1);
      assertTrue(p.percolates());
    }


}
