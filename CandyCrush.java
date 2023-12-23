
//esmanur ulu
//231101024
import java.util.Random;
import java.util.Scanner;


//import homework1.CandyCrush2.Colors;

public class CandyCrush {
//oyunu kazanmak icin gereken puanları mode1 icin 400 puan,
// mode 2de her renk icin 200 puan olarak aldım.
// puanlama sistemi: 3'lü kırmızı, yeşil ve mavi için üçer puan,
// 4'lü kırmızı,yesil ve mavi icin dörder puan ve
// 5'li kırmızı,yesil ve mavi icin beser puan seklindedir.
	
	static Random random = new Random();
	static int countMode1 = 0;
	static int countMode2Red = 0;
	static int countMode2Green = 0;
	static int countMode2Blue = 0;
	static int moveCount = 0;
	
	static String Red = Colors.RED + "R" + Colors.RESET;
	static String Green = Colors.GREEN + "G" + Colors.RESET;
	static String Blue = Colors.BLUE + "B" + Colors.RESET;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
			
		System.out.println( Colors.GREEN + "Welcome to the game!\n" + Colors.RESET
				+ Colors.GREEN + "Enter ’s’ to start the game or ’q’ to quit:" + Colors.RESET );
		
		String stOrEx = scan.next();
		int mode = modeRandom() ;
		
		if(stOrEx.equals("s") && mode==1 ){
			
			System.out.println( Colors.CYAN + " The rules in this mode of the game are as follows: "
					+ "\n You have to collect 400 points in 15 moves!" 
					+ "\n You get 3 points for 3 of each color." 
					+ "\n You get 4 points for 4 of each color."
					+ "\n You get 5 points for 5 of each color."
					+ "\n If you make an invalid move, your moves will be reduced. \n"
					+ "G" + Colors.PURPLE + "oo" + Colors.CYAN + "d l"+ Colors.YELLOW + "uc" + Colors.BLUE + "k!\n" + Colors.RESET 
					+ "Enter the size of the matrix:"); 
			
			int a = scan.nextInt();
			int b = scan.nextInt();
		
		
			String[][] board = new String[a][b] ;
			gameBoard(a, b, board);
		
			for( int move = 15; move>=0; move--) {
				
		
				if(move==15) {
					System.out.println(Colors.RED + "You have 15 moves to reach the goal!" + Colors.RESET );
				}else if (countMode1 < 400 && move > 0){
					System.out.println("You have collected " + countMode1 + " points and you have " + move +" moves left!");
				}else if(countMode1 < 400 && move == 0) {
					System.out.println("You lost!! :(" 
					+"\n Please try again!");
					break;
				}else if (countMode1>=400) {
					break;
				}

				System.out.println("Enter the cell:");
				int x = scan.nextInt();
				int y = scan.nextInt();
				
				System.out.println("Enter the direction:");
				String direction = scan.next();
				
				System.out.println("Clearing Board:");
			
				swapCells(x, y, board, direction);
				showBoard(board, a, b);
				
				while(checkForMatches(board)) {
					System.out.println("--------------");
					showBoard(board, a, b);
					if  (countMode1>=400){
						System.out.println("You win!");
						break;
					}
				}
		
			}
		}else if(stOrEx.equals("s") && mode==2 ) {
			
			System.out.println( Colors.CYAN + " The rules in this mode of the game are as follows: "
					+ "\n You have to collect 200 points for each color in 20 moves!" 
					+ "\n You get 3 points for 3 of each color." 
					+ "\n You get 4 points for 4 of each color."
					+ "\n You get 5 points for 5 of each color."
					+ "\n If you make an invalid move, your moves will be reduced. \n"
					+ "G" + Colors.PURPLE + "oo" + Colors.CYAN + "d l"+ Colors.YELLOW + "uc" + Colors.BLUE + "k!\n" + Colors.RESET 
					+ "Enter the size of the matrix:"); 
			
			int a = scan.nextInt();
			int b = scan.nextInt();
			
			String[][] board = new String[a][b] ;
			gameBoard(a, b, board);
			
			for( int move = 20; move>=0; move--) {
		
				if(move==20) {
					System.out.println("You have 20 moves to reach the goal!");
				}else if ( ( countMode2Red < 200 || countMode2Blue < 200 || countMode2Green < 200  )&& move>0){
					System.out.println("You have collected " + countMode2Red + " points for red, " + countMode2Green + " points for green, " + countMode2Blue + " points for blue and you have " + move + " moves left!\n");
				}else if( (countMode2Red < 200 || countMode2Blue < 200 || countMode2Green < 200 ) && move == 0) {
					System.out.println("You lost!! :(" +
							"\n Please try again!");
					break;
				}else if(countMode2Blue >200 && countMode2Green >200 && countMode2Red >200 ) {
					break;
				}

				System.out.println("Enter the cell:");
				int x = scan.nextInt();
				int y = scan.nextInt();
				
				System.out.println("Enter the direction:");
				String direction = scan.next();
				
				System.out.println("Clearing Board:");
			
				swapCells(x, y, board, direction);
				
				showBoard(board, a, b);
				
				while(checkForMatches(board)) {
					System.out.println("--------------");
					showBoard(board, a, b);
					if  (countMode2Blue >200 && countMode2Green >200 && countMode2Red >200){
						System.out.println("You win!");
						break;
					}
	 			
				}
			
			}
		}else if(stOrEx.equals("q")) {
			System.out.println( Colors.RED  +"GAME IS OVER! :(" + Colors.RESET );
		}
	}
	
	
	public static boolean checkForMatches (String[][] board ) {
		// bu methodun amacı her hamle sonrasında game board'da denk gelen 3, 4 veya 5'li eslesmeleri bulmak, tabloyu güncellemek, her denk gelen 3, 4 veya 5'li icin iki ayrı oyun moduna göre puanları toplamaktır.
		
		for(int row = 0; row < board.length;row ++ ) {
			for(int column = 0; column<board[0].length; column++) {
				
				if(isRow5(board, row, column)) {
					
					System.out.println("You have 5 row matches!!");
					countMode1+=5;
					if((board[row][column]).equals(Red)){
						countMode2Red += 5;
					}else if((board[row][column]).equals(Green)){
						countMode2Green += 5;
					}else{
						countMode2Blue += 5;
					}
					
					for(int y = row; y>0; y--){
						for(int s = column; s<=column+4; s++) {
							board[y][s] = board[y-1][s];
						}
						
					}for(int s = column; s<=column+4; s++) {
						board[0][s]= colorRandom();
					}
					return true;
					
				}else if(isRow4(board, row, column)) {
					
					System.out.println("You have 4 row matches!!");
					
					countMode1+=4;
					if((board[row][column]).equals(Red))
						countMode2Red += 4;
					else if((board[row][column]).equals(Green))
						countMode2Green += 4;
					else {
						countMode2Blue += 4;
					}
					for(int y = row; y>0; y--){
						for(int s = column; s<=column+3; s++) {
							board[y][s] = board[y-1][s];
							
						}
						
					}for(int s = column; s<=column+3; s++) {
						board[0][s]= colorRandom();
					}
					return true;
					
				}else if (isRow3(board, row, column)) {
					
					System.out.println("You have 3 row matches!!");
					countMode1+=3;
					if((board[row][column]).equals(Red))
						countMode2Red += 3;
					else if((board[row][column]).equals(Green))
						countMode2Green += 3;
					else {
						countMode2Blue += 3;
					}
					for(int y = row; y>0; y--){
						for(int s = column; s<=column+2; s++) {
							board[y][s] = board[y-1][s];
							
						}
						
					}for(int s = column; s<=column+2; s++) {
						board[0][s]= colorRandom();
					}
					return true;
					
				} else if(isColumn5(board, row, column)) {
					
					System.out.println("You have 5 column matches!!");
					countMode1+=5;
					if((board[row][column]).equals(Red))
						countMode2Red += 5;
					else if((board[row][column]).equals(Green))
						countMode2Green += 5;
					else {
						countMode2Blue += 5;
					}
					for(int r = row-1; r>=0; r--) {
						board[r+5][column]=board[r][column];
					}for(int r = 0; r<=row; r++) {
						board[r][column] = colorRandom();
					}
					
					return true;
					
				}else if(isColumn4(board, row, column)) {
					System.out.println("You have 4 column matches!!");
					
					countMode1 +=4;
					if((board[row][column]).equals(Red))
						countMode2Red += 4;
					else if((board[row][column]).equals(Green))
						countMode2Green += 4;
					else {
						countMode2Blue += 4;
					}
					for(int r = row-1; r>=0; r--) {
						board[r+4][column]=board[r][column];
					}for(int r = 0; r<=row; r++) {
						board[r][column] = colorRandom();
					}
					
					return true;
					
				}else if(isColumn3(board, row, column)) {
				
					System.out.println("You have 3 column matches!!");
					countMode1+=3;
					if((board[row][column]).equals(Red))
						countMode2Red += 3;
					else if((board[row][column]).equals(Green))
						countMode2Green += 3;
					else {
						countMode2Blue += 3;
					}
					for(int r = row-1; r>=0; r--) {
						board[r+3][column]=board[r][column];
					}for(int r = 0; r<=row; r++) {
						board[r][column] = colorRandom();
					}
					return true;
				
				}
				
				
			}
		} 
			return false;
			
		 

	}
	//bu satırdan sonraki altı methodun amacı: game board'da satır ve sutunlarda ayrı ayrı denk gelebilecek olan 3, 4 veya 5'li eslesmleri tespit etmektir.
	public static boolean isRow5(String[][] board, int row, int column) {
		
		return(column<board[0].length-4 && (board[row][column]).equals(board[row][column+1]) && (board[row][column]).equals(board[row][column+2]) && (board[row][column]).equals(board[row][column+3]) && (board[row][column]).equals(board[row][column+4]));
	}
	public static boolean isRow4(String[][] board, int row, int column) {
		
		
		return(column<board[0].length-3 && (board[row][column]).equals(board[row][column+1]) && (board[row][column]).equals(board[row][column+2]) && (board[row][column]).equals(board[row][column+3]));
	}
	public static boolean isRow3(String[][] board, int row, int column) {
		
		
		return(column<board[0].length-2 && (board[row][column]).equals(board[row][column+1]) && (board[row][column]).equals(board[row][column+2]));
	}
	public static boolean isColumn5(String[][] board, int row, int column) {
		
		return(row<board.length-4 && (board[row][column]).equals(board[row+1][column]) && (board[row][column]).equals(board[row+2][column]) && (board[row][column]).equals(board[row+3][column]) && (board[row][column]).equals(board[row+4][column]));
	}
	public static boolean isColumn4(String[][] board, int row, int column) {
		
		return(row<board.length-3 && (board[row][column]).equals(board[row+1][column]) && (board[row][column]).equals(board[row+2][column]) && (board[row][column]).equals(board[row+3][column]));
	}
	public static boolean isColumn3(String[][] board, int row, int column) {
		
		return(row<board.length-2 && (board[row][column]).equals(board[row+1][column]) && (board[row][column]).equals(board[row+2][column]));
	}
	
	
	
	public static void swapCells(int row, int column, String[][] board, String direction) {	
		// bu methodun amacı kullanıcıdan alınan hücrenin yine kullanıcıdan alınan yöne göre değiştirilmesidir.
	
		if(direction.equals("up") && row>1 ){
			String temp = board[row-2][column-1] ;
			board[row-2][column-1] = board[row-1][column-1];
			board[row-1][column-1] = temp;
			
		}else if(direction.equals("down") && row<board.length){
			String temp = board[row][column-1];
			board[row][column-1] = board[row-1][column-1];
			board[row-1][column-1] = temp;
			
		}else if(direction.equals("right") && column<board[0].length) {
			String temp = board[row-1][column];
			board[row-1][column] = board[row-1][column-1];
			board[row-1][column-1] = temp;
			
		}else if(direction.equals("left") && column>1){
			String temp = board[row-1][column-2];
			board[row-1][column-2] = board[row-1][column-1];
			board[row-1][column-1] = temp;
			
		}else{
			System.out.println("Invalid move!");
		}	
		
		
	}
	
	public static void gameBoard(int a, int b, String[][] board) {
			//bu method oyunun ilk board cıktısını verir. board'da en basta hicbir sekilde 3, 4 veya 5'li eslesmelerin gerceklesmemesi icindir.
		
		 for(int row = 0; row<a; row++)
			{
				for(int column = 0; column<b; column++)
				{	
					board[row][column] = colorRandom();
					
						while(leftUpColorSame(row, column, board)) {
							board[row][column]= colorRandom();
						}
						
				

				}
			}
		 showBoard(board, a, b);
	
		 
	}
	
	public static boolean leftUpColorSame( int row, int column, String[][] board) {
	//bu method oyunun en basındaki gameBoard methodunundaki eslesmelerin kontrolünü saglar.
		
		return((column > 1 && board[row][column].equals(board[row][column - 1]) && board[row][column].equals(board[row][column - 2]) )
				||  (row>1 && (board[row][column].equals(board[row - 1][column]) && board[row][column].equals(board[row-2][column])) ) ); 
	}

	public static void showBoard(String[][] board, int row, int column) {
	//game board'ın yazdırılmasını saglar.
	 
		String str = "|";
	
		for(int m = 1; m<=column; m++ ) {
			if(m==1)
				System.out.print("   ");
			if(m<10)
			System.out.print("   " + Colors.CYAN  + m + Colors.RESET);
			else {
				System.out.print("  " + Colors.CYAN  + m + Colors.RESET);	
			}
		}
		
		System.out.println();

		for (int r = 0; r < row; r++) {
			
			if(r<9)
            System.out.print(Colors.CYAN  + (r + 1) + Colors.RESET + "  "); 
			else {
				System.out.print(Colors.CYAN  + (r + 1) + Colors.RESET + " "); 
			}

            for (int c = 0; c < column; c++) {
                System.out.print(" " + Colors.BLACK + str + Colors.RESET + " " + board[r][c]); 
            }
            System.out.println(" " + Colors.BLACK + str + Colors.RESET ); 
			}
		
		}

	public class Colors {
		public static final String RESET = "\033[0m";  // Text Reset
		public static final String BLACK = "\033[0;30m";   // BLACK
		public static final String RED = "\033[0;31m";     // RED
		public static final String GREEN = "\033[0;32m";   // GREEN
	    public static final String YELLOW = "\033[0;33m";  // YELLOW
	    public static final String BLUE = "\033[0;34m";    // BLUE
	    public static final String PURPLE = "\033[0;35m";  // PURPLE
	    public static final String CYAN = "\033[0;36m";    // CYAN
	    public static final String WHITE = "\033[0;37m";   // WHITE
	}
	
	public static int modeRandom() {
	//oyunun basında kullanıcıdan bir oyun modu alınmadıgı icin random bir oyun modu icin kullanılan method.
		return(random.nextInt(2)+1);
	}

	public static String colorRandom() {
		 String[] colors = { Red , Green , Blue };
	    return colors[random.nextInt(colors.length)];
	}
	
}

