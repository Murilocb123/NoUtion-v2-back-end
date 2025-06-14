package br.com.murilocb123.noutionbackend.infra.security;

import java.util.UUID;

public class UserContextHolder {

    private static final ThreadLocal<UUID> nCurrentUserId = new ThreadLocal<>();

    public static void setUserId(UUID nUserId) {
        nCurrentUserId.set(nUserId);
    }

    public static UUID getUserId() {
        return nCurrentUserId.get();
    }

    public static void clear() {
        nCurrentUserId.remove();
    }
}
