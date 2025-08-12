import java.io.IOException;
import java.util.Arrays;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

public class Main {
    public static void main(String[] args) throws IOException{

        // print 'hello world' in red color
        // System.out.println("\033[31mHello, World!\033[0m");

        while(true){
            int key = System.in.read();
            System.out.println( (char) key + " (" + key + ")");
        }

        //     int tcgetattr(int fd, struct termios *termios_p);
        //    int tcsetattr(int fd, int optional_actions,
        //                  const struct termios *termios_p);
    }
}

interface LibC extends Library {

    int SYSTEM_OUT_FD = 0;

    int ISIG = 1, ICANON = 2, ECHO = 10, TCSAFLUSH = 2, IXON = 2, ICRNL =400, IEXTEN = 100000, OPOST = 1,VMIN = 6, VTIME = 5, TIOCGWINSZ = 0x5413;

    LibC INSTANCE = Native.load("c", LibC.class);


    @Structure.FieldOrder(value = {"c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc"})
    class Termios extends Structure {
        public int NCCS = 19;

        public int c_iflag;      /* input modes */
        public int c_oflag;      /* output modes */
        public int c_cflag;      /* control modes */
        public int c_lflag;      /* local modes */
        public char[] c_cc = new char[NCCS];   /* special characters */

        public Termios(){

        }

        public static Termios of(Termios t){
            Termios copy = new Termios();
            copy.c_iflag = t.c_iflag;      /* input modes */
            copy.c_oflag = t.c_oflag;      /* output modes */
            copy.c_cflag = t.c_cflag;      /* control modes */
            copy.c_lflag = t.c_lflag;      /* local modes */
            copy.c_cc = t.c_cc.clone();    /* special characters */

            return copy;
        }

        public String toString(){
            return "Termios{" +
                    "c_iflag=" + c_iflag +
                    ", c_oflag=" + c_oflag +
                    ", c_cflag=" + c_cflag +
                    ", c_lflag=" + c_lflag +
                    ", c_cc=" + Arrays.toString(c_cc) +
                    '}';
        }

        // @Override
        // protected List<String> getFieldOrder() {
        //     return Arrays.asList("NCCS", "c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc");
        // }


    }

    int tcgetattr(int fd, Termios termios_p);
    int tcsetattr(int fd, int optional_actions, Termios termios_p);
}
