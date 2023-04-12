package com.dk.server.models;

public class Error {
	private String msg;

    public Error() {}
    
	private Error( Builder b ) {
		this.msg = b.msg;
	}
	
    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public static class Builder {
		private String msg;
		
		public Builder msg( String msg ) { this.msg = msg; return this; }
		public Error build( ) { return new Error( this ); }
	}
}
