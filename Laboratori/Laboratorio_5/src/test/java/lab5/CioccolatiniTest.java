package lab5;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CioccolatiniTest {
    @Test
    public void testProdCons() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Cioccolatini c = new Cioccolatini();
        Thread p = new Thread(c.new Pasticciere());
        p.setDaemon(true);
        p.start();
        for (int i = 1; i <= 3; i++) {
            Thread m = new Thread(c.new Mangiatore(i));
            m.setDaemon(true);
            m.start();
        }
        Thread.sleep(2000);
        String log = out.toString();

        assertTrue(log.contains("Pasticciere riempie la scatola"));
        assertTrue(log.contains("Mangiatore 1 mangia"));
        assertTrue(log.contains("Mangiatore 2 mangia"));
    }
}