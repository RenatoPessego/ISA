package si.um.feri.aiv.jms;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class test {
    public static void main(String[] args) {
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            env.put(Context.SECURITY_PRINCIPAL, "test"); // <--- Substitui se usares outro user
            env.put(Context.SECURITY_CREDENTIALS, "test"); // <--- Idem

            Context ctx = new InitialContext(env);

            // Faz lookup do recurso corretamente exposto
            Object result = ctx.lookup("jms/queue/DoctorQueue"); // <--- Este nome tem que bater com o 'entry' no standalone.xml

            System.out.println("✅ Encontrado: " + result);

        } catch (NamingException e) {
            System.out.println("❌ Erro ao fazer lookup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
