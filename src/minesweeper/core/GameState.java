package minesweeper.core;

/**
 * Game state.
 */
public enum GameState {
	NEW,
    /** Playing game. */
    PLAYING,
    
    /** Game failed. */
    FAILED,
    
    /** Game solved. */
    SOLVED
}
