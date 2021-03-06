/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int get_curr_lineno() {
      return curr_lineno;
    }
    private AbstractSymbol filename;
    void set_filename(String fname) {
      filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
      return filename;
    }
    private boolean foundNullChar = false;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int STRING = 2;
	private final int SINGLE_STRING = 3;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int yy_state_dtrans[] = {
		0,
		49,
		63,
		72
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NOT_ACCEPT,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"8,15:7,41,42,14,41:3,15:18,42,15,7,15:4,5,3,6,4,30,40,31,39,29,46:10,38,37," +
"33,1,2,15,36,44:26,5,9,5,15,45,15,18,10,16,26,20,11,45,22,21,45:2,17,45,13," +
"25,27,45,23,19,12,43,24,28,45:3,34,15,35,32,15,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,123,
"0,1,2,1,3,4,1:2,5,6,1:4,7,1:7,8,9,1:4,5:2,10,5,1:2,5:15,11,1,12,1:2,13,6,1:" +
"7,14,1,15,16,17,18,12,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36" +
",37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61" +
",62,63,64,65,66,67,68,69,70,71")[0];

	private int yy_nxt[][] = unpackFromString(72,47,
"1,2,3,4,5,3,6,7,3,65,8,66,110,95,9,3,111,96,8:2,112,74,8:2,67,77,8,113,119," +
"10,11,12,13,14,15,16,17,18,19,20,21,9:2,8,22,8,23,-1:49,24,-1:48,25,-1:48,2" +
"6,-1:50,8:4,-1:2,8:13,-1:14,8:3,97,-1:14,9,-1:9,9,-1:16,9:2,-1:5,32,-1:29,3" +
"3,-1:25,68:4,-1:2,68:13,-1:14,68:3,97,-1:46,23,-1:10,8:4,-1:2,8:6,122,8:6,-" +
"1:14,8:3,97,1,64,3,70,73,75:2,3:2,65,75:4,78,3,75:8,78,75:4,3:2,75,3:5,18,1" +
"9,75:2,9,78,75:4,-1,51:6,-1:3,51:4,-1,51:32,-1:7,56,-1,57,58,59,60,61,62,-1" +
":32,1,51:2,71,51:3,52,53,54,51:4,55,51:9,76,51:16,76:2,51:4,-1:13,27,-1:43," +
"8:4,-1:2,8:2,114,8:2,28,8:7,-1:14,8:3,97,-1:10,8:4,9,-1,8:8,67,8:4,-1:12,9:" +
"2,8:3,97,-1:10,68:4,-1:2,68:13,-1:14,68:4,-1:3,75,25,75:2,-1:3,75:5,-1,75:1" +
"3,-1:2,75,-1:7,75:2,-1,75:5,-1,51:3,69,51:2,-1:3,51:4,-1,51:32,1,64,3,4,3:2" +
",6,3:2,65,8,66,110,95,9,3,111,96,8:2,112,74,8:2,67,77,8,113,119,3:8,18,19,2" +
"0,21,9:2,8,22,8,23,-1:6,50,-1:50,8,29,8,30,-1:2,8:3,120,8:9,-1:14,8:3,97,-1" +
":3,75,-1,75:2,-1:3,75:5,-1,75:13,-1:2,75,-1:7,75:2,-1,75:5,-1,51:6,-1:3,51:" +
"4,9,51:9,76,51:16,76:2,51:4,-1:10,8,31,8:2,-1:2,8:13,-1:14,8:3,97,-1:3,75,-" +
"1,75:2,-1:3,75:4,78,-1,75:8,78,75:4,-1:2,75,-1:7,75:2,9,78,75:4,-1:10,8:4,-" +
"1:2,8:12,34,-1:14,8:3,97,-1:10,8:2,35,8,-1:2,8:13,-1:14,8:3,97,-1:10,8:2,36" +
",8,-1:2,8:13,-1:14,8:3,97,-1:10,82:4,-1:2,82:13,-1:14,82:3,-1:11,8:3,37,-1:" +
"2,8:13,-1:14,8:3,97,-1:10,8:4,-1:2,8:4,38,8:8,-1:14,8:3,97,-1:10,8:4,-1:2,8" +
":4,39,8:8,-1:14,8:3,97,-1:10,8:4,-1:2,8:11,40,8,-1:14,8:3,97,-1:10,8:4,-1:2" +
",8:4,41,8:8,-1:14,8:3,97,-1:10,8:4,-1:2,42,8:12,-1:14,8:3,97,-1:10,8:4,-1:2" +
",8,43,8:11,-1:14,8:3,97,-1:10,8:4,-1:2,8:4,44,8:8,-1:14,8:3,97,-1:10,8:4,-1" +
":2,8:3,45,8:9,-1:14,8:3,97,-1:10,8:4,-1:2,8:4,46,8:8,-1:14,8:3,97,-1:10,8:4" +
",-1:2,8:10,47,8:2,-1:14,8:3,97,-1:10,8:4,-1:2,8:3,48,8:9,-1:14,8:3,97,-1:10" +
",8:4,-1:2,8:4,79,8:4,80,8:3,-1:14,8:3,97,-1:10,8:4,-1:2,8:4,81,8:4,101,8:3," +
"-1:14,8:3,97,-1:10,82:4,-1:2,82:13,-1:14,82:3,97,-1:10,8:4,-1:2,8:4,83,8:8," +
"-1:14,8:3,97,-1:10,8:4,-1:2,8:13,-1:14,84,8:2,97,-1:10,8:4,-1:2,8:3,85,8:9," +
"-1:14,8:3,97,-1:10,8:4,-1:2,8:9,86,8:3,-1:14,8:3,97,-1:10,8:4,-1:2,8:3,87,8" +
":9,-1:14,8:3,97,-1:10,8:4,-1:2,8:2,88,8:10,-1:14,8:3,97,-1:10,8:4,-1:2,8:9," +
"89,8:3,-1:14,8:3,97,-1:10,8:4,-1:2,8:3,90,8:9,-1:14,8:3,97,-1:10,8:4,-1:2,8" +
":3,91,8:9,-1:14,8:3,97,-1:10,8:4,-1:2,8,92,8:11,-1:14,8:3,97,-1:10,8:4,-1:2" +
",8:5,93,8:7,-1:14,8:3,97,-1:10,8:2,94,8,-1:2,8:13,-1:14,8:3,97,-1:10,8:4,-1" +
":2,8:6,98,99,8:5,-1:14,8:3,97,-1:10,8:4,-1:2,8,115,100,8:10,-1:14,8:3,97,-1" +
":10,8:4,-1:2,8,102,8,103,8:9,-1:14,8:3,97,-1:10,8:4,-1:2,8:9,104,8:3,-1:14," +
"8:3,97,-1:10,8:4,-1:2,8,105,8:11,-1:14,8:3,97,-1:10,8:4,-1:2,8:2,106,8:10,-" +
"1:14,8:3,97,-1:10,8:4,-1:2,8:5,107,8:7,-1:14,8:3,97,-1:10,8:4,-1:2,8:9,108," +
"8:3,-1:14,8:3,97,-1:10,8:4,-1:2,8:5,109,8:7,-1:14,8:3,97,-1:10,8:4,-1:2,8:6" +
",116,8:6,-1:14,8:3,97,-1:10,8:4,-1:2,8:8,117,8:4,-1:14,8:3,97,-1:10,8:4,-1:" +
"2,8:7,118,8:5,-1:14,8:3,97,-1:10,8:4,-1:2,8:4,121,8:8,-1:14,8:3,97");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(yy_lexical_state) {
        case YYINITIAL:
            /* nothing special to do in the initial state */
            break;
        case COMMENT:
            yybegin(YYINITIAL);
            return new Symbol(TokenConstants.ERROR, "EOF in comment");
        case STRING:
            yybegin(YYINITIAL);
            return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
  return new Symbol(TokenConstants.EQ);
}
					case -3:
						break;
					case 3:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
  return new Symbol(TokenConstants.ERROR, yytext());
}
					case -4:
						break;
					case 4:
						{
  return new Symbol(TokenConstants.LPAREN);
}
					case -5:
						break;
					case 5:
						{
  return new Symbol(TokenConstants.MULT);
}
					case -6:
						break;
					case 6:
						{
  return new Symbol(TokenConstants.RPAREN);
}
					case -7:
						break;
					case 7:
						{
  string_buf.setLength(0);
  foundNullChar = false;
  yybegin(STRING);
}
					case -8:
						break;
					case 8:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -9:
						break;
					case 9:
						{}
					case -10:
						break;
					case 10:
						{
  return new Symbol(TokenConstants.DIV);
}
					case -11:
						break;
					case 11:
						{
  return new Symbol(TokenConstants.PLUS);
}
					case -12:
						break;
					case 12:
						{
  return new Symbol(TokenConstants.MINUS);
}
					case -13:
						break;
					case 13:
						{
  return new Symbol(TokenConstants.NEG);
}
					case -14:
						break;
					case 14:
						{
  return new Symbol(TokenConstants.LT);
}
					case -15:
						break;
					case 15:
						{
  return new Symbol(TokenConstants.LBRACE);
}
					case -16:
						break;
					case 16:
						{
  return new Symbol(TokenConstants.RBRACE);
}
					case -17:
						break;
					case 17:
						{
  return new Symbol(TokenConstants.AT);
}
					case -18:
						break;
					case 18:
						{
  return new Symbol(TokenConstants.SEMI);
}
					case -19:
						break;
					case 19:
						{
  return new Symbol(TokenConstants.COLON);
}
					case -20:
						break;
					case 20:
						{
  return new Symbol(TokenConstants.DOT);
}
					case -21:
						break;
					case 21:
						{
  return new Symbol(TokenConstants.COMMA);
}
					case -22:
						break;
					case 22:
						{
  return new Symbol(TokenConstants.TYPEID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
            }
					case -23:
						break;
					case 23:
						{
  return new Symbol(TokenConstants.INT_CONST, new IntSymbol(yytext(), yytext().length(), yytext().hashCode())); 
         }
					case -24:
						break;
					case 24:
						{ /* Sample lexical rule for "=>" arrow.
                         Further lexical rules should be defined
                         here, after the last %% separator */
  return new Symbol(TokenConstants.DARROW); }
					case -25:
						break;
					case 25:
						{
  yybegin(COMMENT);
}
					case -26:
						break;
					case 26:
						{
  return new Symbol(TokenConstants.ERROR, yytext());
}
					case -27:
						break;
					case 27:
						{
  curr_lineno++;
}
					case -28:
						break;
					case 28:
						{
  return new Symbol(TokenConstants.FI);
}
					case -29:
						break;
					case 29:
						{
  return new Symbol(TokenConstants.IF);
}
					case -30:
						break;
					case 30:
						{
  return new Symbol(TokenConstants.IN);
}
					case -31:
						break;
					case 31:
						{
  return new Symbol(TokenConstants.OF);
}
					case -32:
						break;
					case 32:
						{
  return new Symbol(TokenConstants.LE);
}
					case -33:
						break;
					case 33:
						{
  return new Symbol(TokenConstants.ASSIGN);
}
					case -34:
						break;
					case 34:
						{
  return new Symbol(TokenConstants.NEW);
}
					case -35:
						break;
					case 35:
						{
  return new Symbol(TokenConstants.NOT);
}
					case -36:
						break;
					case 36:
						{
  return new Symbol(TokenConstants.LET);
}
					case -37:
						break;
					case 37:
						{
  return new Symbol(TokenConstants.THEN);
}
					case -38:
						break;
					case 38:
						{
  return new Symbol(TokenConstants.BOOL_CONST, "true");
}
					case -39:
						break;
					case 39:
						{
  return new Symbol(TokenConstants.CASE);
}
					case -40:
						break;
					case 40:
						{
  return new Symbol(TokenConstants.LOOP);
}
					case -41:
						break;
					case 41:
						{
  return new Symbol(TokenConstants.ELSE);
}
					case -42:
						break;
					case 42:
						{
  return new Symbol(TokenConstants.ESAC);
}
					case -43:
						break;
					case 43:
						{
  return new Symbol(TokenConstants.POOL);
}
					case -44:
						break;
					case 44:
						{
  return new Symbol(TokenConstants.BOOL_CONST, "false");
}
					case -45:
						break;
					case 45:
						{
  return new Symbol(TokenConstants.CLASS); 
}
					case -46:
						break;
					case 46:
						{
  return new Symbol(TokenConstants.WHILE);
}
					case -47:
						break;
					case 47:
						{
  return new Symbol(TokenConstants.ISVOID);
}
					case -48:
						break;
					case 48:
						{
  return new Symbol(TokenConstants.INHERITS);
}
					case -49:
						break;
					case 49:
						{}
					case -50:
						break;
					case 50:
						{
  yybegin(YYINITIAL);
}
					case -51:
						break;
					case 51:
						{
    string_buf.append(yytext());
}
					case -52:
						break;
					case 52:
						{
    yybegin(YYINITIAL);
    String s = string_buf.toString();
    if (foundNullChar) {
        return new Symbol(TokenConstants.ERROR, "String contains null character");
    } else if (s.length() >= MAX_STR_CONST) {
        return new Symbol(TokenConstants.ERROR, "String constant too long");
    } else {
        return new Symbol(TokenConstants.STR_CONST, 
            new StringSymbol(s, s.length(), s.hashCode()));
    }
}
					case -53:
						break;
					case 53:
						{
    foundNullChar = true;
}
					case -54:
						break;
					case 54:
						{
}
					case -55:
						break;
					case 55:
						{
    string_buf.setLength(0);
    foundNullChar = false;
    yybegin(YYINITIAL);
    return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
}
					case -56:
						break;
					case 56:
						{
    string_buf.append("\"");
}
					case -57:
						break;
					case 57:
						{
    string_buf.append("\\");
}
					case -58:
						break;
					case 58:
						{
    string_buf.append("\b");
}
					case -59:
						break;
					case 59:
						{
    string_buf.append("\f");
}
					case -60:
						break;
					case 60:
						{
    string_buf.append("\t");
}
					case -61:
						break;
					case 61:
						{
    string_buf.append("\n");
}
					case -62:
						break;
					case 62:
						{
    string_buf.append("\n");
}
					case -63:
						break;
					case 64:
						{
  return new Symbol(TokenConstants.EQ);
}
					case -64:
						break;
					case 65:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
  return new Symbol(TokenConstants.ERROR, yytext());
}
					case -65:
						break;
					case 66:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -66:
						break;
					case 67:
						{}
					case -67:
						break;
					case 68:
						{
  return new Symbol(TokenConstants.TYPEID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
            }
					case -68:
						break;
					case 69:
						{
  yybegin(COMMENT);
}
					case -69:
						break;
					case 70:
						{}
					case -70:
						break;
					case 71:
						{
    string_buf.append(yytext());
}
					case -71:
						break;
					case 73:
						{ /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
  return new Symbol(TokenConstants.ERROR, yytext());
}
					case -72:
						break;
					case 74:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -73:
						break;
					case 75:
						{}
					case -74:
						break;
					case 76:
						{
    string_buf.append(yytext());
}
					case -75:
						break;
					case 77:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -76:
						break;
					case 78:
						{}
					case -77:
						break;
					case 79:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -78:
						break;
					case 80:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -79:
						break;
					case 81:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -80:
						break;
					case 82:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -81:
						break;
					case 83:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -82:
						break;
					case 84:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -83:
						break;
					case 85:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -84:
						break;
					case 86:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -85:
						break;
					case 87:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -86:
						break;
					case 88:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -87:
						break;
					case 89:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -88:
						break;
					case 90:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -89:
						break;
					case 91:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -90:
						break;
					case 92:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -91:
						break;
					case 93:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -92:
						break;
					case 94:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -93:
						break;
					case 95:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -94:
						break;
					case 96:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -95:
						break;
					case 97:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -96:
						break;
					case 98:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -97:
						break;
					case 99:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -98:
						break;
					case 100:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -99:
						break;
					case 101:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -100:
						break;
					case 102:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -101:
						break;
					case 103:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -102:
						break;
					case 104:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -103:
						break;
					case 105:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -104:
						break;
					case 106:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -105:
						break;
					case 107:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -106:
						break;
					case 108:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -107:
						break;
					case 109:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -108:
						break;
					case 110:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -109:
						break;
					case 111:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -110:
						break;
					case 112:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -111:
						break;
					case 113:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -112:
						break;
					case 114:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -113:
						break;
					case 115:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -114:
						break;
					case 116:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -115:
						break;
					case 117:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -116:
						break;
					case 118:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -117:
						break;
					case 119:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -118:
						break;
					case 120:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -119:
						break;
					case 121:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -120:
						break;
					case 122:
						{
  return new Symbol(TokenConstants.OBJECTID, new StringSymbol(yytext(), yytext().length(), yytext().hashCode())); 
                }
					case -121:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
