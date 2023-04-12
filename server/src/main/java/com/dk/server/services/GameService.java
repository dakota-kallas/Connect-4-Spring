package com.dk.server.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dk.server.models.Game;
import com.dk.server.models.Theme;
import com.dk.server.repositories.GameRepository;
import com.dk.server.repositories.TokenRepository;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepo;
	@Autowired
	private TokenRepository tokenRepo;
	
	public Game findById(String gid) {
		return gameRepo.findById( gid ).get();
	}
	
	public List<Game> findByOwner(String username) {
		return gameRepo.findByOwner(username);
	}

	public Game deleteById(String id) {
		Game oldGame = findById( id );
		gameRepo.delete( oldGame );
		return oldGame;
	}

	public Game createGame(String color, String playerToken, String computerToken, String owner) {
		Theme theme = new Theme.Builder()
        		.color("#" + color)
        		.playerToken(tokenRepo.findByName(playerToken))
        		.computerToken(tokenRepo.findByName(computerToken))
        		.build();
		
		String[][] myArray = new String[5][7];

		// Initialize each element to be an empty string
		for (int i = 0; i < 5; i++) {
		    for (int j = 0; j < 7; j++) {
		        myArray[i][j] = " ";
		    }
		}
		
		return gameRepo.save(new Game.Builder()
				.owner(owner)
				.theme(theme)
				.start(LocalDate.now())
				.grid(myArray)
				.status("UNFINISHED")
				.build()
				);
	}
	
	public int findNextAvailableSlot(Game game, int column) {
		for (int row = game.getGrid().length - 1; row >= 0; row--) {
		    if (" ".equals(game.getGrid()[row][column])) {
		        return row;
		    }
		}
		
		return -1;
	}
	
	public boolean isGridFull(String[][] grid) {
	    for (int row = 0; row < grid.length; row++) {
	        for (int col = 0; col < grid[0].length; col++) {
	            if (grid[row][col].equals(" ")) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	public String checkForWin(String[][] grid) {
	    int rows = grid.length;
	    int cols = grid[0].length;

	    // Check for horizontal win
	    for (int row = 0; row < rows; row++) {
	        for (int col = 0; col < cols - 3; col++) {
	            if (!grid[row][col].equals(" ") &&
	                    grid[row][col].equals(grid[row][col + 1]) &&
	                    grid[row][col].equals(grid[row][col + 2]) &&
	                    grid[row][col].equals(grid[row][col + 3])) {
	                return grid[row][col];
	            }
	        }
	    }

	    // Check for vertical win
	    for (int row = 0; row < rows - 3; row++) {
	        for (int col = 0; col < cols; col++) {
	            if (!grid[row][col].equals(" ") &&
	                    grid[row][col].equals(grid[row + 1][col]) &&
	                    grid[row][col].equals(grid[row + 2][col]) &&
	                    grid[row][col].equals(grid[row + 3][col])) {
	                return grid[row][col];
	            }
	        }
	    }

	    // Check for diagonal win (top-left to bottom-right)
	    for (int row = 0; row < rows - 3; row++) {
	        for (int col = 0; col < cols - 3; col++) {
	            if (!grid[row][col].equals(" ") &&
	                    grid[row][col].equals(grid[row + 1][col + 1]) &&
	                    grid[row][col].equals(grid[row + 2][col + 2]) &&
	                    grid[row][col].equals(grid[row + 3][col + 3])) {
	                return grid[row][col];
	            }
	        }
	    }

	    // Check for diagonal win (top-right to bottom-left)
	    for (int row = 0; row < rows - 3; row++) {
	        for (int col = 3; col < cols; col++) {
	            if (!grid[row][col].equals(" ") &&
	                    grid[row][col].equals(grid[row + 1][col - 1]) &&
	                    grid[row][col].equals(grid[row + 2][col - 2]) &&
	                    grid[row][col].equals(grid[row + 3][col - 3])) {
	                return grid[row][col];
	            }
	        }
	    }

	    // If no winning combinations are found, return null
	    return null;
	}

	
	public Game addToken(Game game, int row, int column) {
		if (game.getGrid()[row][column].equals(" ")) {
            String [][] grid = game.getGrid();
            grid[row][column] = "X";
            game.setGrid(grid);

            String winner = checkForWin(game.getGrid());
            if (winner != null) {
                if (winner.equals("X")) {
                    game.setStatus("VICTORY");
                    game.setEnd(LocalDate.now());
                    gameRepo.save(game);
                    return game;
                }
            }

            boolean computerMoved = false;
            int computerCol;
            int computerRow;
            boolean gridFull = isGridFull(game.getGrid());

            if (!gridFull) {
                while (!computerMoved) {
                    computerCol = (int) (Math.random() * 7);
                    computerRow = findNextAvailableSlot(game, computerCol);
                    if (computerRow == -1) {
                        continue;
                    } else if (computerRow == row && computerCol == column) {
                        if (computerRow == 0) {
                            continue;
                        }
                        computerRow--;
                    }
                    if (game.getGrid()[computerRow][computerCol].equals(" ")) {
                    	grid = game.getGrid();
                        grid[computerRow][computerCol] = "O";
                        game.setGrid(grid);
                        computerMoved = true;
                    }
                }

                winner = checkForWin(game.getGrid());
                if (winner != null) {
                    if (winner.equals("O")) {
                        game.setStatus("LOSS");
                        game.setEnd(LocalDate.now());
                    }
                }
            } else {
                game.setStatus("TIE");
                game.setEnd(LocalDate.now());
            }
        }
		gameRepo.save(game);
        return game;
	}
}
