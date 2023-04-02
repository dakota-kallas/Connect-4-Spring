package com.dk.server.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Game {
    @Id
	private String id;
    private String owner;
    private Date start;
    private Date end;
    private Theme theme;
    private String status;
    private String[][] grid;

    public Game() {}

	private Game( Builder b ) {
		this.owner = b.owner;
		this.start = b.start;
		this.end = b.end;
		this.theme = b.theme;
		this.status = b.status;
		this.grid = b.grid;
	}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[][] getGrid() {
        return this.grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

	public static class Builder {
        private String owner;
        private Date start;
        private Date end;
        private Theme theme;
        private String status;
        private String[][] grid;
		
		public Builder owner( String owner ) { this.owner = owner; return this; }
		public Builder start( Date start ) { this.start = start; return this; }
		public Builder end( Date end ) { this.end = end; return this; }
		public Builder theme( Theme theme ) { this.theme = theme; return this; }
		public Builder status( String status ) { this.status = status; return this; }
		public Builder grid( String[][] grid ) { this.grid = grid; return this; }
		
		public Game build() { return new Game(this); }
	}
}
