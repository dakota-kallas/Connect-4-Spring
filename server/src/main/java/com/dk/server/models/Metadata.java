package com.dk.server.models;

public class Metadata {
    private Token[] tokens;
    private Theme defaultTheme;

    public Metadata() {}

    private Metadata(Builder b) {
        this.tokens = b.tokens;
        this.defaultTheme = b.defaultTheme;
    }

    public Token[] getTokens() {
        return this.tokens;
    }

    public void setTokens(Token[] tokens) {
        this.tokens = tokens;
    }

    public Theme getDefaultTheme() {
        return this.defaultTheme;
    }

    public void setDefaultTheme(Theme defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

    public static class Builder {
		private Token[] tokens;
		private Theme defaultTheme;
		
		public Builder tokens( Token[] tokens ) { this.tokens = tokens; return this; }
		public Builder defaultTheme( Theme defaultTheme) { this.defaultTheme = defaultTheme; return this; }
		public Metadata build( ) { return new Metadata( this ); }
	}
}
