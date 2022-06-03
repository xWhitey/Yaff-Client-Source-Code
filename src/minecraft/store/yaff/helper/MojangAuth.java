package store.yaff.helper;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.Objects;

public class MojangAuth {
    public static final Minecraft mc = Minecraft.getMinecraft();

    protected static final YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
    protected static final YggdrasilUserAuthentication yggdrasilUserAuthentication = (YggdrasilUserAuthentication) yggdrasilAuthenticationService.createUserAuthentication(Agent.MINECRAFT);

    protected String username;
    protected String password;

    public MojangAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MojangAuth(String username) {
        this.username = username;
        this.password = "";
    }

    public static Session auth(String username, String password) {
        yggdrasilUserAuthentication.setUsername(username);
        yggdrasilUserAuthentication.setPassword(password);
        try {
            yggdrasilUserAuthentication.logIn();
            return new Session(yggdrasilUserAuthentication.getSelectedProfile().getName(), yggdrasilUserAuthentication.getSelectedProfile().getId().toString(), yggdrasilUserAuthentication.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException authenticationException) {
            return null;
        }
    }

    public static String getName() {
        return yggdrasilUserAuthentication.getSelectedProfile().getName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void process() {
        if (this.password.isEmpty()) {
            mc.setSession(new Session(this.username, "", "", "mojang"));
        } else {
            Session session = auth(username, password);
            mc.setSession(Objects.requireNonNullElseGet(session, () -> new Session(this.username, "", "", "mojang")));
        }
    }

}
