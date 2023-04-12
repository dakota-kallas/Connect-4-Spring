package com.dk.server.models;

public class Theme {
    private String color;
    private Token playerToken;
    private Token computerToken;

    public Theme() {
    }

    private Theme(Builder b) {
        this.color = b.color;
        this.playerToken = b.playerToken;
        this.computerToken = b.computerToken;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Token getPlayerToken() {
        return playerToken;
    }

    public void setPlayerToken(Token playerToken) {
        this.playerToken = playerToken;
    }

    public Token getComputerToken() {
        return computerToken;
    }

    public void setComputerToken(Token computerToken) {
        this.computerToken = computerToken;
    }

    public static class Builder {
		private String color;
		private Token playerToken;
		private Token computerToken;
		
		public Builder color( String color ) { this.color = color; return this; }
		public Builder playerToken( Token playerToken) { this.playerToken = playerToken; return this; }
		public Builder computerToken( Token computerToken) { this.computerToken = computerToken; return this; }
		public Theme build( ) { return new Theme( this ); }
	}
}