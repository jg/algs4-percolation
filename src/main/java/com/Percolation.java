public class Percolation {
  private enum State { BLOCKED, OPEN }
  private State[][] grid;
  private int gridSize;
  // virtual cell indices for quick percolation check
  private int startIndex, endIndex;
  // will be connected to startIndex, endIndex, used by .percolation
  private WeightedQuickUnionUF unionFind;
  // will not be connected to endIndex, used by .isFull
  private WeightedQuickUnionUF gridUnionFind;

  public Percolation(int N) {
    grid = new State[N][N];
    this.gridSize = N; 

    for (int i = 1; i <= gridSize; i++)
      for (int j = 1; j <= gridSize; j++)
        setCellAt(new Coords(i, j), State.BLOCKED);

    // init unionFind class N*N elements + 2 'virtual' cells
    unionFind     = new WeightedQuickUnionUF(N*N + 2);
    // gridUnionFind is used by isFull
    gridUnionFind = new WeightedQuickUnionUF(N*N + 1);

    // virtual cells indices
    startIndex = N*N;
    endIndex   = N*N + 1;
  }

  public void open(int i, int j) { open(new Coords(i, j)); }

  public boolean isOpen(int i, int j) { return isOpen(new Coords(i, j)); }

  public boolean isFull(int i, int j) { return isFull(new Coords(i, j)); }

  public boolean percolates() {
    return unionFind.connected(startIndex, endIndex);
  }

  private Coords[] openNeighbours(Coords coords) {
    // count is the number of open neighbours
    int count    = 0;
    Coords[] tmp = new Coords[4];
    for (Coords c:neighbours(coords))
      if (isOpen(c)) tmp[count++] = new Coords(c.row(), c.column());

    // declare array of needed size, assign coords from tmp
    Coords[] openNeighbours = new Coords[count]; 
    for (int i = 0; i < count; i++)
      openNeighbours[i] = tmp[i];

    return openNeighbours;
  }

  private Coords[] neighbours(Coords c) {
    int[] rowOffsets    = generateOffsets(c.row());
    int[] columnOffsets = generateOffsets(c.column());
    Coords[] neighbours  = new Coords[rowOffsets.length + columnOffsets.length];

    int i = 0;
    for (int columnOffset:columnOffsets)
      neighbours[i++] = new Coords(c.row(), c.column() + columnOffset);

    for (int rowOffset:rowOffsets)
      neighbours[i++] = new Coords(c.row() + rowOffset, c.column());

    return neighbours;
  }

  private int[] generateOffsets(int coordinate) {
    int[] offsets;
    if (coordinate > 1 && coordinate < gridSize) {
      offsets    = new int[2];
      offsets[0] = -1;
      offsets[1] = 1;
    } else {
      offsets = new int[1];

      if (coordinate == 1)
        offsets[0] = 1;
      else if (coordinate == gridSize)
        offsets[0] = -1;
    }

    return offsets;
  }

  // index of the starting element in the UF structure
  private int startIndex() { return this.startIndex; }

  // index of the starting element in the UF structure
  private int endIndex() { return this.endIndex; }

  private boolean isOpen(Coords c) {
    if (getCellAt(c) ==  State.OPEN) return true;
    return false;
  }

  // uses separate UF structure so that the open bottom row cells 
  // are not connected with each other
  private boolean isFull(Coords c) {
    return (isOpen(c) && gridUnionFind.connected(c.index(), startIndex)); 
  }

  private State getCellAt(Coords c) {
    int[] coords = mapToGrid(c);
    return grid[coords[0]][coords[1]];
  }

  private void setCellAt(Coords c, State state) {
    int[] coords = mapToGrid(c);
    grid[coords[0]][coords[1]] = state;
  }

  private int[] mapToGrid(Coords c) {
    int m = c.row() - 1;
    int n = c.column() - 1;

    if (inGridBounds(m) && inGridBounds(n)) {
      int[] coords = new int[2];
      coords[0] = m;
      coords[1] = n;
      return coords;
    } else {
      System.out.format("Coords requested: (%d, %d), gridSize is %d\n", 
                        c.row(), c.column(), gridSize);
      throw new IndexOutOfBoundsException();
    }
  }

  private boolean inGridBounds(int index) {
    return (index >= 0) && (index <= gridSize-1);
  }

  private void printGrid() {
    for (int i = 1; i <= gridSize; i++) {
      for (int j = 1; j <= gridSize; j++) {
        if (getCellAt(new Coords(i, j)) == State.OPEN)
          System.out.print(String.format("%1s", "o"));
        else
          System.out.print(String.format("%1s", "x"));
        System.out.print(" ");
      }
      System.out.println();
    }
    System.out.println();
  }

  private void open(Coords c) {
    // open sites in the first row should be connected to the startIndex
    if (c.row() == 1) {
      unionFind.union(c.index(), startIndex);
      gridUnionFind.union(c.index(), startIndex);
    }

    setCellAt(c, State.OPEN);

    for (Coords neighbour: openNeighbours(c)) {
      unionFind.union(c.index(), neighbour.index());
      gridUnionFind.union(c.index(), neighbour.index());
    }

    // connect open sites in the last row to endIndex
    if ((c.row() == gridSize)) unionFind.union(c.index(), endIndex);
  }

  private class Coords {
    private int x, y;

    Coords(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int row() { return x; }

    public int column() { return y; }

    // maps elements on the grid into the uf structure (row, column)
    public int index() { return (x - 1) * gridSize + (y - 1); }

    public void print(String s) {
      System.out.format(s + "(%d, %d)\n", row(), column());
    }
  }
}

