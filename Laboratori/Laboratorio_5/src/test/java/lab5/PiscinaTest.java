package lab5;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PiscinaTest {
    @Test
    public void testConcorrenti() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Piscina p = new Piscina(1, 1);
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            Thread t = new Thread(() -> {
                try {
                    p.cliente_no_deadlook(id);
                } catch (InterruptedException ignored) {
                }
            });
            // t.setDaemon(true); // ← togliamo il daemon
            t.start();
            threads.add(t);
        }

        // aspettiamo tutti i thread (o almeno il primo)
        for (Thread t : threads) {
            t.join();
        }

        String log = out.toString();
        assertTrue(log.matches("(?s).*Cliente \\d si cambia.*"));
        assertTrue(log.matches("(?s).*Cliente \\d nuota.*"));
        assertTrue(log.matches("(?s).*Cliente \\d esce.*"));
    }
}